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
        Events.subscribeAlways(GroupMessage.class){
            GroupMessage event ->
                long id = event.sender.group.id
                if(id == (parser.getValue("bot.group",123456)[0] as Long)){
                    String message = event.message.toString()
                    if(message.startsWith("tw:")){
                        message = message.substring(message.indexOf("tw:")+"tw:".length())
                        if("register".equals(message)){
                            if(server.getPlayer(event.sender.id)==null) {
                                server.executor.registerPlayer("Unknown IP : QQ", event.sender.id)
                                event.sender.group.sendMessage("${event.sender.id} : has registered in the game")
                            }else{
                                event.sender.group.sendMessage("Do not join again!")
                            }
                            //event.sender.sendMessage("${event.sender.id} : has registered in the game")

                        }
                    }
                }
        }
        bot.join()
    }

    @Override
    void sendMessage(long qq,String message) {

    }
}
