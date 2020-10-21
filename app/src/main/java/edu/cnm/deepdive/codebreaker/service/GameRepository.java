package edu.cnm.deepdive.codebreaker.service;

import android.content.Context;
import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.codebreaker.model.Code.Guess;
import edu.cnm.deepdive.codebreaker.model.Game;
import edu.cnm.deepdive.codebreaker.model.entity.Score;
import edu.cnm.deepdive.codebreaker.model.entity.doa.ScoreDao;
import edu.cnm.deepdive.codebreaker.model.pojo.ScoreSummary;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.Date;
import java.util.List;
import java.util.Random;

;

public class GameRepository {

  private final Context context;
  private final ScoreDao scoreDao;

  public GameRepository(Context context) {
    this.context = context;
    scoreDao = CodebreakerDatabase.getInstance().getScoreDao();
  }

  public Completable save(Game game, Date timestamp, int previousGuessCount) {
    return Single.fromCallable(() -> {
      Score score = new Score();
      score.setCodeLengthh(game.getLength());
      score.setTimestamp(timestamp);
      score.setGuessCount(game.getGuessCount() + previousGuessCount);
      return score;
    })
        .subscribeOn(Schedulers.computation())
        .flatMap(scoreDao::insert)
        .ignoreElement()
        .subscribeOn(Schedulers.io());
  }

  public Single<Game> newGame(String pool, int codeLength, Random rng) {
    return Single.fromCallable(() -> new Game(pool, codeLength, rng))
        .subscribeOn(Schedulers.computation());
  }

  public Completable restartGame(Game game) {
    return Completable.fromAction(game::restart)
        .subscribeOn(Schedulers.computation());
  }

  public Single<Guess> guess(Game game, String text) {
    return Single.fromCallable(() -> game.guess(text))
        .subscribeOn(Schedulers.computation());
  }

  public LiveData<List<ScoreSummary>> getSummaries() {
    return scoreDao.selectSummaries();
  }

}
