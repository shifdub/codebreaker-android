package edu.cnm.deepdive.codebreaker.controller;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import edu.cnm.deepdive.codebreaker.R;
import edu.cnm.deepdive.codebreaker.adapter.GuessAdapter;
import edu.cnm.deepdive.codebreaker.model.Code.Guess;
import edu.cnm.deepdive.codebreaker.model.Game;
import java.security.SecureRandom;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnClickListener {

  private static final String POOL = "ROYGBV";
  private static final int CODE_LENGTH = 4;

  private ListView guessList;
  private EditText guess;
  private Button submit;
  private Game game;
  private GuessAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    guessList = findViewById(R.id.guess_list);
    guess = findViewById(R.id.guess);
    submit = findViewById(R.id.submit);
    submit.setOnClickListener(this);
    game = new Game(POOL, CODE_LENGTH, new SecureRandom());
    adapter = new GuessAdapter(this);
    guessList.setAdapter(adapter);
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
        // TODO Start new game.
        break;
      default:
        handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }

  @Override
  public void onClick(View v) {
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

}