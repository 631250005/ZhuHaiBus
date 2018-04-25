package com.scrat.zhuhaibus.data;

import android.content.Context;

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
    private static final String SHARE_URL = "https://xd.scrats.cn/u/zhbus/app.html";

    private static class SingletonHolder {
        private static DataRepository instance = new DataRepository();
    }

    public static DataRepository getInstance() {
        return DataRepository.SingletonHolder.instance;
    }

    private XinheApiService xinheApiService;
    private CoreService coreService;
    private boolean init;

    private DataRepository() {
    }

    public synchronized void init(Context ctx) {
        if (init) {
            return;
        }
        init = true;

        Retrofit xinheRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://www.zhbuswx.com")
                .build();
        xinheApiService = xinheRetrofit.create(XinheApiService.class);

        OkHttpClient.Builder client = new OkHttpClient().newBuilder()
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
                .client(client.build())
                .build();
        coreService = coreRetrofit.create(CoreService.class);
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

    public String getShareUrl() {
        return String.format("%s?_=%s", SHARE_URL, System.currentTimeMillis());
    }
}
