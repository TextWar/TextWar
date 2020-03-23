package cn.qqtextwar.entity;

import cn.qqtextwar.GameMap;
import cn.qqtextwar.blocks.Block;
import cn.qqtextwar.entity.player.Movement;
import cn.qqtextwar.entity.points.SkillPoint;
import cn.qqtextwar.entity.points.SkillPoints;
import cn.qqtextwar.ex.MoveException;
import cn.qqtextwar.math.Vector;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * 这里是所有实体的基类，只要是游戏里活着的东西都继承
 * 它
 *
 * @author gunveda
 * @author MagicLu550 @ 卢昶存
 */
public abstract class Entity {

    private long id;

    private UUID uuid;

    private double healthPoints;

    private double manaPoints;

    private int level;

    protected Random random;
    
    protected Vector vector;

    private Map<SkillPoints,Integer> levels;

    private Map<SkillPoints, LocalDateTime> useDates;


    public Entity(Vector vector, long id) {
        uuid = UUID.randomUUID();
        this.vector = vector;
        this.id = id;
        this.levels = new HashMap<>();
        this.useDates = new HashMap<>();
    }

    public Entity(Vector vector, long id, double healthPoints, double manaPoints) {
        this.uuid = UUID.randomUUID();
        this.vector = vector;
        this.id = id;
        this.healthPoints = healthPoints;
        this.manaPoints = manaPoints;
        this.random = new Random();
    }

    public synchronized int random(int round){
        return random.nextInt(round);
    }

    /**
     * 这个负责执行技能，只有实现了Skillable接口的生物才能使用
     * 这里允许使用的为玩家和有害的生物
     */
    public synchronized SkillPoint doSkill(SkillPoints points){
        LocalDateTime time = useDates.get(points);
        LocalDateTime now = LocalDateTime.now();
        if(time == null){
            useDates.put(points,now);
            SkillPoint point = getSkillByLevel(points);
            setManaPoints(getManaPoints() - point.getBuff());
            return point;
        }else{
            Duration duration = Duration.between(time,now);
            if(duration.toMinutes()*60 >= getSkillByLevel(points).getTime()){
                useDates.put(points,now);
                SkillPoint point = getSkillByLevel(points);
                setManaPoints(getManaPoints() - point.getBuff());
                return point;
            }else{
                return null;
            }
        }
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

    public int getX() {
        return vector.getX();
    }

    public int getY() {
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
        Block block = map.getBlocks().get(vector);
        if(block!=null){
            if(block.isCross()){
                this.vector = block.getVector();
            }else{
                throw new MoveException("You could not cross this block id: "+block.getId());
            }
        }else{
            this.vector = v;
        }
        return new Movement(this,map);
    }

    public void up(GameMap map){
        move(new Vector(0,-1),map);
    }

    public void down(GameMap map){
        move(new Vector(0,1),map);
    }

    public void left(GameMap map){
        move(new Vector(-1,0),map);
    }

    public void update(GameMap map){
        map.updateEntity(this);
    }

    public void right(GameMap map){
        move(new Vector(1,0),map);
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

    protected final void setHealthPoints(double healthPoints) {
        this.healthPoints = healthPoints;
    }

    protected final void setManaPoints(double manaPoints) {
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


}
