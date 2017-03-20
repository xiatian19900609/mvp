package summer.zy.com.mvp.injector.modules;

import dagger.Module;
import dagger.Provides;
import summer.zy.com.mvp.injector.PerActivity;
import summer.zy.com.mvp.module.base.IBasePresenter;
import summer.zy.com.mvp.module.home.HomeActivity;
import summer.zy.com.mvp.module.home.HomePresenter;

/**
 * Created on 2017/3/16 17:33
 *
 * @author summer
 */
@Module
public class HomeModule {
    private final HomeActivity mView;

    public HomeModule(HomeActivity mView) {
        this.mView = mView;
    }

    @PerActivity
    @Provides
    public IBasePresenter provideHomePresenter() {
        return new HomePresenter(mView, mView);
    }
}
