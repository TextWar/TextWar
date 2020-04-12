package cn.qqtextwar.sql

import cn.qqtextwar.entity.player.Player
import cn.qqtextwar.ex.PlayerException

import java.security.MessageDigest

/**
 * 负责进行玩家的操作
 */
class PlayerDAO {

    public static final String PLAYER_TABLE = "player"

    public static final String PLAYER_TABLE_VAR = "#{player_table}"

    private SQLiteConnector database


    PlayerDAO(SQLiteConnector database) {
        this.database = database
    }

    int getId(String name){
        int id = -1
        database.getTable(PLAYER_TABLE).eachRow(SQL.GET_ID_BY_NAME.sql.replace(PLAYER_TABLE_VAR,PLAYER_TABLE),[name]){
            id = it."id"
        }
        return id
    }

    int getMaxId(){
        List<Integer> ids = []
        database.getTable(PLAYER_TABLE).eachRow(SQL.GET_MAX_ID.sql.replace(PLAYER_TABLE_VAR,PLAYER_TABLE)){
            ids.add((Integer)it."id")
        }
        return ids.size() == 0?10000:ids.get(0)
    }


    void setAdmin(String name,int admin){
        database.getTable(PLAYER_TABLE).executeInsert(SQL.PLAYER_SET_ADMIN.sql.replace(PLAYER_TABLE_VAR,PLAYER_TABLE),[admin,name])
    }

    //如果已经存在玩家，则比较password和id,成功则将玩家初始化，不成功返回false
    //如果不存在玩家，则插入数据，返回true
    boolean registerPlayer(boolean register,String name,String password,Player player){
        String sql = SQL.PLAYER_SELECT.sql.replace(PLAYER_TABLE_VAR,PLAYER_TABLE)
        List<PlayerDatas> datas = database.getByBean(sql,[name],PlayerDatas.class,PLAYER_TABLE)
        if(datas.size() == 0){
            PlayerDatas data = new PlayerDatas()
            data.password = getMd5(password)
            data.name = name
            data.inventoryId = player.id
            data.id = player.id
            data.health = player.healthPoints
            data.mana = player.manaPoints
            data.joinTime = new Date()
            data.xp = player.xp
            data.admin = player.isAdmin()?1:0
            database.insertBean(PLAYER_TABLE,[data])
            return true
        }else if(register){
            throw new PlayerException("This name has existed")
        }else{
            PlayerDatas data = datas.get(0)
            String passwordMd5 = getMd5(password)
            boolean access = name == data.name && data.password == passwordMd5
            if(access){
                //TODO Inventory没写
                player.level = data.level
                player.healthPoints =  data.health
                player.manaPoints = data.mana
                player.name = data.name
                player.xp = data.xp
                player.id = data.id
                player.admin = data.admin
                return true
            }else{
                throw new PlayerException("username or password is error")
            }
        }

    }

    static String getMd5(String password){
        return new String(Base64.encoder.encode(MessageDigest.getInstance("md5").digest(password.getBytes())))
    }
}
