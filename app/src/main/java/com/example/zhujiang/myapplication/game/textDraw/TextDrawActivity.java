package com.example.zhujiang.myapplication.game.textDraw;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.zhujiang.myapplication.R;
import java.util.Random;

public class TextDrawActivity extends AppCompatActivity {

    @BindView(R.id.mtv) MyTextView mMtv;
    @BindView(R.id.btn) Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_draw);
        ButterKnife.bind(this);

        Random random = new Random();
        int value = random.nextInt(10);
        mMtv.setText(String.valueOf(value));
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {

        Random random = new Random();
        int value = random.nextInt(10);
        mMtv.setText(String.valueOf(value));

    }
}
