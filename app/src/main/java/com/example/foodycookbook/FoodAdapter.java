package com.example.foodycookbook;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
//import androidx.annotation.NonNull;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import static android.content.ContentValues.TAG;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private Context context;

    private ApiData data;

    public FoodAdapter(Context context, ApiData data){
        this.context = context;
        this.data = data;

    }
    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list,parent,false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        ApiData user = data;
        holder.DishName.setText(user.getStrMeal());
        holder.strArea.setText(user.getStrArea());
        holder.strCategory.setText(user.getStrCategory());
        holder.strDesc.setText(user.getStrInstructions());
        Glide.with(holder.Dishimg.getContext()).load(user.getStrMealThumb()).into(holder.Dishimg);


    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder{
        ImageView Dishimg;
        TextView DishName,strCategory,strArea,strDesc;

        public FoodViewHolder(View itemview){
            super(itemview);
            DishName = itemview.findViewById(R.id.DishName);
            strArea=itemview.findViewById(R.id.strArea);
            strCategory= itemview.findViewById(R.id.strCategory);
            strDesc = itemview.findViewById(R.id.strInstruction);
            Dishimg=itemview.findViewById(R.id.dish_image);
        }
    }
}
