package com.incarta.clearner.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.facebook.ads.AdView;
import com.incarta.clearner.R;
import com.incarta.clearner.activities.HomeActivity;
import com.incarta.clearner.adapters.IndexAdapter;
import com.incarta.clearner.get_data.Config;
import com.incarta.clearner.get_data.apiRequests.NetworkTask;
import com.incarta.clearner.get_data.apiRequests.TaskRunner;
import com.incarta.clearner.get_data.apiRequests.iOnDataFetched;
import com.incarta.clearner.models.IndexModel;
import com.incarta.clearner.models.SubtitleModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    IndexAdapter adapter;
    List<IndexModel> iList;
    ProgressBar progressBar;
    LinearLayout homeScreen;
    LinearLayout discord_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        progressBar = view.findViewById(R.id.homeProgress);
        homeScreen = view.findViewById(R.id.home_ll);
        discord_view = view.findViewById(R.id.ll_discord);
        recyclerView = view.findViewById(R.id.home_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        iList = new ArrayList<>();
        adapter = new IndexAdapter(container.getContext(), iList);
        recyclerView.setAdapter(adapter);
        fetchData();


        discord_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)container.getContext()).rateApplication();
                AdView oldAd = ((HomeActivity)container.getContext()).adView;
                if (oldAd != null) {
                    oldAd.destroy();
                }

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Config.API.DISCORD_SERVER_INVITE));
                startActivity(browserIntent);

            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    iOnDataFetched onDataFetched;
    iOnDataFetched onSubsFetched;

    private void fetchData() {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("titles", Context.MODE_PRIVATE);
        String titles = sharedPreferences.getString("titlesJson","null");
        if (!titles.equals("null")) {
            getData(titles);
        }else {
            TaskRunner runner = new TaskRunner();

            onDataFetched = new iOnDataFetched() {
                @Override
                public void showProgressBar() {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void hideProgressBar() {

                }

                @Override
                public void setDataInPageWithResult(Object result) {
                    if (result != null) {
                        String jsonData = result.toString();
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("titles", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("titlesJson", jsonData);
                        editor.apply();
                        getData(jsonData);
                    }
                }
            };
            runner.executeAsync(new NetworkTask(onDataFetched, Config.API.TITLES_URL));
        }

        adapter.notifyDataSetChanged();
    }

    private void getData(String jsonData) {
        String id = null;
        String title = null;
        String no_of_subtitles = null;
        String description = null;

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            int i = 0;
            JSONArray jsonArray = jsonObject.getJSONArray("index");
//            Log.d("tag_for_data","data : " + jsonArray.length());

            while (i < jsonArray.length()) {
                JSONObject data = jsonArray.getJSONObject(i);
                id = data.getString("id");
                title = data.getString("title");
                no_of_subtitles = data.getString("no_of_subtitles");
                description = data.getString("description");
//                GET SUBTITLES
                List<SubtitleModel> sList = getSubTitlesById(id);
                iList.add(new IndexModel(title,sList));


//                Log.d("tag_for_data","data : " + data.getString("title"));

                adapter.notifyDataSetChanged();
                i++;

            }

        }catch(Exception e) {
            Log.d("tag_for_data", " exception : " + e.getMessage());
        }


    }

    private List<SubtitleModel> getSubTitlesById(String id) {
        List<SubtitleModel> sList = new ArrayList<>();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("subtitlesJson",Context.MODE_PRIVATE);
        String subtitles = sharedPreferences.getString(id,"null");
        if (!subtitles.equals("null")) {
            sList.addAll(parseSubtitles(subtitles,id));
        }else {
            TaskRunner runner = new TaskRunner();
            onSubsFetched = new iOnDataFetched() {
                @Override
                public void showProgressBar() {
//                progressBar.setVisibility(View.VISIBLE);
                    homeScreen.setVisibility(View.VISIBLE);
                }

                @Override
                public void hideProgressBar() {
//                progressBar.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);

                }

                @Override
                public void setDataInPageWithResult(Object res) {
                    String result = res.toString();
                    sList.addAll(parseSubtitles(result, id));
                }
            };
            runner.executeAsync(new NetworkTask(onSubsFetched, Config.API.SUBTITLES_URL + id));
        }
        return sList;
    }

    private List<SubtitleModel> parseSubtitles(String result, String ids) {
        List<SubtitleModel> sList = new ArrayList<>();
        SharedPreferences sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences("subtitlesJson", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ids,result);
        editor.apply();

        if (result != null) {
            String jsonData = result.toString();
            String id = null;
            String subtitle_name = null;
            String title_id = null;
            String content_1 = null;
            String content_2 = null;
            String content_3 = null;
            try {
                JSONObject jsonObject = new JSONObject(jsonData);
                int i = 0;
                JSONArray jsonArray = jsonObject.getJSONArray("get_subtitles");
                while (i < jsonArray.length()) {
                    JSONObject data = jsonArray.getJSONObject(i);
                    id = data.getString("id");
                    subtitle_name = data.getString("subtitle_name");
                    title_id = data.getString("title_id");
                    content_1 = data.getString("content_1");
                    content_2 = data.getString("content_2");
                    content_3 = data.getString("content_3");
                    sList.add(new SubtitleModel(id,subtitle_name,false,content_1,content_2,content_3));
                    adapter.notifyDataSetChanged();
                    i++;
                }
            }catch(Exception e) {
                Log.d("tag_for_data", " exception : " + e.getMessage());
            }
        }
        return sList;
    }
}