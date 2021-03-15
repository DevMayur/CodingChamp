package com.incarta.clearner.get_data.apiRequests;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkTask extends BaseAsyncTask {

    private final iOnDataFetched listener;
    String url;
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String dataJson = null;


    public NetworkTask(iOnDataFetched onDataFetchedListener,String url) {
        this.listener = onDataFetchedListener;
        this.url = url;
    }

    @Override
    public Object call() {
        Object result = null;
        Log.d("exception_networking","in call");
        result = getData();
        return result;
    }

    private Object getData() {

        Uri builtURI = Uri.parse(url).buildUpon().build();

        try {
            URL requestURL = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            InputStream inputStream = urlConnection.getInputStream();

            // Read the response string into a StringBuilder.
            StringBuilder builder = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // but it does make debugging a *lot* easier if you print out the completed buffer for debugging.
                builder.append(line + "\n");
            }

            if (builder.length() == 0) {
                // Stream was empty.  No point in parsing.
                // return null;
                return null;
            }
            dataJson = builder.toString();

        }catch(Exception e) {
            Log.d("exception_networking", " exception : " + e.getMessage());
        }


        return dataJson;
    }

    @Override
    public void setUiForLoading() {
        listener.showProgressBar();
    }

    @Override
    public void setDataAfterLoading(Object result) {
        listener.setDataInPageWithResult(result);
        listener.hideProgressBar();
    }
}

