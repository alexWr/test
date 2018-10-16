package com.faceit.testopenplatform.di.modules;


import android.content.Context;
import android.content.SharedPreferences;


import com.faceit.testopenplatform.Application;
import com.faceit.testopenplatform.R;
import com.faceit.testopenplatform.executor.JobExecutor;
import com.faceit.testopenplatform.executor.PostExecutionThread;
import com.faceit.testopenplatform.executor.UIThread;
import com.faceit.testopenplatform.repository.ItemDataRepository;
import com.faceit.testopenplatform.repository.ItemRepository;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
      this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
      return this.application;
    }

    @Provides
    @Singleton
    Executor provideThreadExecutor(JobExecutor jobExecutor) {
      return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
      return uiThread;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreference() {
      return application.getSharedPreferences(
              application.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    ItemRepository provideItemRepository(ItemDataRepository itemDataRepository) {
        return itemDataRepository;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }
}
