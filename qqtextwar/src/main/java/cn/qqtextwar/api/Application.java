package cn.qqtextwar.api;

import cn.qqtextwar.Server;

/**
 * 作为与qq交互的接口，未来将会作为和其他客户端交互的接口
 *
 */
public interface Application {

    void init(Server server);

    void run();

}
