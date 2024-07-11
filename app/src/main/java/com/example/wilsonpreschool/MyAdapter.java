package com.example.wilsonpreschool;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private List<DataClass> dataList;
    private List<DataClass> originalList; // To store the original list

    public MyAdapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.originalList = new ArrayList<>(dataList); // Make a copy of the original list
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataClass data = dataList.get(position);
        holder.name.setText(data.getName());
        holder.gender.setText(data.getGender());
//        holder.birth.setText(data.getBirth());
//        holder.address.setText(data.getAddress());
//        holder.parent.setText(data.getParent());
//        holder.home.setText(data.getHome());
//        holder.parents.setText(data.getParents());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    DataClass data = dataList.get(pos);
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("Name", data.getName());
                    intent.putExtra("Gender", data.getGender());
                    intent.putExtra("Date of Birth", data.getBirth());
                    intent.putExtra("Address", data.getAddress());
                    intent.putExtra("Parent's Name", data.getParent());
                    intent.putExtra("Home Phone Number", data.getHome());
                    intent.putExtra("Parent's Phone Number", data.getParents());
                    intent.putExtra("Key", data.getKey());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void searchDataList(ArrayList<DataClass> searchList){
        dataList.clear();
        dataList.addAll(searchList);
        notifyDataSetChanged();
    }

    public void resetDataList() {
        dataList.clear();
        dataList.addAll(originalList);
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, gender, birth, address, parent, home, parents;
        CardView recCard;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameApp);
            gender = itemView.findViewById(R.id.genderApp);
            birth = itemView.findViewById(R.id.birthApp);
            address = itemView.findViewById(R.id.addressApp);
            parent = itemView.findViewById(R.id.pnameApp);
            home = itemView.findViewById(R.id.hphoneApp);
            parents = itemView.findViewById(R.id.pphoneApp);
            recCard = itemView.findViewById(R.id.recCard);
        }
    }
}