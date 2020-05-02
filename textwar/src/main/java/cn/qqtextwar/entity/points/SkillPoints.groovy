package cn.qqtextwar.entity.points

import cn.qqtextwar.GameMap
import cn.qqtextwar.entity.Entity
import groovy.transform.ToString

/**
 * 这个用于容纳多个技能点，根据所有者等级使用相应等级的技能点
 */
@ToString
class SkillPoints {

    private static final Map<String,SkillPoints> SKILLS = [
            big_storm : new SkillPoints(
                    "big_storm",
                    new SkillPoint(10,100,0,"_level1",10){
                        @Override
                        String doSkill(Entity entity,SkillPoint point,GameMap map) {
                            return ""
                        }
                    },
                    new SkillPoint(20,200,1,"_level2",10){
                        @Override
                        String doSkill(Entity entity,SkillPoint point,GameMap map) {
                            return ""
                        }
                    }
            )
    ]

    private String name

    private Map<Integer,SkillPoint> points

    SkillPoints(String name, SkillPoint... points) {
        this.name = name
        this.points = [:]
        points.each {
            x->
                points[x.level] = x
                x.setSkillPoints(this)
        }
    }

    SkillPoint getSkillByLevel(int level){ points[level] }

    static SkillPoints getSkillPointsByName(String name){
        SKILLS.get(name)
    }

}
