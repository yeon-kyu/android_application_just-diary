package com.yeonkyu.diary;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;



public class TextFileManager implements Comparable<TextFileManager> {



    //private static final String FILE_NAME = "memo.txt";
    String FILE_NAME=null;

    Context mContext = null;
    File file =null;
    String time;



    public TextFileManager(Context context){ //생성자
        mContext = context;
        //Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
        //File file = new File(context.getFilesDir(),FILE_NAME);
        //Toast.makeText(context, "파일의 위치는"+context.getFilesDir(), Toast.LENGTH_SHORT).show();


//        if(FILE_NAME==null) {         //시간으로 하면 안되는 이유 : 컴퓨터는 빠르기 때문에 다 같은 이름으로 만들어진다.
//            Date now = new Date();
//            SimpleDateFormat time = new SimpleDateFormat("yyyyMMddhhmmss");
//
//            FILE_NAME = time.format(now);
//
//        }


    }

    public void save(String time, String title,String strData,String color){   //파일에 메모를 저장하는 메소드
//        if(title.equals("")||strData.equals("")){
//            Toast.makeText(mContext, "메모가 비었습니다", Toast.LENGTH_SHORT).show();
//            return ;
//        }


        FileOutputStream fosMemo = null;
        FILE_NAME = time;
        //file = new File(mContext.getFilesDir(),FILE_NAME);

        try{
            //파일에 데이터 쓰기 위해 output스트림 생성
            fosMemo = mContext.openFileOutput(FILE_NAME,Context.MODE_PRIVATE);
            //파일에 메모 적기
            fosMemo.write(time.getBytes());
            fosMemo.write("@@@!".getBytes());
            fosMemo.write(title.getBytes());
            fosMemo.write("@@@!".getBytes());
            fosMemo.write(strData.getBytes());
            fosMemo.write("@@@!".getBytes());
            fosMemo.write(color.getBytes());
            fosMemo.close();
            //Toast.makeText(mContext, "save 완료", Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    public String load(){   //저장된 메모 파일을 불러오는 메소드

        //File file = new File("/data/user/0/com.example.diary/files"+FILE_NAME);



        try{
            //파일에서 저장된 데이터를 읽기 위해 input스트림 생성
            FileInputStream fisMemo = mContext.openFileInput(FILE_NAME);
            //데이터를 읽어온뒤, String 타입 객체로 반환
            byte[] memoData = new byte[fisMemo.available()];
            while(fisMemo.read(memoData)!=-1){}
            return new String(memoData);
        }catch (IOException e){
            Log.d("file 읽어오는 중","오류 발생");
        }

        return "";
    }

    public void delete(){   //메모 파일 삭제하는 메소드

        mContext.deleteFile(FILE_NAME);
    }

    public void setFile(File file2){
        file = file2;
    }

    public void setDate(){
        //file = new File(mContext.getFilesDir(),FILE_NAME);


        String pattern = "yyyyMMdd HHmmss";
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        Toast.makeText(mContext, sf.format(file.lastModified()), Toast.LENGTH_SHORT).show();
    }


    @Override
    public int compareTo(TextFileManager textFileManager) {
        Toast.makeText(mContext, "되고있니?", Toast.LENGTH_SHORT).show();
        return time.compareTo(textFileManager.time); //return값이 0이나 음수이면 자리를 바꾸지않고, 양수이면 자리를 변경한다.
        // 위 방식은 오름차순.
    }
}
