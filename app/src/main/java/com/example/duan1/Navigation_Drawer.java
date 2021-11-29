package com.example.duan1;

import static com.example.duan1.R.drawable.menu;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;

import com.example.duan1.Fragment.HangXe_Fragment;
import com.example.duan1.Fragment.HoaDonChiTiet_Fragment;
import com.example.duan1.Fragment.HoaDon_Fragment;
import com.example.duan1.Fragment.Xe_Fragment;
import com.google.android.material.navigation.NavigationView;

public class Navigation_Drawer extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.my_drawerLayout);
        drawerToggle = setupDrawerToggle();


        //show icon menu
//        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.xanh));

        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);

        navigationView = (NavigationView) findViewById(R.id.my_navigation);
        setUpDrawerContent(navigationView);
        //show <-
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(menu);
        //hàm set layout

        if (savedInstanceState == null) {
            Class FragmentClass = HangXe_Fragment.class;

            androidx.fragment.app.Fragment fragment = null;
            try {
                fragment = (
                        androidx.fragment.app.Fragment) FragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            navigationView.getMenu().getItem(3).setChecked(true);

            FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.my_frame_layout, fragment);
            transaction.commit();
        }
    }

    // tạo drawer Toggle
    private ActionBarDrawerToggle setupDrawerToggle(){
        return new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
    }

    // sự kiện khi click vào drawer Toggle
    private void setUpDrawerContent(NavigationView _navigationView){
        _navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // chọn fragment khi click
                selectDrawerItem(item);
                return true;
            }
        });
    }

    //chọn fragment
    private void selectDrawerItem(MenuItem item){
        Fragment fragment = null;
        Class fragmentClass;

        switch (item.getItemId()){
            case R.id.id_2:
                fragmentClass = HangXe_Fragment.class;
                break;
            case R.id.id_3:
                fragmentClass = Xe_Fragment.class;
                break;
            case R.id.id_4:
                fragmentClass = HoaDon_Fragment.class;
                break;
            case R.id.id_8:
                finish();
                return;
            default:
                fragmentClass = HangXe_Fragment.class;
                break;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();

        }catch (Exception ex){

        }
        //gắn layout của fragment 1 -> layout main
        getSupportFragmentManager().beginTransaction().replace(R.id.my_frame_layout,fragment).commit();
//        getSupportFragmentManager().addToBackStack(Bill_Detail_Fragment.TAG);
        setTitle(item.getTitle());
        drawerLayout.closeDrawers();
    }
    //click quay về trang chủ
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.my_drawerLayout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
    //kiểm tra xem menu đã được click chưa
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //hàm đồng bộ dữ liệu
    @Override
    public void onPostCreate( Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        drawerToggle.syncState();
    }
    //thay đổi drawerToggle: background,color...
    @Override
    public void onConfigurationChanged( Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);

    }
}