package com.faceit.testopenplatform.net;

import com.faceit.testopenplatform.model.Comments;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;


import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Retrofit interface for make request
 */

public interface RetrofitRequestService {

    @GET("comments")
    Observable<Response<ArrayList<Comments>>> getComments(@QueryMap Map<String, String> parameters);

}
