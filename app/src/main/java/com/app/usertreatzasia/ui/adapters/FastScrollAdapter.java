package com.app.usertreatzasia.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.activities.DockActivity;
import com.app.usertreatzasia.entities.FilterArrayEnt;
import com.app.usertreatzasia.interfaces.HeaderNameInterface;
import com.app.usertreatzasia.interfaces.RecyclerViewOnClick;

import java.util.ArrayList;
import java.util.List;

public class FastScrollAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SectionIndexer {

    private List<FilterArrayEnt> mDataArray;
    private ArrayList<Integer> mSectionPositions;
    private DockActivity dockActivity;
    private HeaderNameInterface headerNameInterface;
    private RecyclerViewOnClick recyclerViewOnClick;

    public FastScrollAdapter(List<FilterArrayEnt> dataset, DockActivity dockActivity, HeaderNameInterface headerNameInterface, RecyclerViewOnClick recyclerViewOnClick) {
        mDataArray = dataset;
        this.dockActivity = dockActivity;
        this.headerNameInterface = headerNameInterface;
        this.recyclerViewOnClick = recyclerViewOnClick;
    }

    @Override
    public int getItemCount() {
        if (mDataArray == null)
            return 0;
        return mDataArray.size();
    }

    @Override
    public FastScrollAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fast_scroll, parent, false);
        return new FastScrollAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        FastScrollAdapter.ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.tv_alphabet.setText(mDataArray.get(position).getName());
        viewHolder.mainFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewOnClick.click(position, mDataArray);
            }
        });

        if (String.valueOf(mDataArray.get(position).getName()).equals("")) {
        } else {
            String temp = String.valueOf(mDataArray.get(position).getName().charAt(0));

            if (!temp.equals(mDataArray.get(position).getName().charAt(0))) {
                String alpha = String.valueOf(mDataArray.get(position).getName().charAt(0));
                headerNameInterface.getHeaderName(alpha);
                //UIHelper.showShortToastInCenter(dockActivity,alpha);
            }
        }

    }

    @Override
    public int getSectionForPosition(int position) {
        return position;
    }

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>(26);
        mSectionPositions = new ArrayList<>(26);

        for (int i = 0, size = mDataArray.size(); i < size; i++) {
            if (String.valueOf(mDataArray.get(i).getName()).equals("")) {

            } else {
                String section = String.valueOf(mDataArray.get(i).getName().charAt(0)).toUpperCase();
                if (!sections.contains(section)) {
                    sections.add(section);
                    mSectionPositions.add(i);
                }
            }
        }
        return sections.toArray(new String[0]);
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return mSectionPositions.get(sectionIndex);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_alphabet;
        LinearLayout mainFrame;

        ViewHolder(View view) {
            super(view);
            tv_alphabet = (TextView) view.findViewById(R.id.tv_alphabet);
            mainFrame = (LinearLayout) view.findViewById(R.id.mainFrame);
        }
    }
}
