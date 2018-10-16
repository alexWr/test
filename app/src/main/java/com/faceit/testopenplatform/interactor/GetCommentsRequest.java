
package com.faceit.testopenplatform.interactor;

import com.faceit.testopenplatform.executor.PostExecutionThread;
import com.faceit.testopenplatform.model.Comments;
import com.faceit.testopenplatform.repository.ItemRepository;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Response;


/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link ?}.
 */
public class GetCommentsRequest extends UseCase<Response<ArrayList<Comments>>, Map<String, String>> {

    private final ItemRepository userRepository;

    @Inject
    GetCommentsRequest(ItemRepository userRepository, Executor threadExecutor,
                       PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    Observable<Response<ArrayList<Comments>>> buildUseCaseObservable(Map<String, String> parameters) {
        return this.userRepository.getComments(parameters);
    }
}
