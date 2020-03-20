package cn.qqtextwar.entity;

import cn.qqtextwar.entity.points.SkillPoint;
import cn.qqtextwar.entity.points.SkillPoints;
import cn.qqtextwar.math.Vector;

public abstract class CommonMob extends Mob implements Registered{

    public CommonMob(Vector vector, int diff,int id) {
        super(vector, id, false,diff);
    }

    @Override
    public SkillPoint doSkill(SkillPoints points) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addSkill(SkillPoints points) {
        throw new UnsupportedOperationException();
    }
}
