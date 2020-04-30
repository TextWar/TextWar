package cn.qqtextwar.command.commands

import cn.qqtextwar.CommandSender
import cn.qqtextwar.command.Command
import cn.qqtextwar.command.Permission
import cn.qqtextwar.entity.player.Player

class SetAdminCommand extends Command{

    SetAdminCommand() {
        super(["/admin"], "dmin_give_cmd", Permission.ADMIN)
    }

    @Override
    String execute(CommandSender player, Command command, String[] args) {
        if(args.length != 0){
            int id
            if((id = server.daoFactory.playerDAO.getId(args[0])) != -1){
                Player p = server.getPlayerReturnNull(id)
                if(p == null){
                    return "the player is offline"
                }else{
                    try{
                        p.setAdmin(Boolean.parseBoolean(args[1]))
                        return "added ${p.name} as a admin"
                    }catch(Exception ignore){}
                }
            }else{
                return "no such player"
            }
        }
        return "/admin player true/false"
    }
}
