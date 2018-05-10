package com.example.dell.cambien.ui.home;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.dell.cambien.R;
import com.example.dell.cambien.widget.NonSwipeViewpager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    @BindView(R.id.vg_page_home)
    NonSwipeViewpager viewpager;
    @BindView(R.id.search_view)
    MaterialSearchView searchView;
    RecyclerView rcvMenu;
    FilmHomePageAdapter adapter;
    FilmMenuAdapter menuAdapter;
    Toolbar toolbar;
    AdView adView;
    int admobCount=0;
    int maxCount= 5;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.home));
        setSupportActionBar(toolbar);
        applyFontForToolbarTitle();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        rcvMenu = navigationView.findViewById(R.id.rcv_menu_home);
        navigationView.setNavigationItemSelectedListener(this);
        adView = findViewById(R.id.adView);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.admob_full_id));
        init();
        initAdmob();
    }
    void initAdmob(){

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }
    public void initAdmobFull(){
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
        }

    }
    void init(){
        viewpager.setPagingEnabled(false);
        adapter = new FilmHomePageAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                viewpager.setCurrentItem(1);
            }

            @Override
            public void onSearchViewClosed() {
                viewpager.setCurrentItem(0);
                Utils.hideKeyboard(MainActivity.this);
            }
        });
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Utils.hideKeyboard(MainActivity.this);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.trim().length()>0){
                    EventBus.getDefault().post(new MessageEvent(Define.EVENT_SEARCH,newText));
                }
                return false;
            }
        });
    }
    public void initMenu(List<CategoryEntity> list){
        menuAdapter = new FilmMenuAdapter(list, new ItemOnClick() {
            @Override
            public void onItemClick(int pos, Object item) {

                if(pos == 0){
                    Intent t = new Intent(getBaseContext(), FavoriteActivity.class);
                    startActivity(t);
                }else{
                    CategoryEntity entity = (CategoryEntity) item;
                    Intent t = new Intent(getBaseContext(), FilmCategoryActivity.class);
                    t.putExtra("category",entity);
                    startActivity(t);
                }


            }
        });
        rcvMenu.setAdapter(menuAdapter);
        rcvMenu.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false));
    }
    @Override
    public void onBackPressed() {
        if(viewpager.getCurrentItem()==1){
            searchView.closeSearch();
            return;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
    public void applyFontForToolbarTitle(){
        for(int i = 0; i < toolbar.getChildCount(); i++){
            View view = toolbar.getChildAt(i);
            if(view instanceof TextView){
                TextView tv = (TextView) view;
                Typeface titleFont = Typeface.
                        createFromAsset(getApplicationContext().getAssets(), "fonts/ChalkboardSE-Bold.ttf");
                if(tv.getText().equals(toolbar.getTitle())){
                    tv.setTypeface(titleFont);
                    break;
                }
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item_film_home_detail clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify empty parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item_film_home_detail clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        initAdmob();

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {/* Do something */};
}
