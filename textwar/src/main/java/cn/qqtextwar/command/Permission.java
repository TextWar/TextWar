package cn.qqtextwar.command;

import cn.qqtextwar.entity.player.Player;

public enum Permission {

    ALL{
        @Override
        public boolean access(Player player) {
            return true;
        }
    },
    ADMIN{
        @Override
        public boolean access(Player player) {
            return player.isAdmin();
        }
    },
    PLAYER{
        @Override
        public boolean access(Player player) {
            return !player.isAdmin();
        }
    },
    CONSOLE{
        @Override
        public boolean access(Player player) {
            return false;
        }
    };

    public abstract boolean access(Player player);
}
