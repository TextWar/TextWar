package cn.qqtextwar.entity.points

import groovy.transform.ToString;

@ToString
class SkillPoints {

    private static final Map<String,SkillPoints> SKILLS = [
            big_storm : new SkillPoints(
                    "big_storm",
                    new SkillPoint(10,100,0,"_level1",10),
                    new SkillPoint(20,200,1,"_level2",10)
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
        }
    }

    SkillPoint getSkillByLevel(int level){ points[level] }

    static SkillPoints getSkillPointsByName(String name){
        SKILLS.get(name)
    }
}
