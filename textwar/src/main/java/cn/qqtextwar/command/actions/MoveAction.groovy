package cn.qqtextwar.command.actions

import cn.qqtextwar.command.Action
import cn.qqtextwar.entity.player.Player
import cn.qqtextwar.ex.MoveException
import groovy.transform.CompileStatic

@CompileStatic
class MoveAction extends Action{

    MoveAction() {
        super(["w","a","s","d","W","A","S","D"],"player moving")
    }

    @Override
    String execute(Player player, String command) {
        try{
            if("w" == command.toLowerCase()){
                player.up(server.gameMap).update()
                return "${player.id}: move up 1"
            }
            if("s" == command.toLowerCase()){
                player.down(server.gameMap).update()
                return "${player.id}: move down 1"
            }
            if("a" == command.toLowerCase()){
                player.left(server.gameMap).update()
                return "${player.id}: move left 1"
            }
            if("d" == command.toLowerCase()){
                player.right(server.gameMap).update()
                return "${player.id}: move right 1"
            }
        }catch(Exception e){
            if(e instanceof MoveException){
                return e.message
            }
        }
    }
}
