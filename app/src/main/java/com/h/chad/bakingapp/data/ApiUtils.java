package com.h.chad.bakingapp.data;

/**
 * Created by chad on 7/6/2017.
 */

public class ApiUtils {
    public final static String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";

    public static SOService getSOService(){
        return NetworkClient.getClient(BASE_URL).create(SOService.class);
    }
}
