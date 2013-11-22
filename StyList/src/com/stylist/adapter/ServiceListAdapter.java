package com.stylist.adapter;

import java.util.List;


import com.stylist.R;
import com.stylist.dataclasses.Appointment;
import com.stylist.utils.Common;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class ServiceListAdapter extends ArrayAdapter<Appointment> {

	Context mContext;
	List<Appointment> namesList;
	LayoutInflater inflater;

	public ServiceListAdapter(Context context, int resource,
			int textViewResourceId, List<Appointment> objects) {

		super(context, resource, textViewResourceId, objects);
		this.mContext=context;
		namesList = objects;
		inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		//System.out.println("Size : "+namesList.size());
		return namesList.size();
	}


	@Override
	public Appointment getItem(int position) {
		// TODO Auto-generated method stub
		return namesList.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ContactsViewHolder viewHolder;
		if(convertView==null)
		{
			convertView = inflater.inflate(R.layout.contact_row, null);
			viewHolder = new ContactsViewHolder();
			viewHolder.region=(TextView) convertView.findViewById(R.id.match_tex1);
			viewHolder.name=(TextView) convertView.findViewById(R.id.match_tex2);
			viewHolder.rl=(RelativeLayout) convertView.findViewById(R.id.playout);
			viewHolder.serviceListRightPanelEdit = (ImageView) convertView.findViewById(R.id.serviceListRightPanelEdit);
			convertView.setTag(viewHolder);
		}else
		{
			viewHolder = (ContactsViewHolder) convertView.getTag();
		}
		try {
			final int j=position;
			final Appointment round = namesList.get(position);
			try {
				viewHolder.region.setText(round.getFname()+" - ");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				viewHolder.name.setText(round.getLname()+" "+round.getStatus());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (position % 2 == 1) {
				viewHolder.rl
				.setBackgroundResource(R.drawable.round_layout_shape_drawable1);
			} else {
				viewHolder.rl
				.setBackgroundResource(R.drawable.round_layout_shape_drawable);
			}
			viewHolder.serviceListRightPanelEdit.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Common.inflateRightMenu(R.layout.client_list_detail, mContext, "ServiceclientDetail");
					Common.clientNameText.setText(round.getFname());
					System.out.println("position" +j);
					Common.clientDetailStatus.setText(round.getStatus());
					Common.clientDetailNote.setText(round.getNote());
					Common.clientDetailChild.setText(round.getChild_count());
					Common.clientDetailAdult.setText(round.getAdult_count());
				}
			});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertView;
	}

	static class ContactsViewHolder {


		TextView region;
		TextView name;
		ImageView serviceListRightPanelEdit;
		RelativeLayout rl;

	}
	
}
