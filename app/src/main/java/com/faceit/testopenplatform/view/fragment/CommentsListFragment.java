package com.faceit.testopenplatform.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.faceit.testopenplatform.R;
import com.faceit.testopenplatform.di.components.ItemComponent;
import com.faceit.testopenplatform.model.Comments;
import com.faceit.testopenplatform.presenter.HomePresenter;
import com.faceit.testopenplatform.utils.EndlessRecyclerViewListener;
import com.faceit.testopenplatform.view.ItemListView;
import com.faceit.testopenplatform.view.MainActivity;
import com.faceit.testopenplatform.view.adapter.CommentsAdapter;
import com.faceit.testopenplatform.view.base.BaseFragment;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Fragment that shows a list of Users.
 */
public class CommentsListFragment extends BaseFragment implements ItemListView {

    /**
     * Interface for listening user list events.
     */
    public interface ItemListListener {
        void onItemClicked(final Comments comments);
    }

    @Inject
    HomePresenter homePresenter;
    @Inject
    CommentsAdapter commentsAdapter;

    @BindView(R.id.rv_entity)
    RecyclerView rv_entity;
    @BindView(R.id.loading_container)
    FrameLayout fl_load;
    @BindView(R.id.cl_retry)
    ConstraintLayout cl_retry;
    @BindView(R.id.bt_retry)
    Button bt_retry;

    private String firstID;
    private String secondId;

    private Unbinder unbind;
    private SharedPreferences sharedPreferences;
    private ArrayList<Comments> commentsList = new ArrayList<>();

    private ItemListListener itemListListener;

    public CommentsListFragment() {
        //setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemListListener) {
            this.itemListListener = (ItemListListener) context;
        }
        if(context instanceof MainActivity)
            sharedPreferences = ((MainActivity)context).sharedPreferences;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(ItemComponent.class).inject(this);
        homePresenter.setSharedPreferences(sharedPreferences);
        if(getArguments() != null){
            if(getArguments().containsKey("FIRST")) {
                firstID = getArguments().getString("FIRST", null);
                homePresenter.setFirstId(firstID);
            }
            if(getArguments().containsKey("SECOND")) {
                secondId = getArguments().getString("SECOND", null);
                homePresenter.setSecondId(secondId);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_list, container, false);
        unbind = ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.homePresenter.setView(this);
        if (commentsList.size() == 0) {
            this.loadItemList();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.homePresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.homePresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rv_entity.setAdapter(null);
        unbind.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.homePresenter.destroy();
        this.homePresenter = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //this.itemListListener = null;
    }

    @Override
    public void showLoading() {
        this.fl_load.setVisibility(View.VISIBLE);
        //this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {
        this.fl_load.setVisibility(View.GONE);
        //this.getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void showRetry() {
        this.cl_retry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        this.cl_retry.setVisibility(View.GONE);
    }

    @Override
    public void renderList(Collection<Comments> commentsModelCollection) {
        if (commentsModelCollection != null) {
            commentsList.addAll(commentsModelCollection);
            this.commentsAdapter.setArticleCollection(commentsList);
        }
    }

    @Override
    public void viewEntity(Comments comments) {
        if (this.itemListListener != null) {
            this.itemListListener.onItemClicked(comments);
        }
    }

    @Override
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }

    private void setupRecyclerView() {
        this.commentsAdapter.setOnItemClickListener(onItemClickListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context());
        this.rv_entity.setLayoutManager(linearLayoutManager);
        this.rv_entity.setNestedScrollingEnabled(false);
        this.rv_entity.setAdapter(commentsAdapter);
        EndlessRecyclerViewListener endlessRecyclerViewListener = new EndlessRecyclerViewListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if(commentsList.size() >= HomePresenter.LIMIT)
                    homePresenter.loadCommentList(page);

            }
        };
        rv_entity.addOnScrollListener(endlessRecyclerViewListener);
    }

    /**
     * Loads all items.
     */
    private void loadItemList() {
        this.homePresenter.initialize();
    }

    @OnClick(R.id.bt_retry) void onButtonRetryClick() {
        this.loadItemList();
    }


    private CommentsAdapter.OnItemClickListener onItemClickListener = entityModel -> {
        if (CommentsListFragment.this.homePresenter != null && entityModel != null) {
            CommentsListFragment.this.homePresenter.onItemClicked(entityModel);
        }
    };



}
