package Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Model.ListItem;
import Model.Topic;
import jp.faris.PracticeActivity;
import jp.faris.R;

public class CardAdapter extends ArrayAdapter<Topic> {
    private Integer[] topicImages = {R.drawable.rawpixel_559744_unsplash, R.drawable.kitchenpic, R.drawable.goh_rhy_yan_273919_unsplash, R.drawable.kendall_henderson_43316_unspla, R.drawable.domenico_loia_272251_unsplash};

    private int layoutResource;
    private Activity activity;
    private ArrayList<Topic> tList = new ArrayList<>();

    public CardAdapter(Activity act, int resource, ArrayList<Topic> data) {
        super(act, resource, data);
        layoutResource = resource;
        activity = act;
        tList = data;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return tList.size();
    }

    @Override
    public Topic getItem(int position) {
        return tList.get(position);
    }

    @Override
    public int getPosition(Topic item) {
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

            holder.imageView = (ImageView) row.findViewById(R.id.pics);
            holder.topicText = row.findViewById(R.id.topic_Text);
            holder.descriptionText = row.findViewById(R.id.description_Text);

            row.setTag(holder);
        }else {
            holder = (ViewHolder) row.getTag();
        }

        holder.topic = getItem(position);
        holder.imageView.setImageResource(topicImages[position]);
        holder.topicText.setText(holder.topic.getTopic());
        holder.descriptionText.setText(tList.get(position).getDescription());

        final ViewHolder finalHolder = holder;
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, PracticeActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("topicObj", finalHolder.topic);
                intent.putExtras(bundle);

                activity.startActivity(intent);

            }
        });

        return row;
    }

    public class ViewHolder {
        Topic topic;
        TextView topicText;
        TextView descriptionText;
        ImageView imageView;
    }

}
