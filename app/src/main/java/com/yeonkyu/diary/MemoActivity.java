package com.yeonkyu.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class MemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);


        Button savebtn = findViewById(R.id.save);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MemoActivity.this, "저장합니다", Toast.LENGTH_SHORT).show();



                EditText mMemoEdit = null;
                EditText titleEdit=null;
                mMemoEdit = (EditText)findViewById(R.id.memo_edit);
                titleEdit = findViewById(R.id.memo_title);

                SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
                String time = format1.format (System.currentTimeMillis());

                String memo = mMemoEdit.getText().toString();
                String title = titleEdit.getText().toString();
                String color = "blue";

                //Toast.makeText(MemoActivity.this, "title : "+title +",memo : "+memo + ",time : "+time, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MemoActivity.this,MainActivity.class);
                intent.putExtra("time",time);
                intent.putExtra("memo",memo);
                intent.putExtra("title",title);
                intent.putExtra("color",color);

                setResult(Activity.RESULT_OK,intent);
                finish();

            }
        });

    }
}
