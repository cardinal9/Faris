package Adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import Model.Score;

import jp.faris.R;

public class ScoreAdapter extends ArrayAdapter<Score> {

    private int layoutResource;
    private Activity activity;
    private ArrayList<Score> scoreList;

    public ScoreAdapter(Activity act, int resource, ArrayList<Score> data) {
        super(act, resource, data);
        layoutResource = resource;
        activity = act;
        scoreList = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return scoreList.size();
    }

    @Override
    public Score getItem(int position) {
        return scoreList.get(position);
    }

    @Override
    public int getPosition(Score item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = null;

        if (row == null || (row.getTag() == null)) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            row = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();

            holder.scoreText = row.findViewById(R.id.scoresText);
            holder.scoreTopicText = row.findViewById(R.id.scoresTopic);

            row.setTag(holder);
        }else {
            holder = (ScoreAdapter.ViewHolder) row.getTag();
        }

        holder.score = getItem(position);
        holder.scoreText.setText(String.valueOf(holder.score.getScore()));
        holder.scoreTopicText.setText(holder.score.getScoreTopic());

        final ViewHolder finalHolder = holder;

        return row;
    }

    public class ViewHolder {
        Score score;
        TextView scoreText;
        TextView scoreTopicText;
    }

}
