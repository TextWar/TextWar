package cn.qqtextwar.core

import cn.qqtextwar.Server
import cn.qqtextwar.api.Application
import cn.qqtextwar.log.ServerLogger
import cn.qqtextwar.utils.Translate
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

    @CompileStatic(TypeCheckingMode.SKIP)
    @Override
    void init(Server server) {
        this.server = server
        Translate.getType("en").putAll(
                [
                        "cr" : "TextWar Game @CopyRight TextWar Dev has started!Version Beta 1,Please add me.",
                        "registered" : "#{id} : has registered in the game",
                        "add" : "I think you should add me",
                        "again_warn" : "Do not join again!",
                        "illegal_cmd" : "illegal command",
                        "use_register" : "please login use: /register",
                        "time_cool" : "cooling"
                ]
        )
        Translate.getType("zh").putAll(
                [
                        "cr" : "TextWar Game @CopyRight TextWar Dev 已经启动！版本 Beta 1",
                        "registered" : "#{id} : has registered in the game",
                        "add" : "I think you should add me",
                        "again_warn" : "Do not join again!",
                        "illegal_cmd" : "illegal command",
                        "use_register" : "please login use: /register",
                        "time_cool" : "冷却后再试"
                ]
        )
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
            groupObj.sendMessage(server.translate("cr"))
        }
        Events.subscribeAlways(GroupMessage.class){
            GroupMessage event ->
                long id = event.sender.group.id
                if(id == group){
                    String message = event.message.toString()
                    if(message.startsWith("/")){
                        message = message.substring(message.indexOf("/")+"/".length()).trim()
                        if("register".equals(message)){
                            if(server.getPlayer(event.sender.id)==null) {
                                server.executor.registerPlayer("Unknown IP : QQ", event.sender.id,this)
                                event.sender.group.sendMessage(server.translate("registered").replace("#{id}","${event.sender.id} "))
                                if(bot.friends.contains(event.sender.id)){
                                    event.sender.sendMessage(server.translate("registered").replace("#{id}","${event.sender.id} "))
                                }else{
                                    event.sender.group.sendMessage(server.translate("add"))
                                }
                            }else{
                                event.sender.group.sendMessage(server.translate("again_warn"))
                            }
                        }else{
                            String[] things = message.split(server.translate("time_cool"))
                            if(things.length == 0){
                                event.sender.group.sendMessage(server.translate("illegal_cmd"))
                            }else{
                                if(server.getPlayer(event.sender.id) != null){
                                    if(server.getPlayer(event.sender.id).done(server)){
                                        String[] args = new String[things.length-1]
                                        System.arraycopy(things,1,args,0,args.length)
                                        event.group.sendMessage(server.executor.doCommandOrAction(things[0],event.sender.id,things))
                                        server.wantUpdate()
                                        if(!server.isTest()){
                                            //TODO 临时
                                            String image = server.consumeImage()

                                            if(image != null){
                                                this.logger.debug("IMAGE: "+image)
                                                groupObj.sendMessage(event.group.uploadImage(new FileInputStream(new File(server.consumeImage()))))
                                            }
                                        }
                                    }else{
                                        event.group.sendMessage(server.translate("time_cool"))
                                    }
                                }else{
                                    event.group.sendMessage(server.translate("use_register"))
                                }

                            }
                        }


                        if(server.isTest()){
                            groupObj.sendMessage(server.gameMap.toString())
                        }
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
