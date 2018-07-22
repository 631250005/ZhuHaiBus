package com.scrat.zhuhaibus.data;

import android.content.Context;

import com.scrat.zhuhaibus.data.net.AmapService;
import com.scrat.zhuhaibus.data.net.CoreService;
import com.scrat.zhuhaibus.data.net.XinheApiService;
import com.scrat.zhuhaibus.framework.util.Utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by scrat on 2018/3/24.
 */

public class DataRepository {
    private static final String SHARE_URL = "https://scrats.cn/u/zhbus/app.html";

    private static class SingletonHolder {
        private static DataRepository instance = new DataRepository();
    }

    public static DataRepository getInstance() {
        return DataRepository.SingletonHolder.instance;
    }

    private XinheApiService xinheApiService;
    private CoreService coreService;
    private AmapService amapService;
    private boolean init;

    private DataRepository() {
    }

    public synchronized void init(Context ctx) {
        if (init) {
            return;
        }
        init = true;

        OkHttpClient.Builder xinheClient = new OkHttpClient().newBuilder()
                .addInterceptor(chain -> {
                    Request req = chain.request().newBuilder()
                            .addHeader("Cookie", "openid3=aiFDwshul-vFhiZppWLrFNdmfXNG")
                            .build();
                    return chain.proceed(req);
                });
        Retrofit xinheRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://www.zhbuswx.com")
                .client(xinheClient.build())
                .build();
        xinheApiService = xinheRetrofit.create(XinheApiService.class);

        OkHttpClient.Builder coreClient = new OkHttpClient().newBuilder()
                .addInterceptor(chain -> {
                    Request req = chain.request().newBuilder()
                            .addHeader("ch", Utils.getChannelName(ctx))
                            .addHeader("vc", String.valueOf(Utils.getVersionCode(ctx)))
                            .addHeader("vn", Utils.getVersionName(ctx))
                            .addHeader("app", "zhuhaibus")
                            .build();
                    return chain.proceed(req);
                });
        Retrofit coreRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("https://gogo.scrats.cn")
                .client(coreClient.build())
                .build();
        coreService = coreRetrofit.create(CoreService.class);

        Retrofit amapRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("https://restapi.amap.com")
                .build();
        amapService = amapRetrofit.create(AmapService.class);
    }

    public void release() {
        init = false;
    }

    public XinheApiService getXinheApiService() {
        return xinheApiService;
    }

    public CoreService getCoreService() {
        return coreService;
    }

    public AmapService getAmapService() {
        return amapService;
    }

    public String getShareUrl() {
        return String.format("%s?_=%s", SHARE_URL, System.currentTimeMillis());
    }

}
