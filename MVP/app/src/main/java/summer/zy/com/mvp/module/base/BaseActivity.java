package summer.zy.com.mvp.module.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import summer.zy.com.mvp.R;

/**
 * Created on 2017/3/16 16:56
 *
 * @author summer
 */

public abstract class BaseActivity<T extends IBasePresenter> extends RxAppCompatActivity implements IBaseView {
    /**
     * 把 ProgressBar 放在基类统一处理，@Nullable 表明 View 可以为 null，详细可看 ButterKnife
     */
    @Nullable
    @BindView(R.id.pb_loading)
    SpinKitView mPbLoading;
    @Nullable
    @BindView(R.id.tv_net_error)
    TextView mTvNetError;

    /**
     * 把 Presenter 提取到基类需要配合基类的 initInjector() 进行注入，如果继承这个基类则必定要提供一个 Presenter 注入方法，
     * 该APP所有 Presenter 都是在 Module 提供注入实现，也可以选择提供另外不带 Presenter 的基类
     */
    @Inject
    protected T mPresenter;
    private Unbinder bind;

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @LayoutRes
    protected abstract int attachLayoutRes();

    /**
     * Dagger 注入
     */
    protected abstract void initInjector();

    /**
     * 初始化视图控件
     */
    protected abstract void initViews();

    /**
     * 更新视图控件
     */
    protected abstract void updateViews(boolean isRefresh);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(attachLayoutRes());
        bind = ButterKnife.bind(this);
        initInjector();
        initViews();
        updateViews(false);
    }


    @Override
    public void showLoading() {
        mPbLoading.setVisibility(View.VISIBLE);
        mTvNetError.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        mPbLoading.setVisibility(View.GONE);
        mTvNetError.setVisibility(View.GONE);
    }

    @Override
    public void showNetError() {
        mPbLoading.setVisibility(View.GONE);
        mTvNetError.setVisibility(View.VISIBLE);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindUntilEvent(ActivityEvent.STOP);
    }

    @Override
    public void finishRefresh() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
