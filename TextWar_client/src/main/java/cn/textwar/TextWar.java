package cn.textwar;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

public class TextWar extends GameApplication {

    public static volatile boolean isStarting = true;

    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("TextWar");
        settings.setVersion("1.0.0");
    }

    //如何更新服务端的玩家
    // 1. 得到玩家的新坐标
    // 2. 通过新坐标找到GPlayer对象
    // 3. 通过小地图和玩家坐标计算出玩家的位置
    // 4. 之后用GameMap的addEntity更新即可

    //进入游戏有启动页面
    //之后输入账号密码服务端ip端口进入
    @Override
    protected void initGame() {

    }

    public static void main(String[] args) {
        launch(args);
        isStarting = false;
    }
}
