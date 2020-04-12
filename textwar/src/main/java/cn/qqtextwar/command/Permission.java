package cn.qqtextwar.command;

import cn.qqtextwar.entity.player.Player;

public enum Permission {

    ALL,
    ADMIN,
    PLAYER,
    CONSOLE;

    public boolean access(Player player){
        return !player.isAdmin()?
                this.name().equals(ALL.name()) || this.name().equals(PLAYER.name())
                :!(this.name().equals(PLAYER.name()) || this.name().equals(CONSOLE.name()));
    }
}
