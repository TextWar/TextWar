package cn.textwar.client.handlers;

import cn.qqtextwar.Server;
import cn.qqtextwar.sql.PlayerDAO;

public class DAOFactory {

    private PlayerDAO playerDAO;

    public DAOFactory(Server server){
        this.playerDAO = new PlayerDAO(server.getDatabase());
    }

    public PlayerDAO getPlayerDAO(){
        return playerDAO;
    }
}
