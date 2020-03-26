package cn.qqtextwar.tasks;

import cn.qqtextwar.entity.Mob;

import java.util.Map;

public class KillMobTask extends Task {

    private Map<Class<? extends Mob>,Integer> mobs;

    public KillMobTask(Map<Class<? extends Mob>, Integer> mobs,Award award) {
        super(award);
        this.mobs = mobs;
    }

    public Map<Class<? extends Mob>, Integer> getMobs() {
        return mobs;
    }
}
