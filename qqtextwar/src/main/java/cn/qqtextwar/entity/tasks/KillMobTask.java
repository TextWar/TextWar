package cn.qqtextwar.entity.tasks;

import cn.qqtextwar.entity.Mob;

import java.util.Map;

public class KillMobTask extends Task {

    private Map<Mob.MobEnum,Integer> mobs;

    public KillMobTask(Map<Mob.MobEnum, Integer> mobs,Award award) {
        super(award);
        this.mobs = mobs;
    }

    public Map<Mob.MobEnum, Integer> getMobs() {
        return mobs;
    }
}
