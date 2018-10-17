package com.example.zhujiang.myapplication.listView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import com.example.zhujiang.myapplication.R;
import java.util.List;

/**
 * @author zhuj 2018/1/30 上午9:21.
 */
public class StringAdapter2 extends BGARecyclerViewAdapter<String> {

  private Context mContext;

  public StringAdapter2(RecyclerView recyclerView) {
    super(recyclerView, R.layout.item_string);
  }



  @Override
  protected void fillData(BGAViewHolderHelper helper, int position, String model) {
    helper.setText(R.id.tv_name, model);
    helper.setBackgroundColorRes(R.id.tv_name, position%2==0 ? R.color.colorAccent : R.color.btn_press);
  }
}
