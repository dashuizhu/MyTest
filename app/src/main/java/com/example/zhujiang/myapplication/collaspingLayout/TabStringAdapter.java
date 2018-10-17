package com.example.zhujiang.myapplication.collaspingLayout;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.zhujiang.myapplication.R;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import java.util.List;

/**
 * 电影分类 适配器
 * Created by zhuj on 2017/2/18 10:42.
 */
public class TabStringAdapter extends RecyclerView.Adapter<TabStringAdapter.MyViewHolder> {

  private List<String> mListCategory;
  private Context mContext;
  private CategoryClickListener mCategoryClickListener;

  private int width;
  public TabStringAdapter(Context context, List<String> list) {
    mContext = context;
    mListCategory = list;
    width = context.getResources().getDisplayMetrics().widthPixels /3;
  }

  @Override public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(mContext);
    View view = inflater.inflate(R.layout.recycler_movie_category_item, parent, false);
    MyViewHolder holder = new MyViewHolder(view, mCategoryClickListener);
    return holder;
  }

  @Override public void onBindViewHolder(MyViewHolder holder, final int position) {
    LinearLayout.LayoutParams params =
            (LinearLayout.LayoutParams) holder.mTextView.getLayoutParams();
    params.width = width;
    holder.mTextView.setText(mListCategory.get(position));
    holder.mTextView.setBackgroundColor(Color.parseColor("#13"+position+"2"+position +"3"));
    //holder.mTextView.setSelected(mListCategory.get(position).isChecked());
    //holder.mTextView.setTextSize(holder.mTextView.isSelected() ? 15 : 13);
  }

  @Override public int getItemCount() {
    return mListCategory.size();
  }

  public void setCategoryClickListener(CategoryClickListener listener) {
    this.mCategoryClickListener = listener;
  }

  public interface CategoryClickListener {
    void onItemClick(String string, int position);
  }

  class MyViewHolder extends RecyclerView.ViewHolder {

    TextView mTextView;

    public MyViewHolder(View view, final CategoryClickListener listener) {
      super(view);
      mTextView = (TextView) view.findViewById(R.id.cb_movie_category);
      mTextView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (listener != null) {
            listener.onItemClick(mListCategory.get(getAdapterPosition()), getLayoutPosition());
          }
        }
      });
    }
  }
}
