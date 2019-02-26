package com.app.usertreatzasia.interfaces;

import com.app.usertreatzasia.entities.FilterArrayEnt;

import java.util.List;
import java.util.Objects;

/**
 * Created by ahmedsyed on 11/30/2017.
 */

public interface RecyclerViewOnClick {

    void click(int position, List<FilterArrayEnt> mDataArray);
}
