package jp.faris;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import Data.DatabaseHandler;
import Model.Topic;
import Model.Word;
import Util.Utils;

/**
 * Created by JP on 9.5.2018.
 */

public class PracticeActivity extends AppCompatActivity {

    ImageButton speakButton;
    ImageButton listenButton;
    ImageButton refreshButton;
    ImageView backgroundImage;
    TextView textToSpeak;
    TextView userSayInput;
    TextView wordCount;
    TextView pointCount;
    TextView topicText;
    TextToSpeech tts;
    Boolean resultCheck;

    DatabaseHandler db;
    ArrayList wordArraylist = new ArrayList();

    private static final int SPEECH_REQUEST_CODE = 0;
    private List<String> wordList;
    private int wordCounter = 0;
    private int pointCounter = 0;
    private int add = 100;
    private int wordCheck = 0;

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        speakButton = findViewById(R.id.speak_button);
        listenButton = findViewById(R.id.listen_button);
        refreshButton = findViewById(R.id.refresh_button);
        backgroundImage = findViewById(R.id.imageView3);
        textToSpeak = findViewById(R.id.wordToSay_button);
        topicText = findViewById(R.id.topicView);
        userSayInput = findViewById(R.id.word_said);
        pointCount = findViewById(R.id.Points);

        final Topic topic = (Topic) getIntent().getSerializableExtra("topicObj");
        topicText.setText(topic.getTopic());

        //Setting up Toolbar for the app
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //StatusBar color change
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        if (topicText.getText().toString().equalsIgnoreCase("Kitchen")) {
            backgroundImage.setImageResource(R.drawable.michael_browning_14090_unsplas);
        }else if (topicText.getText().toString().equalsIgnoreCase("ICT")) {
            backgroundImage.setImageResource(R.drawable.farzad_nazifi_71686_unsplash);
        }else if (topicText.getText().toString().equalsIgnoreCase("Sports")) {
            backgroundImage.setImageResource(R.drawable.goh_rhy_yan_273919_unsplash);
        }else if (topicText.getText().toString().equalsIgnoreCase("Business")) {
            backgroundImage.setImageResource(R.drawable.rawpixel_378006_unsplash);
        }else if (topicText.getText().toString().equalsIgnoreCase("Transportation")) {
            backgroundImage.setImageResource(R.drawable.scott_webb_57628_unsplash);
        }

        //DatabaseHandler db = new DatabaseHandler(this);
        db = new DatabaseHandler(this);
        speakButton.setEnabled(false);

        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSpeechRecognizer();
            }
        });

        listenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO sanojen toisto: SPEAK vanhentunut, tutustu uuteen tapaan
                String toSay = textToSpeak.getText().toString();
                tts.speak(toSay, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshButton.setEnabled(false);
                speakButton.setEnabled(true);

                String topicMatch = topicText.getText().toString();

                if (topicMatch.equalsIgnoreCase("Business")) {
                    Cursor cursor = db.getBusinessWords();
                    cursor.moveToNext();
                    textToSpeak.setText(cursor.getString(cursor.getColumnIndex(Utils.KEY_WORDS)));
                }else if (topicMatch.equalsIgnoreCase("Kitchen")) {
                    Cursor cursor = db.getKitchenWords();
                    cursor.moveToNext();
                    textToSpeak.setText(cursor.getString(cursor.getColumnIndex(Utils.KEY_WORDS)));
                }else if (topicMatch.equalsIgnoreCase("Sports")) {
                    Cursor cursor = db.getSportsWords();
                    cursor.moveToNext();
                    textToSpeak.setText(cursor.getString(cursor.getColumnIndex(Utils.KEY_WORDS)));

                }else if (topicMatch.equalsIgnoreCase("Transportation")) {
                    Cursor cursor = db.getTransportWords();
                    cursor.moveToNext();
                    textToSpeak.setText(cursor.getString(cursor.getColumnIndex(Utils.KEY_WORDS)));

                }else if (topicMatch.equalsIgnoreCase("ICT")) {
                    Cursor cursor = db.getICTWords();
                    cursor.moveToNext();
                    textToSpeak.setText(cursor.getString(cursor.getColumnIndex(Utils.KEY_WORDS)));

                }else {
                    Cursor cursor = db.getAllWords();
                    cursor.moveToNext();
                    textToSpeak.setText(cursor.getString(cursor.getColumnIndex(Utils.KEY_WORDS)));
                }

                wordCounter++;
                speakButton.setEnabled(true);

                if (wordCounter > 5) {
                    //refreshButton.setText("Tulokset");
                    Intent intent = new Intent(PracticeActivity.this, ScoreActivity.class);
                    intent.putExtra("SCORE", pointCounter);
                    intent.putExtra("CORRECT_WORDS", wordCheck);
                    startActivity(intent);
                    db.addScore(pointCounter, topic.getTopic());
                }

                //Manual search from resourcefile
                /*if (wordList == null || wordList.size() == 0) {
                    wordList = new ArrayList<String>();
                    Collections.addAll(wordList, res.getStringArray(R.array.business_word_list));
                    Collections.shuffle(wordList);
                }
                String w = wordList.remove(0);
                textToSpeak.setText(w);*/

                //TODO älä poista, vanha random sekoittaja
                //String[] wordList = getResources().getStringArray(R.array.business_word_list);
                //int randomWord = random.nextInt((wordList.length - 0));
               // String chosen = wordList[randomWord];
               // textToSpeak.setText(chosen);

                //TODO estä samojen sanojen toistaminen
            }
        });

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                }
            }
        });

    }
    //Metodi joka tarkastaa käyttäjän sanoman ja vertaa sitä valittuun sanaan
    private void checkUserResponse() {
        //TODO Parempi tapa antaa palaute vastauksesta

        String correctAnswer = textToSpeak.getText().toString();
        String voiceInput = userSayInput.getText().toString();

        if (voiceInput.equalsIgnoreCase(correctAnswer)){
            pointCounter += add;
            wordCheck += 1;
            userSayInput.setTextColor(Color.GREEN);
            pointCount.setText("Pisteet: " + Integer.toString(pointCounter));
            refreshButton.setEnabled(true);
            speakButton.setEnabled(false);
        }else{
            if (pointCounter > 0) {
                pointCounter -= 50;
            }
            userSayInput.setTextColor(Color.RED);
            refreshButton.setEnabled(true);
            speakButton.setEnabled(false);
        }
    }

    //Metodi jossa käynnistetään puheentunnistus
    private void openSpeechRecognizer() {
        //TODO Paranna puheen tarkkuutta
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Puhu nyt");
        try {
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        }catch (ActivityNotFoundException a) {

        }

    }

    //TODO Puheen käsittely

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SPEECH_REQUEST_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    userSayInput.setText(result.get(0));
                }
                checkUserResponse();
                break;
            }
        }
    }
}