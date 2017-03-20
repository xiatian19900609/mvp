package summer.zy.com.http;

/**
 * 不想把请求的结果转化为String.如果不是对外提供的接口就使用这个回调函数接口
 * 因为对外的接口都需要返回String
 * Created on 2017/3/17 10:41
 *
 * @author summer
 */

public interface ApiNetworkServiceCallBack<T> {
    /***
     * 网络事物成功
     * 服务器返回的json字符串，应用层需根据业务号使用gson解析成java对象
     *
     * @param result 请求结果
     */
    void onApiServiceFinished(T result);

    public static final int ERROR_NETWORK = 0x02;            // 网络错误
    public static final int ERROR_NO_DATA = 0x08;            // 未返回数据
    public static final int ERROR_PARSE_DATA = 0x10;         //解析数据错误

    /***
     * 网络事务异常
     *
     * @param errorCode
     * @param msg       ApiResponseException 当errorCode为ERROR_SYSTEM时，errorInfo有效 当errorInfo.errorCode == 401000001时，需要提示用户重新登录
     */
    void onApiServiceError(int errorCode, String msg);


    void onApiServiceCompleted();
}
