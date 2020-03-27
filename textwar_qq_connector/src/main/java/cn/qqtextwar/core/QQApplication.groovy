package cn.qqtextwar.core

import cn.qqtextwar.Server
import cn.qqtextwar.api.Application
import cn.qqtextwar.log.ServerLogger
import groovy.transform.CompileStatic
import net.mamoe.mirai.Bot
import net.mamoe.mirai.BotFactoryJvm
import net.mamoe.mirai.japt.Events
import net.mamoe.mirai.message.GroupMessage
import net.mamoe.mirai.message.data.MessageUtils
import net.mamoe.mirai.message.data.QuoteReplyToSend

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
    void run() {
        this.bot = BotFactoryJvm.newBot((Integer)parser.getValue("bot.qq",123456)[0],parser.getHeadValue("bot.password"))
        this.bot.login()
        this.logger.info("The QQ Bot has registered...")
        Events.subscribeAlways(GroupMessage.class){
            GroupMessage event ->
                if(event.getMessage().contains("at")){
                    final QuoteReplyToSend quote = MessageUtils.quote(event.getMessage(), event.getSender());
                }
        }
        bot.join()
    }

    @Override
    void sendMessage(long qq,String message) {

    }
}
