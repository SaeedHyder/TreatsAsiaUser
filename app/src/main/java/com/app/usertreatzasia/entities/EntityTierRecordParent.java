package com.app.usertreatzasia.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EntityTierRecordParent {

    @SerializedName("record")
    @Expose
    private ArrayList<EntityTierRecord> record ;
    @SerializedName("total")
    @Expose
    private Integer total;

    public ArrayList<EntityTierRecord> getRecord() {
        return record;
    }

    public void setRecord(ArrayList<EntityTierRecord> record) {
        this.record = record;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
