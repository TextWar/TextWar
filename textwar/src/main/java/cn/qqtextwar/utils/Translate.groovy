package cn.qqtextwar.utils


//翻译
class Translate {

    static {
        langs = [:]
        addLanguage("en",[
                "start_exception" : "the server has started!",
                "map_starting" : "Map thread is starting...",
                "map_started": "Map thread has started",
                "server_started" :"Server has started successfully",
                "copyright" : "The [YELLOW]TextWar[DEFAULT] game's server core v1.0.0 Copyright @TextWar",
                "closed":"the Server has closed",
                "closed_info" : "the Server has closed state: 3 - CLOSED",
                "map_thread_closed" : "The map thread has closed",
                "move_up" : "#{id} move up 1",
                "move_down" : "#{id} move down 1",
                "move_right" : "#{id} move right 1",
                "move_left" : "#{id} move left 1",
                "player_existed" : "#{id} has existed",
                "help_cmd" : "help command",
                "exit_cmd" : "player exit",
                "update_cmd" : "a command for updating the map",
                "move_cmd" : "player moving: /w is up,/s is down,/a is left,/d is right",
                "server_closing" : "the server is closing...",
                "map_not_found" : "the map is not found",
                "create_random_map" : "creating a random map",
                "admin_give_cmd" : "give somebody admin",
                "reload_cmd" : "reloaded"
        ])
        addLanguage("zh",[
                "start_exception" : "服务端已经启动过了",
                "map_starting" : "地图获取线程正在启动中...",
                "map_started": "地图获取线程已经启动",
                "server_started" :"服务端已经启动成功",
                "copyright" : "The TextWar game's server core v1.0.0 CopyRight @TextWar",
                "closed":"服务端已经关闭",
                "closed_info" : "服务端已经关闭，状态号码: 3 - CLOSED",
                "map_thread_closed" : "地图获取线程已经关闭",
                "move_up" : "#{id} 向上移动一格",
                "move_down" : "#{id} 向下移动一格",
                "move_right" : "#{id} 向右移动一格",
                "move_left" : "#{id} 向左移动一格",
                "player_existed" : "#{id} 已经退出游戏",
                "help_cmd" : "帮助命令",
                "exit_cmd" : "玩家退出",
                "update_cmd" : "请求更新地图",
                "move_cmd" : "玩家移动: /w 向上,/s 向下,/a 向左,/d 向右"
        ])
    }

    private String type

    Translate(String type){
        this.type = type
    }

    static Map<String,Map<String,String>> langs


    static addLanguage(String type,Map<String,String> langs){
        Translate.langs."${type}" = langs
    }

    static getType(String type){
        return langs.get(type)
    }

    String translate(String key){
        return langs.get(type).get(key)
    }

    String getType(){
        return type
    }


}
