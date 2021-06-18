package com.example.zhujiang.myapplication.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.util.Log;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import org.jetbrains.annotations.NotNull;

/**
 * @author zhuj 2019/4/17 上午10:44.
 */
public class BasePresenter implements IPresenter {

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public void destroy() {
        if (mCompositeDisposable == null) {
            return;
        }
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
        mCompositeDisposable = null;
    }

    protected void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            return;
        }
        mCompositeDisposable.add(disposable);
    }


    @Override
    public void onCreate(@NotNull LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(@NotNull LifecycleOwner owner) {
        Log.e("test " , "ondestroy " + getClass().getSimpleName() + " " + owner.getClass().getSimpleName());
        destroy();
    }

    @Override
    public void onLifecycleChanged(@NotNull LifecycleOwner owner, @NotNull Lifecycle.Event event) {

    }

}
