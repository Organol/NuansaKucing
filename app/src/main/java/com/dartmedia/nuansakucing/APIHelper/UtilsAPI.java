package com.dartmedia.nuansakucing.APIHelper;

public class UtilsAPI {

    // 10.0.2.2 ini adalah localhost.
//    public static final String BASE_URL_API = "http://192.168.0.116/RestAPI/test/";
    public static final String BASE_URL_API = "https://api.myjson.com/bins/";
//    public static final String BASE_URL_API = "https://reqres.in/api/";


    // Mendeklarasikan Interface BaseApiService
    public static BaseAPIService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseAPIService.class);
    }


}
