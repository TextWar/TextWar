package cn.qqtextwar.entity;

import cn.qqtextwar.ProtocolVar;
import cn.qqtextwar.ex.IllegalIdException;
import cn.qqtextwar.tasks.Task;
import cn.qqtextwar.math.Vector;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Npc,可以售出货物和给予任务
 * id> 3000
 * @author MagicLu550 @ 卢昶存
 */
public class Npc extends Entity{

    private String name;

    private NpcEnum type;

    private boolean trader;

    private boolean acceptTask;

    private Queue<Task> tasks;

    public Npc(String name, NpcEnum type, Vector vector, long id) {
        super(vector,id);
        if(id > Integer.MAX_VALUE || !ProtocolVar.NPC_ID.contains((int)id)){
            throw new IllegalIdException("the npc's id is between 1000 and 10000");
        }
        this.name = name;
        this.type = type;
        this.trader = type.isSellable();
        this.acceptTask = type.isSellable();
        this.tasks = new LinkedBlockingQueue<>();
    }

    public enum NpcEnum {
        TRADER(true,false);

        boolean sellable;

        boolean acceptTask;

        NpcEnum(boolean sellable,boolean acceptTask) {
            this.sellable = sellable;
            this.acceptTask = acceptTask;
        }

        public boolean isSellable() {
            return sellable;
        }
    }

    public String getName() {
        return name;
    }


    public NpcEnum getType() {
        return type;
    }


    public boolean isTrader() {
        return trader;
    }

    public boolean isAcceptTask() {
        return acceptTask;
    }

    public Queue<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    @Override
    public int initAggressivity(int level) {
        return 0;
    }
}
