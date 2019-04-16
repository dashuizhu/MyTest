package com.example.zhujiang.myapplication.parent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.zhujiang.myapplication.R;

public class ParentViewActivity extends AppCompatActivity {

    @BindView(R.id.btn1) Button mBtn1;
    @BindView(R.id.btn2) Button mBtn2;
    @BindView(R.id.btn3) Button mBtn3;
    @BindView(R.id.tv) TextView mTv;
    @BindView(R.id.fl) FrameLayout mFl;
    @BindView(R.id.rl) RelativeLayout mRl;
    @BindView(R.id.ll) LinearLayout mLl;

    private ViewGroup mLastView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_view);
        ButterKnife.bind(this);

        mLastView = mFl;
    }

    @OnClick({ R.id.btn1, R.id.btn2, R.id.btn3 })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:

                if (mLastView != mLl) {
                    mLastView.removeView(mTv);

                    mLl.addView(mTv);
                    mLastView = mLl;

                }

                break;
            case R.id.btn2:
                if (mLastView != mFl) {
                    mLastView.removeView(mTv);

                    mFl.addView(mTv);
                    mLastView = mFl;

                }
                break;
            case R.id.btn3:
                if (mLastView != mRl) {
                    mLastView.removeView(mTv);

                    mRl.addView(mTv);
                    mLastView = mRl;

                }
                break;
        }
    }
}
