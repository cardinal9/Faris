package Data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Model.Score;
import Model.Topic;
import Model.Word;
import Util.Utils;
import jp.faris.R;

import static Util.Utils.KEY_DESCRIPTION;
import static Util.Utils.KEY_TOPICS;
import static android.content.ContentValues.TAG;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final Context sContext;
    private final ArrayList<Topic> topicsList = new ArrayList<>();
    private final ArrayList<Score> scoreList = new ArrayList<>();

    public DatabaseHandler(Context context) {
        super(context, Utils.DATABASE_NAME, null, Utils.DATABASE_VERSION);
        sContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WORD_TABLE = "CREATE TABLE " + Utils.TABLE_WORDS + "("
                + Utils.KEY_WORD_ID + " INTEGER PRIMARY KEY,"
                + Utils.KEY_WORDS + " TEXT,"
                + Utils.KEY_WORD_TOPIC +
                " TEXT" + ");";

        String CREATE_TOPIC_TABLE = "CREATE TABLE " + Utils.TABLE_TOPICS + "("
                + Utils.KEY_TOPIC_ID + " INTEGER PRIMARY KEY,"
                + KEY_TOPICS + " TEXT,"
                + Utils.KEY_DESCRIPTION + " TEXT" + ");";

        String CREATE_SCORE_TABLE = "CREATE TABLE " + Utils.TABLE_SCORES + "("
                +Utils.KEY_SCORE_ID + " INTEGER PRIMARY KEY,"
                +Utils.KEY_SCORE + " INTEGER," + Utils.KEY_SCORE_TOPIC + " TEXT" + ");";

        db.execSQL(CREATE_WORD_TABLE);
        db.execSQL(CREATE_TOPIC_TABLE);
        db.execSQL(CREATE_SCORE_TABLE);

        ContentValues values = new ContentValues();
        ContentValues valuesa = new ContentValues();
        ContentValues valuesk = new ContentValues();
        ContentValues valuesb = new ContentValues();
        ContentValues valuesi = new ContentValues();
        ContentValues valuess = new ContentValues();
        ContentValues valuest = new ContentValues();

        Resources res = sContext.getResources();
        String[] wordsLists = res.getStringArray(R.array.all_array);
        String[] businessList = res.getStringArray(R.array.business_array);
        String[] kitchenList = res.getStringArray(R.array.kitchen_array);
        String[] sportsList = res.getStringArray(R.array.sports_array);
        String[] ictList = res.getStringArray(R.array.ict_array);
        String[] transportList = res.getStringArray(R.array.transportation_array);
        String[] topicsLists = res.getStringArray(R.array.topic_list);

        int lengthy = wordsLists.length;
        int kitchy = kitchenList.length;
        int sportsy = sportsList.length;
        int ictsy = ictList.length;
        int businesy = businessList.length;
        int transportsy = transportList.length;
        int topicsy = topicsLists.length;

        for (int i = 0; i < lengthy; i++) {
            values.put(Utils.KEY_WORDS, wordsLists[i]);
            db.insert(Utils.TABLE_WORDS, null, values);
        }

        for (int i = 0; i < kitchy; i++) {
            valuesk.put(Utils.KEY_WORDS, kitchenList[i]);
            valuesk.put(Utils.KEY_WORD_TOPIC, "Kitchen");
            db.insert(Utils.TABLE_WORDS, null, valuesk);
        }

        for (int i = 0; i < businesy; i++) {
            valuesb.put(Utils.KEY_WORDS, businessList[i]);
            valuesb.put(Utils.KEY_WORD_TOPIC, "Business");
            db.insert(Utils.TABLE_WORDS, null, valuesb);
        }

        for (int i = 0; i < ictsy; i++) {
            valuesi.put(Utils.KEY_WORDS, ictList[i]);
            valuesi.put(Utils.KEY_WORD_TOPIC, "ICT");
            db.insert(Utils.TABLE_WORDS, null, valuesi);
        }

        for (int i = 0; i < sportsy; i++) {
            valuess.put(Utils.KEY_WORDS, sportsList[i]);
            valuess.put(Utils.KEY_WORD_TOPIC, "Sports");
            db.insert(Utils.TABLE_WORDS, null, valuess);
        }

        for (int i = 0; i < transportsy; i++) {
            valuest.put(Utils.KEY_WORDS, transportList[i]);
            valuest.put(Utils.KEY_WORD_TOPIC, "Transports");
            db.insert(Utils.TABLE_WORDS, null, valuesk);
        }

        for (int i = 0; i < topicsy; i++) {
            valuesa.put(Utils.KEY_TOPICS, topicsLists[i]);
            valuesa.put(Utils.KEY_DESCRIPTION, topicsLists[i]);
            db.insert(Utils.TABLE_TOPICS, null, valuesa);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_WORDS);
        db.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_TOPICS);
        db.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_SCORES);
        onCreate(db);
    }

    public Cursor getBusinessWords() {
        SQLiteDatabase db = this.getReadableDatabase();
        String businessQuery = "SELECT * FROM " + Utils.TABLE_WORDS + " WHERE " + Utils.KEY_WORD_TOPIC + " = 'Business' ORDER BY RANDOM() LIMIT 1";
        Cursor cursor = db.rawQuery(businessQuery, null);

        return cursor;
    }

    public Cursor getKitchenWords() {
        SQLiteDatabase db = this.getReadableDatabase();
        String kitchenQuery = "SELECT * FROM " + Utils.TABLE_WORDS + " WHERE " + Utils.KEY_WORD_TOPIC + " = 'Kitchen' ORDER BY RANDOM() LIMIT 1";
        Cursor cursor = db.rawQuery(kitchenQuery, null);
        return cursor;
    }

    public Cursor getSportsWords() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sportsQuery = "SELECT * FROM " + Utils.TABLE_WORDS + " WHERE " + Utils.KEY_WORD_TOPIC + " = 'Sports' ORDER BY RANDOM() LIMIT 1";
        Cursor cursor = db.rawQuery(sportsQuery, null);
        return cursor;
    }

    public Cursor getTransportWords() {
        SQLiteDatabase db = this.getReadableDatabase();
        String transportQuery = "SELECT * FROM " + Utils.TABLE_WORDS + " WHERE " + Utils.KEY_WORD_TOPIC + " = 'Transports' ORDER BY RANDOM() LIMIT 1";
        Cursor cursor = db.rawQuery(transportQuery, null);
        return cursor;
    }

    public Cursor getICTWords() {
        SQLiteDatabase db = this.getReadableDatabase();
        String ictQuery = "SELECT * FROM " + Utils.TABLE_WORDS + " WHERE " + Utils.KEY_WORD_TOPIC + " = 'ICT' ORDER BY RANDOM() LIMIT 1";
        Cursor cursor = db.rawQuery(ictQuery, null);
        return cursor;
    }

    public Cursor getAllWords() {
        SQLiteDatabase db = this.getReadableDatabase();
        String getQuery = "SELECT * FROM " + Utils.TABLE_WORDS + " ORDER BY RANDOM() LIMIT 1";
        Cursor cursor = db.rawQuery(getQuery, null);
        return cursor;
    }

    public ArrayList<Topic> getAllTopics() {

        topicsList.clear();

        SQLiteDatabase db = this.getReadableDatabase();
        String topicQuery = " SELECT * FROM " + Utils.TABLE_TOPICS;
        Cursor cursor = db.rawQuery(topicQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Topic topic = new Topic();
                topic.setTopic(cursor.getString(cursor.getColumnIndex(Utils.KEY_TOPICS)));
                topic.setDescription(cursor.getString(cursor.getColumnIndex(Utils.KEY_DESCRIPTION)));
                topicsList.add(topic);

            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return topicsList;
    }

    public void addScore(int score, String scoreTopic) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utils.KEY_SCORE, score);
        values.put(Utils.KEY_SCORE_TOPIC, scoreTopic);
        Log.v(TAG, "Added score" + values);
        db.insert(Utils.TABLE_SCORES, null, values);
        db.close();
    }

    public ArrayList<Score> getScores() {

        SQLiteDatabase db = this.getReadableDatabase();
        String scoreQuery = "SELECT * FROM " + Utils.TABLE_SCORES + " ORDER BY " + Utils.KEY_SCORE + " DESC LIMIT 10";
        Cursor cursor = db.rawQuery(scoreQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Score score = new Score();
                score.setScore(cursor.getInt(cursor.getColumnIndex(Utils.KEY_SCORE)));
                score.setScoreTopic(cursor.getString(cursor.getColumnIndex(Utils.KEY_SCORE_TOPIC)));
                scoreList.add(score);

            }while (cursor.moveToNext());
        }

        return scoreList;
    }

}
