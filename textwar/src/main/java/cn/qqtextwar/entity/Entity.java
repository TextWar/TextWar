package cn.qqtextwar.entity;

import cn.qqtextwar.GameMap;
import cn.qqtextwar.blocks.Block;
import cn.qqtextwar.entity.player.Movement;
import cn.qqtextwar.entity.points.SkillPoint;
import cn.qqtextwar.entity.points.SkillPoints;
import cn.qqtextwar.ex.MoveException;
import cn.qqtextwar.math.Vector;
import cn.qqtextwar.utils.Utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 这里是所有实体的基类，只要是游戏里活着的东西都继承
 * 它
 *
 * @author gunveda
 * @author MagicLu550 @ 卢昶存
 */
public abstract class Entity {

    private Block onFoot;

    private long id;

    private UUID uuid;

    private double healthPoints;

    private double manaPoints;

    private int level;

    //攻击力
    protected int aggressivity;

    protected Random random;
    
    protected Vector vector;

    private Map<SkillPoints,Integer> levels;

    private Map<SkillPoints, LocalDateTime> useDates;


    public Entity(Vector vector, long id) {
        uuid = UUID.randomUUID();
        this.vector = vector;
        this.id = id;
        this.levels = new HashMap<>();
        this.useDates = new ConcurrentHashMap<>();
        this.aggressivity = initAggressivity(getLevel());
    }

    public Entity(Vector vector, long id, double healthPoints, double manaPoints) {
        this.uuid = UUID.randomUUID();
        this.vector = vector;
        this.id = id;
        this.healthPoints = healthPoints;
        this.manaPoints = manaPoints;
        this.random = new Random();
    }

    public Entity addInto(GameMap map){
        map.addEntity(this);
        return this;
    }

    public <T extends Entity> T as(Class<T> type){
        return type.cast(this);
    }

    public synchronized int random(int round){
        return random.nextInt(round);
    }

    /**
     * 这个负责执行技能，只有实现了Skillable接口的生物才能使用
     * 这里允许使用的为玩家和有害的生物
     */
    public SkillPoint doSkill(SkillPoints points){
        LocalDateTime time = useDates.get(points);
        LocalDateTime now = LocalDateTime.now();
        if(time == null){
            useDates.put(points,now);
            SkillPoint point = getSkillByLevel(points);
            setManaPoints(getManaPoints() - point.getBuff());
            return point;
        }else{
            Duration duration = Duration.between(time,now);
            if(duration.toMillis()/1000 >= getSkillByLevel(points).getTime()){
                useDates.put(points,now);
                SkillPoint point = getSkillByLevel(points);
                setManaPoints(getManaPoints() - point.getBuff());
                return point;
            }else{
                return null;
            }
        }
    }

    public void setOnFoot(Block onFoot) {
        this.onFoot = onFoot;
    }

    public Block getOnFoot() {
        return onFoot;
    }

    public String hit(Entity entity, GameMap map){
        Vector vector = this.getVector().reduce(entity.getVector());
        if(vector.mod() <= Math.sqrt(2) && this.haveObstacle(entity,map)){
            entity.delHealthPoints(aggressivity);
            return "Success -- HIT";
        }
        return "Insufficient or obstructed";
    }

    //TODO 技能攻击
    public String skill(SkillPoints points,GameMap map,Entity entity){
        SkillPoint point = getSkillByLevel(points);
        return point.doSkill(this,entity,point,map);
    }


    //是否两个个体之间有障碍,返回与x轴的夹角tan值
    //如果无法看见，返回-1
    //*A**********
    //**C*********
    //**CB****B***
    public boolean haveObstacle(Entity entity,GameMap map){
        //水平，斜面
        Vector vector = entity.getVector();
        Vector dir = vector.reduce(this.getVector()).toPositive();
        Vector max = Utils.maxX(vector,getVector());
        Vector min = Utils.minX(vector,getVector());
        double tan = dir.getDirection();
        double xl = max.getX() - min.getX();
        if(xl != 0){
            double startX = min.getX();
            for(int i = 0;i<=xl;i++){
                Block block = map.getBlocks().get(new Vector(i+startX,(int)(startX+i*tan)));
                if(!block.isCross()){
                    return false;
                }
            }
        }else{
            int mx = (int)Math.max(vector.getY(),this.getVector().getY());
            for(int i = (int)Math.min(vector.getY(),this.getVector().getY());i<mx;i++){
                Block block = map.getBlocks().get(new Vector(max.getX(),i));
                if(!block.isCross()){
                    return false;
                }
            }
        }
        return true;
    }



    /**
     * 这里可以对相应的技能升级，只要传入SkillPoints对象即可
     */
    private void upLevel(SkillPoints points){
        levels.put(points,levels.get(points)+1);
    }


    /**
     * 可以添加技能
     */
    public void addSkill(SkillPoints points){
        this.levels.put(points,0);
    }

    /**
     * 获得生物相相应等级的特定技能
     */
    private SkillPoint getSkillByLevel(SkillPoints points){
        return points.getSkillByLevel(levels.get(points));
    }


    /**
     * 每个实体类型都有自己的特有id
     * mob > 1000
     * qq > 10000
     * dynamic block > 2000
     * npc > 3000
     * 映射字符 *
     * *name 为可穿越方块
     */
    public long getId() {
        return id;
    }

    /**
     * 每个实体的基准id，用于辨别特定的实体
     */
    public UUID getUuid() {
        return uuid;
    }

    public double getX() {
        return vector.getX();
    }

    public double getY() {
        return vector.getY();
    }

    public synchronized Movement move(Vector vector, GameMap map) throws MoveException{
        Vector v = this.vector.add(vector);
        if(v.getX()<0){
            throw new MoveException("out of bounds of the map");
        }
        if(v.getY()<0){
            throw new MoveException("out of bounds of the map");
        }
        Block block = map.getBlocks().get(v);
        if(block.isCross()){
            if(map.getMapData()[(int)v.getY()][(int)v.getX()] != block.getId()){
                throw new MoveException("You could not cross this place,there is a people");
            }
            this.vector = v;
        }else{
            throw new MoveException("You could not cross this block id: "+block.getId());
        }
        return new Movement(this,map);
    }

    public Movement up(GameMap map){
        return move(new Vector(0,-1),map);
    }

    public Movement down(GameMap map){
        return move(new Vector(0,1),map);
    }

    public Movement left(GameMap map){
        return move(new Vector(-1,0),map);
    }

    public void update(GameMap map){
        map.updateEntity(this);
    }

    public Movement right(GameMap map){
        return move(new Vector(1,0),map);
    }

    /**
     * 获得向量，每个实体的坐标都是默认为从原点开始的向量
     * 原点为左上角第一个方格，从上到下，从左到右，坐标
     * 量增加，均为正
     */
    public Vector getVector() {
        return vector;
    }

    public double getHealthPoints() {
        return healthPoints;
    }

    public double getManaPoints() {
        return manaPoints;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setId(long id) {
        this.id = id;
    }

    public final void setHealthPoints(double healthPoints) {
        this.healthPoints = healthPoints;
    }

    public final void setManaPoints(double manaPoints) {
        this.manaPoints = manaPoints;
    }

    protected final synchronized void addHealthPoints(double value) {
        this.healthPoints += value;
    }

    protected final synchronized void delHealthPoints(double value) {
        this.healthPoints -= value;
    }

    protected final synchronized void addManaPoints(double value) {
        this.manaPoints += value;
    }

    protected final synchronized void delManaPoints(double value) {
        this.manaPoints -= value;
    }

    public void kill(GameMap map){
        this.healthPoints = 0;
        map.removeEntity(this);
    }

    public abstract int initAggressivity(int level);

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", healthPoints=" + healthPoints +
                ", manaPoints=" + manaPoints +
                ", level=" + level +
                ", random=" + random +
                ", vector=" + vector +
                ", levels=" + levels +
                ", useDates=" + useDates +
                '}';
    }


}
