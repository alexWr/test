package com.faceit.testopenplatform.di.components;



import com.faceit.testopenplatform.di.modules.ActivityModule;
import com.faceit.testopenplatform.di.modules.ItemModule;
import com.faceit.testopenplatform.di.scopes.PerActivity;
import com.faceit.testopenplatform.view.fragment.CommentsDetailFragment;
import com.faceit.testopenplatform.view.fragment.CommentsListFragment;
import com.faceit.testopenplatform.view.fragment.SplashFragment;

import dagger.Component;


/**
 * A scope {@link com.faceit.testopenplatform.di.scopes.PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, ItemModule.class})
public interface ItemComponent extends ActivityComponent {

    void inject(SplashFragment splashFragment);
    void inject(CommentsListFragment listFragment);
    void inject(CommentsDetailFragment detailFragment);

}
