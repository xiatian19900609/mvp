package summer.zy.com.http;

/**
 * Created on 2017/3/16 15:34
 * @author summer
 */

public class HttpResult<T> {
    public int code;
    public String message;
    public T data;
}
