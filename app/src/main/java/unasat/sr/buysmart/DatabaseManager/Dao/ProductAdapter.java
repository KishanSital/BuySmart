package unasat.sr.buysmart.DatabaseManager.Dao;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import unasat.sr.buysmart.Activities.ProductListActivity;
import unasat.sr.buysmart.Entities.Product;
import unasat.sr.buysmart.Fragments.ProductDetailsFragment;
import unasat.sr.buysmart.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    Context context;
    List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_item_list, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        System.out.println(productList.get(position).getName());
        System.out.println(productList.get(position).getPrice());
        holder.productTextView.setText(productList.get(position).getName());
        holder.priceTextView.setText("Price: " + String.valueOf(productList.get(position).getPrice()));


        holder.productItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AppCompatActivity activity = (AppCompatActivity) v.getContext();
//                Bundle extras = activity.getIntent().getExtras();
//                String user = extras.getString("username");
//                if (user!= null) {
//                    System.out.println(user);
////                    username.setText(user);
//                }
//                Bundle bundle = new Bundle();
//                bundle.putString("productName", productList.get(position).getName());
//                bundle.putString("productPrice", String.valueOf(productList.get(position).getPrice()));
//                bundle.putInt("productId", productList.get(position).getId());
//                bundle.putString("username", user);
//
//                Fragment myFragment = new ProductDetailsFragment();
//                myFragment.setArguments(bundle);
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, myFragment).addToBackStack(null).commit();
                Context context = v.getContext();
                Intent intent = new Intent(context, ProductListActivity.class);
                intent.putExtra(ProductDetailsFragment.ARG_PROD_ID, productList.get(position).getId());
                System.out.println("Master" + productList.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        TextView productTextView, priceTextView;
        Button orderBtn;
        ConstraintLayout productItemLayout;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productTextView = itemView.findViewById(R.id.productTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            orderBtn = itemView.findViewById(R.id.orderBtn);
            productItemLayout = itemView.findViewById(R.id.productItemLayout);
        }
    }
}