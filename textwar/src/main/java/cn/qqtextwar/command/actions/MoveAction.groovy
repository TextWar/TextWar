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
    void execute(Player player, String command) {
        if("w" == command.toLowerCase()){
            player.up(server.gameMap)
        }
        if("s" == command.toLowerCase()){
            player.down(server.gameMap)
        }
        if("a" == command.toLowerCase()){
            player.left(server.gameMap)
        }
        if("d" == command.toLowerCase()){
            player.right(server.gameMap)
        }
    }
}
