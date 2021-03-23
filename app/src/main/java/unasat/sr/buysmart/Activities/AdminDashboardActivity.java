package unasat.sr.buysmart.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import unasat.sr.buysmart.DatabaseManager.Dao.GlobalDAO;
import unasat.sr.buysmart.Entities.User;
import unasat.sr.buysmart.Fragments.DashboardFragment;
import unasat.sr.buysmart.Fragments.OrdersFragment;
import unasat.sr.buysmart.Fragments.UsersFragment;
import unasat.sr.buysmart.R;
import unasat.sr.buysmart.Services.LoggedInService;

public class AdminDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private TextView username;
    private GlobalDAO globalDAO;
    private String user;
    private int nav_Id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setContentView(R.layout.activity_admin_dashboard);
        drawerLayout= findViewById(R.id.drawer_layout);
        navigationView= findViewById(R.id.nav_view);
        toolbar= findViewById(R.id.toolbar);
        View header = navigationView.getHeaderView(0);
        username = header.findViewById(R.id.username_textView);
        Bundle extras = getIntent().getExtras();
         user = extras.getString("username");
         nav_Id = 0;
         nav_Id = extras.getInt("nav_id");
        if (user!= null) {
            System.out.println(user);
            username.setText(user);
        }

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open_drawer, R.string.nav_close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Bundle bundle = new Bundle();
        bundle.putString("username", user);
        OrdersFragment ordersFragment = new OrdersFragment();
        ordersFragment.setArguments(bundle);
      //  loadFragment(ordersFragment);

        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        globalDAO = new GlobalDAO(getApplicationContext());
        loadFragment();

    }

    private void loadFragment (){
        if (R.id.nav_users == nav_Id){
            System.out.println("Found users fragment");
            loadFragment(new UsersFragment());
            return;
        } else if (R.id.nav_report == nav_Id){
            System.out.println("found reports fragment");
            User userEntity = globalDAO.findByUsername(user);
            System.out.println(userEntity.getUsername());
            Bundle bundle1 = new Bundle();
            bundle1.putString("username", userEntity.getUsername());
            bundle1.putInt("userId", userEntity.getUserId());
            OrdersFragment ordersFragment1 = new OrdersFragment();
            ordersFragment1.setArguments(bundle1);
            loadFragment(ordersFragment1);
            return;
        } else {
            System.out.println("Nothing found");
            loadFragment(new UsersFragment());

        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_users) {
            loadFragment(new UsersFragment());
            return true;
        } else if (id == R.id.nav_report) {
                User userEntity = globalDAO.findByUsername(user);
            System.out.println(userEntity.getUsername());

                Bundle bundle = new Bundle();
                bundle.putString("username", userEntity.getUsername());
                bundle.putInt("userId", userEntity.getUserId());
                OrdersFragment ordersFragment = new OrdersFragment();
                ordersFragment.setArguments(bundle);
                loadFragment(ordersFragment);
                return true;


          //  loadFragment(new ReportFragment());
        } /*else if (id == R.id.nav_home){

            Bundle bundle1 = new Bundle();
            bundle1.putString("username",username.getText().toString().trim());
            DashboardFragment dashboardFragment = new DashboardFragment();
            dashboardFragment.setArguments(bundle1);

            loadFragment(new DashboardFragment());
        }*/

        else if  (id == R.id.nav_logout) {
            Intent serviceIntent = new Intent(this, LoggedInService.class);
            stopService(serviceIntent);

            AppState.getSingleInstance().setLoggingOut(true);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();


        }

        else if (id == R.id.nav_manage_products){
            Intent manageProductsIntent = new Intent(AdminDashboardActivity.this, AddProductsActivity.class);
            manageProductsIntent.putExtra("username",user);
            startActivity(manageProductsIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }
    public void loadFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }
}