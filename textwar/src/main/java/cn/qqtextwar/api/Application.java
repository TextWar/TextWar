package cn.qqtextwar.api;

import cn.qqtextwar.Server;

/**
 * 作为与qq交互的接口，未来将会作为和其他客户端交互的接口
 *
 */
public interface Application extends Cloneable{

    void init(Server server);

    void run();

    void sendMessage(long qq,String message);

    default void playerChat(long qq,String message){

    }

    default void broadcast(String message){

    }

    default void reload(){

    }

}
