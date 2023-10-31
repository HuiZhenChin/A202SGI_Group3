package com.example.busybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.example.busybuddy.R;
import com.example.busybuddy.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private boolean isMenuExpanded = false;

    private RecyclerView recyclerView;
    private MenuAdapter menuAdapter;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        DrawerLayout drawer = binding.drawerLayout;
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
       // mAppBarConfiguration = new AppBarConfiguration.Builder(
               // R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
               // .setOpenableLayout(drawer)
              //  .build();
       // NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
      //  NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
       // NavigationUI.setupWithNavController(navigationView, navController);
        navigationView = findViewById(R.id.nav_view); // Replace with the actual ID of your NavigationView
        Menu menu = navigationView.getMenu();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout,  R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
              if (id == R.id.folder) {
                  openFolder();
              }else if (id== R.id.prioritylist){
                  openPriority();
              }else if(id== R.id.calendar){
                  openCalendar();
              }else if (id==R.id.notification){
                  openNotification();
              }else if (id==R.id.faq){
                  openFAQ();
              }else if(id==R.id.profile){
                  openProfile();
              }
                drawer.closeDrawer(GravityCompat.START);
                //actionbar.setDisplayHomeAsUpEnabled(true);
                return true;
            }
        });

        Button logout = findViewById(R.id.logOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
*/
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void openFolder() {
        Intent intent = new Intent(this, DisplayFolder.class);
        startActivity(intent);
    }

    private void openPriority() {
        Intent intent = new Intent(this, PriorityList.class);
        startActivity(intent);
    }

    private void openFAQ() {
        Intent intent = new Intent(this, FaqMain.class);
        startActivity(intent);
    }

    private void openProfile() {
        Intent intent = new Intent(this, MyAccount.class);
        startActivity(intent);
    }

    private void openNotification() {
        Intent intent = new Intent(this, ShowNotification.class);
        startActivity(intent);
    }

    private void openCalendar() {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }
}
