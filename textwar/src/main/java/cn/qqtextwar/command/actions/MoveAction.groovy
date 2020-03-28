package cn.qqtextwar.command.actions

import cn.qqtextwar.command.Action
import cn.qqtextwar.entity.player.Player
import groovy.transform.CompileStatic

@CompileStatic
class MoveAction extends Action{

    MoveAction() {
        super(["w","a","s","d","W","A","S","D"],"player moving")
    }

    @Override
    String execute(Player player, String command) {
        if("w" == command.toLowerCase()){
            player.up(server.gameMap)
            return "${player.id}: move up 1"
        }
        if("s" == command.toLowerCase()){
            player.down(server.gameMap)
            return "${player.id}: move down 1"
        }
        if("a" == command.toLowerCase()){
            player.left(server.gameMap)
            return "${player.id}: move left 1"
        }
        if("d" == command.toLowerCase()){
            player.right(server.gameMap)
            return "${player.id}: move right 1"
        }
        return ""
    }
}
