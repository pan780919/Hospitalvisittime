package com.jackpan.hospitalvisittime;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jackpan.hospitalvisittime.Data.CychData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class CychActivity extends Activity {
    private MyAdapter mAdapter;
    private ArrayList<CychData> list = new ArrayList<>();
    private ProgressDialog p;
    private ArrayList<CychData> cychDataArrayList = new ArrayList<>();
    private static final String TAG = "CychActivity";
    private ListView myListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cych);
        p = new ProgressDialog(this);
        p.setMessage("讀取中");
        p.show();
        test();
        myListView = (ListView) findViewById(R.id.mylist);


    }


    private void test() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {

                    Document doc = Jsoup.connect("http://www.cych.org.tw/cych_OA/view.aspx/Dr_view.aspx").get();
                    for (int i = 1; i < doc.select("tbody > tr").size() - 1; i++) {
                        Log.d(TAG, "run: " + doc.select("tbody > tr").get(i).select("td").text());
                        String array[] = doc.select("tbody > tr").get(i).select("td").text().split(" ");
                        Log.d(TAG, "看診狀態: " + array[0].toString());
                        Log.d(TAG, "科 別: " + array[1].toString());
                        Log.d(TAG, "看診號: " + array[2].toString());
                        Log.d(TAG, "最後掛號號碼: " + array[3].toString());
                        Log.d(TAG, "現場待診人數: " + array[4].toString());
                        Log.d(TAG, "報到狀態: " + array[5].toString());
                        Log.d(TAG, "醫師姓名: " + array[6].toString());
                        Log.d(TAG, "位 置: " + array[7].toString());
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
                    Log.d(TAG, "run: " + e.getMessage());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new MyAdapter(cychDataArrayList);
                        myListView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();

                    }
                });
            }

        }.start();
    }

    public class MyAdapter extends BaseAdapter {
        private ArrayList<CychData> mDatas;

        public MyAdapter(ArrayList<CychData> datas) {
            mDatas = datas;
        }

        public void updateData(ArrayList<CychData> datas) {
            mDatas = datas;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = LayoutInflater.from(CychActivity.this).inflate(
                        R.layout.mylayout, null);
            CychData taipeiZoo = mDatas.get(position);
            TextView textname = (TextView) convertView.findViewById(R.id.text1);
            TextView list = (TextView) convertView.findViewById(R.id.text2);
            TextView bigtext = (TextView) convertView.findViewById(R.id.text3);

            TextView place = (TextView) convertView.findViewById(R.id.text4);
            TextView time = (TextView) convertView.findViewById(R.id.text5);
            TextView userview = (TextView) convertView.findViewById(R.id.text6);
            TextView userlike = (TextView) convertView.findViewById(R.id.text7);
            TextView addd = (TextView) convertView.findViewById(R.id.text8);

            textname.setText(taipeiZoo.state);
            list.setText(taipeiZoo.division);
            bigtext.setText(taipeiZoo.number);
            place.setText(taipeiZoo.lastNumber);
            time.setText(taipeiZoo.watingNum);
            userview.setText(taipeiZoo.registrationState);
            userlike.setText(taipeiZoo.docName);
            addd.setText(taipeiZoo.address);
            return convertView;
        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK)) {   //確定按下退出鍵

         this.finish();//關閉activity

            return true;

        }

        return super.onKeyDown(keyCode, event);

    }

}
