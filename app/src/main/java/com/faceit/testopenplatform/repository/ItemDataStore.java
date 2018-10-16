package com.faceit.testopenplatform.repository;


import com.faceit.testopenplatform.model.Comments;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public interface ItemDataStore {
    /**
     * Get an {@link Observable} which will emit a List of {@link Comments}.
     */
    Observable<Response<ArrayList<Comments>>> getComments(Map<String, String> parameters);

}
