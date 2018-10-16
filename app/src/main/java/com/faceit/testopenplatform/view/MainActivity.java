package com.faceit.testopenplatform.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;


import com.faceit.testopenplatform.R;
import com.faceit.testopenplatform.di.HasComponent;
import com.faceit.testopenplatform.di.components.DaggerItemComponent;
import com.faceit.testopenplatform.di.components.ItemComponent;
import com.faceit.testopenplatform.model.Comments;
import com.faceit.testopenplatform.utils.Const;
import com.faceit.testopenplatform.view.base.BaseActivity;
import com.faceit.testopenplatform.view.fragment.CommentsDetailFragment;
import com.faceit.testopenplatform.view.fragment.CommentsListFragment;
import com.faceit.testopenplatform.view.fragment.SplashFragment;


import javax.inject.Inject;

public class MainActivity extends BaseActivity implements HasComponent<ItemComponent>, CommentsListFragment.ItemListListener,
        SplashFragment.SplashClickListener {

    private ItemComponent itemComponent;
    private ActionBar mActionBar;
    private Toolbar toolbar;

    @Inject
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getApplicationComponent().inject(this);
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeActivity(savedInstanceState);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        getSupportFragmentManager().addOnBackStackChangedListener(backStackChangedListener);
    }

    private void initializeInjector(){
        this.itemComponent = DaggerItemComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            addFragment(R.id.mainContainer, new SplashFragment(), false);
        }
    }

    private final FragmentManager.OnBackStackChangedListener backStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                if(mActionBar != null)
                    mActionBar.setDisplayHomeAsUpEnabled(true);
                toolbar.setNavigationOnClickListener(v -> onBackPressed());
            } else {
                mActionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
    };

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager() != null && getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
        else
            super.onBackPressed();
    }

    @Override
    public void onItemClicked(Comments comments) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("COMMENT", comments);
        CommentsDetailFragment fragment = new CommentsDetailFragment();
        fragment.setArguments(bundle);
        replaceFragment(R.id.mainContainer, fragment, true);
    }

    @Override
    public void onShowCommentsClick(String first, String second) {
        Const.hideKeyboard(this);
        CommentsListFragment fragment = new CommentsListFragment();
        Bundle bundle = new Bundle();
        int tmpFId = Math.abs(Integer.parseInt(first));
        int tmpSId = Math.abs(Integer.parseInt(second));
        if(tmpFId<tmpSId) {
            bundle.putString("FIRST", String.valueOf(tmpFId));
            bundle.putString("SECOND", String.valueOf(tmpSId));
        }
        else{
            bundle.putString("FIRST", String.valueOf(tmpSId));
            bundle.putString("SECOND", String.valueOf(tmpFId));
        }
        fragment.setArguments(bundle);
        replaceFragment(R.id.mainContainer, fragment, true);
    }

    @Override
    public ItemComponent getComponent() {
        return this.itemComponent;
    }
}
