package com.appcloud.frankiapp.Sync;

/**
 * Created by cristian on 28/04/2016.
 */
public class APIRest {

    // KEY CONSTANTS
    public static final String KEY_USERNAME = "username";
    public static final String KEY_NAME = "name";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_CLIENT_ID = "client_id";
    public static final String KEY_SCOPE = "scope";

    // Errors
    public static final String INVALID_USER = "invalid_user";
    public static final String INVALID_CREDENTIALS = "invalid_credentials";


    // Base URL
    public static final String BASE_URL = "http://appclouds.es";
    public static final String AUTENTIFICACION = "/oauth2/auth";
    public static final String RESTABLECER_PASSWORD = "/comercialapi/user/request_new_password";


    public static final String CLIENT_ID = "Oauth2_comercial";
    public static final String SCOPE = "comercialapi";

    public static String autentificacion(){
        return String.format("%s%s", BASE_URL, AUTENTIFICACION);
    }
    public static String restablecerPassword(){
        return String.format("%s%s", BASE_URL, RESTABLECER_PASSWORD);
    }
    // GET Methods Urls
    public static String getPostList(){
        return String.format("%s%s", BASE_URL, "/posts");
    }
    // GET Methods with Params
    public static String getPostById(int id){
        return String.format("%s%s%d", BASE_URL, "post/", id);
    }



}
