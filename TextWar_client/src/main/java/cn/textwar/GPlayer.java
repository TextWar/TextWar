package cn.textwar;

import cn.qqtextwar.entity.player.Player;
import cn.qqtextwar.math.Vector;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GPlayer extends Player{


    private Entity playerEntity;

    //TODO 获得玩家的信息可以通过数据包获得，后期写
    //这里只存储玩家的坐标
    public GPlayer(Vector vector, long id) {
        super(null,null,"", vector, id,0,0,0);
        this.playerEntity = FXGL
                .entityBuilder()
                .at(vector.getX(),vector.getY())
                //TODO The Image will be received by server and game
                .view(new Rectangle(25,25, Color.BLUE))
                .buildAndAttach();
    }
}
