package com.example.zhujiang.myapplication.listView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.zhujiang.myapplication.R;
import java.util.List;

/**
 * @author zhuj 2018/1/30 上午9:21.
 */
public class StringAdapter extends BaseAdapter {

  List<String> mList;
  private Context mContext;

  public StringAdapter(Context context, List<String> list) {
    mContext = context;
    mList = list;
  }

  @Override public int getCount() {
    return mList.size();
  }

  @Override public Object getItem(int position) {
    return mList.get(position);
  }

  @Override public long getItemId(int position) {
    return 0;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(mContext).inflate(R.layout.item_string, null);
    }
    TextView tv = (TextView) convertView.findViewById(R.id.tv_name);
    tv.setText(mList.get(position));
    return tv;
  }
}
