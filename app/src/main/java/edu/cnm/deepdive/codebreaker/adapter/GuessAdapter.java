package edu.cnm.deepdive.codebreaker.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import edu.cnm.deepdive.codebreaker.R;
import edu.cnm.deepdive.codebreaker.model.Code.Guess;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GuessAdapter extends ArrayAdapter<Guess> {
  private static final char[] colorCharacters = {'R','O', 'Y', 'B', 'V'};
  private static final int[] colorValues =
      {Color.RED, 0xffa500, Color.YELLOW, Color.GREEN, Color.BLUE, 0xee82ee};

  private final Map<Character, Integer> colorMap;


  public GuessAdapter(@NonNull Context context) {
    super(context, R.layout.item_guess, new ArrayList<Guess>());
    colorMap = new HashMap<>();
    for (int i = 0; i < colorCharacters.length; i++){
      colorMap.put(colorCharacters[i], colorValues[i]);
    }
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    View layout = (convertView != null)
        ? convertView
        : LayoutInflater.from(getContext()).inflate(R.layout.item_guess, parent, false);
      Guess guess = getItem(position);
      TextView correct = layout.findViewById(R.id.correct);
      TextView close = layout.findViewById(R.id.close);
      correct.setText(String.valueOf(guess.getCorrect()));
      close.setText(Integer.toString(guess.getClose()));
      // TODO Iterate over the characters of guess.getText(), and create ImageView with corresponding background color.

    return layout;
  }

}