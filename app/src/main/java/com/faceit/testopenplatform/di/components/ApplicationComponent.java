package com.faceit.testopenplatform.di.components;

import android.content.Context;

import com.faceit.testopenplatform.di.modules.ApplicationModule;
import com.faceit.testopenplatform.di.modules.NetModule;
import com.faceit.testopenplatform.executor.PostExecutionThread;
import com.faceit.testopenplatform.repository.ItemRepository;
import com.faceit.testopenplatform.view.MainActivity;
import com.faceit.testopenplatform.view.base.BaseActivity;
import com.google.gson.Gson;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Component;


/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = {ApplicationModule.class, NetModule.class})
public interface ApplicationComponent {

    void inject(BaseActivity baseActivity);

    void inject(MainActivity mainActivity);

    //Exposed to sub-graphs.
    Context context();

    Executor threadExecutor();

    PostExecutionThread postExecutionThread();

    ItemRepository itemRepository();

    Gson gson();
}
