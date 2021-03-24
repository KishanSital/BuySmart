package unasat.sr.buysmart.DatabaseManager.Dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import unasat.sr.buysmart.Activities.ProductListActivity;
import unasat.sr.buysmart.Entities.Product;
import unasat.sr.buysmart.Entities.Product2;
import unasat.sr.buysmart.Entities.User;
import unasat.sr.buysmart.Fragments.ProductDetailsFragment;
import unasat.sr.buysmart.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    Context context;
   // List<Product> productList;
   private final ArrayList<Product2> product2List;
    String usernameString;

    public ProductAdapter(Context context, ArrayList<Product2> product2List) {
        this.context = context;
        this.product2List = product2List;
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
        System.out.println("Product name : "+product2List.get(position).getName());
        System.out.println(product2List.get(position).getPrice());
        Product2 product2 = product2List.get(position);
        byte[] Image = product2.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(Image, 0, Image.length);
        holder.imageView.setImageBitmap(bitmap);
        holder.productTextView.setText(product2List.get(position).getName());
        holder.priceTextView.setText("Price: SRD " + product2List.get(position).InttoString(product2.getPrice()) +",-");

        holder.productItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Bundle extras = activity.getIntent().getExtras();
                String user = extras.getString("username");
                if (user!= null) {
                    System.out.println(user);
                } else {
                    System.out.println("username is null");
                }
                Bundle bundle = activity.getIntent().getExtras();
                if (bundle!= null) {
                     usernameString = bundle.getString("username");
                    System.out.println(usernameString);
                    GlobalDAO globalDAO = new GlobalDAO(v.getContext());
                    User userClass = globalDAO.findByUsername(usernameString);
                } else {
                    System.out.println("Bundle username is null");
                }

                Context context = v.getContext();
                Intent intent = new Intent(context, ProductListActivity.class);
                intent.putExtra(ProductDetailsFragment.ARG_PROD_ID, product2List.get(position).getId());
                intent.putExtra(ProductDetailsFragment.ARG_USERNAME, user);
                intent.putExtra(ProductDetailsFragment.ARG_USERNAME2, usernameString);
                System.out.println("Master" + product2List.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return product2List.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        TextView productTextView, priceTextView;
        ImageView imageView;
        Button orderBtn;
        ConstraintLayout productItemLayout;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            productTextView = itemView.findViewById(R.id.productTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            productItemLayout = itemView.findViewById(R.id.productItemLayout);
        }
    }
}