package cn.textwar.protocol;

import java.io.IOException;

@FunctionalInterface
public interface Connecting {

    void connecting(ConnectServer.ClientThread thread,ConnectServer cs) throws IOException;

}
