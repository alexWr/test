package com.faceit.testopenplatform.repository;



import com.faceit.testopenplatform.model.Comments;
import com.faceit.testopenplatform.net.RetrofitRequestService;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Response;


/**
 * {@link ItemDataStore} implementation based on connections to the api (Cloud).
 */
public class CloudItemDataStore implements ItemDataStore {

    RetrofitRequestService restApi;

    /**
     * Construct a {@link ItemDataStore} based on connections to the api (Cloud).
     *
     * @param restApi The {@link RetrofitRequestService} implementation to use.
     */
    @Inject
    public CloudItemDataStore(RetrofitRequestService restApi) {
        this.restApi = restApi;
    }


    @Override
    public Observable<Response<ArrayList<Comments>>> getComments(Map<String, String> parameters) {
        return restApi.getComments(parameters).delay(5, TimeUnit.SECONDS);
    }
}
