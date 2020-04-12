package cn.qqtextwar.command;

import cn.qqtextwar.entity.player.Player;

public enum Permission {

    ALL,
    ADMIN,
    PLAYER,
    CONSOLE;

    public boolean access(Player player){
        if(!player.isAdmin()){
            if(this.name().equals(ALL.name()) || this.name().equals(PLAYER.name())){
                return true;
            }
            if(this.name().equals(ADMIN.name()) || this.name().equals(CONSOLE.name())){
                return false;
            }
            return false;
        }else{
            if(this.name().equals(PLAYER.name()) || this.name().equals(CONSOLE.name())){
                return false;
            }
            return true;
        }
    }
}
