package jp.faris;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import Data.DatabaseHandler;

/**
 * Created by JP on 9.5.2018.
 */

public class ScoreActivity extends AppCompatActivity {

    TextView points;
    TextView correctWords;
    TextView toolbarText;
    Button menuButton;
    Button retryButton;
    DatabaseHandler db;
    int score;
    int wordCheckCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        points = (TextView) findViewById(R.id.scorepoints);
        correctWords = (TextView) findViewById(R.id.resultView);
        //toolbarText = (TextView) findViewById(R.id.toolbarText);
        menuButton = (Button) findViewById(R.id.toMenuButton);
        retryButton = (Button) findViewById(R.id.retryButton);

        db = new DatabaseHandler(this);

        score = getIntent().getIntExtra("SCORE", 0);
        wordCheckCount = getIntent().getIntExtra("CORRECT_WORDS", 0);
        points.setText("Pisteet: " + score);
        correctWords.setText("Sanat: " + wordCheckCount + " / 10");

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
