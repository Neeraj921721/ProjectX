package com.example.projectx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mNavDrawer;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNavDrawer=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.navigation_view);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,mNavDrawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        mNavDrawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);



    }

    @Override
    public void onBackPressed() {
        if(mNavDrawer.isDrawerOpen(GravityCompat.START)) {
            mNavDrawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.nav_boards :
            {
                //add the board fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,new BoardFragment()).commit();
                break;
            }
            case R.id.nav_home :
            {
                //add the Home fragment
                Intent inToHome = new Intent(HomeActivity.this,HomeActivity.class);
                startActivity(inToHome);
                break;
            }
            case R.id.nav_cards :
            {
                //add the cards fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,new CardsFragment()).commit();
                break;
            }
            case R.id.nav_settings :
            {
                //add the Settings fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,new SettingsFragment()).commit();
                break;
            }
            case R.id.nav_help :
            {
                //add the Help fragment
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HelpFragment()).commit();
                break;
            }
            case R.id.nav_logout :
            {
                       FirebaseAuth.getInstance().signOut();
                       Intent inToLogin = new Intent(HomeActivity.this,LoginActivity.class);
                       startActivity(inToLogin);
            }
        }
        mNavDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
