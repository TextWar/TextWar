package cn.qqtextwar.entity.player;

import cn.qqtextwar.entity.points.SkillPoints;

import java.util.HashMap;
import java.util.Map;

public class SkillTree {

    private Player player;

    public Player getPlayer() {
        return player;
    }

    public SkillTree(Player player) {
        this.player = player;
    }

    public Map<Integer,SkillPoints> getSkillTree(){
        Map<SkillPoints,Integer> skillPointsIntegerMap = player.getSkillLevels();
        Map<Integer, SkillPoints> skillTreeMap = new HashMap<>();
        skillPointsIntegerMap.forEach((x,y)->skillTreeMap.put(y,x));
        return skillTreeMap;
    }
}
