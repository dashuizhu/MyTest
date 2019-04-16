package com.example.zhujiang.myapplication.phone;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserPhone {

    public static String inPhone(Context context) {
        JSONArray jsonArray = new JSONArray();
        ContentResolver contentResolver = context.getContentResolver();
        // 获得所有的联系人
        Cursor cursor =
                contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null,
                        null);// 循环遍历
        try {
            if (cursor.moveToFirst()) {
                int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                int displayNameColumn =
                        cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                do {// 获得联系人的ID号
                    String contactId = cursor.getString(idColumn);
                    // 获得联系人姓名
                    String disPlayName = cursor.getString(displayNameColumn);

                    long lastTime = cursor.getLong(cursor.getColumnIndex(
                            ContactsContract.Data.CONTACT_LAST_UPDATED_TIMESTAMP));

                    // 查看该联系人有多少个电话号码。如果没有这返回值为0
                    int phoneCount = cursor.getInt(
                            cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    //在联系人数量不为空的情况下执行
                    if (phoneCount > 0) {
                        // 获得联系人的电话号码列表
                        Cursor phonesCursor = context.getContentResolver()
                                .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                        null, null, null);
                        if (phonesCursor.moveToFirst()) {
                            do {// 遍历所有的电话号码
                                JSONObject json = new JSONObject();

                                String phoneNumber = phonesCursor.getString(
                                        phonesCursor.getColumnIndex(
                                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                                //                            "name": "姓名", "relation": "关系", "phone": "电话", "count": "半年通话次数", "lastTime": "最后一次通话时间"

                                String[] names = phonesCursor.getColumnNames();
                                for (String key : names) {
                                    json.put(key, phonesCursor.getString(
                                            phonesCursor.getColumnIndex(key)));
                                }

                                json.put("name", disPlayName);
                                json.put("relation", "1");
                                json.put("phone", phoneNumber);
                                json.put("count", "1");
                                json.put("lastTime", "1");
                                Log.w("userPhone", json.toString());
                                //Log.e("userphone", "联系人姓名：" + disPlayName + " 联系人电话：" + phoneNumber);

                                jsonArray.put(json);
                            } while (phonesCursor.moveToNext());
                        }
                    }
                } while (cursor.moveToNext());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray.toString();
    }

    /**
     * 读取数据
     *
     * @return 读取到的数据
     */
    public static String getDataList(Context mcontext) {
        JSONArray jsonArray = new JSONArray();
        Uri callUri = CallLog.Calls.CONTENT_URI;
        String[] columns = {
                CallLog.Calls.CACHED_NAME// 通话记录的联系人
                , CallLog.Calls.NUMBER// 通话记录的电话号码
                , CallLog.Calls.DATE// 通话记录的日期
                , CallLog.Calls.DURATION// 通话时长
                , CallLog.Calls.TYPE
        };// 通话类型}

        // 1.获得ContentResolver
        ContentResolver resolver = mcontext.getContentResolver();
        if (ContextCompat.checkSelfPermission(mcontext, Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {
        }
        // 2.利用ContentResolver的query方法查询通话记录数据库
        /**
         * @param uri 需要查询的URI，（这个URI是ContentProvider提供的）
         * @param projection 需要查询的字段
         * @param selection sql语句where之后的语句
         * @param selectionArgs ?占位符代表的数据
         * @param sortOrder 排序方式
         */
        Cursor cursor = resolver.query(callUri, // 查询通话记录的URI
                columns, null, null, CallLog.Calls.DEFAULT_SORT_ORDER// 按照时间逆序排列，最近打的最先显示
        );

        Map<String, JSONObject> map = new LinkedHashMap<>();

        // 3.通过Cursor获得数据
        while (cursor.moveToNext()) {

            String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
            String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
            long dateLong = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(dateLong));

            //拿到所有的字段名
            //String[] keys = cursor.getColumnNames();
            //for (String  key : keys) {
            //    //将字段名 ， 和 值 写入json
            //    json.put(key, cursor.getString(cursor.getColumnIndex(key)));
            //}
            //
            try {
                //如果号码已经有 信息了， 就通话次数+1
                if (map.containsKey(number)) {
                    JSONObject jsonObj = map.get(number);
                    jsonObj.put("count", jsonObj.getInt("count") + 1);
                } else {
                    //没有就保存
                    JSONObject json = new JSONObject();
                    if (TextUtils.isEmpty(name)) {
                        name = "未备注联系人";
                    }
                    json.put("name", name);
                    json.put("relation", "1");
                    json.put("phone", number);
                    json.put("count", 1);
                    json.put("lastTime", date);

                    map.put(number, json);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //获得 map的  key 集合
        Set<String> keys = map.keySet();
        //通过key ，获得对应的jsonObject
        for (String key : keys) {
            jsonArray.put(map.get(key));
        }

        return jsonArray.toString();
    }
}
