package com.example.dell.cambien.ui.home.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.cambien.ui.home.MainActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DELL on 5/10/2018.
 */

public class HomeFragment extends Fragment implements HomeMvpView,SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.rcv_film_home)
    RecyclerView rcv;
    @BindView(R.id.sf_home)
    SwipeRefreshLayout sfLayout;
    HomePresenter presenter;
    FilmTypeAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new HomePresenter();
        presenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this,rootView);
        sfLayout.setOnRefreshListener(this);
        onLoading();
        return rootView;
    }

    @Override
    public void onLoading() {
        LoadingDialog.show(getContext());
        presenter.onLoad();
        presenter.onLoadMenu();
    }

    @Override
    public void onLoadSuccess(List<FilmTypeEntity> list) {
        adapter = new FilmTypeAdapter(list,getContext());
        rcv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rcv.setAdapter(adapter);
    }

    @Override
    public void onLoadMenuSuccess(List<CategoryEntity> list) {
        MainActivity activity = (MainActivity) getActivity();
        activity.initMenu(list);
    }

    @Override
    public void onHideDialog() {
        LoadingDialog.hide();
    }

    @Override
    public void onRefresh() {
        sfLayout.setRefreshing(false);
        presenter.onLoad();
        presenter.onLoadMenu();
    }
}
