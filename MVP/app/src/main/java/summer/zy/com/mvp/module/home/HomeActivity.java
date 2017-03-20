package summer.zy.com.mvp.module.home;

import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;
import summer.zy.com.http.response.DataNewsResponse;
import summer.zy.com.mvp.R;
import summer.zy.com.mvp.adapter.HomeAdpter;
import summer.zy.com.mvp.injector.components.DaggerHomeComponent;
import summer.zy.com.mvp.injector.modules.HomeModule;
import summer.zy.com.mvp.module.base.BaseActivity;
import summer.zy.com.mvp.module.base.IBasePresenter;

/**
 * Created on 2017/3/16 17:26
 *
 * @author summer
 */

public class HomeActivity extends BaseActivity<IBasePresenter> implements HomeView {
    @BindView(R.id.recyclerView)
    XRecyclerView mRecyclerView;
    private HomeAdpter mAdapter;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInjector() {
        DaggerHomeComponent.builder().homeModule(new HomeModule(this)).build().inject(this);
    }

    @Override
    protected void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPresenter.getData(true);
            }

            @Override
            public void onLoadMore() {

            }
        });
        mAdapter = new HomeAdpter(this);
        SlideInRightAnimationAdapter animationAdapter = new SlideInRightAnimationAdapter(mAdapter);
        mRecyclerView.setAdapter(animationAdapter);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(false);
    }

    @Override
    public void loadData(DataNewsResponse response) {
        mRecyclerView.refreshComplete();
        mAdapter.setData(response.news);
    }

    @Override
    public void onLoadDataError() {
        mRecyclerView.refreshComplete();
    }
}
