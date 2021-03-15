package com.incarta.clearner.get_data.apiRequests;

public interface iOnDataFetched{
    void showProgressBar();
    void hideProgressBar();
    void setDataInPageWithResult(Object result);
}
