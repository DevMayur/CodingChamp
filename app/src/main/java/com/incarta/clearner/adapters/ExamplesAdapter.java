package com.incarta.clearner.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.AdView;
import com.incarta.clearner.R;
import com.incarta.clearner.activities.CustomCodeViewerActivity;
import com.incarta.clearner.activities.HomeActivity;
import com.incarta.clearner.models.ExamplesModel;

import java.util.List;

public class ExamplesAdapter extends RecyclerView.Adapter<ExamplesAdapter.ViewHolder> {
    Context context;
    List<ExamplesModel> eList;

    public ExamplesAdapter(Context context, List<ExamplesModel> eList) {
        this.context = context;
        this.eList = eList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_example_title, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.title.setText(eList.get(position).getProgram_title());



//        holder.codeView.setOptions(Options.Default.get(context)
//                .withLanguage("c")
//                .withCode(eList.get(position).getCode())
//                .withTheme(ColorTheme.MONOKAI));
//
//
//        holder.outputView.setOptions(Options.Default.get(context)
//                .withLanguage("c")
//                .withCode(eList.get(position).getOutput())
//                .withTheme(ColorTheme.SOLARIZED_LIGHT));
//
//        holder.exaplainationView.setOptions(Options.Default.get(context)
//                .withLanguage("c")
//                .withCode(eList.get(position).getExplanation())
//                .withTheme(ColorTheme.DEFAULT));

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AdView oldAd = ((HomeActivity)context).adView;
                if (oldAd != null) {
                    oldAd.destroy();
                }

                Intent intent = new Intent(context, CustomCodeViewerActivity.class);
                intent.putExtra("program_title",eList.get(position).getProgram_title());
                intent.putExtra("code",eList.get(position).getCode());
                intent.putExtra("output",eList.get(position).getOutput());
                intent.putExtra("explanation",eList.get(position).getExplanation());
                intent.putExtra("title_id",eList.get(position).getTitle_id());
                intent.putExtra("subtitle_id",eList.get(position).getSubtitle_id());
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return eList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_single_example);

        }
    }
}
