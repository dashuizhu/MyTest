package com.example.zhujiang.myapplication.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
  protected OnStartDragListener startDragListener;

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
  public void onBindViewHolder(final StringHolder holder, int position) {
    holder.textView.setText(mList.get(position));
    holder.ll.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && getItemCount() > 1 && startDragListener != null) {
          // Step 9-5: 只有调用onStartDrag才会触发拖拽 (这里在touch时开始拖拽，当然也可以单击或长按时才开始拖拽)
          startDragListener.onStartDrag(holder);
          return true;
        }
        return false;
      }
    });
  }

  @Override
  public int getItemCount() {
    return mList.size();
  }

  static class StringHolder extends RecyclerView.ViewHolder {

    TextView textView;
    LinearLayout ll;

    public StringHolder(View itemView) {
      super(itemView);
      textView = (TextView) itemView.findViewById(R.id.tv_name);
      ll = (LinearLayout) itemView.findViewById(R.id.ll);
    }
  }

  public void setOnStartDragListener(OnStartDragListener startDragListener) {
    this.startDragListener = startDragListener;
  }
}
