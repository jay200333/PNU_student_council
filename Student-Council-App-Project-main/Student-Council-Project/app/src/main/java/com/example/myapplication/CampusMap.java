package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CampusMap extends AppCompatActivity {


    Button btn_restaurant;
    Button btn_cafe;
    Button btn_store;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campusmap);

        final ImageButton close = (ImageButton)findViewById(R.id.close);
        btn_restaurant = (Button)findViewById(R.id.btn_restaurant);
        btn_cafe=(Button)findViewById(R.id.btn_cafe);
        btn_store=(Button)findViewById(R.id.btn_store);

        close.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View v){
                finish();
            }
        });

        btn_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(CampusMap.this);
                //ad.setIcon(R.mipmap.ic_launcher);  //여기는 아이콘을 넣음. mipmap안에 있는 ic_launcher을 넣음 안드로이드 모양의 아이콘임
                //ad.setMessage("무야호!!");
                ad.setView(R.layout.map_restaurant);


                ad.setNeutralButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }

                });
                ad.show();
            }
        });
        btn_cafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(CampusMap.this);
                //ad.setIcon(R.mipmap.ic_launcher);  //여기는 아이콘을 넣음. mipmap안에 있는 ic_launcher을 넣음 안드로이드 모양의 아이콘임
                //ad.setMessage("무야호!!");
                ad.setView(R.layout.map_cafe);


                ad.setNeutralButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }

                });
                ad.show();
            }
        });
        btn_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(CampusMap.this);
                //ad.setIcon(R.mipmap.ic_launcher);  //여기는 아이콘을 넣음. mipmap안에 있는 ic_launcher을 넣음 안드로이드 모양의 아이콘임
                //ad.setMessage("무야호!!");
                ad.setView(R.layout.map_store);


                ad.setNeutralButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }

                });
                ad.show();
            }
        });

    }




}
