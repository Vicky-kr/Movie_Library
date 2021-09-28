package com.example.volley;

import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {
    private int[] listdata;

    private List<JSONObject> objectList;

    // RecyclerView recyclerView;
    public MyListAdapter(int[] listdata) {
        this.listdata = listdata;
    }

    public MyListAdapter(List<JSONObject> list) {
        this.objectList = list;
    }

    View view;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.content_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        view = listItem;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        final int myListData = listdata[position];
//        holder.title.setText(listdata[position]);
        String myListData = null;
        try {
            myListData = objectList.get(position).getString(Constants.IMDBID);
            holder.title.setText((objectList.get(position).getString(Constants.TITLE)));
            holder.releaseYear.setText(objectList.get(position).getString(Constants.YEAR));
            holder.type.setText(objectList.get(position).getString(Constants.TYPE));
            Glide.with(view).load(objectList.get(position).getString(Constants.POSTER)).into(holder.imageView);

        } catch (JSONException e) {
            e.printStackTrace();
        }


//        holder.imageView.setImageResource(listdata[position].getImgId());
        String finalMyListData = myListData;
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(view.getContext(),MovieDetailsActivity.class)
                        .putExtra(Constants.MOVIE_ID,finalMyListData));
            }
        });


    }


    @Override
    public int getItemCount() {

//        return listdata.length;
        return objectList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView title;
        public TextView releaseYear;
        public TextView type;
        //        public RelativeLayout relativeLayout;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.releaseYear = (TextView) itemView.findViewById(R.id.released);
            this.type = (TextView) itemView.findViewById(R.id.type);
//            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
            linearLayout = itemView.findViewById(R.id.linearLayoutS);
        }
    }

}
