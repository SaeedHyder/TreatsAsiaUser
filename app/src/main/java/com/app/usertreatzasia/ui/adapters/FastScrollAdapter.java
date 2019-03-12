package com.app.usertreatzasia.ui.adapters;

import android.support.annotation.NonNull;
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
    private String previous;
    private String newCharacter;
    private int previousPos=0;

    public FastScrollAdapter(List<FilterArrayEnt> dataset, DockActivity dockActivity, HeaderNameInterface headerNameInterface, RecyclerViewOnClick recyclerViewOnClick) {
        mDataArray = dataset;
        this.dockActivity = dockActivity;
        this.headerNameInterface = headerNameInterface;
        this.recyclerViewOnClick = recyclerViewOnClick;

        if (dataset != null && dataset.size() > 0) {
            previous = String.valueOf(mDataArray.get(0).getName().charAt(0));
            previousPos=0;
        }
    }

    @Override
    public int getItemCount() {
        if (mDataArray == null)
            return 0;
        return mDataArray.size();
    }

  /*  @Override
    public int getItemViewType(int position) {
        if (mDataArray.get(position) != null && mDataArray.get(position).getName() != null && !mDataArray.get(position).getName().isEmpty() && !mDataArray.get(position).getName().equals("")) {
            newCharacter = String.valueOf(mDataArray.get(position).getName().charAt(0));

            if (previousPos!=position && !newCharacter.toLowerCase().trim().equals(previous.toLowerCase().trim())) {
                previous = newCharacter;
                previousPos=position;
                return 0;
            }else if(previousPos==position && newCharacter.toLowerCase().trim().equals(previous.toLowerCase().trim())) {
             //   previous = newCharacter;
                return 0;
            }else {
                return 2;
            }

        } else {
            return 2;
        }
    }*/
  @Override
  public int getItemViewType(int position) {
     return position;
  }

  public int checkViewType(int position){
       if (mDataArray.get(position) != null && mDataArray.get(position).getName() != null && !mDataArray.get(position).getName().isEmpty() && !mDataArray.get(position).getName().equals("")) {
          newCharacter = String.valueOf(mDataArray.get(position).getName().charAt(0));

          if (previousPos!=position && !newCharacter.toLowerCase().trim().equals(previous.toLowerCase().trim())) {
              previous = newCharacter;
              previousPos=position;
              return 0;
          }else if(previousPos==position && newCharacter.toLowerCase().trim().equals(previous.toLowerCase().trim())) {
              //   previous = newCharacter;
              return 0;
          }else {
              return 2;
          }

      } else {
          return 2;
      }
  }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;
        if (checkViewType(viewType) == 0) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);
            return new HeaderViewHolder(itemView);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fast_scroll, parent, false);
            return new ChildViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int i) {

        if (checkViewType(i) == 0) {
            try {
                HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
                viewHolder.title.setText(String.valueOf(mDataArray.get(i).getName().charAt(0)).toUpperCase());
                viewHolder.tv_alphabet.setText(String.valueOf(mDataArray.get(i).getName()));

                viewHolder.tv_alphabet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerViewOnClick.click(i, mDataArray);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else  {
            try {

                ChildViewHolder viewHolder = (ChildViewHolder) holder;

                viewHolder.tv_alphabet.setText(mDataArray.get(i).getName());
                viewHolder.mainFrame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerViewOnClick.click(i, mDataArray);
                    }
                });

                if (String.valueOf(mDataArray.get(i).getName()).equals("")) {
                } else {
                    String temp = String.valueOf(mDataArray.get(i).getName().charAt(0));

                    if (!temp.equals(mDataArray.get(i).getName().charAt(0))) {
                        String alpha = String.valueOf(mDataArray.get(i).getName().charAt(0));
                        headerNameInterface.getHeaderName(alpha);
                        //UIHelper.showShortToastInCenter(dockActivity,alpha);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
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


    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView tv_alphabet;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_header);
            tv_alphabet = (TextView) itemView.findViewById(R.id.tv_alphabet);

        }

    }


    public static class ChildViewHolder extends RecyclerView.ViewHolder {

        TextView tv_alphabet;
        LinearLayout mainFrame;

        public  ChildViewHolder(View view) {
            super(view);
            tv_alphabet = (TextView) view.findViewById(R.id.tv_alphabet);
            mainFrame = (LinearLayout) view.findViewById(R.id.mainFrame);
        }
    }
}
