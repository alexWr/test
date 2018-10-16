package com.faceit.testopenplatform.presenter;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;


import com.faceit.testopenplatform.R;
import com.faceit.testopenplatform.di.scopes.PerActivity;
import com.faceit.testopenplatform.exception.ArrayIsEmptyException;
import com.faceit.testopenplatform.exception.DefaultErrorBundle;
import com.faceit.testopenplatform.exception.ErrorBundle;
import com.faceit.testopenplatform.exception.ErrorMessageFactory;
import com.faceit.testopenplatform.interactor.DefaultObserver;
import com.faceit.testopenplatform.interactor.GetCommentsRequest;
import com.faceit.testopenplatform.model.Comments;
import com.faceit.testopenplatform.view.ItemListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.inject.Inject;



import retrofit2.Response;


/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
public class HomePresenter implements Presenter {

    public static final int LIMIT = 10;

    private ItemListView viewItemListView;

    private final GetCommentsRequest getItemsListUseCase;
    private SharedPreferences sharedPreferences;
    private String firstId;
    private String secondId;

    @Inject
    public HomePresenter(GetCommentsRequest getUserListItemCase) {
        this.getItemsListUseCase = getUserListItemCase;
    }

    public void setView(@NonNull ItemListView view) {
        this.viewItemListView = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {
        hideViewLoading();
    }

    @Override
    public void destroy() {
        this.getItemsListUseCase.dispose();
        this.viewItemListView = null;
    }

    /**
     * Initializes the presenter by start retrieving the user list.
     */
    public void initialize() {
        this.loadCommentList(1);
    }

    /**
     * Loads all articles.
     */
    public void loadCommentList(int page) {
        this.hideViewRetry();
        this.showViewLoading();
        this.getCommentsList(page);
    }

    public void onItemClicked(Comments article) {
        this.viewItemListView.viewEntity(article);
    }

    private void showViewLoading() {
        this.viewItemListView.showLoading();
    }

    private void hideViewLoading() {
        this.viewItemListView.hideLoading();
    }

    private void showViewRetry() {
        this.viewItemListView.showRetry();
    }

    private void hideViewRetry() {
        this.viewItemListView.hideRetry();
    }

    public void setFirstId(String firstId) {
        this.firstId = firstId;
    }

    public void setSecondId(String secondId) {
        this.secondId = secondId;
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewItemListView.context(),
                errorBundle.getException());
        this.viewItemListView.showError(errorMessage);
    }

    private void showUsersCollectionInView(Collection<Comments> itemCollection) {

        this.viewItemListView.renderList(itemCollection);
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    private void getCommentsList(int page) {
        HashMap<String, String> queryParameters = new HashMap<>();
        queryParameters.put("_limit", String.valueOf(LIMIT));
        queryParameters.put("_page", String.valueOf(page));
        queryParameters.put("_sort", "id");
        queryParameters.put("_order", "asc");
        queryParameters.put("id_gte", firstId);
        queryParameters.put("id_lte", secondId);
        this.getItemsListUseCase.execute(new ItemListObserver(), queryParameters);
    }

    private final class ItemListObserver extends DefaultObserver<Response<ArrayList<Comments>>> {

        @Override
        public void onComplete() {
            HomePresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            HomePresenter.this.hideViewLoading();
            HomePresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            HomePresenter.this.showViewRetry();
        }

        @Override
        public void onNext(Response<ArrayList<Comments>> response) {
            if(response.isSuccessful() && response.body() != null &&
                    response.body().size() > 0) {
                HomePresenter.this.showUsersCollectionInView(response.body());
            }
            else{
                ArrayIsEmptyException exception = new ArrayIsEmptyException(
                        new Throwable(HomePresenter.this.viewItemListView.context().getString(R.string.exception_array_is_empty)));
                HomePresenter.this.showErrorMessage(new DefaultErrorBundle(exception));
            }

        }
    }
}
