package com.example.zhujiang.myapplication.game;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import com.example.zhujiang.myapplication.R;

/**
 * @author zhuj 2018/9/15 下午2:23.
 */
public class SoundManager {

    private static SoundManager mSoundManager;

    private SoundPool mSoundPool;

    public static int VIDEO_EAT;
    public static int VIDEO_MOVE;
    public static int VIDEO_SUCCESS;
    public static int VIDEO_FAIL;
    public static int VIDEO_BEGIN;
    public static int VIDEO_GAME_START;
    public static int VIDEO_FORBID;

    public static SoundManager getInstance(Context context) {
        if (mSoundManager == null) {
            synchronized (SoundManager.class) {
                if (mSoundManager == null) {
                    mSoundManager = new SoundManager();
                    mSoundManager.init(context.getApplicationContext());
                }
                return mSoundManager;
            }
        }
        return mSoundManager;
    }

    public void init(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSoundPool = new SoundPool.Builder().setMaxStreams(10).build();
        } else {
            mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
        }
        VIDEO_EAT = mSoundPool.load(context, R.raw.eat, 1);
        VIDEO_MOVE = mSoundPool.load(context, R.raw.move, 1);
        VIDEO_BEGIN = mSoundPool.load(context, R.raw.begin, 1);
        VIDEO_SUCCESS = mSoundPool.load(context, R.raw.success, 1);
        VIDEO_FAIL = mSoundPool.load(context, R.raw.fail, 1);
        VIDEO_GAME_START = mSoundPool.load(context, R.raw.game_start, 1);
        VIDEO_FORBID = mSoundPool.load(context, R.raw.forbid, 1);
    }

    public void play(int i) {
        mSoundPool.play(i, 1f, 1f, 1, 0, 1);
    }
}