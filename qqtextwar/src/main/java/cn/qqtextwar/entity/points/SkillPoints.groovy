package cn.qqtextwar.entity.points

import groovy.transform.ToString;

@ToString
enum SkillPoints {

    BIG_STROM("big_strom",
            new SkillPoint(10,100,0,"_level1"),
            new SkillPoint(20,200,1,"_level2")
    );

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

    static SkillPoints getByName(String name){
        return (SkillPoints)SkillPoints.class.getDeclaredFields().findResult{
            SkillPoints points = ((SkillPoints)it.get(null))
            if(points.name == name){
                return points
            }
        }
    }

    SkillPoint getSkillByLevel(int level){ points[level] }
}
