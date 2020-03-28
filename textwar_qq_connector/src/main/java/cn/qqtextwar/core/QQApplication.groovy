package cn.qqtextwar.core

import cn.qqtextwar.Server
import cn.qqtextwar.api.Application
import cn.qqtextwar.log.ServerLogger
import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode
import net.mamoe.mirai.Bot
import net.mamoe.mirai.BotFactoryJvm
import net.mamoe.mirai.japt.Events
import net.mamoe.mirai.message.GroupMessage

@CompileStatic
class QQApplication implements Application {

    public static final String CONFIG = "bot.cfg"

    private Bot bot

    private Server server

    private QQConfigParser parser

    private ServerLogger logger

    @Override
    void init(Server server) {
        this.server = server
        this.logger = new ServerLogger()
        this.logger.info("You are using the qq application to run the TextWar")
        this.logger.info("Use the mirai 0.30.1")

        this.server.getRegister().register(CONFIG)
        this.parser = new QQConfigParser(server.getRegister().getConfig(CONFIG))
    }

    @Override
    @CompileStatic(TypeCheckingMode.SKIP)
    void run() {
        println()
        this.bot = BotFactoryJvm.newBot((Long)parser.getValue("bot.qq",123456)[0],parser.getHeadValue("bot.password"))
        this.bot.login()
        this.logger.info("The QQ Bot has registered...")
        long group = (parser.getValue("bot.group",123456)[0] as Long)
        def groupObj = bot.groups.get(group)
        if(groupObj == null){
            this.logger.error("No Such Group ${group}")
            server.close0(null)
        }else{
            groupObj.sendMessage("TextWar Game @CopyRight TextWar Dev has started!Version Beta 1")
        }

        Events.subscribeAlways(GroupMessage.class){
            GroupMessage event ->
                long id = event.sender.group.id
                if(id == group){
                    String message = event.message.toString()
                    if(message.startsWith("tw:")){
                        message = message.substring(message.indexOf("tw:")+"tw:".length()).trim()
                        if("register".equals(message)){
                            if(server.getPlayer(event.sender.id)==null) {
                                server.executor.registerPlayer("Unknown IP : QQ", event.sender.id)
                                event.sender.group.sendMessage("${event.sender.id} : has registered in the game")
                                if(bot.friends.contains(event.sender.id)){
                                    event.sender.sendMessage("${event.sender.id} : has registered in the game")
                                }else{
                                    event.sender.group.sendMessage("I think you should add me")
                                }
                            }else{
                                event.sender.group.sendMessage("Do not join again!")
                            }
                        }else{
                            String[] things = message.split(" ")
                            if(things.length == 0){
                                event.sender.group.sendMessage("illegal command")
                            }else{
                                if(server.getPlayer(event.sender.id) != null){
                                    String[] args = new String[things.length-1]
                                    System.arraycopy(things,1,args,0,args.length)
                                    event.group.sendMessage(server.executor.doCommandOrAction(things[0],event.sender.id,things))
                                }else{
                                    event.group.sendMessage("please login use: tw:register")
                                }
                            }
                        }
                        if(server.isTest())groupObj.sendMessage(server.gameMap.toString())
                    }
                }
        }
        bot.join()
    }

    @Override
    void sendMessage(long qq,String message) {
        bot.friends.get(qq).sendMessage(message)
    }
}
