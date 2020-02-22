package com.yeonkyu.diary;

import android.app.Activity;
import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;


import android.content.Intent;


public class MainActivity extends AppCompatActivity {


    final ArrayList<String> list = new ArrayList<>(); // 제목 저장
    ArrayList<TextFileManager> TFM = new ArrayList<>(); // file manager 저장
    ListView listView;
    ListAdapter adapter;
    int n = 0;
    //final static String filePath = Environment.getExternalStorageDi()


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);   //툴바
        setSupportActionBar(toolbar);


        Button fab = findViewById(R.id.fab);                //새 메모 만들기
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "새 메모를 만듭니다", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MemoActivity.class);
                startActivityForResult(intent, 1);


            }
        });

        listView = findViewById(R.id.ListView);             //메모 리스트 보여주기

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list); //simple_list_item_1은 만들어진 형식같은것임
        listView.setAdapter(adapter);
        //Toast.makeText(this, "시작 ", Toast.LENGTH_SHORT).show();


        //Toast.makeText(this, "실험 ㅇㅇ : "+this.getFilesDir().listFiles().length, Toast.LENGTH_SHORT).show();

        //this.getFilesDir().listFiles().getClass();


        loadItemsFromFile();            //백그라운드 데이터 불러오기
        //((ArrayAdapter) adapter).notifyDataSetChanged();


        //불러오기
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


                if (list.get(position).equals("<삭제됨>")) {
                    Toast.makeText(MainActivity.this, "삭제된 메세지입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    String memoData = TFM.get(position).load();

                    //Toast.makeText(MainActivity.this, memoData, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, ReMemoActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("memoData", memoData);

                    startActivityForResult(intent, 2); //제목 + @@@! + 내용 을 넘겨줌

                }
            }
        });


    }

//    private File[] sortFileList(File[] files) {
//
//
//
//        Arrays.sort(files, new Comparator<Object>()
//
//        {
//           @Override
//           public int compare (Object object1, Object object2){
//
//           String s1 = "";
//           String s2 = "";
//
//            s1 = ((File) object1).lastModified() + "";
//            s2 = ((File) object2).lastModified() + "";
//
//
//           return s1.compareTo(s2);
//           }
//    });
//
//        return files;
//}



    private void loadItemsFromFile(){
        File[] backGroundFiles = this.getFilesDir().listFiles();

        int file_num = backGroundFiles.length;
        n=0;

        backGroundFiles = sortFileList(backGroundFiles);
        //Arrays.sort(backGroundFiles);


        for(int i=0;i<file_num;i++){
            try{
                FileInputStream fisMemo = this.openFileInput(backGroundFiles[i].getName());
                byte[] memoData = new byte[fisMemo.available()];
                while(fisMemo.read(memoData)!=-1){}

                String prevData = new String(memoData);
                String[] memo = prevData.split("@@@!");


                TFM.add(i,new TextFileManager(this));
                TFM.get(i).save(memo[0],memo[1],memo[2],memo[3]);
                list.add(i,memo[1]);


                //Toast.makeText(this, "저장되는 내용: "+ prevData, Toast.LENGTH_SHORT).show();

            }catch (IOException e){
                Log.d("file 읽어오는 중","오류 발생");
                Toast.makeText(this, "오류 발생", Toast.LENGTH_SHORT).show();
            }
            n++;
        }






    }

    public File[] sortFileList(File[] files){
        //파일을 리스트에 만든시간의 오름차순으로 정렬하기 위함
        //파일 이름 (만든 시간 [년도-달-일-시간]) 순으로 정렬
        Arrays.sort(files,
                new Comparator<Object>() {
                    @Override
                    public int compare(Object object1, Object object2) {

                        String s1 = "";
                        String s2 = "";
                        s1 = ((File)object1).getName();
                        s2 = ((File)object2).getName();

                        return s1.compareTo(s2);

                    }
                });

        return files;
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1){                                                         //첫 저장
            if(resultCode== Activity.RESULT_OK){
                Log.e("LOG","결과받기성공");
                Bundle bundle = data.getExtras();
                String myTime = bundle.getString("time");
                String myTitle = bundle.getString("title");
                String myMemo = bundle.getString("memo");
                String myColor = bundle.getString("color");
                //Toast.makeText(this, myMemo, Toast.LENGTH_SHORT).show();


                if(myTitle.equals("")&&myMemo.equals("")){
                    return;
                }
                else if(myTitle.equals("")){
                    myTitle = "<제목없음>";
                }
                else if(myMemo.equals("")){
                    myMemo=" ";
                }

                list.add(n,myTitle);
                ((ArrayAdapter) adapter).notifyDataSetChanged();

//                mTextFileManager = new TextFileManager(this); //TextFileManager 객체 생성
//                mTextFileManager.save(myMemo);

                TFM.add(n, new TextFileManager(this));
                TFM.get(n).save(myTime,myTitle,myMemo,myColor);        //제목,내용을 넘겨줌. TFM 버퍼에는 제목 + @@@! + 내용 이 저장됨
                n++;

            }
        }
        else if(requestCode==2){                                                            //re memo 저장
            if(resultCode==Activity.RESULT_OK){
                Log.e("Log","결과받기성공");
                Bundle bundle = data.getExtras();
                String myTime = bundle.getString("time");
                String myTitle = bundle.getString("title");
                String myMemo = bundle.getString("memo");
                String myColor = bundle.getString("color");
                int position = bundle.getInt("position");
                //Toast.makeText(this, myMemo, Toast.LENGTH_SHORT).show();

                if(myTitle.equals("")&&myMemo.equals("")){
                    //list.remove(position);
                    //list.set(position,"<삭제됨>");
                    TFM.get(position).delete();
                    list.removeAll(list);
                    n--;
                    loadItemsFromFile();
                    Toast.makeText(this, "현재 list의 수는 "+ n , Toast.LENGTH_SHORT).show();

                    //Toast.makeText(this, "삭제합니다", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(myTitle.equals("")){ //제목이 없을때
                        myTitle = "<제목없음>";
                    }
                    if(myMemo.equals("")){ //내용이 없을때
                        myMemo=" ";
                    }
                    TFM.get(position).save(myTime,myTitle, myMemo,myColor);
                    list.set(position,myTitle);
                }


                ((ArrayAdapter) adapter).notifyDataSetChanged();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {           //툴바(액션바) 클릭했을 때
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
