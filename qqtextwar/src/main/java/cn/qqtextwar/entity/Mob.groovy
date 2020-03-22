package cn.qqtextwar.entity

import cn.qqtextwar.math.Vector
import groovy.transform.CompileStatic
import groovy.transform.Memoized
import groovy.transform.ToString

/**
 * 生物类
 * @author MagicLu550 @ 卢昶存
 */
@CompileStatic
@ToString
abstract class Mob extends Entity{

    private static Map<String,Class<? extends Registered>> mobs = new HashMap<>()

    private boolean harmful

    Mob(Vector vector, long id,boolean harmful,int difficulty) {
        super(vector, id, 0,0)
        this.harmful = harmful
        setLevel(randomLevel(difficulty))
        setHealthPoints(initHealth(getLevel()))
    }

    static void registerMob(Class<? extends Registered> mob){
        mobs.put(mob.getName(),mob)
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

    static Map<String, Class<? extends Registered>> getMobs() {
        return mobs
    }

    abstract double getXp(int level)


}
