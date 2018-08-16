package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import Model.Topic;
import jp.faris.R;

public class TopicAdapter extends ArrayAdapter<Topic> {
    public TopicAdapter(Context context, ArrayList<Topic> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Topic topic = getItem(position);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_row, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.topic = getItem(position);
            viewHolder.topicText = (TextView) convertView.findViewById(R.id.topic_Text);
            viewHolder.descriptionText = (TextView) convertView.findViewById(R.id.description_Text);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.topicText.setText(topic.getTopic());
        viewHolder.descriptionText.setText(topic.getDescription());

        return convertView;

    }


    static class ViewHolder {
        Topic topic;
        TextView topicText;
        TextView descriptionText;
    }
}
