package com.example.zhujiang.myapplication.viewShare;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.zhujiang.myapplication.R;

public class ViewShare1Activity extends AppCompatActivity {

    @BindView(R.id.image) ImageView mImage;
    @BindView(R.id.button) Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_share1);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.image)
    public void onViewClicked() {
        Intent intent = new Intent(this, ViewShare2Activity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /*startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());*/
            //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,
            //        Pair.create(mImage, "image"),
            //        Pair.create(mButton, "button")).toBundle());

            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,
                            mImage, "image").toBundle());

        } else {
            startActivity(intent);
        }
    }

    @OnClick(R.id.button)
    public void onButtonClick() {
        Intent intent = new Intent(this, ViewShare2Activity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /*startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());*/
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,
                    new Pair[] { Pair.create(mImage, "image") , Pair.create(mButton, "button")}).toBundle());

            //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,
            //        mButton, "button").toBundle());

        } else {
            startActivity(intent);
        }
    }

    @OnClick(R.id.btn_skip)
    public void onButtonSkipClick() {
        Intent intent = new Intent(this, ViewShare2Activity.class);
        startActivity(intent);
    }
}
