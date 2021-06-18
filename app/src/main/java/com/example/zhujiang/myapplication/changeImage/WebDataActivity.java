package com.example.zhujiang.myapplication.changeImage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.example.zhujiang.myapplication.R;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 爬取指定网页的内容
 */
public class WebDataActivity extends AppCompatActivity {

    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_data);
        mTv = findViewById(R.id.tv);

        getBaidu("天赋管家老师端", "https://shouji.baidu.com/software/26438661.html");
        getTencent("com.pwe.android.teach");
        get360("com.pwe.android.teach",
                "http://zhushou.360.cn/detail/index/soft_id/4033786?recrefer=SE_D_%E5%A4%A9%E8%B5%8B%E7%AE%A1%E5%AE%B6");

        getBaidu("天赋管家", "https://shouji.baidu.com/software/26439057.html");
        getTencent("com.pwe.android.parent");
        get360("com.pwe.android.parent",
                "http://zhushou.360.cn/detail/index/soft_id/4034413?recrefer=SE_D_%E5%A4%A9%E8%B5%8B%E7%AE%A1%E5%AE%B6");

        getBaidu("天赋管家家长端", "https://shouji.baidu.com/software/26440174.html");
        getTencent("com.pwe.android.child");
        get360("com.pwe.android.child",
                "http://zhushou.360.cn/detail/index/soft_id/4100963?recrefer=SE_D_%E5%A4%A9%E8%B5%8B%E7%AE%A1%E5%AE%B6");


        //getHuawei("aa" ,"https://appstore.huawei.com/app/C100823265");
    }

    private void getBaidu(final String appName, final String appUrl) {
        //直接从搜索页面抓取
        //Disposable dis = Observable.create(new ObservableOnSubscribe<String>() {
        //    @Override
        //    public void subscribe(ObservableEmitter<String> e) throws Exception {
        //        String str = getWebData(appUrl);
        //
        //        String reg = "href=\"/software\\S*.html";
        //        Pattern pattern = Pattern.compile(reg);
        //        Matcher matcher = pattern.matcher(str);
        //        while (matcher.find()) {
        //            String href = matcher.group();
        //            href = href.substring(5);
        //            e.onNext(getWebData("https://shouji.baidu.com/"+href));
        //        }
        //    }
        //})

        Disposable dis =
                Observable.just(appUrl)
                        .flatMap(new Function<String, ObservableSource<String>>() {
                            @Override
                            public ObservableSource<String> apply(String s) throws Exception {
                                String webData = getWebData(s);

                                String reg = "\"download_url\":\"\\S*.apk\"";
                                Pattern pattern = Pattern.compile(reg);
                                Matcher matcher = pattern.matcher(webData);
                                String group;
                                StringBuilder sb = new StringBuilder();
                                sb.append("百度\n");
                                while (matcher.find()) {
                                    group = matcher.group();
                                    sb.append(UnicodeToGB(group.substring(16, group.length() - 1)));
                                }
                                sb.append("\n\n");
                                return Observable.just(sb.toString());
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String str) throws Exception {
                                mTv.append(str);
                            }
                        });
    }

    private void getTencent(final String packageName) {
        Disposable dis =
                Observable.just("https://sj.qq.com/myapp/detail.htm?apkName=" + packageName)
                        .flatMap(new Function<String, ObservableSource<String>>() {
                            @Override
                            public ObservableSource<String> apply(String s) throws Exception {
                                String webData = getWebData(s);

                                String reg = "ex_url=\"\\S*\"";
                                Pattern pattern = Pattern.compile(reg);
                                Matcher matcher = pattern.matcher(webData);
                                String group;
                                StringBuilder sb = new StringBuilder();
                                sb.append("腾讯应用宝\n");
                                while (matcher.find()) {
                                    group = matcher.group();
                                    if (group.contains(packageName)) {
                                        sb.append(group.substring(8, group.length() - 1));
                                        break;
                                    }
                                }
                                sb.append("\n\n");
                                return Observable.just(sb.toString());
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String str) throws Exception {
                                mTv.append(str);
                            }
                        });
    }

    private void get360(final String packName, final String appUrl) {

        Disposable dis =
                Observable.just(appUrl)
                        .flatMap(new Function<String, ObservableSource<String>>() {
                            @Override
                            public ObservableSource<String> apply(String s) throws Exception {
                                String webData = getWebData(s);
                                String reg = "data-url=\"\\S*.apk\"";
                                Pattern pattern = Pattern.compile(reg);
                                Matcher matcher = pattern.matcher(webData);
                                String group;
                                StringBuffer sb = new StringBuffer();
                                sb.append("360\n");
                                while (matcher.find()) {
                                    group = matcher.group();
                                    if (group.contains(packName)) {
                                        sb.append(group.substring(10, group.length() - 1));
                                        break;
                                    }
                                }
                                sb.append("\n\n");
                                return Observable.just(sb.toString());
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String str) throws Exception {
                                mTv.append(str);
                            }
                        });
    }


    private void getHuawei(final String packName, final String appUrl) {

        Disposable dis =
                Observable.just(appUrl)
                        .flatMap(new Function<String, ObservableSource<String>>() {
                            @Override
                            public ObservableSource<String> apply(String s) throws Exception {
                                String webData = getWebData(s);
                                String reg = "data-url=\"\\S*.apk\"";
                                Pattern pattern = Pattern.compile(reg);
                                Matcher matcher = pattern.matcher(webData);
                                String group;
                                StringBuffer sb = new StringBuffer();
                                sb.append("华为\n");
                                while (matcher.find()) {
                                    group = matcher.group();
                                    if (group.contains(packName)) {
                                        sb.append(group.substring(10, group.length() - 1));
                                        break;
                                    }
                                }
                                sb.append("\n\n");
                                return Observable.just(sb.toString());
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String str) throws Exception {
                                mTv.append(str);
                            }
                        });
    }

    private String getWebData(String urlStr) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                connection.disconnect();
            }
        }

        return sb.toString();
    }

    public static String UnicodeToGB(String content) {
        //String reg = "\\\\u([a-zA-Z0-9]{4})";
        //Pattern pattern = Pattern.compile(reg);
        //Matcher matcher = pattern.matcher(content);
        //
        //StringBuilder sb = new StringBuilder(content);
        //while (matcher.find())
        //{
        //
        //    int data = Integer.parseInt(matcher.group(), 16);// 转换出每一个代码点
        //    //matcher.replaceFirst(data);
        //}
        //return sb.toString();

        StringBuffer stringBuffer = new StringBuffer();
        String pattern = "\\\\u[0-9,a-f,A-F]{4}";
        Matcher matcher = Pattern.compile(pattern).matcher(content);
        while (matcher.find()) {
            char c = (char) Integer.parseInt(matcher.group().replaceAll("\\\\u", ""), 16);
            matcher.appendReplacement(stringBuffer, new String(new char[] { c }));
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }
}
