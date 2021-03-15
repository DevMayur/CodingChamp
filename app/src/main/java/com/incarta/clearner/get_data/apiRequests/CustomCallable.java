package com.incarta.clearner.get_data.apiRequests;

import java.util.concurrent.Callable;

public interface CustomCallable<R> extends Callable<R> {
    void setDataAfterLoading(R result);
    void setUiForLoading();
}