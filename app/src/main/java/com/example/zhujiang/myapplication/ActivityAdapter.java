package com.example.zhujiang.myapplication;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.jpush.im.android.api.model.Message;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuj 2017/8/24 上午11:27.
 */
public class ActivityAdapter extends BaseAdapter implements Filterable {

  private List<ActivityBean> mList;
  private List<ActivityBean> mSource;
  private Context mContext;
  public ActivityAdapter(Context context, List<ActivityBean> list) {
    mContext = context;
    mSource = list;
    mList = mSource;
  }

  @Override public int getCount() {
    return mList.size();
  }

  @Override public Object getItem(int i) {
    return mList.get(i);
  }

  @Override public long getItemId(int i) {
    return 0;
  }

  @Override public View getView(int i, View view, ViewGroup viewGroup) {
    MyHolder myHolder;
    if (view == null) {
      view = LayoutInflater.from(mContext).inflate(R.layout.listview_activity_item, null);
      myHolder = new MyHolder();
      myHolder.tvActivity = (TextView) view .findViewById(R.id.tv_activity);
      myHolder.tvName = (TextView) view.findViewById(R.id.tv_name);
      myHolder.layout = (LinearLayout) view.findViewById(R.id.layout);
      myHolder.iv = (ImageView) view.findViewById(R.id.iv);
      view.setTag(myHolder);
    } else {
      myHolder = (MyHolder) view.getTag();
    }
    myHolder.tvActivity.setText(mList.get(i).getActivityName());
    myHolder.tvName.setText(mList.get(i).getName());


    RequestOptions options = new RequestOptions()
            //.override(480)
            .centerCrop()
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL);


    Glide.with(mContext).load(getUrl(i))
            //.thumbnail(0.2f)
            //.transition(new DrawableTransitionOptions().crossFade())
            .apply(options)
            .into(myHolder.iv);

    //if (i %2 ==1) {
    //  myHolder.layout.setVisibility(View.GONE);
    //} else {
    //  myHolder.layout.setVisibility(View.VISIBLE);
    //}
    return view;
  }

  private static class MyHolder {
    TextView tvName;
    TextView tvActivity;
    LinearLayout layout;
    ImageView iv;

  }

  GroupWonerFilter mFilter;
  @Override public Filter getFilter() {
    if (mFilter == null) {
      mFilter = new GroupWonerFilter();
    }
    return mFilter;
  }

  private class GroupWonerFilter extends Filter {

    @Override protected FilterResults performFiltering(CharSequence constraint) {

      FilterResults results = new FilterResults();
      if (TextUtils.isEmpty(constraint)) {
        results.values = mSource;
        results.count = mSource.size();
      } else {
        List<ActivityBean> newlist = new ArrayList<>();

        for (int i=0; i<mSource.size(); i++) {
          String userName = mSource.get(i).getActivityName();
          if (userName.contains(constraint)) {
            newlist.add(mSource.get(i));
          }
        }
        results.values = newlist;
        results.count =newlist.size();
      }


      return results;
    }

    @Override protected void publishResults(CharSequence constraint, FilterResults results) {
      //results.values;
      mList = (List<ActivityBean>) results.values;
      notifyDataSetChanged();
    }
  }


  private String getUrl(int position) {
    String url;
    switch (position%11) {
      case 0:
        url = "http://buddyweb.oss-cn-zhangjiakou.aliyuncs.com/test/rcmd/2018031310533835062664.jpg";
        break;
      case 1:
        url = "http://buddyweb.oss-cn-zhangjiakou.aliyuncs.com/test/rcmd/2018031314023253775898.png";
        break;
      case 2:
        url = "http://buddyweb.oss-cn-zhangjiakou.aliyuncs.com/test/rcmd/2018031314195216057911.jpg";
        break;
      case 3:
        url = "http://buddyweb.oss-cn-zhangjiakou.aliyuncs.com/test/rcmd/2018032010163890637985.jpg";
        break;
      case 4:
      default:
        url = "http://buddyweb.oss-cn-zhangjiakou.aliyuncs.com/test/rcmd/2018032310493199262749.png";
        break;
      case 5:
        url = "https://buddyweb.oss-cn-zhangjiakou.aliyuncs.com/story/201709251424059335113.png";
        break;
      case 6:
        url = "https://buddyweb.oss-cn-zhangjiakou.aliyuncs.com/story/2017092514224221785668.png";
        break;
      case 7:
        url = "https://buddyweb.oss-cn-zhangjiakou.aliyuncs.com/story/2017092819371032387784.jpg";
        break;
      case 8:
        url = "https://buddyweb.oss-cn-zhangjiakou.aliyuncs.com/story/201709201438245328192.png";
        break;
      case 9:
        url = "https://buddyweb.oss-cn-zhangjiakou.aliyuncs.com/story/2017091910000172091597.png";
        break;
      case 10:
        url = "https://buddyweb.oss-cn-zhangjiakou.aliyuncs.com/story/2017092014140695926499.png";
        break;
    }
    return url;
  }

}
