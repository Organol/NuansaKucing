package com.dartmedia.nuansakucing.APIHelper;



import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface BaseAPIService {


    @GET("u06zr")
    Call<ResponseBody> getPetshop();

    @GET("2")
    Call<ResponseBody> getUser();

    @GET("All/forum")
    Call<ResponseBody> getForum();




//    @FormUrlEncoded
//    @POST
//    Call<ResponseBody> postDataForum(@Field("titleForum") String titleForum,
//                                     @Field("deskForum") String deskForum,
//                                     @Field("imgForum") String imgForum);
//
//    @Multipart
//    @POST("All/forum")
//    Call uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name);
//
////    @Multipart
////    @POST("All/forum")
////    Call<ResponseBody> uploadImage(@Part("imgForum") RequestBody file, @Part("deskForum") RequestBody desc);
//
//    @Multipart
//    @POST("All/forum")
//    Call<MyResponse> uploadImage(@Part("imgForum\"; filename=\"myfile.jpg\" ")  RequestBody file, @Part("deskForum") RequestBody desc);


}
