package cn.textwar.protocol;

import cn.qqtextwar.Server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ConnectServer extends Thread {

    private Executor executor;

    private List<OutputStream> streamList;

    private Server server;


    private int port;

    private Connecting connecting;

    public ConnectServer(Server server,Connecting runnable,int threads){
        this.server = server;
        this.streamList = new ArrayList<>();
        this.connecting = runnable;
        this.executor = Executors.newFixedThreadPool(threads);
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (
                ServerSocket server = new ServerSocket(port)
        ){

            while (true){
                Socket socket = server.accept();
                streamList.add(socket.getOutputStream());
                executor.execute(new ClientThread(socket,this.server,this,connecting));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public class ClientThread implements Runnable{

        private Socket socket;

        private Protocol protocol;

        private Server server;

        private Connecting runnable;

        private ConnectServer cs;

        ClientThread(Socket socket,Server server,ConnectServer cs,Connecting runnable){
            this.socket = socket;
            this.protocol = new Protocol();
            this.server = server;
            this.runnable = runnable;
            this.cs = cs;
        }

        public Socket getSocket() {
            return socket;
        }

        public Server getServer() {
            return server;
        }


        public Protocol getProtocol() {
            return protocol;
        }



        @Override
        public void run() {
            try {
                while (this.server.getState().get() != Server.CLOSED) {
                    connecting.connecting(this,cs);
                }
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                try {
                    socket.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
