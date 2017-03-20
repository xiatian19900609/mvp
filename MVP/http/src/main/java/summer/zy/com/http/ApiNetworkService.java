package summer.zy.com.http;

import android.content.Context;
import android.util.Log;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created on 2017/3/16 14:21
 *
 * @author summer
 */

public class ApiNetworkService {
    public static final int CONNECTION_TIME = 5;
    public static final int HTTPSTATUS_OK = 0;


    private static ApiNetworkService instance;
    private Context context;
    private HttpApiService excuteCommandApi;

    public static ApiNetworkService getInstance(Context context) {
        if (instance == null) {
            instance = new ApiNetworkService(context);
        }
        return instance;
    }

    public ApiNetworkService(Context context) {
        this.context = context.getApplicationContext();
        OkHttpClient client = initBuilderClient();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://query-api.8win.com/")
                .build();
        excuteCommandApi = retrofit.create(HttpApiService.class);
    }

    private OkHttpClient initBuilderClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECTION_TIME, TimeUnit.SECONDS)
                .readTimeout(CONNECTION_TIME, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder().build();
                        Log.d("========TAG======= ", "request - url: " + request.url());
                        return chain.proceed(request);
                    }
                });
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        builder.cookieJar(cookieJar);
        return builder.build();
    }


    /**
     * 处理网络请求
     *
     * @param observable
     * @param callBack
     * @param transformer
     */
    protected void handleRequest(Observable observable, final ApiNetworkServiceCallBack callBack, Observable.Transformer transformer) {
        observable.subscribeOn(Schedulers.io())
                .compose(transformer)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResult>() {
                    @Override
                    public void onCompleted() {
                        callBack.onApiServiceCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dealWithThrowable(e, callBack);
                    }

                    @Override
                    public void onNext(HttpResult result) {
                        if (callBack != null) {
                            if (result.code == HTTPSTATUS_OK) {
                                callBack.onApiServiceFinished(result.data);
                            } else {
                                callBack.onApiServiceError(result.code, result.message);
                            }
                        }
                    }
                });
    }


    /***
     * 处理异常
     *
     * @param callBack
     */
    private void dealWithThrowable(Throwable throwable, ApiNetworkServiceCallBack callBack) {
        if (callBack != null) {
            if (throwable instanceof ConnectException) {
                callBack.onApiServiceError(ApiNetworkServiceCallBack.ERROR_NETWORK, context.getString(R.string.no_network));
            } else if (throwable instanceof TimeoutException) {
                callBack.onApiServiceError(ApiNetworkServiceCallBack.ERROR_NETWORK, context.getString(R.string.network_timeout));
            } else {
                callBack.onApiServiceError(ApiNetworkServiceCallBack.ERROR_NETWORK, context.getString(R.string.network_failure));
            }
        }
    }


    /************************************ API *******************************************/


    /***
     * 比赛爆料详情
     *
     * @param matchId     赛事编号
     * @param lotteryType 彩种 1：竞彩足球 2：竞彩篮球 7：北单
     * @param callBack    数据回调
     * @param transformer
     */
    public void startRequestBrokeDetailsInfomation(String matchId, String lotteryType, ApiNetworkServiceCallBack callBack, Observable.Transformer transformer) {
        handleRequest(excuteCommandApi.brokeDetailsInfomation(matchId, lotteryType), callBack, transformer);
    }
}
