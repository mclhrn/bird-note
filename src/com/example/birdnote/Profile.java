package com.example.birdnote;
 
import com.example.birdnote.R;
import com.example.birdnote.model.Bird;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Profile extends Activity{

	Bird bird;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ExpandableListView elv = getExpandableListView();
		setContentView(R.layout.profile);
    
		Bundle b = getIntent().getExtras();
		bird = b.getParcelable("com.example.birdnote.model.Bird");
		
//		TextView tv = (TextView) findViewById(R.id.name);
//		tv.setText(bird.getName());
//		
//		TextView tv2 = (TextView) findViewById(R.id.latin);
//		tv2.setText(bird.getLatinName());
		
//		elv.setDividerHeight(2);
//		elv.setGroupIndicator(null);
//		elv.setClickable(true);

//		setGroupData();
//		setChildGroupData();

//		NewAdapter myNewAdapter = new NewAdapter(groupItem, childItem);
//		myNewAdapter
//				.setInflater(
//						(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),
//						this);
//		getExpandableListView().setAdapter(myNewAdapter);
//		elv.setOnChildClickListener(this);
		
		refreshDisplay();
	}
//	public void setGroupData() {
//		groupItem.add("Status");
//		groupItem.add("Identification");
//		groupItem.add("Diet");
//		groupItem.add("Breeding");
//		groupItem.add("Wintering Habits");
//		groupItem.add("Where to See");
//		groupItem.add("Conservation Status");
//	}
//
//	ArrayList<String> groupItem = new ArrayList<String>();
//	ArrayList<Object> childItem = new ArrayList<Object>();

//	public void setChildGroupData() {
//		/**
//		 * Add Data For TecthNology
//		 */
//		ArrayList<String> child = new ArrayList<String>();
//		child.add("Java");
//		child.add("Drupal");
//		child.add(".Net Framework");
//		child.add("PHP");
//		childItem.add(child);
//
//		/**
//		 * Add Data For Mobile
//		 */
//		child = new ArrayList<String>();
//		child.add("Android");
//		child.add("Window Mobile");
//		child.add("iPHone");
//		child.add("Blackberry");
//		childItem.add(child);
//		/**
//		 * Add Data For Manufacture
//		 */
//		child = new ArrayList<String>();
//		child.add("HTC");
//		child.add("Apple");
//		child.add("Samsung");
//		child.add("Nokia");
//		childItem.add(child);
//		/**
//		 * Add Data For Extras
//		 */
//		child = new ArrayList<String>();
//		child.add("Contact Us");
//		child.add("About Us");
//		child.add("Location");
//		child.add("Root Cause");
//		childItem.add(child);
//	}
//
//	@Override
//	public boolean onChildClick(ExpandableListView parent, View v,
//			int groupPosition, int childPosition, long id) {
//		Toast.makeText(Profile.this, "Clicked On Child",
//				Toast.LENGTH_SHORT).show();
//		return true;
//	}
//	
//	public class NewAdapter extends BaseExpandableListAdapter {
//
//		public ArrayList<String> groupItem, tempChild;
//		public ArrayList<Object> Childtem = new ArrayList<Object>();
//		public LayoutInflater minflater;
//		public Activity activity;
//
//		public NewAdapter(ArrayList<String> grList, ArrayList<Object> childItem) {
//			groupItem = grList;
//			this.Childtem = childItem;
//		}
//
//		public void setInflater(LayoutInflater mInflater, Activity act) {
//			this.minflater = mInflater;
//			activity = act;
//		}
//
//		@Override
//		public Object getChild(int groupPosition, int childPosition) {
//			return null;
//		}
//
//		@Override
//		public long getChildId(int groupPosition, int childPosition) {
//			return 0;
//		}
//
//		@SuppressWarnings("unchecked")
//		@Override
//		public View getChildView(int groupPosition, final int childPosition,
//				boolean isLastChild, View convertView, ViewGroup parent) {
//			tempChild = (ArrayList<String>) Childtem.get(groupPosition);
//			TextView text = null;
//			if (convertView == null) {
//				convertView = minflater.inflate(R.layout.childrow, null);
//			}
//			text = (TextView) convertView.findViewById(R.id.textView1);
//			text.setText(tempChild.get(childPosition));
//			convertView.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					Toast.makeText(activity, tempChild.get(childPosition),
//							Toast.LENGTH_SHORT).show();
//				}
//			});
//			return convertView;
//		}
//
//		@SuppressWarnings("unchecked")
//		@Override
//		public int getChildrenCount(int groupPosition) {
//			return ((ArrayList<String>) Childtem.get(groupPosition)).size();
//		}
//
//		@Override
//		public Object getGroup(int groupPosition) {
//			return null;
//		}
//
//		@Override
//		public int getGroupCount() {
//			return groupItem.size();
//		}
//
//		@Override
//		public void onGroupCollapsed(int groupPosition) {
//			super.onGroupCollapsed(groupPosition);
//		}
//
//		@Override
//		public void onGroupExpanded(int groupPosition) {
//			super.onGroupExpanded(groupPosition);
//		}
//
//		@Override
//		public long getGroupId(int groupPosition) {
//			return 0;
//		}
//
//		@Override
//		public View getGroupView(int groupPosition, boolean isExpanded,
//				View convertView, ViewGroup parent) {
//			if (convertView == null) {
//				convertView = minflater.inflate(R.layout.childrow, null);
//			}
//			((CheckedTextView) convertView).setText(groupItem.get(groupPosition));
//			((CheckedTextView) convertView).setChecked(isExpanded);
//			return convertView;
//		}
//
//		@Override
//		public boolean hasStableIds() {
//			return false;
//		}
//
//		@Override
//		public boolean isChildSelectable(int groupPosition, int childPosition) {
//			return false;
//		}
//
//	}

	private void refreshDisplay() {
		
		TextView tv = (TextView) findViewById(R.id.name);
		tv.setText(bird.getName());
		
		TextView tv2 = (TextView) findViewById(R.id.latin);
		tv2.setText(bird.getLatinName());
//		
//		elv = (ExpandableListView)findViewById(R.id.expandableListView1);
//		elv.setAdapter(new MyCustomExpandableListAdapter(this));
		
		TextView tv3 = (TextView) findViewById(R.id.status);
		tv3.setText(bird.getStatus());
		
		TextView tv4 = (TextView) findViewById(R.id.identification);
		tv4.setText(bird.getIdentification());
		
		TextView tv5 = (TextView) findViewById(R.id.diet);
		tv5.setText(bird.getDiet());
		
		TextView tv6 = (TextView) findViewById(R.id.breeding);
		tv6.setText(bird.getBreeding());
		
		TextView tv7 = (TextView) findViewById(R.id.wintering_habits);
		tv7.setText(bird.getWinteringHabits());
		
		TextView tv8 = (TextView) findViewById(R.id.where_to_see);
		tv8.setText(bird.getWhereToSee());
		
		TextView tv9 = (TextView) findViewById(R.id.conservation);
		tv9.setText(bird.getConservation());
		
		/*ImageView iv = (ImageView) findViewById(R.id.main_profile_image);
        int imageResource = getResources().getIdentifier(
        		bird.getImage(), "drawable", getPackageName());
        if (imageResource != 0) {
        	iv.setImageResource(imageResource);
        }*/
//	}
	
	}
}
