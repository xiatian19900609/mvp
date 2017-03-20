package summer.zy.com.mvp.module.home;

import summer.zy.com.http.response.DataNewsResponse;
import summer.zy.com.mvp.module.base.IBaseView;

/**
 * Created on 2017/3/16 17:38
 *
 * @author summer
 */

public interface HomeView extends IBaseView {
    /**
     * 加载数据
     *
     * @param response
     */
    void loadData(DataNewsResponse response);

    /**
     * 加载数据失败
     */
    void onLoadDataError();
}
