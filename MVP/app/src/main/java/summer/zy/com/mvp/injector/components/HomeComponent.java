package summer.zy.com.mvp.injector.components;

import dagger.Component;
import summer.zy.com.mvp.injector.modules.HomeModule;
import summer.zy.com.mvp.module.home.HomeActivity;

/**
 * Created on 2017/3/16 17:51
 *
 * @author summer
 */
@Component(modules = HomeModule.class)
public interface HomeComponent {
    void inject(HomeActivity activity);
}
