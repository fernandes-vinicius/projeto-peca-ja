package com.pecaja.app.pecajamobile.api;

public class Api {

    private Api() {}

    public static final String IP = "10.113.1.87";
    public static final String PORT = "8080";
    public static final String BASE_URL = "http://"+ IP + ":" + PORT + "/";

    public static Service getAPIService() {
        return Client.getClient(BASE_URL).create(Service.class);
    }

}
