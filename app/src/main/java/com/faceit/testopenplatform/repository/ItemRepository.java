package com.faceit.testopenplatform.repository;


import com.faceit.testopenplatform.model.Comments;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;


/**
 * Interface that represents a Repository for getting {@link Comments} related data.
 */
public interface ItemRepository {
    /**
     * Get an {@link Observable} which will emit a List of {@link Comments}.
     */
    Observable<Response<ArrayList<Comments>>> getComments(Map<String, String> parameters);

}
