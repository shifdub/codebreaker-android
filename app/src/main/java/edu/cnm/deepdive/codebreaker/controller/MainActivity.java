package edu.cnm.deepdive.codebreaker.controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.codebreaker.R;
import edu.cnm.deepdive.codebreaker.adapter.GuessAdapter;
import edu.cnm.deepdive.codebreaker.viewmodel.MainViewModel;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

  private static final int[] colorValues =
      {Color.RED, 0xffffa500, Color.YELLOW, Color.GREEN, Color.BLUE, 0xff4b0082, 0xffee82ee};
  private static final Map<Character, Integer> colorMap =
      buildColorMap(MainViewModel.POOL.toCharArray(), colorValues);

  private ListView guessList;
  private EditText guess;
  private MainViewModel viewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setupViews();
    setupViewModel();
  }

  private void setupViewModel() {
    View guessControls = findViewById(R.id.guess_controls);
    viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    viewModel.getGame().observe(this, (game) -> {
      GuessAdapter adapter = new GuessAdapter(MainActivity.this, colorMap);
      adapter.addAll(game.getGuesses());
      guessList.setAdapter(adapter);
      guessList.setSelection(adapter.getCount() - 1);
      guess.setText("");
    });
    viewModel.getSolved().observe(this, solved ->
      guessControls.setVisibility(solved ? View.INVISIBLE :View.VISIBLE));
    viewModel.getThrowable().observe(this, (throwable)-> {
      if(throwable != null) {
        Toast.makeText(this, throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
      }
    });
  }

  private void setupViews() {
    guessList = findViewById(R.id.guess_list);
    guess = findViewById(R.id.guess);
    findViewById(R.id.submit).setOnClickListener((view) -> recordGuess());
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
      case R.id.settings:
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        break;
        default:
        handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }

  private void recordGuess() {
    viewModel.guess(guess.getText().toString().trim().toUpperCase());
  }

  private void startGame() {
    viewModel.startGame();
  }

  private void restartGame() {
    viewModel.restartGame();
  }

  private static Map<Character, Integer> buildColorMap(char[] chars, int[] values) {
    Map<Character, Integer> colorMap = new HashMap<>();
    for (int i = 0; i < chars.length; i++) {
      colorMap.put(chars[i], values[i]);
    }
    return colorMap;
  }

}