package com.example.rahul.learnnfun;


import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class CustomList extends ArrayAdapter<String> {
    private String[] ids;
    private String[] names;
    private String[] emails;
    private Activity context;

//    public CustomList(Activity context, String[] ids, String[] names, String[] emails) {
//        super(context, R.layout.list_item, ids);
//        this.context = context;
//        this.ids = ids;
//        this.names = names;
//        this.emails = emails;
//    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(this.ids[position]);
    }

    public CustomList(Activity context, String[] ids, String[] names) {
        super(context, R.layout.list_item, ids);
        this.context = context;
        this.ids = ids;
        this.names = names;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return String.valueOf(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_item, null, true);
        TextView name=(TextView)listViewItem.findViewById(R.id.topic_name);
//        TextView textViewId = (TextView) listViewItem.findViewById(R.id.textViewId);
//        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
//        TextView textViewEmail = (TextView) listViewItem.findViewById(R.id.textViewEmail);
            int serial_no=position+1;
        //name.setText(serial_no+"."+" "+names[position]);
        name.setText(names[position]);
//        textViewId.setText(ids[position]);
//        textViewName.setText(names[position]);
//        textViewEmail.setText(emails[position]);

        return listViewItem;
    }
}