package com.example.zhujiang.myapplication.recycler;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.zhujiang.myapplication.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerTest extends Activity {

  @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
  private ItemTouchHelper itemTouchHelper;
  StringAdapter adapter;
  List<String> list;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recycler_test);
    ButterKnife.bind(this);

    LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
    mRecyclerView.setLayoutManager(layoutManager);

    list  = new ArrayList<String>();
    list.add("111");
    list.add("222");
    list.add("333");
    list.add("444");

    adapter = new StringAdapter(this, list);
    mRecyclerView.setAdapter(adapter);
    adapter.setOnStartDragListener(new OnStartDragListener() {
      @Override
      public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
      }
    });

    ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {
      @Override
      public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        final int swipeFlags = 0;
        return makeMovementFlags(dragFlags, swipeFlags);
      }

      @Override
      public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
              RecyclerView.ViewHolder target) {
        int from = viewHolder.getAdapterPosition();
        int to = target.getAdapterPosition();

        if (Math.abs(from - to) > 1) {
          String str = list.get(from);
          list.remove(from);
          list.add(to, str);
        } else {
          Collections.swap(list, from, to);
        }

        adapter.notifyItemMoved(from, to);
        //if (Math.abs(fromPosition - toPosition) > 1) {
        //  Bean from = mDatas.get(fromPosition);
        //  mDatas.remove(fromPosition);
        //  mDatas.add(toPosition, from);
        //} else {
        //  Collections.swap(mDatas, fromPosition, toPosition);
        //}
        //adapter.notifyItemMoved(fromPosition, toPosition);
        return true;
      }

      @Override
      public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

      }

      @Override
      public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        // We only want the active item to change
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
          //if (viewHolder instanceof ItemTouchHelperViewHolder) {
          //  // Let the view holder know that this item is being moved or dragged
          //  ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
          //  itemViewHolder.onItemSelected();
          //}
        }

        super.onSelectedChanged(viewHolder, actionState);
      }
    };
    itemTouchHelper = new ItemTouchHelper(callback);
    itemTouchHelper.attachToRecyclerView(mRecyclerView);
  }
}
