package edu.cnm.deepdive.codebreaker.controller;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import edu.cnm.deepdive.codebreaker.R;
import edu.cnm.deepdive.codebreaker.adapter.GuessAdapter;
import edu.cnm.deepdive.codebreaker.model.Code.Guess;
import edu.cnm.deepdive.codebreaker.model.Game;
import edu.cnm.deepdive.codebreaker.viewmodel.MainViewModel;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

  private static final int[] colorValues =
      {Color.RED, 0xffffa500, Color.YELLOW, Color.GREEN, Color.BLUE, 0xff4b0082, 0xffee82ee};
  private static final Map<Character, Integer> colorMap =
      buildColorMap(MainViewModel.POOL.toCharArray(), colorValues);

  private ListView guessList;
  private EditText guess;
  private Button submit;
  private Game game;
  private GuessAdapter adapter;
  private SecureRandom rng;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    guessList = findViewById(R.id.guess_list);
    guess = findViewById(R.id.guess);
    submit = findViewById(R.id.submit);
    submit.setOnClickListener(view -> recordGuess());{
      adapter = new GuessAdapter(this, colorMap);
      rng = new SecureRandom();
      startGame();
      recordGuess();
    }


  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.options, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    boolean handled = true;
    switch (item.getItemId()) {
      case R.id.new_game:
        startGame();
        break;
      case R.id.restart_game:
        restartGame();
        break;
      default:
        handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }


  public void recordGuess() {
    try {
      String text = this.guess.getText().toString().toUpperCase();
      Guess guess = game.guess(text);
      adapter.add(guess);
      guessList.setSelection(adapter.getCount() - 1);
      this.guess.setText("");
    } catch (IllegalArgumentException e) {
      Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }
  }

  private void startGame() {
    game = new Game(MainViewModel.POOL, MainViewModel.CODE_LENGTH, rng);
    resetList();
  }

  private void restartGame() {
    game.restart();
    resetList();
  }

  private void resetList() {
    adapter.clear();
    guessList.setAdapter(adapter);
  }

  private static Map<Character, Integer> buildColorMap(char[] chars, int[] values) {
    Map<Character, Integer> colorMap = new HashMap<>();
    for (int i = 0; i < chars.length; i++) {
      colorMap.put(chars[i], values[i]);
    }
    return colorMap;
  }
}