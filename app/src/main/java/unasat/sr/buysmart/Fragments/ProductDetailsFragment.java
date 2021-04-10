package unasat.sr.buysmart.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import unasat.sr.buysmart.DatabaseManager.Dao.GlobalDAO;
import unasat.sr.buysmart.Entities.Order;
import unasat.sr.buysmart.Entities.Product;
import unasat.sr.buysmart.Entities.Product2;
import unasat.sr.buysmart.Entities.User;
import unasat.sr.buysmart.R;

public class ProductDetailsFragment extends Fragment {

    public static final String ARG_PROD_ID = "productId";
    public static final String ARG_USERNAME = "username";
    public static final String ARG_USERNAME2 = "username2";
    private int mProd_id;
    private TextView productTextViewDetailTextView, priceTextViewDetailTextView;
    private ImageView imageView;
    private ArrayList<Product2> list;

    private Product2 product;

    public ProductDetailsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProd_id = getArguments().getInt(ARG_PROD_ID);
            System.out.println("Details " + mProd_id);
        }
        list = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_product_details, container, false);
        GlobalDAO globalDAO = new GlobalDAO(v.getContext());
        product = globalDAO.findProduct2ById(String.valueOf(mProd_id));
        productTextViewDetailTextView = v.findViewById(R.id.productTextViewDetailTextView);
        priceTextViewDetailTextView = v.findViewById(R.id.priceTextViewDetailTextView);
        imageView = v.findViewById(R.id.imageView2);
        byte[] image = product.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        imageView.setImageBitmap(bitmap);
        productTextViewDetailTextView.setText("Product naam: "+product.getName());
        priceTextViewDetailTextView.setText("Product prijs: SRD"+String.valueOf(product.InttoString(product.getPrice()))+",-");
        return v;
    }

}