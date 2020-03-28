package cn.qqtextwar.utils


//翻译
class Translate {

    static {
        langs = [:]
        addLanguage("en",[
                "start_exception" : "the server has started!",
                "map_starting" : "Map thread is starting...",
                "map_started": "Map thread has started",
                "server_started" :"Server has started..",
                "copyright" : "The TextWar game's server core v1.0.0 CopyRight @TextWar",
                "closed":"the Server has closed",
                "closed_info" : "the Server has closed state: 3 - CLOSED",
                "map_thread_closed" : "The map thread has closed",
                "move_up" : "#{id} move up 1",
                "move_down" : "#{id} move down 1",
                "move_right" : "#{id} move right 1",
                "move_left" : "move left 1",
                "player_existed" : "#{id} has existed",
                "help_cmd" : "help command",
                "exit_cmd" : "player exit",
                "update_cmd" : "a command for updating the map",
                "move_cmd" : "player moving: w is up,s is down,a is left,d is right"
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
