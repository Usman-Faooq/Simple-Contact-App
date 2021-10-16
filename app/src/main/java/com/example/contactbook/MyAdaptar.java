package com.example.contactbook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URI;
import java.util.ArrayList;

public class MyAdaptar extends RecyclerView.Adapter<MyAdaptar.myviewholder> {

    ArrayList<Model> data;
    Context getcontext;

    public MyAdaptar(Context context, ArrayList<Model> data) {
        this.data = data;
        this.getcontext = context;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowdesign, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {

        holder.name.setText(data.get(position).getName());
        holder.contact.setText(data.get(position).getContact());
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String callno = holder.contact.getText().toString().trim();
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+callno));
                getcontext.startActivity(i);
            }
        });

        holder.dataview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                ContactDetail detail = new ContactDetail(holder.name.getText().toString(), holder.contact.getText().toString());
                detail.show(activity.getSupportFragmentManager(),"");

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{
        TextView name, contact;
        ImageView call;
        CardView dataview;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.layoutname);
            contact = itemView.findViewById(R.id.layoutcontact);
            call = itemView.findViewById(R.id.connectcall);
            dataview = itemView.findViewById(R.id.dataview);
        }
    }
}
