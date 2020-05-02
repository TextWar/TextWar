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
                    [
                            SkillType.MAGICAL_ATTACK,
                            SkillType.RANGE_ATTACK

                    ],
                    new SkillPoint(10,100,0,"_level1",10){
                        @Override
                        String doSkill(Entity self,Entity entity,SkillPoint point,GameMap map) {
                            return ""
                        }
                    },
                    new SkillPoint(20,200,1,"_level2",10){
                        @Override
                        String doSkill(Entity self,Entity entity,SkillPoint point,GameMap map) {
                            return ""
                        }
                    }
            )
    ]

    private String name

    private List<SkillType> types

    private Map<Integer,SkillPoint> points

    SkillPoints(String name, List<SkillType> types,SkillPoint... points) {
        this.name = name
        this.types = types
        this.points = [:]
        points.each {
            x->
                points[x.level] = x
                x.setSkillPoints(this)
        }
    }

    List<SkillType> getTypes() {
        return types
    }

    SkillPoint getSkillByLevel(int level){ points[level] }

    static SkillPoints getSkillPointsByName(String name){
        SKILLS.get(name)
    }

    enum SkillType{

        PHYSICAL_ATTACK,

        MAGICAL_ATTACK,

        SHORT_RANGE_ATTACK,

        RANGE_ATTACK,

        NONE_ATTACK

    }

}
