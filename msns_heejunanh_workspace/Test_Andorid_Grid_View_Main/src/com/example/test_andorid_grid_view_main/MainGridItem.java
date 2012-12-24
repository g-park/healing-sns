package com.example.test_andorid_grid_view_main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainGridItem extends LinearLayout {

	 ImageView mIcon;
	 TextView mLabel;
	 int mIconId;
	 String mLabelString;
	 int mPosition;
	 Context mContext;
	
	public MainGridItem(Context context) {
		super(context);
		mContext = context;
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.main_grid_item, this);
		
		mIcon = (ImageView)layout.findViewById(R.id.icon);
		mLabel = (TextView)layout.findViewById(R.id.label);
		

	}
	
	public boolean setData(int iconId, String label,int position){
		mIconId = iconId;
		mLabelString = label;
		mPosition = position;
		
		mIcon.setImageResource(mIconId);
		mLabel.setText(label);
		
		return true;
	}


}
