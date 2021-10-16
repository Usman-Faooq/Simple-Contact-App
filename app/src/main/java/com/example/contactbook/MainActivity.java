package com.example.contactbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText personname, personcontact;
    Button addcontact;
    FloatingActionButton book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Create New Contact");
        personname = findViewById(R.id.personname);
        personcontact = findViewById(R.id.personphone);
        addcontact = findViewById(R.id.Addcontact);
        book = findViewById(R.id.contactBook);

        DBConnector db = new DBConnector(this);

        addcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = personname.getText().toString().trim();
                String contact = personcontact.getText().toString().trim();
                String checkname = null;

                Cursor cursor = new DBConnector(MainActivity.this).viewContactBook();
                while(cursor.moveToNext()){
                    checkname = cursor.getString(1);
                }
                if (name.equals("") || contact.equals("")){
                    Toast.makeText(MainActivity.this, "All fields Required", Toast.LENGTH_SHORT).show();
                }else if (name.equals(checkname)){
                    Toast.makeText(MainActivity.this, "Name already taken", Toast.LENGTH_SHORT).show();
                }else{
                    boolean check = db.addcontact(name, contact);
                    if (check == true){
                        Toast.makeText(MainActivity.this, "Sucessfull", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Something Went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ContactBook.class);
                startActivity(i);
            }
        });
    }
}