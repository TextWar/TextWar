package cn.qqtextwar.entity;

import cn.qqtextwar.entity.points.SkillPoint;
import cn.qqtextwar.entity.points.SkillPoints;
import cn.qqtextwar.math.Vector;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Player extends Entity{

    private Map<SkillPoints,Integer> levels;

    private Map<SkillPoints, LocalDateTime> useDates;

    public Player(long qq, Vector vector) {
        super(vector,qq);
        this.levels = new HashMap<>();
        this.useDates = new HashMap<>();
    }

    private void upLevel(SkillPoints points){
        levels.put(points,levels.get(points)+1);
    }

    public SkillPoint doSkill(SkillPoints points){
        LocalDateTime time = useDates.get(points);
        LocalDateTime now = LocalDateTime.now();
        if(time == null){
            useDates.put(points,now);
            return getSkillByLevel(points);
        }else{
            Duration duration = Duration.between(time,now);
            if(duration.toMinutes()*60 >= getSkillByLevel(points).getTime()){
                useDates.put(points,now);
                return getSkillByLevel(points);
            }else{
                return null;
            }
        }
    }

    public void addSkill(SkillPoints points){
        this.levels.put(points,0);
    }

    public SkillPoint getSkillByLevel(SkillPoints points){
        return points.getSkillByLevel(levels.get(points));
    }

}
