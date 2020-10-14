package edu.cnm.deepdive.codebreaker.GameDao.model.doa;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import edu.cnm.deepdive.codebreaker.model.entity.Game;
import io.reactivex.Single;
import java.util.Collection;
import java.util.List;

@Dao
public interface GameDao {

  @Insert
  Single<Long> insert(Game game);

  @Insert
  Single<List<Long>> insert(Game... games);

  @Insert

  Single<List<Long>> insert(Collection<Game> games);

  @Delete
  Single<Integer> delete(Game game);

  @Delete
  Single<Integer> delete(Game... games);

  @Delete
  Single<Integer> delete(Collection<Game> games);



}
