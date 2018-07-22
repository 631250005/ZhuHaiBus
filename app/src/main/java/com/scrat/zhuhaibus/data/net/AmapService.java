package com.scrat.zhuhaibus.data.net;

import com.scrat.zhuhaibus.data.modle.route.RouteRes;
import com.scrat.zhuhaibus.data.modle.route.TipsRes;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface AmapService {
    @GET("/v3/assistant/inputtips")
    Observable<TipsRes> inputTips(
            @Query("s") String s,
            @Query("key") String key,
            @Query("city") String city,
            @Query("citylimit") boolean cityLimit,
            @Query("platform") String platform,
            @Query("logversion") String logVersion,
            @Query("sdkversion") String sdkVersion,
            @Query("keywords") String keywords);


//    http://restapi.amap.com/v3/place/text?s=rsv3&children=&key=ceb54024fae4694f734b1006e8dc8324&extensions=all&page=1&offset=10&city=%E7%8F%A0%E6%B5%B7&language=zh_cn&platform=JS&logversion=2.0&sdkversion=1.3&keywords=%E5%94%90%E5%AE%B6%E6%B9%BE%E7%AB%99
//    http://restapi.amap.com/v3/direction/transit/integrated?origin=113.551792,22.363067&destination=113.549616,22.223118&city=珠海&strategy=0&nightflag=0&extensions=&s=rsv3&cityd=珠海&key=ceb54024fae4694f734b1006e8dc8324&platform=JS&logversion=2.0&sdkversion=1.3

    @GET("/v3/direction/transit/integrated")
    Observable<RouteRes> integrated(
            @Query("origin") String origin,
            @Query("destination") String destination,
            @Query("city") String city,
            @Query("strategy") int strategy,
            @Query("nightflag") int nightflag,
            @Query("s") String s,
            @Query("cityd") String cityd,
            @Query("key") String key,
            @Query("platform") String platform,
            @Query("logversion") String logversion,
            @Query("sdkversion") String sdkversion);
}
