package cn.qqtextwar

import cn.qqtextwar.log.ServerLogger
import groovy.transform.CompileStatic
import org.apache.xmlrpc.client.XmlRpcClient
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl

@CompileStatic
class RPCRunner {

    private ServerLogger logger = new ServerLogger()

    static final String PKEY = "textwar."

    private XmlRpcClient client

    RPCRunner(){
        this.client = new XmlRpcClient()
    }

    void start(String ip,String port) {
        logger.info("The xml rpc is starting....")
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl()
        config.setServerURL(new URL("http://${ip}:${port}"))
        client.setConfig(config)
        logger.info("The xml rpc has started!")
    }

    public <T> T execute(String name,Class<T> returnType,Object... params){
        String pMethod = PKEY+name
        return returnType.cast(client.execute(pMethod,params))
    }


}
