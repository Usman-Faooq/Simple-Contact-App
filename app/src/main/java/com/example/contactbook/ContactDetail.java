package com.example.contactbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ContactDetail extends BottomSheetDialogFragment {
    View view;

    TextView textView;
    EditText pname, pcontact;
    FloatingActionButton edit, delete, updatedone;
    ImageView call, sms;

    String name, contact;

    public ContactDetail(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_contact_detail, container, false);

        pname = view.findViewById(R.id.detailname);
        pcontact = view.findViewById(R.id.detailcontact);
        textView = view.findViewById(R.id.textView2);
        edit = view.findViewById(R.id.editcontact);
        delete = view.findViewById(R.id.deletecontact);
        updatedone = view.findViewById(R.id.updatedone);
        call = view.findViewById(R.id.detailcall);
        sms = view.findViewById(R.id.detailsms);

        updatedone.setVisibility(View.INVISIBLE);
        pname.setText(name);
        pcontact.setText(contact);
        pname.setEnabled(false);
        pcontact.setEnabled(false);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pname.setEnabled(true);
                pcontact.setEnabled(true);
                edit.setVisibility(View.INVISIBLE);
                delete.setVisibility(View.INVISIBLE);
                updatedone.setVisibility(View.VISIBLE);
                textView.setText("Edit Contact");
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = new DBConnector(getContext()).deletedata(name);
                if (check == true){
                    Toast.makeText(getContext(), "Sucessfully Delete", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Something went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        updatedone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = pname.getText().toString();
                String contact = pcontact.getText().toString();
                if (name.equals("") || contact.equals("")){
                    Toast.makeText(getContext(), "All Fields Required", Toast.LENGTH_SHORT).show();
                }else{
                    boolean check = new DBConnector(getContext()).updatedata(name, contact);
                    if (check == true){
                        Toast.makeText(getContext(), "Update Sucessfully", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    }else{
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = pcontact.getText().toString();
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ contact));
                startActivity(i);
            }
        });

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = pcontact.getText().toString();
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + contact));
                startActivity(i);
            }
        });


        return view;
    }
}