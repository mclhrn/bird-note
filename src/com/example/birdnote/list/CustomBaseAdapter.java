package com.example.birdnote.list;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
 
    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
        View view = vi.inflate(R.layout.list_item, null);
	
        Bird bird = birds.get(position);
        
        TextView tv = (TextView) view.findViewById(R.id.list_item_name);
        tv.setText(bird.getName());

        tv = (TextView) view.findViewById(R.id.latin);
        tv.setText(bird.getLatinName());
        
        ImageView iv = (ImageView) view.findViewById(R.id.thumb);
        int imageResource = context.getResources().getIdentifier(
        		bird.getImageThumb(), "drawable", context.getPackageName());
        if (imageResource != 0) {
        	iv.setImageResource(imageResource);
        }
        
        return view;
	}
}
