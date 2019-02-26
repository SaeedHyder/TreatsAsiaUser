package com.app.usertreatzasia.interfaces;

public interface FavoriteStatus {

    void eVoucherLike(Integer entityId, boolean id);

    void eventsLike(Integer id, boolean isChecked);

    void flashSaleLike(Integer id, boolean isChecked);
}
