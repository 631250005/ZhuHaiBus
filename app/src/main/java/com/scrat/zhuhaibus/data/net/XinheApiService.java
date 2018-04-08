package com.scrat.zhuhaibus.data.net;

import com.scrat.zhuhaibus.data.modle.BaseXinHeRes;
import com.scrat.zhuhaibus.data.modle.BusLine;
import com.scrat.zhuhaibus.data.modle.BusStation;
import com.scrat.zhuhaibus.data.modle.BusStop;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by scrat on 2018/3/24.
 */

public interface XinheApiService {
    @Headers("Referer: http://www.zhbuswx.com/busline/BusQuery.html?v=2.01")
    @GET("/Handlers/BusQuery.ashx")
    Observable<BaseXinHeRes<List<BusLine>>> getBusLine(
            @Query("handlerName") String handlerName,
            @Query("key") String key,
            @Query("_") long now);

    @Headers("Referer: http://www.zhbuswx.com/busline/BusQuery.html?v=2.01")
    @GET("/Handlers/BusQuery.ashx")
    Observable<BaseXinHeRes<List<BusStop>>> getBusStop(
            @Query("handlerName") String handlerName,
            @Query("lineId") String lineId,
            @Query("_") long now);

    @Headers("Referer: http://www.zhbuswx.com/busline/BusQuery.html?v=2.01")
    @GET("/Handlers/BusQuery.ashx")
    Observable<BaseXinHeRes<List<BusStation>>> getBusStation(
            @Query("handlerName") String handlerName,
            @Query("lineName") String lineName,
            @Query(value = "fromStation", encoded = true) String fromStation,
            @Query("_") long now);
}
