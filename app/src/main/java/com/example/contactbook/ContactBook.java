package com.example.contactbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactBook extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Model> data;
    MyAdaptar adaptar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_book);
        setTitle("Contact List");

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        data = new ArrayList<>();
        Cursor cursor = new DBConnector(this).viewContactBook();
        if(cursor.getCount() == 0){
            Toast.makeText(ContactBook.this, "Data not found", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            while (cursor.moveToNext()){
                String id = Integer.toString(cursor.getInt(0));
                String name = cursor.getString(1);
                String contact = cursor.getString(2);
                Model obj = new Model(id, name, contact);
                data.add(obj);
            }
        }

        adaptar = new MyAdaptar(this, data);
        recyclerView.setAdapter(adaptar);
    }
}