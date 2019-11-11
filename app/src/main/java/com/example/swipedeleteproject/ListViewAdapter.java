package com.example.swipedeleteproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * date on  2019/11/7
 * time on  15:13
 * author LUO
 */
public class ListViewAdapter extends BaseAdapter {

    private List<Sport> mSportList;
    private Context mContext;

    public ListViewAdapter(Context context, List<Sport> sportList) {
        mSportList = sportList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mSportList.size();
    }

    @Override
    public Object getItem(int position) {
        return mSportList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Sport sport = (Sport)getItem(position);
        ViewHolder viewHolder;
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.sport_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.sportName = (TextView)view.findViewById(R.id.sportName);
            viewHolder.sportStyle = (TextView)view.findViewById(R.id.sportStyle);
            view.setTag(viewHolder); //将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag(); //重新获取ViewHolder
        }
        //设置相关值
        viewHolder.sportName.setText(sport.getSprotName());
        viewHolder.sportStyle.setText(sport.getSportStyle());

        viewHolder.sportName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "现在内容的位置是" + position, Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.sportStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "现在的删除位置是" + position, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    class ViewHolder {
        TextView sportName;

        TextView sportStyle;
    }
}
