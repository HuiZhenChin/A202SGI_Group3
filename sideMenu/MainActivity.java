package com.example.test5;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.content.Intent;
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

import com.example.test5.databinding.ActivityMainBinding;
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

              }else if (id==R.id.notification){

              }else if (id==R.id.faq){
                  openFAQ();
              }else if(id==R.id.profile){

              }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        Button logout = findViewById(R.id.logOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class); // Use MainActivity.this as the context
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


    private void openProject2Activity() {
     //   Intent intent = new Intent(this, Project2Activity.class);
       // startActivity(intent);
    }
}
