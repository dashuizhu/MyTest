package com.example.zhujiang.myapplication.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.zhujiang.myapplication.R;
import java.util.List;

/**
 * 管理闹钟 页面的适配器
 *
 * @author zhuj 2017/7/4 上午10:26
 */
public class StringAdapter extends RecyclerView.Adapter<StringAdapter.StringHolder> {

  private List<String> mList;
  private Context mContext;

  public StringAdapter(Context context, List<String> data) {
    mContext = context;
    mList = data;
  }

  @Override
  public StringHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(mContext).inflate(R.layout.item_string, parent, false);
    return new StringHolder(view);
  }

  @Override
  public void onBindViewHolder(StringHolder holder, int position) {
    holder.textView.setText(mList.get(position));
  }

  @Override
  public int getItemCount() {
    return mList.size();
  }

  static class StringHolder extends RecyclerView.ViewHolder {

    TextView textView;

    public StringHolder(View itemView) {
      super(itemView);
      textView = (TextView) itemView.findViewById(R.id.tv_name);
    }
  }
}
