package cn.textwar.langs.python;

import py4j.ClientServer;

import java.util.List;

public class Py4jServer {

    public Py4jServer server;

    public void loadPlugins(){

    }

    public static void main(String[] args) {
        ClientServer server = new ClientServer(null);
        PyPluginLoader pythonPlugin = (PyPluginLoader) server.getPythonServerEntryPoint(new Class[]{PythonPlugin.class});
        List<PythonPlugin> pluginList = pythonPlugin.loadPlugins("");
    }



}
