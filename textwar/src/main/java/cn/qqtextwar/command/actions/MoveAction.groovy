package cn.qqtextwar.command.actions

import cn.qqtextwar.command.Action
import cn.qqtextwar.entity.player.Player
import cn.qqtextwar.ex.MoveException
import groovy.transform.CompileStatic

@CompileStatic
class MoveAction extends Action{

    MoveAction() {
        super(["w","a","s","d","W","A","S","D"],"move_cmd")
    }

    @Override
    String execute(Player player, String command) {
        try{
            if("w" == command.toLowerCase()){
                player.up(server.gameMap).update()
                return "${server.translate("move_up")}".replace("#{id}","${player.id}")
            }
            if("s" == command.toLowerCase()){
                player.down(server.gameMap).update()
                return "${server.translate("move_down")}".replace("#{id}","${player.id}")
            }
            if("a" == command.toLowerCase()){
                player.left(server.gameMap).update()
                return "${server.translate("move_left")}".replace("#{id}","${player.id}")
            }
            if("d" == command.toLowerCase()){
                player.right(server.gameMap).update()
                return "${server.translate("move_right")}".replace("#{id}","${player.id}")
            }
        }catch(Exception e){
            if(e instanceof MoveException){
                return e.message
            }
        }
    }
}
