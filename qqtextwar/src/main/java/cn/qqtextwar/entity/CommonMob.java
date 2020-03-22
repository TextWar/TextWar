package cn.qqtextwar.entity;

import cn.qqtextwar.entity.points.SkillPoint;
import cn.qqtextwar.entity.points.SkillPoints;
import cn.qqtextwar.math.Vector;

/**
 * 这里主要是无害的生物，如Slime(史莱姆)之类的生物
 * @author MagicLu550 @卢昶存
 */
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
