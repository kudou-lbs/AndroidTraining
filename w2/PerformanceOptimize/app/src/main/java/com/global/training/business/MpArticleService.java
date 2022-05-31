package com.global.training.business;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MpArticleService {

    @FormUrlEncoded
    @POST("wxarticle/info")
    Call<ResponseBody> startSession(@FieldMap Map<String,String> requestData);


    /**
     * 获取公众号的历史文字数据
     * @param mpId
     * @param pageId
     * @return
     */
    @GET("wxarticle/list/{mpId}/{pageId}/json")
    Call<ArticlesResult> getArticleList(@Path("mpId") String mpId, @Path("pageId") String pageId);


}
