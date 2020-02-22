package com.yeonkyu.diary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.String;
import androidx.appcompat.app.AppCompatActivity;


public class ReMemoActivity extends AppCompatActivity {
    int position;
    String color;
    String time;

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();
        if(itemId==R.id.action_delete){
            Intent intent2 = new Intent(ReMemoActivity.this,MainActivity.class);
            intent2.putExtra("title","");
            intent2.putExtra("memo","");

            intent2.putExtra("position",position);
            setResult(Activity.RESULT_OK,intent2);
            finish();
        }
        if(itemId==R.id.action_setColor){
            //어떤 색을 정할지 선택창
            final String[] colors = new String[]{"blue", "red", "yellow", "black"};
            final int[] selectedIndex = {0};
            AlertDialog.Builder dialog = new AlertDialog.Builder(ReMemoActivity.this);
            dialog.setTitle("색을 선택하세요");
            dialog.setSingleChoiceItems(colors, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    selectedIndex[0] = i;
                }
            });
            dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    color = colors[selectedIndex[0]];
                    Toast.makeText(ReMemoActivity.this, color+"선택하였습니다", Toast.LENGTH_SHORT).show();
                    final EditText memoArea = findViewById(R.id.memo_edit);
                    final EditText titleArea = findViewById(R.id.memo_title);
                    final String memo = memoArea.getText().toString();
                    final String title = titleArea.getText().toString();
                    if(color.equals("blue")){
                        setContentView(R.layout.activity_memo);
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF5555cc));
                    }
                    else if(color.equals("red")){
                        setContentView(R.layout.activity_memo_red);
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFcc5555));
                    }
                    else if(color.equals("yellow")){
                        setContentView(R.layout.activity_memo_yellow);
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFcccc55));
                    }
                    else if(color.equals("black")){
                        setContentView(R.layout.activity_memo_black);
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF555555));
                    }
                    final EditText memoArea2 = findViewById(R.id.memo_edit);
                    final EditText titleArea2 = findViewById(R.id.memo_title);
                    memoArea2.setText(memo);
                    titleArea2.setText(title);

                    Button savebtn = findViewById(R.id.save);
                    savebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent2 = new Intent(ReMemoActivity.this,MainActivity.class);
                            intent2.putExtra("time",time);
                            intent2.putExtra("title",title);
                            intent2.putExtra("memo",memo);
                            intent2.putExtra("color",color);
                            //Toast.makeText(ReMemoActivity.this, "time: "+memo[0]+"title: "+memo[1]+"memo: "+memo[2], Toast.LENGTH_SHORT).show();
                            intent2.putExtra("position",position);
                            setResult(Activity.RESULT_OK,intent2);
                            finish();

                        }
                    });

                }
            }).create().show();


        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_memo);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();


        position = bundle.getInt("position");
        final String memoData = bundle.getString("memoData");

        //final EditText prevMemo = findViewById(R.id.memo_edit);
        ///final EditText prevTitle = findViewById(R.id.memo_title);

        final String[] memo = memoData.split("@@@!");

        time = memo[0];
        //prevTitle.setText(memo[1]);
        //prevMemo.setText(memo[2]);
        color = memo[3];

        if(color.equals("blue")){
            setContentView(R.layout.activity_memo);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF929abc));
        }
        else if(color.equals("red")){
            setContentView(R.layout.activity_memo_red);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFcc5555));
        }
        else if(color.equals("yellow")){
            setContentView(R.layout.activity_memo_yellow);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFffd700));
        }
        else if(color.equals("black")){
            setContentView(R.layout.activity_memo_black);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF111111));
        }
        final EditText prevMemo = findViewById(R.id.memo_edit);
        final EditText prevTitle = findViewById(R.id.memo_title);
        prevTitle.setText(memo[1]);
        prevMemo.setText(memo[2]);

        //Toast.makeText(ReMemoActivity.this, "색깔은 "+color+" 입니다", Toast.LENGTH_SHORT).show();
        //<-처음 rememoActivity열었을때 파일에 저장된 색상으로 update해주기작업ㄱㄱ


        Button savebtn = findViewById(R.id.save);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                memo[1] = prevTitle.getText().toString();
                memo[2] = prevMemo.getText().toString();

                Intent intent2 = new Intent(ReMemoActivity.this,MainActivity.class);
                intent2.putExtra("time",memo[0]);
                intent2.putExtra("title",memo[1]);
                intent2.putExtra("memo",memo[2]);
                intent2.putExtra("color",color);
                //Toast.makeText(ReMemoActivity.this, "time: "+memo[0]+"title: "+memo[1]+"memo: "+memo[2], Toast.LENGTH_SHORT).show();
                intent2.putExtra("position",position);
                setResult(Activity.RESULT_OK,intent2);
                finish();

            }
        });

    }
}
