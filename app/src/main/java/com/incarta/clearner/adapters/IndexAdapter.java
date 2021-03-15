package com.incarta.clearner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.incarta.clearner.R;
import com.incarta.clearner.models.IndexModel;
import com.incarta.clearner.models.SubtitleModel;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

public class IndexAdapter extends RecyclerView.Adapter<IndexAdapter.ViewHolder> {
    Context context;
    List<IndexModel> iList;

    public IndexAdapter(Context context, List<IndexModel> iList) {
        this.context = context;
        this.iList = iList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_index_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_title.setText(iList.get(position).getTitle());
        List<SubtitleModel> sList = iList.get(position).getSubtitles();
        holder.rv_subTitles.setLayoutManager(new LinearLayoutManager(context));
        SubtitleAdapter subtitleAdapter = new SubtitleAdapter(context,sList);
        holder.rv_subTitles.setAdapter(subtitleAdapter);
        holder.tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.el.toggle();
            }
        });

    }

    @Override
    public int getItemCount() {
        return iList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        RecyclerView rv_subTitles;
        ExpandableLayout el;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.single_title);
            rv_subTitles = itemView.findViewById(R.id.recycler_view_subtitles);
            el = itemView.findViewById(R.id.expandable_layout);
        }
    }
}
