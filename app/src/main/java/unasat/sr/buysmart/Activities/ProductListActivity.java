package unasat.sr.buysmart.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

import unasat.sr.buysmart.DatabaseManager.Dao.GlobalDAO;
import unasat.sr.buysmart.Entities.Order;
import unasat.sr.buysmart.Entities.User;
import unasat.sr.buysmart.Fragments.ProductDetailsFragment;
import unasat.sr.buysmart.R;

public class ProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        int prodId = getIntent().getIntExtra(ProductDetailsFragment.ARG_PROD_ID, 0);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addOrder(view, getIntent().getStringExtra(ProductDetailsFragment.ARG_USERNAME), prodId, getApplicationContext());
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();

            Fragment productDetailFragment = new ProductDetailsFragment();
            arguments.putInt(ProductDetailsFragment.ARG_PROD_ID, prodId);
            productDetailFragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.item_detail_container, productDetailFragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void addOrder(View view, String username, int prodId, Context context) {
        GlobalDAO globalDAO = new GlobalDAO(context);
        User user = globalDAO.findByUsername(username);
        if(user != null) {
            Order order = new Order();
            order.setCustomerId(user.getUserId());
            order.setProductId(prodId);
            Date date = new Date();
            order.setOrderedDate(String.valueOf(date));
            globalDAO.addOrder(order);
            System.out.println("Added order for " + username);
            Snackbar.make(view, prodId + " ordered", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            System.out.println("Failed to add ordered.");
        }
    }
}