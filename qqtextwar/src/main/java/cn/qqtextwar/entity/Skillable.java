package cn.qqtextwar.entity;

import cn.qqtextwar.entity.points.SkillPoint;
import cn.qqtextwar.entity.points.SkillPoints;

public interface Skillable {

    SkillPoint doSkill(SkillPoints points);

    void addSkill(SkillPoints points);

}
