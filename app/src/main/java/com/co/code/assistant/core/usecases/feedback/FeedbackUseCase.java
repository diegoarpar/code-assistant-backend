package com.co.code.assistant.core.usecases.feedback;

import com.co.code.assistant.core.domains.implementation.LogDomain;
import com.co.code.assistant.core.usecases.SuggestionSafeUseCase;
import io.reactivex.rxjava3.core.Observable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FeedbackUseCase implements SuggestionSafeUseCase<List<LogDomain>, Map<String, List<String>>> {


    @Override
    public Observable<List<LogDomain>> fallback(Throwable t) {
        return Observable.just(getFallbackObject());
    }

    @Override
    public Observable<List<LogDomain>> run(Map<String, List<String>> params) {

        return Observable.just(Collections.EMPTY_LIST);
    }

    public List<LogDomain> getFallbackObject() {
        return Collections.EMPTY_LIST;
    }
}
