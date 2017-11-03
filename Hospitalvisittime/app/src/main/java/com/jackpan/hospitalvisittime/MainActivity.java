package com.jackpan.hospitalvisittime;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jackpan.hospitalvisittime.Data.CychData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ArrayList<CychData> cychDataArrayList = new ArrayList<>();
    private ProgressDialog  p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        p = new ProgressDialog(this);
        p.setMessage("122");
        p.show();
        test();


    }
    private void test(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {

                    Document doc = Jsoup.connect("http://www.cych.org.tw/cych_OA/view.aspx/Dr_view.aspx").get();
                    for (int i = 1; i < doc.select("tbody > tr").size()-1; i++) {
                        Log.d(TAG, "run: "+  doc.select("tbody > tr").get(i).select("td").text());
                        String array [] =doc.select("tbody > tr").get(i).select("td").text().split(" ");
                        Log.d(TAG, "看診狀態: "+array[0].toString());
                        Log.d(TAG, "科 別: "+array[1].toString());
                        Log.d(TAG, "看診號: "+array[2].toString());
                        Log.d(TAG, "最後掛號號碼: "+array[3].toString());
                        Log.d(TAG, "現場待診人數: "+array[4].toString());
                        Log.d(TAG, "報到狀態: "+array[5].toString());
                        Log.d(TAG, "醫師姓名: "+array[6].toString());
                        Log.d(TAG, "位 置: "+array[7].toString());
                        CychData cychData = new CychData(array[0].toString(),
                                array[1].toString(),
                                array[2].toString(),
                                array[3].toString(),
                                array[4].toString(),
                                array[5].toString(),
                                array[6].toString(),
                                array[7].toString());

                        cychDataArrayList.add(cychData);
                        


                    }
                    p.dismiss();


                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "run: "+e.getMessage());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                    }
                });
            }

        }.start();
    }
    private  void test2(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {

                    Document doc = Jsoup.connect("http://www.chimei.org.tw/left/register/progress/qprogress.asp?idept=752&host=10").get();
                    Log.d(TAG, "run: "+doc.select("font[size=2]").text());
                    for (Element font : doc.select("font[size=2]")) {
                        Log.d(TAG, "run: "+font.text());
                        Log.d(TAG, "run: "+font.getElementsByTag("a").attr("href").toString());
                    }
                    for (int i = 1; i < doc.select("tr").size(); i++) {
                        Log.d(TAG, "run: "+doc.select("tr").get(i).text());
                        String [] array  = doc.select("tr").get(i).text().split("");
                        Log.d(TAG, "班別: "+array[0].toString());
                        Log.d(TAG, "科別名稱: "+array[1].toString());
                        Log.d(TAG, "醫師姓名: "+array[2].toString());
                        Log.d(TAG, "已掛號人數: "+array[3].toString());
                        Log.d(TAG, "等候看診人數: "+array[4].toString());
                        Log.d(TAG, "現在看到幾號: "+array[5].toString());


                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "run: "+e.getMessage());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

        }.start();
    }
}
