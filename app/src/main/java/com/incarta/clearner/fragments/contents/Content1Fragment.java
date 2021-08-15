package com.incarta.clearner.fragments.contents;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incarta.clearner.R;
import com.incarta.clearner.activities.ContentActivity;
import com.incarta.clearner.adapters.ContentAdapter;
import com.incarta.clearner.get_data.Config;
import com.incarta.clearner.get_data.apiRequests.NetworkTask;
import com.incarta.clearner.get_data.apiRequests.TaskRunner;
import com.incarta.clearner.get_data.apiRequests.iOnDataFetched;
import com.incarta.clearner.models.ContentModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Content1Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Content1Fragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Content1Fragment newInstance(String param1, String param2) {
        Content1Fragment fragment = new Content1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    RecyclerView recyclerView;
    List<ContentModel> cList;
    ContentAdapter adapter;
    String content_id = "0";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content1, container, false);
        content_id = ((ContentActivity)container.getContext()).getSubtitleId();

        recyclerView = view.findViewById(R.id.recycler_view_content1);
        cList = new ArrayList<>();
        adapter = new ContentAdapter(container.getContext(), cList);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(adapter);

        fetchData();

        // Inflate the layout for this fragment
        return view;
    }

    iOnDataFetched onDataFetched;

    private void fetchData() {
        TaskRunner runner = new TaskRunner();

        onDataFetched = new iOnDataFetched() {
            @Override
            public void showProgressBar() {

            }

            @Override
            public void hideProgressBar() {

            }

            @Override
            public void setDataInPageWithResult(Object result) {
                if (result != null) {
                    String jsonData = result.toString();
                    Log.d(TAG, "setDataInPageWithResult: ");

//                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("contents", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString(content_id, jsonData);
//                    editor.apply();

                    getData(jsonData);
                }
            }
        };


            runner.executeAsync(new NetworkTask(onDataFetched, Config.API.CONTENT_URL + content_id));
        


        adapter.notifyDataSetChanged();
    }


    public static final String TAG = "content1";

    private void getData(String jsonData) {
        Log.d(TAG, "getData: ");
//        Log.d(TAG, "getData: " + jsonData);
            String id = null;
            String content_title = null;
            String content_text = null;
            String content_img = null;
            String subtitle_id = null;
            String content_code = null;

            try {
                Log.d(TAG, "getData: try");
                JSONObject jsonObject = new JSONObject(jsonData);
                int i = 0;
                JSONArray jsonArray = jsonObject.getJSONArray("get_subtitles");

                while (i < jsonArray.length()) {

                    JSONObject data = jsonArray.getJSONObject(i);

                    id = data.getString("id");
                    content_title = data.getString("content_title");
                    content_text = data.getString("content_text");
                    content_img = data.getString("content_img");
                    subtitle_id = data.getString("subtitle_id");
                    content_code = data.getString("content_code");
                    cList.add(new ContentModel(id,content_title,content_text,content_img,subtitle_id,content_code));
                    i++;

                    Log.d(TAG, "getData: " + i);

                }

                Log.d(TAG,  " out getData: " + i);
                adapter.notifyDataSetChanged();

            }catch(Exception e) {
                Log.d(TAG, "getData: exception");
                Log.d("tag_for_data2", " exception : " + e.getMessage());
            }
    }
}