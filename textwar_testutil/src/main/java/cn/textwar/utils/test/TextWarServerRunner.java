package cn.textwar.utils.test;

import cn.qqtextwar.Server;
import cn.qqtextwar.api.Application;
import cn.qqtextwar.log.LogFormat;
import cn.textwar.plugins.Listener;
import org.fusesource.jansi.Ansi;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class TextWarServerRunner{

    private List<Method> tests = new ArrayList<>();

    private List<Method> befores = new ArrayList<>();

    private List<Method> afters = new ArrayList<>();

    private List<Application> clients = new ArrayList<>();

    private TextWarServerRunner runner;


    public TextWarServerRunner() {
        this.runner = this;
        Arrays.stream(this.getClass().getDeclaredMethods()).forEach(
                x->{
                    if(x.getAnnotation(TextWarBefore.class)!=null){
                        befores.add(x);
                    }
                    if(x.getAnnotation(TextWarServerTest.class) != null){
                        tests.add(x);
                    }
                    if(x.getAnnotation(TextWarAfter.class)!=null){
                        afters.add(x);
                    }
                }
        );
    }

    public static void doTest(Class<? extends TextWarServerRunner> runner){
        try {
            TextWarServerRunner r = runner.newInstance();
            r.doTest();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void doTest(){
        if(runner != null){
            Arrays.stream(this.getClass().getAnnotation(Client.class).value()).forEach(
                    x->{
                        try {
                            clients.add(x.newInstance());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
            );
            Server.start(clients.toArray(new Application[0])).initMap();
            Server.getServer().setTest(true);
            Server.getServer().getLogger().setAsDebug(true);
            if(runner instanceof Listener){
                Server.getServer().getEventExecutor().registerNativeEvents((Listener) runner);
            }
            invokeAll(befores,TextWarBefore.class);
            invokeAll(tests,TextWarServerTest.class);
            invokeAll(afters,TextWarAfter.class);
            if(runner.getClass().getAnnotation(Close.class)!=null){
                Server.stop();
            }
        }

    }

    public void invokeAll(List<Method> methods, Class<? extends Annotation> test){
        methods.forEach(x->{
            try {
                long free = Runtime.getRuntime().freeMemory();
                if ((boolean)test.getDeclaredMethod("doIt").invoke(x.getAnnotation(test))) {
                    long testTimes = System.currentTimeMillis();
                    if (x.getParameterTypes().length == 1 && x.getParameterTypes()[0].equals(Server.class)) {
                        x.invoke(runner,Server.getServer());
                    } else {
                        x.invoke(runner);
                    }
                    System.out.println("---------------------TEST:"+x.getName()+"("+")"+"------------------------");
                    System.out.println(LogFormat.fg(Ansi.Color.BLUE)+"USE_MEMORY: "+LogFormat.fg(Ansi.Color.GREEN)+((double)(free - Runtime.getRuntime().freeMemory())/(1024*1024))+LogFormat.fg(Ansi.Color.RED)+" MB"+LogFormat.fg(Ansi.Color.DEFAULT));
                    System.out.println(LogFormat.fg(Ansi.Color.BLUE)+"TIME: "+LogFormat.fg(Ansi.Color.GREEN)+(System.currentTimeMillis()-testTimes)+LogFormat.fg(Ansi.Color.RED)+" MS"+LogFormat.fg(Ansi.Color.DEFAULT));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}
