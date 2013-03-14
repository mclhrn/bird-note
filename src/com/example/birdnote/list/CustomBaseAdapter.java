package com.example.birdnote.list;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.birdnote.R;
import com.example.birdnote.model.Bird;
 
public class CustomBaseAdapter extends ArrayAdapter<Bird> {
    Context context;
    List<Bird> birds;
 
    public CustomBaseAdapter(Context context, List<Bird> birds) {
    	super(context, android.R.id.content, birds);
        this.context = context;
        this.birds = birds;
    }
 
    /*private view holder class*/
    /*private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
    }*/
 
    /*public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
 
        LayoutInflater mInflater = (LayoutInflater)
            context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.desc);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        
        
        RowItem rowItem = (RowItem) getItem(position);
 
        holder.txtDesc.setText(rowItem.getDesc());
        holder.txtTitle.setText(rowItem.getTitle());
        holder.imageView.setImageResource(rowItem.getImageId());
 
        return convertView;
    }
 
    @Override
    public int getCount() {
        return rowItems.size();
    }
 
    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }*/
    
    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
        View view = vi.inflate(R.layout.list_item, null);
	
        Bird bird = birds.get(position);
        
        TextView tv = (TextView) view.findViewById(R.id.name);
        tv.setText(bird.getName());

        tv = (TextView) view.findViewById(R.id.latin);
        tv.setText(bird.getLatinName());
        
        /*ImageView iv = (ImageView) view.findViewById(R.id.thumb);
        int imageResource = context.getResources().getIdentifier(
        		bird.getImage(), "drawable", context.getPackageName());
        if (imageResource != 0) {
        	iv.setImageResource(imageResource);
        }
        */
        return view;
	}
}
