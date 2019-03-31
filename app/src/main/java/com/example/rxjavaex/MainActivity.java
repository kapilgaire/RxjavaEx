package com.example.rxjavaex;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();


    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Observable<String> observable = getAnimalObservable();

        Observer<String> observer = getAnimalObserver();

        observable.observeOn(Schedulers.io()).filter(new Predicate<String>() {
            @Override
            public boolean test(String s) throws Exception {
                return s.toLowerCase().startsWith("l");
            }
        })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

        private Observer<String> getAnimalObserver(){
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable=d;
                Log.e(TAG, "onSubscribe: " );

            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "onNext: "+s );

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: "+ e.getMessage());

            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: " );

            }
        };
        }

    private Observable<String> getAnimalObservable(){
        return Observable.just("deer","lion","rabbit","tiger","elephant");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
