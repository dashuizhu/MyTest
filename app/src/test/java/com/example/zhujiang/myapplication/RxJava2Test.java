package com.example.zhujiang.myapplication;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * @author zhuj 2017/10/16 上午10:59.
 */
public class RxJava2Test {

CompositeDisposable mCompositeDisposable;
  @Test
  public void testRxjava() {
    mCompositeDisposable = new CompositeDisposable();
    Observable<Long> observable = Observable.interval(1, TimeUnit.SECONDS);
    observable.subscribe(new Observer<Long>() {
      @Override public void onSubscribe(@NonNull Disposable d) {
        System.out.println("onSubscribe");
        mCompositeDisposable.add(d);
      }

      @Override public void onNext(@NonNull Long s) {
        System.out.println("onNext " + s);
        if (s>2) {
          System.out.println("onNext dispose " + mCompositeDisposable.size());
          mCompositeDisposable.dispose();
        }
      }

      @Override public void onError(@NonNull Throwable e) {

      }

      @Override public void onComplete() {
        System.out.println("完成");
      }
    });

    Observable.timer(3, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
      @Override public void onSubscribe(@NonNull Disposable d) {
        mCompositeDisposable.add(d);
        System.out.println("timer on subscribe " + mCompositeDisposable.size());
      }

      @Override public void onNext(@NonNull Long aLong) {
        System.out.println("timer on onNext " + aLong);
      }

      @Override public void onError(@NonNull Throwable e) {

      }

      @Override public void onComplete() {
        System.out.println("timer on onComplete ");
      }
    });

    Observable.timer(1, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
      @Override public void onSubscribe(@NonNull Disposable d) {
        mCompositeDisposable.add(d);
        System.out.println("timer1 on subscribe " + mCompositeDisposable.size());
      }

      @Override public void onNext(@NonNull Long aLong) {
        System.out.println("timer1 on onNext " + aLong);
      }

      @Override public void onError(@NonNull Throwable e) {

      }

      @Override public void onComplete() {
        System.out.println("timer1 on onComplete ");
      }
    });

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
