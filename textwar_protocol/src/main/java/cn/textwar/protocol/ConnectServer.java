package cn.textwar.protocol;

import cn.qqtextwar.Server;
import cn.qqtextwar.log.LogFormat;
import cn.qqtextwar.log.ServerLogger;
import com.alibaba.fastjson.JSONObject;
import org.fusesource.jansi.Ansi;

import java.io.IOException;
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

/**
 * The Connect Server : TextWar protocol impl
 *
 * @author MagicLu550
 */
public abstract class ConnectServer extends Thread {

    public static final TextWarProtocol CLOSE = new TextWarProtocol().addAll(Handler.createResponse(null,Handler.CLOSE,Handler.CLOSE,"close the server",new JSONObject()).toJSONString());

    public static final TextWarProtocol ALIVE = new TextWarProtocol().addAll(Handler.createResponse(null,Handler.ALIVE,Handler.ALIVE,"alive?",new JSONObject()).toJSONString());

    protected HandlerExecutor handlerExecutor;

    private Executor executor;

    private Queue<ClientThread> streamList;

    private Server server;

    private CountTime counter;

    private int port;

    private Connecting connecting;

    private ServerLogger logger;

    private int time;

    private AtomicInteger tasks;

    private Connecting whenOut;

    private int heartbeatTime;

    private boolean splittingMode;

    public ConnectServer(Server server,Connecting runnable,Connecting whenOut,int threads,int time,int heartbeatTime){
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
        this.heartbeatTime = heartbeatTime;
        this.registerHandlers(handlerExecutor);
        this.splittingMode = (Boolean) server.getParser().getValue("server.splittingMode",false)[0];
    }

    public ConnectServer(Server server,Connecting runnable,Connecting whenOut,int threads,int time){
        this(server,runnable,whenOut,threads,time,1000);
    }


    public abstract void registerHandlers(HandlerExecutor executor);

    public Queue<ClientThread> getStreamList() {
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
                x.write(protocol);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public void whenJoin(ClientThread socket){

    }

    public void addStream(ClientThread socket) throws IOException{
        streamList.add(socket);
    }

    public Executor getExecutor() {
        return executor;
    }

    public int getPort() {
        return port;
    }

    public void reload(){
        callMessage(CLOSE);
    }

    public abstract boolean heartBeat(ClientThread thread);

    public double getTPS(){
        return ((double) tasks.get())/((double) counter.integer.get());
    }

    public boolean isConnected(ClientThread thread) {
        return !thread.closed;
    }

    @Override
    public void run() {
        try (
                ServerSocket server = new ServerSocket(port)
        ){

            while (this.server == null || !(this.server.getState().get() == CLOSED)){
                Socket socket = server.accept();
                ClientThread thread = new ClientThread(socket,this.server,this,connecting,whenOut);
                whenJoin(thread);
                logger.info("New Client : "+socket.getInetAddress());
                addStream(thread);
                executor.execute(thread);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean isSplittingMode() {
        return splittingMode;
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

        private volatile boolean closed;

        private HeartBeat heartBeat;

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
                while (isConnected(this) && (tw = this.getProtocol().decode(this.getSocket().getInputStream())) == null) ;
                return tw;
            }catch (IOException e){
                e.printStackTrace();
                return null;
            }
        }

        public void write(TextWarProtocol protocol) throws IOException{
            synchronized (this){
                socket.getOutputStream().write(protocol.encode());
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
                this.heartBeat = new HeartBeat(this);
                getExecutor().execute(heartBeat);
                while ((this.server == null && isConnected(this)) || (this.server.getState().get() != CLOSED && isConnected(this) )) {
                    runnable.connecting(this,cs);
                    if(properties.get("heartbeat") != null && !(boolean)properties.get("heartbeat")){
                        heartBeat.stopIt();
                    }
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
                heartBeat.stopIt();
                try {
                    synchronized (cs) {
                        cs.streamList.remove(this);
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

    class HeartBeat extends Thread{

        private ClientThread thread;

        private volatile boolean stop;

        public HeartBeat(ClientThread thread){
            this.thread = thread;
        }

        @Override
        public void run() {
            while (true){
                if(stop)break;
                thread.closed = !heartBeat(thread);
                if(thread.closed)break;
                try {
                    Thread.sleep(heartbeatTime);
                }catch (Exception ignore){ }
            }
        }

        public void stopIt(){
            this.stop = true;
        }
    }

}
