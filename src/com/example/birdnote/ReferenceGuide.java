package com.example.birdnote;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ReferenceGuide extends ListActivity {

	private static final String[] items = { "lorem", "ipsum", "dolor", "sit",
			"amet", "consectetuer", "adipiscing", "elit", "morbi", "vel",
			"ligula", "vitae", "arcu", "aliquet", "mollis", "etiam", "vel",
			"erat", "placerat", "ante", "porttitor", "sodales", "pellentesque",
			"augue", "purus" };

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setListAdapter(new IconicAdapter());
	}

	/*
	 * private String getModel(int position) {
	 * return(((IconicAdapter)getListAdapter()).getItem(position)); }
	 */

	class IconicAdapter extends ArrayAdapter<String> {
		IconicAdapter() {
			super(ReferenceGuide.this, R.layout.row, R.id.label, items);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = super.getView(position, convertView, parent);
			ViewHolder holder = (ViewHolder) row.getTag();

			if (holder == null) {
				holder = new ViewHolder(row);
				row.setTag(holder);
			}

			holder.icon.setImageResource(R.drawable.hawk);

			/*
			 * if (getModel(position).length()>4) {
			 * holder.icon.setImageResource(R.drawable.delete); } else {
			 * holder.icon.setImageResource(R.drawable.ok); }
			 */

			holder.size.setText(String.format(getString(R.string.size_template),
							items[position].length()));

			return (row);
		}
	}
}
