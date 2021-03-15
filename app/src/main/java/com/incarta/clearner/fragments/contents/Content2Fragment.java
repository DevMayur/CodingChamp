package com.incarta.clearner.fragments.contents;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.incarta.clearner.R;
import com.incarta.clearner.activities.ContentActivity;
import com.incarta.clearner.get_data.Config;
import com.incarta.clearner.get_data.apiRequests.NetworkTask;
import com.incarta.clearner.get_data.apiRequests.TaskRunner;
import com.incarta.clearner.get_data.apiRequests.iOnDataFetched;
import com.incarta.clearner.models.ExamplesModel;

import org.json.JSONArray;
import org.json.JSONObject;

import io.github.kbiakov.codeview.CodeView;
import io.github.kbiakov.codeview.adapters.Options;
import io.github.kbiakov.codeview.highlight.ColorTheme;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Content2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Content2Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Content2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Content2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Content2Fragment newInstance(String param1, String param2) {
        Content2Fragment fragment = new Content2Fragment();
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


    ExamplesModel model;
    TextView title;
    CodeView codeView;
    CodeView outputView;
    CodeView exaplainationView;
    String content_id = "0";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content2, container, false);
        content_id = ((ContentActivity)container.getContext()).getSubtitleId();
        // Inflate the layout for this fragment
        title = view.findViewById(R.id.single_title_example);
        codeView = view.findViewById(R.id.codeView);
        outputView = view.findViewById(R.id.outputView);
        exaplainationView = view.findViewById(R.id.explainationView);
        getExampleById();

        return view;
    }

    iOnDataFetched onDataFetched;

    private void getExampleById() {
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
        runner.executeAsync(new NetworkTask(onDataFetched, Config.API.EXAMPLE_BY_SUBTITLE_URL +content_id));
    }

    private void parseExample(String result) {
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

                model = new ExamplesModel(id,title_id,subtitle_id,program_title,code,output,explanation);



                title.setText(model.getProgram_title());
                codeView.setOptions(Options.Default.get(getContext())
                        .withLanguage("c")
                        .withCode(model.getCode())
                        .withTheme(ColorTheme.MONOKAI));

                outputView.setOptions(Options.Default.get(getContext())
                        .withLanguage("c")
                        .withCode(model.getOutput())
                        .withTheme(ColorTheme.SOLARIZED_LIGHT));

                exaplainationView.setOptions(Options.Default.get(getContext())
                        .withLanguage("c")
                        .withCode(model.getExplanation())
                        .withTheme(ColorTheme.DEFAULT));

                i++;

            }

        }catch(Exception e) {
            Log.d("tag_for_data", " exception : " + e.getMessage());
        }

    }
}