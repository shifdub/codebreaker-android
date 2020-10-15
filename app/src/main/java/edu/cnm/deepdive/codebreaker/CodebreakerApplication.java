package edu.cnm.deepdive.codebreaker;

import android.app.Application;
import android.util.Log;
import com.facebook.stetho.Stetho;
import edu.cnm.deepdive.codebreaker.service.CodeBreakerDatabase;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class CodebreakerApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    CodeBreakerDatabase.setContext(this);
    CodeBreakerDatabase.getInstance().getGameDao().delete()
        .subscribeOn(Schedulers.io())
        .subscribe();
  }
}

