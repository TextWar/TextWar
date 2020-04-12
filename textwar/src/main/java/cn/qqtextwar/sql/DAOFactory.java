package cn.qqtextwar.sql;

import cn.qqtextwar.Server;

public class DAOFactory {

    private PlayerDAO playerDAO;

    public DAOFactory(Server server){
        this.playerDAO = new PlayerDAO(server.getDatabase());
    }

    public PlayerDAO getPlayerDAO(){
        return playerDAO;
    }
}
