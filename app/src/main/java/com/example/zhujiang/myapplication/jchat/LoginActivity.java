package com.example.zhujiang.myapplication.jchat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import com.example.zhujiang.myapplication.R;

public class LoginActivity extends Activity {

  @BindView(R.id.et_account) EditText mEtAccount;
  @BindView(R.id.et_password) EditText mEtPassword;
  @BindView(R.id.btn_login) Button mBtnLogin;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.btn_login) public void onViewClicked() {
    String account = mEtAccount.getText().toString();
    String password = mEtPassword.getText().toString();
    //JMessageClient.login(account, password, new BasicCallback() {
    //  @Override
    //  public void gotResult(int responseCode, String LoginDesc) {
    //    if (responseCode == 0) {
    //      Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
    //      Log.i("MainActivity", "JMessageClient.login" + ", responseCode = " + responseCode + " ; LoginDesc = " + LoginDesc);
          Intent intent = new Intent();
          intent.setClass(getApplicationContext(), ChatActivity.class);
          startActivity(intent);
          finish();
    //    } else {
    //      Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
    //      Log.i("MainActivity", "JMessageClient.login" + ", responseCode = " + responseCode + " ; LoginDesc = " + LoginDesc);
    //    }
    //  }
    //});

  }
}
