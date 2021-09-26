package com.varun.firebasesecuritywithlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myAdapter {

    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyviewHolder> {
        Context context;
        ArrayList<Student> list;

        public MyAdapter(Context context, ArrayList<Student> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
            return new MyviewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
            Student student = list.get(position);
            holder.tv_city.setText(student.getCity());
            holder.tv_contact.setText(student.getContact());
            holder.tv_name.setText(student.getName());

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public static class MyviewHolder extends RecyclerView.ViewHolder{
            TextView tv_city,tv_contact,tv_name;

            public MyviewHolder(@NonNull View itemView) {
                super(itemView);
                tv_city = itemView.findViewById(R.id.data1);
                tv_contact = itemView.findViewById(R.id.data2);
                tv_name = itemView.findViewById(R.id.data3);
            }
        }
    }

}
