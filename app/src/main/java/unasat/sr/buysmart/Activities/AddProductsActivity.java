package unasat.sr.buysmart.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;

import unasat.sr.buysmart.DatabaseManager.Dao.GlobalDAO;
import unasat.sr.buysmart.Entities.Product2;
import unasat.sr.buysmart.Entities.ProductType;
import unasat.sr.buysmart.Entities.User;
import unasat.sr.buysmart.Fragments.MyDialogFragment;
import unasat.sr.buysmart.Fragments.OrdersFragment;
import unasat.sr.buysmart.Fragments.UsersFragment;
import unasat.sr.buysmart.R;
import unasat.sr.buysmart.Services.LoggedInService;

public class AddProductsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private static byte[] byteArray;
    private static Bitmap bitmap;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private TextView username;
    private GlobalDAO globalDAO;
    private String user;
    private Bitmap bitmapimg;

    private EditText edtName, edtPrice;
    private String edTnaam;
    private int edTprice;
    private Button btnChoose, btnAdd, btnList;
    private ImageView imageView;
    private Product2 product2;

    final int REQUEST_CODE_GALLERY = 999;
    public static GlobalDAO sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        AddProductsActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductType productType = globalDAO.findProductTypeByName("Phones");
               edTnaam = edtName.getText().toString().trim();
               if (StringUtils.isBlank(edTnaam)){
                   Bundle bundle = new Bundle();
                   bundle.putString("error",getString(R.string.error_name_product));
                   MyDialogFragment myDialogFragment = new MyDialogFragment();
                   myDialogFragment.setArguments(bundle);
                   myDialogFragment.show(getSupportFragmentManager(),"MyFragment");
                   return;
               }
               try {
                   edTprice = Integer.parseInt(edtPrice.getText().toString());
               }catch (Exception e){
                   Bundle bundle = new Bundle();
                   bundle.putString("error",getString(R.string.error_numeric));
                   MyDialogFragment myDialogFragment = new MyDialogFragment();
                   myDialogFragment.setArguments(bundle);
                   myDialogFragment.show(getSupportFragmentManager(),"MyFragment");
                   e.printStackTrace();
                   return;
               }

                try{

                        product2.setName(edtName.getText().toString().trim());
                        product2.setPrice( Integer.parseInt(edtPrice.getText().toString().trim()));
                        product2.setImage(imageViewToByte(imageView));
                        product2.setProductTypeId(productType.getProductTypeId());

                        globalDAO.insertProduct2(product2);
                    System.out.printf(Arrays.toString(product2.getImage()));

                        Toast.makeText(getApplicationContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
                        edtName.setText(null);
                        edtPrice.setText(null);
                        imageView.setImageResource(R.mipmap.ic_launcher);

                }
                catch (Exception e){
                    Bundle bundle = new Bundle();
                    bundle.putString("error", getString(R.string.error_image));
                    MyDialogFragment myDialogFragment = new MyDialogFragment();
                    myDialogFragment.setArguments(bundle);
                    myDialogFragment.show(getSupportFragmentManager(),"MyFragment");
                    e.printStackTrace();
                }
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddProductsActivity.this, Product2ListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init()  {
        setContentView(R.layout.activity_add_products);

        globalDAO = new GlobalDAO(this);
        product2 = new Product2();
        edTnaam = null;
        edTprice = 0;
        byteArray = null;
        bitmapimg = null;

        edtName = (EditText) findViewById(R.id.edtName);
        edtPrice = (EditText) findViewById(R.id.edtPrice);
        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnList = (Button) findViewById(R.id.btnList);
        imageView = (ImageView) findViewById(R.id.imageView);

        drawerLayout= findViewById(R.id.drawer_layout);
        navigationView= findViewById(R.id.nav_view);
        toolbar= findViewById(R.id.toolbar);
        View header = navigationView.getHeaderView(0);
        username = header.findViewById(R.id.username_textView);
        Bundle extras = getIntent().getExtras();
        user = extras.getString("username");
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

     /*   OrdersFragment ordersFragment = new OrdersFragment();
        ordersFragment.setArguments(bundle);*/

        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        globalDAO = new GlobalDAO(getApplicationContext());

//        loadFragment(new UsersFragment());

    }

    public static byte[] imageViewToByte(ImageView image) {
            bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_users) {
            Intent adminDashboardIntent = new Intent(AddProductsActivity.this, AdminDashboardActivity.class);
            adminDashboardIntent.putExtra("username",user);
            adminDashboardIntent.putExtra("nav_id", R.id.nav_users);
            startActivity(adminDashboardIntent);

           // loadFragment(new UsersFragment());
            return true;
        } else if (id == R.id.nav_report) {

            Intent adminDashboardIntent = new Intent(AddProductsActivity.this, AdminDashboardActivity.class);
            adminDashboardIntent.putExtra("username",user);
            adminDashboardIntent.putExtra("nav_id", R.id.nav_report);
            startActivity(adminDashboardIntent);

          /*  User userEntity = globalDAO.findByUsername(user);
            System.out.println(userEntity.getUsername());

            Bundle bundle = new Bundle();
            bundle.putString("username", userEntity.getUsername());
            bundle.putInt("userId", userEntity.getUserId());
            OrdersFragment ordersFragment = new OrdersFragment();
            ordersFragment.setArguments(bundle);
            loadFragment(ordersFragment);*/
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
            Intent manageProductsIntent = new Intent(AddProductsActivity.this, AddProductsActivity.class);
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