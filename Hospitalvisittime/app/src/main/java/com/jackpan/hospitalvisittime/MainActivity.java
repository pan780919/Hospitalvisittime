package com.jackpan.hospitalvisittime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    List<Map<String, String>> groups;
    List<List<Map<String, String>>> childs;
    private ExpandableListView mExpandableListView;
    private  boolean deleteBool = false;
    private String[] items;

    private List<String> mExpandableListTitle;
    private ExpandableListAdapter mExpandableListAdapter;

    private Map<String, List<String>> mExpandableListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initExpandableListView();

    }

    private void initData() {
        items = getResources().getStringArray(R.array.film_genre);
        mExpandableListData = ExpandableListDataSource.getData(this);
        mExpandableListTitle = new ArrayList();
        for (String item : items) {
            mExpandableListTitle.add(item);

        }

    }

    private void initExpandableListView() {

        final ExpandableListView elv = (ExpandableListView) findViewById(R.id.expandableListView);
        /*限制只展開一組*/
        elv.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });


        mExpandableListAdapter = new CustomExpandableListAdapter(this, mExpandableListTitle, mExpandableListData);

        elv.setAdapter(mExpandableListAdapter);
        elv.setGroupIndicator(null);

        elv.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String selectedItem = ((List) (mExpandableListData.get(mExpandableListTitle.get(groupPosition))))
                        .get(childPosition).toString();
                Log.d(TAG, "selectedItem: " + selectedItem);
                if (groupPosition == 1) {
                }

                return false;
            }
        });
    }



    private void test2() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {

                    Document doc = Jsoup.connect("http://www.chimei.org.tw/left/register/progress/qprogress.asp?idept=752&host=10").get();
                    Log.d(TAG, "run: " + doc.select("font[size=2]").text());
                    for (Element font : doc.select("font[size=2]")) {
                        Log.d(TAG, "run: " + font.text());
                        Log.d(TAG, "run: " + font.getElementsByTag("a").attr("href").toString());
                    }
                    for (int i = 1; i < doc.select("tr").size(); i++) {
                        Log.d(TAG, "run: " + doc.select("tr").get(i).text());
                        String[] array = doc.select("tr").get(i).text().split("");
                        Log.d(TAG, "班別: " + array[0].toString());
                        Log.d(TAG, "科別名稱: " + array[1].toString());
                        Log.d(TAG, "醫師姓名: " + array[2].toString());
                        Log.d(TAG, "已掛號人數: " + array[3].toString());
                        Log.d(TAG, "等候看診人數: " + array[4].toString());
                        Log.d(TAG, "現在看到幾號: " + array[5].toString());


                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "run: " + e.getMessage());
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
