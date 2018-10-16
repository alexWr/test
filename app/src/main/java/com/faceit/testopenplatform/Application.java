package com.faceit.testopenplatform;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;


import com.faceit.testopenplatform.di.components.ApplicationComponent;
import com.faceit.testopenplatform.di.components.DaggerApplicationComponent;
import com.faceit.testopenplatform.di.modules.ApplicationModule;
import com.faceit.testopenplatform.di.modules.NetModule;
import com.squareup.leakcanary.AndroidExcludedRefs;
import com.squareup.leakcanary.ExcludedRefs;
import com.squareup.leakcanary.LeakCanary;



/**
 *  Application class with multiDex support
 */

public class Application extends MultiDexApplication {

    private Application application;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        this.initializeInjector();
        this.initializeLeakDetection();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .netModule(new NetModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    private void initializeLeakDetection() {
        if (BuildConfig.DEBUG) {
            ExcludedRefs excludedRefs = AndroidExcludedRefs.createAppDefaults()
                    .staticField("android.view.inputmethod.InputMethodManager", "instance")
                    .build();
            LeakCanary.refWatcher(this).excludedRefs(excludedRefs).buildAndInstall();
        }
    }
}
