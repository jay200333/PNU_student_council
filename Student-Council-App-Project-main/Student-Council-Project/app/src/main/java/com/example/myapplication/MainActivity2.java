package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private ListView noticeListView;
    private NoticeListAdapter adapter2;
    private List<Notice> noticeList;


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter1;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Information> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerview);
        try {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter1);//Recyclerview에 어댑터 연결
        }
        catch (NullPointerException ignored){

    }

        layoutManager=new LinearLayoutManager(this);

        arrayList=new ArrayList<Information>();//Information 담을 리스트

        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("Information");


        adapter1=new CustomAdapter(arrayList,this);



        /**mPostRecyclerView = findViewById(R.id.main_recyclerview);
        mDatas= new ArrayList<>();
        mDatas.add(new Board_Notice(null, "title", "content"));
        mDatas.add(new Board_Notice(null, "title", "content"));
        mDatas.add(new Board_Notice(null, "title", "content"));


        mAdapter = new PostAdapter(mDatas);
        mPostRecyclerView.setAdapter(mAdapter);**/

        noticeListView = (ListView) findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();
        adapter2 = new NoticeListAdapter(getApplicationContext(), noticeList);
        noticeListView.setAdapter(adapter2);


        final ImageButton mapButton = (ImageButton) findViewById(R.id.mapButton);
        final Button noticeButton = (Button) findViewById(R.id.noticeButton);
        //final Button scheduleButton = (Button) findViewById(R.id.scheduleButton);
        final Button informationButton = (Button) findViewById(R.id.informationButton);
        final LinearLayout notice = (LinearLayout) findViewById(R.id.notice);

        informationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Information_sub.class);
                startActivity(intent);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                        arrayList.clear(); //기존 배열리스트 초기화
                        for(DataSnapshot snapshot : datasnapshot.getChildren()){//반복문으로 데이터 List를 추출해냄
                            Information information=snapshot.getValue(Information.class);//만들어놨던 list 객체에 데이터를 담는다.
                            arrayList.add(information);//담은 데이터들을 리스트에 넣고 Recyclerview에 보낼 준비
                        }
                        adapter1.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("MainActivity2",String.valueOf(databaseError.toException()));

                    }
                });

            }

        });

        noticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Board_Notice.class);
                startActivity(intent);
            }

        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CampusMap.class);
                startActivity(intent);
            }

        });





       /** noticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notice.setVisibility(View.GONE);
                noticeButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
               // scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                informationButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment,new NoticeFragment());
                fragmentTransaction.commit();
            }
        });

      /**scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notice.setVisibility(View.GONE);
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                noticeButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                informationButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment,new ScheduleFragment());
                fragmentTransaction.commit();
            }
        });**/

        /**informationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notice.setVisibility(View.GONE);
                noticeButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
               // scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                informationButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment,new InformationFragment());
                fragmentTransaction.commit();
            }
        });**/

        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {

        String target;
        @Override
        protected void onPreExecute(){
            target = "http://mmuyaho.dothome.co.kr/NoticeList.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(temp+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String noticeContent, noticeName, noticeDate;
                while(count<jsonArray.length())
                {
                  JSONObject object = jsonArray.getJSONObject(count);
                  noticeContent = object.getString("noticeContent");
                  noticeName = object.getString("noticeName");
                  noticeDate = object.getString("noticeDate");
                  Notice notice = new Notice(noticeContent, noticeName, noticeDate);
                  noticeList.add(notice);
                  adapter2.notifyDataSetChanged();
                  count++;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
