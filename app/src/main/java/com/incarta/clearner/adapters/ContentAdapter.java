package com.incarta.clearner.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.incarta.clearner.R;
import com.incarta.clearner.get_data.Config;
import com.incarta.clearner.models.ContentModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.github.kbiakov.codeview.CodeView;
import io.github.kbiakov.codeview.adapters.Options;
import io.github.kbiakov.codeview.highlight.ColorTheme;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {
    Context context;
    List<ContentModel> cList;

    public ContentAdapter(Context context, List<ContentModel> cList) {
        this.context = context;
        this.cList = cList;
    }

    @NonNull
    @Override
    public ContentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContentAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_content,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContentAdapter.ViewHolder holder, int position) {

        if (!cList.get(position).getContent_title().equals("") && cList.get(position).getContent_title() != null) {
            holder.tv_title.setText(cList.get(position).getContent_title());
        }else {
            holder.tv_title.setVisibility(View.GONE);
        }

        if (!cList.get(position).getContent_text().equals("") && cList.get(position).getContent_text() != null) {
            holder.tv_text.setText(cList.get(position).getContent_text());
        }else {
            holder.tv_text.setVisibility(View.GONE);
        }

        if (!cList.get(position).getContent_img().equals("") && cList.get(position).getContent_img() != null) {
            Picasso.get().load(Config.API.IMAGE_URL+cList.get(position).getContent_img()).into(holder.iv_image);
        }else {
            holder.iv_image.setVisibility(View.GONE);
        }


        if (!cList.get(position).getContent_code().equals("") && cList.get(position).getContent_code() != null) {
            holder.codeView.setVisibility(View.VISIBLE);
            holder.codeView.setOptions(Options.Default.get(context)
                    .withLanguage("c")
                    .withCode(cList.get(position).getContent_code())
                    .withTheme(ColorTheme.MONOKAI));

        }else {
            holder.codeView.setVisibility(View.GONE);
        }


    }


    public static final String TAG = "cAdpater";

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + cList.size());
        return cList.size();
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_image;
        TextView tv_title,tv_text;
        CodeView codeView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_image = itemView.findViewById(R.id.iv_content_image);
            tv_title = itemView.findViewById(R.id.content_title);
            tv_text = itemView.findViewById(R.id.content_description);
            codeView = itemView.findViewById(R.id.codeView);

        }
    }
}
