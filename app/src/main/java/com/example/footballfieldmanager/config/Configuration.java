package com.example.footballfieldmanager.config;

public class Configuration {

    private static Configuration instance;

    public static Configuration getInstance(){
        if(instance==null){
            instance = new Configuration();
        }
        return instance;
    }

    private Configuration(){

    }

    private final Protocol DEFAULT_PROTOCOL = Protocol.HTTP;
    private final String SERVER_IP_ADDRESS = "192.168.1.12";
    private final int PORT = 3000;

    public String getURL(Protocol protocol, Service service){
        return protocol + SERVER_IP_ADDRESS + ":" + this.PORT + service;
    }

    public String getURL(Service service){
        return getURL(DEFAULT_PROTOCOL, service);
    }

}
