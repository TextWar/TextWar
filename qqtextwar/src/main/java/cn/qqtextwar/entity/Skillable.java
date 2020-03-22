package cn.qqtextwar.entity;

import cn.qqtextwar.entity.points.SkillPoint;
import cn.qqtextwar.entity.points.SkillPoints;

/**
 * 任何能够发动技能的生物都应该实现它，
 * 人类和有害生物可以使用技能
 */
public interface Skillable {

    SkillPoint doSkill(SkillPoints points);

    void addSkill(SkillPoints points);

}
