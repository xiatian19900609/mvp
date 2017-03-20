package summer.zy.com.http;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;
import summer.zy.com.http.response.DataNewsResponse;

/**
 * Created on 2017/3/16 15:28
 *
 * @author summer
 */

public interface HttpApiService<T> {
    @GET("command/execute?command=200014")
    @Headers({"Cache-Control: no-cache"})
    Observable<HttpResult<DataNewsResponse>> brokeDetailsInfomation(@Query("matchId") String matchId, @Query("lotteryType") String lotteryType);
}
