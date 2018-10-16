package com.faceit.testopenplatform.di.components;

import android.app.Activity;


import com.faceit.testopenplatform.di.modules.ActivityModule;
import com.faceit.testopenplatform.di.scopes.PerActivity;

import dagger.Component;


/**
 * A base component upon which fragment's components may depend.
 * Activity-level components should extend this component.
 *
 * Subtypes of ActivityComponent should be decorated with annotation:
 * {@link com.faceit.testopenplatform.di.scopes.PerActivity}
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
interface ActivityComponent {
  //Exposed to sub-graphs.
  Activity activity();
}
