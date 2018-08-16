package jp.faris;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Adapter.CardAdapter;
import Data.DatabaseHandler;
import Model.ListItem;
import Model.Topic;
import Util.Utils;

/**
 * Created by JP on 9.5.2018.
 */

public class MenuActivity extends AppCompatActivity {

    private DatabaseHandler db;
    private ArrayList<Topic> tList = new ArrayList<>();
    private CardAdapter cardAdapter;
    private ListView listView;
    private Topic topic;
    private TextView topicText;
    private TextView descText;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        listView = (ListView) findViewById(R.id.list);
        topicText = (TextView) findViewById(R.id.topic_Text);
        descText = (TextView) findViewById(R.id.description_Text);
        imageView = (ImageView) findViewById(R.id.pics);

        db = new DatabaseHandler(getApplicationContext());
        ArrayList<Topic> topicsListDB = db.getAllTopics();
        String topictext = Utils.KEY_TOPICS;

        //topicText.setText(topictext);

        for (int i = 0; i < topicsListDB.size(); i++) {
            String topico = topicsListDB.get(i).getTopic();
            String desci = topicsListDB.get(i).getDescription();

            Log.v("TOPICS", String.valueOf(topico));
            Log.v("DESC", String.valueOf(desci));

            topic = new Topic();
            topic.setTopic(topico);
            //topic.setDescription(desci);

            tList.add(topic);
        }
        db.close();

        cardAdapter = new CardAdapter(MenuActivity.this, R.layout.list_row, tList);
        listView.setAdapter(cardAdapter);
        cardAdapter.notifyDataSetChanged();

        //TODO Puhutun ja kirjoitetun välillä valitseminen
    }

}