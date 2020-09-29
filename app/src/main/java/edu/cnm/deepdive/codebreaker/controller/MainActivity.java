package edu.cnm.deepdive.codebreaker.controller;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import edu.cnm.deepdive.codebreaker.R;
import edu.cnm.deepdive.codebreaker.model.Code.Guess;
import edu.cnm.deepdive.codebreaker.model.Game;
import java.security.SecureRandom;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  OnClickListener{

  private static final String POOL ="ROYGBIV";
  private static final int CODE_LENGTH = 4;

  private ListView guessList = findViewById(R.id.guess_list);
  private EditText guess;
  private Button submit;
  private Game game;
  private ArrayAdapter<Guess> adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    guess = findViewById(R.id.guess);
    submit = findViewById(R.id.submit);
    submit.setOnClickListener(this);
    game = new Game(POOL, CODE_LENGTH, new SecureRandom());
    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<Guess>());
    guessList.setAdapter(adapter);

  }

  @Override
  public void onClick(View v) {
    try {
      String text = guess.getText().toString().toUpperCase();
      Guess guess = game.guess(text);
      adapter.add(guess);
      this.guess.setText("");
    } catch (IllegalArgumentException e) {
      Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
    }

  }


}