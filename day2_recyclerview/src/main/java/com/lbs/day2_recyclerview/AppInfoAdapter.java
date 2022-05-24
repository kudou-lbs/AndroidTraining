package com.lbs.day2_recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.ViewHolder> {

    //展示内容列表
    private List<AppInfo> list;
    private Context context;

    public AppInfoAdapter(List<AppInfo> list, Context context){
        this.list=list;
        this.context=context;
    }

    @NonNull
    @Override
    public AppInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contentView=LayoutInflater.from(context).inflate(R.layout.app_item,parent,false);
        return new ViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(@NonNull AppInfoAdapter.ViewHolder holder, int position) {
        AppInfo appInfo=list.get(position);
        holder.appIcon.setImageDrawable(appInfo.icon);
        holder.appName.setText(appInfo.name);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView appIcon;
        TextView appName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appIcon=itemView.findViewById(R.id.app_icon);
            appName=itemView.findViewById(R.id.app_name);
        }
    }
}
