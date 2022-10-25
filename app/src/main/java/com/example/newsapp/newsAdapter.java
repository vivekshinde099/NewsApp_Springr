package com.example.newsapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;

import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class newsAdapter extends RecyclerView.Adapter<newsAdapter.viewHolder> {

    private Activity context;
    private Pojo arrayList;

    public newsAdapter(Activity context, Pojo arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    private newsAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(newsAdapter.OnItemClickListener listener) {
        listener = this.listener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.news_adapter, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.title.setText(arrayList.getSources().get(position).getDescription());

        Glide.with(context) //1
                .load("https://ranartblog.com/images/depth-drawing.jpg")
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .placeholder(R.drawable.newslogo)
                .apply(RequestOptions.signatureOf(new ObjectKey(String.valueOf(System.currentTimeMillis()))))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                .into(viewHolder.imageV);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,ViewNewsDetails.class);
                context.startActivity(intent);
                Common.description=arrayList.getSources().get(position).getDescription();
                Common.link=arrayList.getSources().get(position).getUrl();
                Common.category=arrayList.getSources().get(position).getCategory();
                Common.name=arrayList.getSources().get(position).getName();
            }
        });

        //setting globally value to carry forward to next page

    }

    @Override
    public int getItemCount() {
        if(arrayList!=null)
        return arrayList.getSources().size();
        else return 0;
    }

    public Pojo getData() {
        return arrayList;
    }

    public static class viewHolder extends RecyclerView.ViewHolder {

        TextView title, publishedAt,name,category;
        static ImageView imageV;
        static CardView card;
        LinearLayout layout;

        public viewHolder(View itemView){
            super(itemView);
            title=itemView.findViewById(R.id.title);
            publishedAt=itemView.findViewById(R.id.publishedAt);
            imageV=itemView.findViewById(R.id.image);
            card=itemView.findViewById(R.id.cardView);
            layout=itemView.findViewById(R.id.layout_click);
            name = itemView.findViewById(R.id.name);
            category = itemView.findViewById(R.id.category);



        }
    }





}