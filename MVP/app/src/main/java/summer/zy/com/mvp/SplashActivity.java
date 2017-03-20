package summer.zy.com.mvp;

import android.content.Intent;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import summer.zy.com.mvp.module.base.BaseActivity;
import summer.zy.com.mvp.module.home.HomeActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void updateViews(boolean isRefresh) {
        Observable.interval(3, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .compose(this.<Long>bindToLife())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        doSkip();
                    }
                });
    }


    private void doSkip() {
        finish();
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        overridePendingTransition(R.anim.hold, R.anim.zoom_in_exit);
    }
}
