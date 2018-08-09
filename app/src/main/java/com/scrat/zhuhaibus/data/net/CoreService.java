package com.scrat.zhuhaibus.data.net;

import com.scrat.zhuhaibus.data.modle.Feedback;
import com.scrat.zhuhaibus.data.modle.News;
import com.scrat.zhuhaibus.data.modle.Res;
import com.scrat.zhuhaibus.data.modle.ResList;
import com.scrat.zhuhaibus.data.modle.UpdateInfo;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface CoreService {
    @POST("/zhuhaibus/api/feedback")
    Observable<Res> feedback(@Body Feedback feedback);

    @GET("/zhuhaibus/api/news")
    Observable<Res<ResList<News>>> getNewsList(@Query("index") String index, @Query("size") int size);

    @GET("/zhuhaibus/api/news/{news_id}")
    Observable<Res<News>> getNewsDetail(@Path("news_id") String newsId);

    @GET("/zhuhaibus/static/update/cfg/android/update.json")
    Observable<Res<UpdateInfo>> getUpdateInfo(@Query("ch") String ch,
                                              @Query("vc") int vc,
                                              @Query("_") long ts);
}
