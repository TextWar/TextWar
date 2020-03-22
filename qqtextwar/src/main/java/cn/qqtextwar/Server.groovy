package cn.qqtextwar

import cn.qqtextwar.dsl.ServerConfigParser
import cn.qqtextwar.entity.Registered
import cn.qqtextwar.entity.impl.SkeletonMan
import cn.qqtextwar.entity.Mob
import cn.qqtextwar.entity.impl.Slime
import cn.qqtextwar.entity.player.Player
import cn.qqtextwar.ex.CloseException
import cn.qqtextwar.ex.ServerException
import cn.qqtextwar.math.Vector
import cn.qqtextwar.log.ServerLogger
import groovy.transform.CompileStatic

import java.util.concurrent.atomic.AtomicInteger

/**
 * TextWar游戏的服务端对象，为单例模式，只能通过
 * {@code Server.start()}和{@code Server.stop()}开启
 * 并且只能开启一次和关闭一次。
 *
 * @author MagicLu550 @ 卢昶存
 */
@CompileStatic
class Server {

    static{
        registerMobs()
    }

    /** 第一次开启时的状态 */
    static final int NO = -1

    /** 游戏开启的状态 */
    static final int START = 0

    /** 游戏进行的状态 */
    static final int GAMEING = 1

    /** 游戏一回合结束的状态 */
    static final int END = 2

    /** 服务端即将关闭的状态 **/
    static final int CLOSED = 3

    /** 从python端更新地图图片的pkey */
    static final String UPDATE_MAP = "update_map"

    /** 从python端获得最新地图图片的pkey */
    static final String GET_MAP = "get_map"

    /** 从python端初始化地图元素图片的pkey，如怪物的图片 */
    static final String UPDATE_PIC = "update_pic"

    /** 游戏难度，目前还没有完成 **/
    private int difficulty //难度，后定

    /** 服务端的日志对象，用于输出日志 */
    private ServerLogger logger = new ServerLogger()

    /** 指向服务端的根目录 */
    private File baseFile

    /** server.cfg的解析器 */
    private ServerConfigParser parser

    /** 负责和rpc服务端交互的客户端，目标为python端 */
    private RPCRunner rpcRunner

    /** 记录回合数量 */
    private AtomicInteger round //记录回合

    /**
     * 记录游戏的状态，这里同时也是用于控制每个线程的进行
     * 如果为CLOSED，线程全部结束
     */
    private AtomicInteger state

    //qq - 玩家 qq唯一
    /** 存储全部玩家的对象，以qq为唯一键值对 */
    private Map<Long, Player> players = new HashMap<>()

    //怪物 - uuid唯一
    /** 记录全部怪物和生物 */
    private Map<UUID, Mob> freaksMap = new HashMap<>()

    /** 用于将指定的resources文件复制到服务端根目录下，方便玩家修改，并存储相应的文件对象 */
    private FileRegister register

    /** 随机数器，不要直接使用，请使用线程安全的random方法*/
    private Random random

    /** 单例服务端，会在start静态方法时创建  */
    private static Server server

    /** 服务端构造方法，请不要直接使用它 */
    private Server(){
        if(!server){
            server = this
        }else{
            throw new ServerException("the server has started!")
        }
        this.baseFile = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile()
        this.register = new FileRegister(this)
        this.register.register()
        this.parser = new ServerConfigParser(register.getConfig(FileRegister.MAIN_CONFIG))
        this.round = new AtomicInteger()
        this.state = new AtomicInteger()
        this.random = new Random()
    }

    //启动的方法组合顺序
    // 1. 开启数据库链接，RPC
    // 2. 获得开局信息
    // 3. 开局结束则重新获得地图，如果还在开局则从数据库获得地图
    // 4. 创建玩家对象
    // 5. 从数据库获得信息初始化玩家，玩家坐标如果是重新开局则重置坐标
    // 6. 创建怪物对象
    // 7. 如果重新开局直接重新创建对象，如果没有结束则从数据库读取对象
    // 8. 游戏开始
    // 9. 游戏结束，初始化全部对象。
    /** 服务端对象的启动方法，请不要直接调用，否则会出现不可预料的错误 */
    private void start0(){
        this.logger.info("server is starting...")
        String ip = this.parser.getHeadValue("server.ip")
        String port = this.parser.getHeadValue("server.port")
        this.rpcRunner = new RPCRunner()
        rpcRunner.start(ip,port)
    }

    /** 服务端只能开启一次 */
    static start(){
        new Server().start0()
    }

    /** 服务端只能关闭一次，同时改变状态使所有线程关闭 */
    static stop(){
        if(server){
            server.close0()
        }else{
            throw new CloseException("You could not stop the server before starting state: -1 - NO")
        }
    }

    /** 服务端只能关闭一次*/
    void close0(){
        if(this.state.get() == CLOSED){
            throw new CloseException("the Server has closed")
        }
        this.logger.info("the server is closing...")
        this.state.compareAndSet(this.state.get(),CLOSED)
        throw new CloseException("the Server has closed state: 3 - CLOSED")
    }

    /** 负责注册全部的怪物，会在createMobs里使用，会根据这个表随机创建怪物 */
    static void registerMobs(){
        Mob.registerMob(Slime.class)
        Mob.registerMob(SkeletonMan.class)
    }

    /** 用于创建玩家对象，首次创建要从数据获得数据 */
    Player createPlayer(long qq,GameMap map){
        if(!players.containsKey(qq)){
            Vector vector = map.randomVector()
            Player player = new Player(vector,qq,100,100,100)
            players[qq] = player
            return player
        }else{
            return players[qq]
        }
    }

    /** 这里通过数据库初始化信息 */
    void initPlayers(){

    }
    /** 同上 */
    void initFreaks(){

    }

    /** 线程安全的随机表，在创建怪物时使用 */
    synchronized int random(int round){
        random.nextInt(round)
    }

    /** 通过地图创建单个怪物 */
    Mob createMob(GameMap map,Class<? extends Registered> clz){
        Registered registered = clz.newInstance(map.randomVector(),difficulty)
        if(registered instanceof Mob){
            Mob mob = registered as Mob
            freaksMap.put(mob.uuid,mob)
            return mob
        }

        return null
    }

    /** n为创建的数量，创建多个怪物，根据怪物表 */
    List<Mob> createRandomMobs(GameMap map,int n){
        List<Mob> mobs = new ArrayList<>()
        (1..n).each {
            int round = random(Mob.getMobs().size())
            mobs.add(createMob(map,Mob.mobs().get(round)))
        }
        return mobs
    }


    /** 向rpc服务端发出指令，以更新图片，传入修改过的map对象 */
    String updateMap(String image,GameMap map){
        if(rpcRunner){
            return rpcRunner.execute(UPDATE_MAP,String.class,image,map.getMapData())
        }
        return ""
    }

    /**获得最新的Map，只有开始第一回合或者下一回合调用  */
    GameMap getMap(String file){
        if(rpcRunner){
            return new GameMap(file,(int[][])rpcRunner.execute(GET_MAP,int[][].class,file))
        }
        return null
    }

    /** 初始化全部怪物的图片，在开启时调用 */
    void updatePicture(int id,String file){
        if(rpcRunner){
            rpcRunner.execute(UPDATE_PIC,id,file)
        }
    }

    File getBaseFile() {
        return baseFile
    }

    ServerConfigParser getParser() {
        return parser
    }

    static Server getServer(){
        return server
    }

}
