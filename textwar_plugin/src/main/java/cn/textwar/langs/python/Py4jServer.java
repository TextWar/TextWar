package cn.textwar.langs.python;

import py4j.ClientServer;


public class Py4jServer {

    private PyPluginLoader loader;

    private ClientServer server;

    public Py4jServer(){
        this.server = new ClientServer(null);
        this.server.startServer();
        this.loader = (PyPluginLoader) server.getPythonServerEntryPoint(new Class[]{PyPluginLoader.class});
    }

    public PyPluginLoader getLoader() {
        return loader;
    }

    public ClientServer getClientServer() {
        return server;
    }
}
