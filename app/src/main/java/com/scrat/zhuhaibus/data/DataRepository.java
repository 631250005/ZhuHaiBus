package com.scrat.zhuhaibus.data;

import com.scrat.zhuhaibus.data.net.CoreService;
import com.scrat.zhuhaibus.data.net.XinheApiService;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by scrat on 2018/3/24.
 */

public class DataRepository {
    private static class SingletonHolder {
        private static DataRepository instance = new DataRepository();
    }

    public static DataRepository getInstance() {
        return DataRepository.SingletonHolder.instance;
    }

    private XinheApiService xinheApiService;
    private CoreService coreService;

    private DataRepository() {
        Retrofit xinheRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://www.zhbuswx.com")
                .build();
        xinheApiService = xinheRetrofit.create(XinheApiService.class);

        OkHttpClient.Builder client = new OkHttpClient().newBuilder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request req = chain.request().newBuilder()
                                .addHeader("ch", "ch")
                                .addHeader("vc", "0")
                                .addHeader("vn", "vn")
                                .addHeader("app", "app")
                                .build();
                        return chain.proceed(req);
                    }
                });
        Retrofit coreRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("https://gogo.scrats.cn")
                .client(client.build())
                .build();
        coreService = coreRetrofit.create(CoreService.class);
    }

    public XinheApiService getXinheApiService() {
        return xinheApiService;
    }

    public CoreService getCoreService() {
        return coreService;
    }
}
