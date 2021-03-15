package com.incarta.clearner.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incarta.clearner.R;
import com.incarta.clearner.adapters.ExamplesAdapter;
import com.incarta.clearner.get_data.Config;
import com.incarta.clearner.get_data.apiRequests.NetworkTask;
import com.incarta.clearner.get_data.apiRequests.TaskRunner;
import com.incarta.clearner.get_data.apiRequests.iOnDataFetched;
import com.incarta.clearner.models.ExamplesModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExamplesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExamplesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExamplesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExamplesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExamplesFragment newInstance(String param1, String param2) {
        ExamplesFragment fragment = new ExamplesFragment();
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
    ExamplesAdapter adapter;
    List<ExamplesModel> eList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("examples_debug","in examples fragment");
        View view = inflater.inflate(R.layout.fragment_examples, container, false);
        recyclerView = view.findViewById(R.id.examples_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        eList = new ArrayList<>();
        adapter = new ExamplesAdapter(container.getContext(),eList);
        recyclerView.setAdapter(adapter);

        getExamples();

        return view;
    }

    iOnDataFetched onDataFetched;

    private void getExamples() {
        Log.d("examples_debug","in get examples");
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
                    parseExample(result.toString());
                }
            }
        };
        runner.executeAsync(new NetworkTask(onDataFetched, Config.API.ALL_EXAMPLE_URL));
    }

    private void parseExample(String result) {
        Log.d("examples_debug","in  parse example : " + result);

        String id = null;
        String title_id = null;
        String subtitle_id = null;
        String program_title = null;
        String code = null;
        String output = null;
        String explanation = null;

        try {
            JSONObject jsonObject = new JSONObject(result);
            int i = 0;
            JSONArray jsonArray = jsonObject.getJSONArray("get_examples");


            while (i < jsonArray.length()) {
                JSONObject data = jsonArray.getJSONObject(i);
                id = data.getString("id");
                title_id = data.getString("title_id");
                subtitle_id = data.getString("subtitle_id");
                program_title = data.getString("program_title");
                code = data.getString("code");
                output = data.getString("output");
                explanation = data.getString("explanation");

                ExamplesModel model = new ExamplesModel(id,title_id,subtitle_id,program_title,code,output,explanation);

                eList.add(model);
                adapter.notifyDataSetChanged();

                i++;

            }

        }catch(Exception e) {
            Log.d("tag_for_data", " exception : " + e.getMessage());
        }

    }
}