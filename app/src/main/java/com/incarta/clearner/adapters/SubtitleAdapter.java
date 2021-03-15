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
import com.incarta.clearner.activities.ContentActivity;
import com.incarta.clearner.activities.HomeActivity;
import com.incarta.clearner.models.SubtitleModel;

import java.util.List;

public class SubtitleAdapter extends RecyclerView.Adapter<SubtitleAdapter.ViewHolder> {
    Context context;
    List<SubtitleModel> sList;

    public SubtitleAdapter(Context context, List<SubtitleModel> sList) {
        this.context = context;
        this.sList = sList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_subtitle, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_subtitle.setText(sList.get(position).getSubtitle());
        holder.tv_subtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdView oldAd = ((HomeActivity)context).adView;
                if (oldAd != null) {
                    oldAd.destroy();
                }
                Intent intent = new Intent(context, ContentActivity.class);
                intent.putExtra("subtitle_id",sList.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_subtitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_subtitle = itemView.findViewById(R.id.subtitle_text);
        }
    }
}
