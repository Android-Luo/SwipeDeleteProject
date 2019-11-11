package com.example.swipedeleteproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private List<Sport> mSportData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSportData = new ArrayList<>();
        ListView listView = (ListView)findViewById(R.id.listView);
        ListViewAdapter mAdapter = new ListViewAdapter(this, mSportData);
        listView.setAdapter(mAdapter);

        initData();
    }

    private void initData() {
        Sport aa = new Sport("aa", "aaa01");
        mSportData.add(aa);
        Sport bb = new Sport("bb", "bbb01");
        mSportData.add(bb);
        Sport cc = new Sport("cc", "ccc01");
        mSportData.add(cc);
        Sport dd = new Sport("dd", "ddd01");
        mSportData.add(dd);
        Sport ee = new Sport("ee", "eee01");
        mSportData.add(ee);
        Sport ff = new Sport("ff", "fff01");
        mSportData.add(ff);
        Sport gg = new Sport("gg", "ggg01");
        mSportData.add(gg);
        Sport hh = new Sport("hh", "hhh01");
        mSportData.add(hh);
        Sport ii = new Sport("ii", "iii01");
        mSportData.add(ii);
        Sport jj = new Sport("jj", "jjj01");
        mSportData.add(jj);
        Sport kk = new Sport("kk", "kkk01");
        mSportData.add(kk);
        Sport ll = new Sport("ll", "lll01");
        mSportData.add(ll);
        Sport mm = new Sport("mm", "mmm01");
        mSportData.add(mm);
        Sport nn = new Sport("nn", "nnn01");
        mSportData.add(nn);
        Sport oo = new Sport("oo", "00001");
        mSportData.add(oo);
    }
}
