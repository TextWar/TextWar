import cn.qqtextwar.Server
import org.sqlite.JDBC

boolean isRandom = true

Server gameServer = Server.server

server{
    rpc{
        ip "127.0.0.1"
        port "3000"
    }
    database{
        url "${gameServer.baseFile}/main.db"
        driver JDBC.class.name
    }
    game{
        type 1
        difficulty 1
        player{
            born{
                money 100
                HP 100
                mana 120
            }
        }
    }
    translate "en"
    messageTime 5
    map{

        random isRandom
        if(!isRandom){
            name "map.json"
        }
    }

    python{
        command(["python3 XmlRpcServer.py"])
        reload "python3 #{reload}"
    }
}
