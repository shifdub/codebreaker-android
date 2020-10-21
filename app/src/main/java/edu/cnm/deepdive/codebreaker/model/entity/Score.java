package edu.cnm.deepdive.codebreaker.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity
public class Score {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "game_id")
  private long id;

  @ColumnInfo(index = true)
  private Date timestamp;

  @ColumnInfo(name = "guess_count")
  private int guessCount;

  @ColumnInfo(name = "code_length", index = true)
  private int codeLength;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }
}
