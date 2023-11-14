package com.example.busybuddy;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

// Main Activity (Welcome Page and Navigation Drawer)
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    private DBManager dbManager;
    TextView displayUser;  // display username on the navigation drawer
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intentLogin = getIntent();
        dbManager = new DBManager(this);  // open the database
        dbManager.open();

        // get the username and password to pass to other pages
        String usernameValue = intentLogin.getStringExtra("usernameValue");
        String passwordValue = intentLogin.getStringExtra("passwordValue");

        NavigationView navigationView = binding.navView;

        // notification dot to trigger user that there is an unread message
        ImageView notifyDot= findViewById(R.id.dot);
        userId = dbManager.getUserID(usernameValue);
        Cursor cursor = dbManager.fetchUnreadActivity(userId);

        if(cursor.getCount() > 0){
            notifyDot.setVisibility(View.VISIBLE);
        }
        else{
            notifyDot.setVisibility(View.INVISIBLE);
        }


        // find the header view within the NavigationView
        View headerView = navigationView.getHeaderView(0);

        // find the TextView with the ID usernameDisplay within the header view
        displayUser = headerView.findViewById(R.id.usernameDisplay);
        displayUser.setText(usernameValue);  // display the username on the navigation drawer

        DrawerLayout drawer = binding.drawerLayout;
        drawerLayout = findViewById(R.id.drawer_layout);

        // pull to open the navigation drawer, and push to close
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout,  R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // item navigation to respective pages
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                // Display Folder
              if (id == R.id.folder) {
                  Intent intentLogin = new Intent(MainActivity.this, DisplayFolder.class);
                  intentLogin.putExtra("usernameValue", usernameValue);
                  intentLogin.putExtra("passwordValue", passwordValue);
                  startActivity(intentLogin);

                  // Priority List
              }else if (id== R.id.prioritylist){
                  Intent intentLogin = new Intent(MainActivity.this, PriorityList.class);
                  intentLogin.putExtra("usernameValue", usernameValue);
                  intentLogin.putExtra("passwordValue", passwordValue);
                  startActivity(intentLogin);

                  // Calendar
              }else if(id== R.id.calendar){

                  Intent intentLogin = new Intent(MainActivity.this, CalendarActivity.class);
                  intentLogin.putExtra("usernameValue", usernameValue);
                  intentLogin.putExtra("passwordValue", passwordValue);
                  startActivity(intentLogin);

                  // Notifications
              }else if (id==R.id.notification){
                  userId = dbManager.getUserID(usernameValue);
                  Intent intent = new Intent(MainActivity.this, ShowNotification.class);
                  intent.putExtra("usernameValue", usernameValue);
                  intent.putExtra("passwordValue", passwordValue);
                  intent.putExtra("id",userId);
                  startActivity(intent);

                  // FAQ
              }else if (id==R.id.faq){
                  Intent intentLogin = new Intent(MainActivity.this, FaqMain.class);
                  intentLogin.putExtra("usernameValue", usernameValue);
                  intentLogin.putExtra("passwordValue", passwordValue);
                  startActivity(intentLogin);

                  // My Account
              }else if(id==R.id.profile){
                  userId = dbManager.getUserID(usernameValue);
                  Intent iProfile = new Intent(MainActivity.this,MyAccount.class);
                  iProfile.putExtra("usernameValue", usernameValue);
                  iProfile.putExtra("passwordValue", passwordValue);
                  iProfile.putExtra("id",userId);
                  startActivity(iProfile);
              }

              // close the drawer once an item is pressed
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        // Log Out
        Button logout = findViewById(R.id.logOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



}