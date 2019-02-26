package com.app.usertreatzasia.ui.adapters;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.activities.DockActivity;
import com.app.usertreatzasia.activities.MainActivity;
import com.app.usertreatzasia.entities.SubscriptionEntity;
import com.app.usertreatzasia.fragments.Pager1;
import com.app.usertreatzasia.retrofit.WebService;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private MainActivity mainActivity;
    private WebService webService;
    private DockActivity getDockActivity;
    private ArrayList<SubscriptionEntity> entitySubscriptions;

    final int PAGE_COUNT = 3;
    private String itemPrice;
    private String tabTitles[] = new String[]{"PRESTIGE", "PRESTIGE", "ORDINARY"};
    private String tabTitles2[] = new String[]{"PARTNERS", "MEMBERS", "MEMBERS"};
    private Context context;
    private ArrayList<Pager1> registeredFragment;

    public ViewPagerAdapter(FragmentManager fm, Context context, MainActivity mainActivity, ArrayList<SubscriptionEntity> result) {
        super(fm);
        this.mainActivity = mainActivity;
        this.context = context;
        this.entitySubscriptions = result;
        registeredFragment = new ArrayList<>();
        registeredFragment.add(new Pager1());
        registeredFragment.add(new Pager1());
        registeredFragment.add(new Pager1());
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }


    @Override
    public Fragment getItem(int position) {
        /*return PageFragment.newInstance(position + 1);*/

        itemPrice=entitySubscriptions.get(position).getPrice();

        if (registeredFragment != null && registeredFragment.size() > position) {
            if (entitySubscriptions != null && entitySubscriptions.size() > position) {
                registeredFragment.get(position).setContent(entitySubscriptions.get(position));
            }
            return registeredFragment.get(position);
        } else {
            return new Pager1();
        }
    }

    public void data(ArrayList<SubscriptionEntity> item) {
        entitySubscriptions = item;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    public String getPrice(int position){
        return entitySubscriptions.get(position).getPrice();
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragment.get(position);
    }

    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tv = (TextView) v.findViewById(R.id.textView);
        TextView tv2 = (TextView) v.findViewById(R.id.textView_small);
        tv.setText(tabTitles[position]);
        tv2.setText(tabTitles2[position]);
        return v;
    }

    public SubscriptionEntity getSelectedEntity(int position) {
        if (entitySubscriptions != null && entitySubscriptions.size() > position)
            return entitySubscriptions.get(position);
        else {
            return null;
        }
    }

    public void SetOnSelectView(TabLayout.Tab tabLayout, int position) {
        //  TabLayout.Tab tab = tabLayout.getTabAt(position);

        View selected = tabLayout.getCustomView();
        if (selected != null) {
            TextView tv = (TextView) selected.findViewById(R.id.textView);
            tv.setTextColor(context.getResources().getColor(R.color.white));

            TextView tv2 = (TextView) selected.findViewById(R.id.textView_small);
            tv2.setTextColor(context.getResources().getColor(R.color.white));

            // tabLayout.select();
        }
    }

    public void SetUnSelectView(TabLayout.Tab tabLayout, int position) {
        // TabLayout.Tab tab = tabLayout.getTabAt(position);
        View selected = tabLayout.getCustomView();

        TextView tv = (TextView) selected.findViewById(R.id.textView);
        tv.setTextColor(context.getResources().getColor(R.color.tab_grey));

        TextView tv2 = (TextView) selected.findViewById(R.id.textView_small);
        tv2.setTextColor(context.getResources().getColor(R.color.tab_grey));
    }


}
