package cn.textwar.protocol;

import cn.qqtextwar.Server;
import cn.qqtextwar.log.LogFormat;
import cn.qqtextwar.log.ServerLogger;
import org.fusesource.jansi.Ansi;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static cn.qqtextwar.Server.CLOSED;

public abstract class ConnectServer extends Thread {

    protected HandlerExecutor handlerExecutor;

    private Executor executor;

    private Queue<OutputStream> streamList;

    private Server server;

    private CountTime counter;

    private int port;

    private Connecting connecting;

    private ServerLogger logger;

    private int time;

    private AtomicInteger tasks;

    private Connecting whenOut;

    public ConnectServer(Server server,Connecting runnable,Connecting whenOut,int threads,int time){
        this.server = server;
        this.streamList = new LinkedBlockingQueue<>();
        this.connecting = runnable;
        this.executor = Executors.newFixedThreadPool(threads);
        this.logger = new ServerLogger();
        this.time = time;
        this.handlerExecutor = new HandlerExecutor();
        this.tasks = new AtomicInteger();
        this.counter = new CountTime();
        this.counter.start();
        this.whenOut = whenOut;
        this.registerHandlers(handlerExecutor);
    }

    public abstract void registerHandlers(HandlerExecutor executor);

    public Queue<OutputStream> getStreamList() {
        return streamList;
    }

    public int getTime() {
        return time;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public synchronized void callMessage(TextWarProtocol protocol){
        this.getStreamList().forEach(x->{
            try {
                x.write(protocol.encode());
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public double getTPS(){
        return ((double) tasks.get())/((double) counter.integer.get());
    }


    @Override
    public void run() {
        try (
                ServerSocket server = new ServerSocket(port)
        ){

            while (this.server == null || !(this.server.getState().get() == CLOSED)){
                Socket socket = server.accept();
                logger.info("New Client : "+socket.getInetAddress());
                streamList.add(socket.getOutputStream());
                executor.execute(new ClientThread(socket,this.server,this,connecting,whenOut));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public HandlerExecutor getHandlerExecutor() {
        return handlerExecutor;
    }

    public class ClientThread implements Runnable{

        private Map<String,Object> properties;

        private ServerLogger logger;

        private Socket socket;

        private Protocol protocol;

        private Server server;

        private Connecting runnable;

        private final ConnectServer cs;

        private Connecting whenOut;

        ClientThread(Socket socket,Server server,ConnectServer cs,Connecting runnable,Connecting whenOut){
            this.socket = socket;
            this.protocol = new Protocol();
            this.server = server;
            this.runnable = runnable;
            this.whenOut = whenOut;
            this.cs = cs;
            this.logger = new ServerLogger();
            this.properties = new ConcurrentHashMap<>();
        }

        public Map<String, Object> getProperties() {
            return properties;
        }

        public TextWarProtocol whenGetProtocol(){
            try {
                TextWarProtocol tw = null;
                while (isConnected() && (tw = this.getProtocol().decode(this.getSocket().getInputStream())) == null) ;
                return tw;
            }catch (IOException e){
                e.printStackTrace();
                return null;
            }
        }

        public boolean isConnected(){
            try {
                this.socket.sendUrgentData(0xFF);
                return true;
            }catch (Exception e){
                return false;
            }
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
                while ((this.server == null && isConnected()) || (this.server.getState().get() != CLOSED && isConnected() )) {
                    runnable.connecting(this,cs);
                    Thread.sleep(cs.getTime());
                    cs.tasks.addAndGet(1);
                }
            }catch (Exception e){
                System.out.println(LogFormat.fg(Ansi.Color.BLUE)+"exited.."+LogFormat.fg(Ansi.Color.DEFAULT));
            }finally {
                try {
                    whenOut.connecting(this, cs);
                }catch (IOException e){
                    e.printStackTrace();
                }
                try {
                    synchronized (cs) {
                        cs.streamList.remove(socket.getOutputStream());
                    }
                    socket.close();
                    logger.info(LogFormat.fg(Ansi.Color.BLUE)+socket.getInetAddress()+": exited"+LogFormat.fg(Ansi.Color.DEFAULT));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    class CountTime extends Thread{

        private AtomicInteger integer;

        public CountTime(){
            this.integer = new AtomicInteger();
        }
        @Override
        public void run() {
            while (true){
                integer.addAndGet(1);
                try{
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
