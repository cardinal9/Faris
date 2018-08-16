package jp.faris;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

import Adapter.ScoreAdapter;
import Data.DatabaseHandler;
import Model.Score;

public class ScoreBoardFragment extends Fragment {

    ScoreAdapter scoreAdapter;
    ListView listView;
    ArrayList<Score> scoreList = new ArrayList<>();
    TextView scoreTexts;
    TextView scoreTopicTexts;
    Score scoreObj;
    DatabaseHandler db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        final View view = inflater.inflate(R.layout.scoreboard_fragment, container, false);

        listView = (ListView) view.findViewById(R.id.scoreListView);
        scoreTexts = (TextView) view.findViewById(R.id.scoresText);
        scoreTopicTexts = (TextView) view.findViewById(R.id.scoresTopic);

        db = new DatabaseHandler(getContext());
        ArrayList<Score> scoreArrayListDB = db.getScores();

        for (int i = 0; i < scoreArrayListDB.size(); i++) {
            int score = scoreArrayListDB.get(i).getScore();
            String scoreText = scoreArrayListDB.get(i).getScoreTopic();

            Log.v("Score: ", String.valueOf(score));
            Log.v("Topic: ", String.valueOf(scoreText));

            scoreObj = new Score();
            scoreObj.setScore(score);
            scoreObj.setScoreTopic(scoreText);

            scoreList.add(scoreObj);
        }

        db.close();

        scoreAdapter = new ScoreAdapter(getActivity(), R.layout.score_row, scoreList);
        listView.setAdapter(scoreAdapter);
        scoreAdapter.notifyDataSetChanged();


        //SharedPreferences preferences = this.getActivity().getSharedPreferences("SCORE", 0);


        return view;
    }
}
