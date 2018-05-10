package com.example.dell.cambien.ui.home.home;

import java.util.List;

/**
 * Created by DELL on 5/10/2018.
 */

public interface HomeMvpView{
    void onLoading();
    void onLoadSuccess(List<FilmTypeEntity> list);
    void onLoadMenuSuccess(List<CategoryEntity> list);
    void onHideDialog();
}
