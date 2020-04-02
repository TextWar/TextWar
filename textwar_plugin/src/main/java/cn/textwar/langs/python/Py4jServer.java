package cn.textwar.langs.python;

import py4j.ClientServer;


public class Py4jServer {

    private PyPluginLoader loader;

    public Py4jServer(){
        ClientServer server = new ClientServer(null);
        this.loader = (PyPluginLoader) server.getPythonServerEntryPoint(new Class[]{PythonPlugin.class});
    }

    public PyPluginLoader getLoader() {
        return loader;
    }
}
