package com.app.usertreatzasia.ui.viewbinders.abstracts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

public abstract class RecyclerViewBinder<T> {
	private int LayoutResID;
	public RecyclerViewBinder(int LayoutResID) {
		this.LayoutResID = LayoutResID;
	}

	public View createView (Context activity, int LayoutResId) {
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View view = inflater.inflate( LayoutResId , null );
		view.setTag( createViewHolder( view ) );
		return view;

	}
	public View createView (Context activity) {
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View view = inflater.inflate( LayoutResID , null );
		view.setTag( createViewHolder( view ) );
		return view;

	}
	public abstract BaseViewHolder createViewHolder (View view);

	/**
	 * @param entity object with view Data
	 * @param position view postion in recycler view
	 * @param viewHolder holder to hold views
	 * @param context
	 */

	public abstract void bindView(T entity, int position, Object viewHolder, Context context);

	public static class BaseViewHolder extends RecyclerView.ViewHolder {

		public BaseViewHolder(View itemView) {
			super(itemView);
		}
	}
}
