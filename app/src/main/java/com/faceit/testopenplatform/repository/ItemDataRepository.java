package com.faceit.testopenplatform.repository;

import com.faceit.testopenplatform.model.Comments;

import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * {@link ItemRepository} for retrieving user data.
 */
@Singleton
public class ItemDataRepository implements ItemRepository {

    private final CloudItemDataStore userDataStoreFactory;

    /**
     * Constructs a {@link ItemRepository}.
     *
     * @param cloudItemDataStore   {@link CloudItemDataStore}.
     */
    @Inject
    ItemDataRepository(CloudItemDataStore cloudItemDataStore) {
        this.userDataStoreFactory = cloudItemDataStore;
    }

    @Override
    public Observable<Response<ArrayList<Comments>>> getComments(Map<String, String> parameters) {
        return userDataStoreFactory.getComments(parameters);
    }
}
