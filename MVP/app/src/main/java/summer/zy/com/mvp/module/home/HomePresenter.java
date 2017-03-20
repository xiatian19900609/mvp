package summer.zy.com.mvp.module.home;

import android.content.Context;

import summer.zy.com.http.ApiNetworkService;
import summer.zy.com.http.ApiNetworkServiceCallBack;
import summer.zy.com.http.HttpResult;
import summer.zy.com.http.response.DataNewsResponse;
import summer.zy.com.mvp.module.base.IBasePresenter;

/**
 * Created on 2017/3/16 17:36
 *
 * @author summer
 */

public class HomePresenter implements IBasePresenter {
    private final HomeView mView;
    private final Context mContext;

    public HomePresenter(HomeView mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void getData(final boolean isRefresh) {
        if (!isRefresh)
            mView.showLoading();

        ApiNetworkService.getInstance(mContext).startRequestBrokeDetailsInfomation("201703175024", "1", new ApiNetworkServiceCallBack<DataNewsResponse>() {
            @Override
            public void onApiServiceFinished(DataNewsResponse result) {
                mView.loadData(result);
            }

            @Override
            public void onApiServiceError(int errorCode, String msg) {
                mView.onLoadDataError();
            }

            @Override
            public void onApiServiceCompleted() {
                if (!isRefresh)
                    mView.hideLoading();
            }
        }, mView.<HttpResult>bindToLife());
    }

    @Override
    public void getMoreData() {

    }
}
