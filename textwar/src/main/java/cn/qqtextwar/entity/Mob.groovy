package cn.qqtextwar.entity

import cn.qqtextwar.ProtocolVar
import cn.qqtextwar.annotations.NotImpl
import cn.qqtextwar.ex.IllegalIdException
import cn.qqtextwar.math.Vector
import groovy.transform.CompileStatic
import groovy.transform.Memoized
import groovy.transform.ToString

/**
 * 生物类
 * id > 1000
 * @author MagicLu550 @ 卢昶存
 */
@CompileStatic
@ToString
abstract class Mob extends Entity{

    private static Map<String,Class<? extends Registered>> mobs = new HashMap<>()

    private static Map<Class<? extends Registered>,String> mobImages = new HashMap<>()

    private static Map<Integer,Class<? extends Registered>> idMapping = new HashMap<>()
    private boolean harmful

    Mob(Vector vector, long id,boolean harmful,int difficulty) {
        super(vector, id, 0,0)
        if(!ProtocolVar.MOB_ID.contains(id)){
            throw new IllegalIdException("mob's id is between 1000 and 2000")
        }
        this.harmful = harmful
        setLevel(randomLevel(difficulty))
        setHealthPoints(initHealth(getLevel()))
    }

    static void registerMob(int id,Class<? extends Registered> mob,String image){
        mobs.put(mob.getName(),mob)
        mobImages.put(mob,image)
        idMapping.put(id,mob)
    }

    @Memoized
    static List<Class<? extends Registered>> mobs(){
        return mobs.values().asList()
    }

     boolean isHarmful() {
        return harmful
    }

    abstract int randomLevel(int difficulty)

    abstract double initHealth(int level)

    double getXp(){
        return getXp(getLevel())
    }

    @NotImpl("If you register the image,the image will not work")
    static Map<Class<? extends Registered>, String> getMobImages() {
        return mobImages
    }

    static Map<String, Class<? extends Registered>> getMobs() {
        return mobs
    }

    static Map<Integer, Class<? extends Registered>> getIdMapping() {
        return idMapping
    }

    abstract double getXp(int level)


}
