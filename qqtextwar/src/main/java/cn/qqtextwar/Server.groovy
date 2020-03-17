package cn.qqtextwar

import cn.qqtextwar.dsl.ServerConfigParser
import cn.qqtextwar.entity.Entity
import cn.qqtextwar.entity.Freaks
import cn.qqtextwar.entity.Freaks.FreaksEnum
import cn.qqtextwar.entity.GameMap
import cn.qqtextwar.entity.Player
import cn.qqtextwar.entity.Vector
import cn.qqtextwar.log.ServerLogger
import groovy.transform.CompileStatic

@CompileStatic
class Server {

    static final int NO = -1

    static final int START = 0

    static final int GAMEING = 1

    static final int END = 2

    static final String UPDATE_MAP = "update_map"

    static final String GET_MAP = "get_map"

    static final String MAIN_CONFIG = "server.cfg"

    private ServerLogger logger = new ServerLogger()

    private File baseFile

    private ServerConfigParser parser

    private RPCRunner rpcRunner



    private int round //记录回合

    private int state

    //qq - 玩家 qq唯一
    private Map<Long, Player> players = new HashMap<>()

    //怪物 - uuid唯一
    private Map<UUID, Entity> freaksMap = new HashMap<>()

    Server(){
        this.baseFile = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile()
        File main = new File(baseFile,MAIN_CONFIG)
        if(!main.exists()){
            main.createNewFile()
        }
        this.parser = new ServerConfigParser(main)
        this.round = 0
        this.state = NO
    }

    void start(){
        this.logger.info("server is starting...")
        String ip = this.parser.getHeadValue("server.ip")
        String port = this.parser.getHeadValue("server.port")
        this.rpcRunner = new RPCRunner()
        rpcRunner.start(ip,port)
    }

    Player createPlayer(long qq,GameMap map){
        if(!players.containsKey(qq)){
            Vector vector = map.randomVector()
            Player player = new Player(qq,vector)
            players[qq] = player
            return player
        }else{
            return players[qq]
        }
    }

    Freaks createFreaks(GameMap map,FreaksEnum freaksId){
        Freaks freaks = new Freaks(map.randomVector(),freaksId.mapValue)
        freaksMap.put(freaks.uuid,freaks)
        return freaks
    }


    String updateMap(String image,List<List<Integer>> map){
        if(rpcRunner){
            return rpcRunner.execute(UPDATE_MAP,String.class,image,map)
        }
        return ""
    }

    GameMap getMap(){
        if(rpcRunner){
            return new GameMap("",rpcRunner.execute(GET_MAP,List<List<Integer>>.class))
        }
        return null
    }

}
