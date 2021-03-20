package unasat.sr.buysmart.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.Date;

import unasat.sr.buysmart.DatabaseManager.Dao.GlobalDAO;
import unasat.sr.buysmart.Entities.Order;
import unasat.sr.buysmart.Entities.Product;
import unasat.sr.buysmart.Entities.User;
import unasat.sr.buysmart.R;

public class ProductDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PROD_ID = "productId";
    public static final String ARG_USERNAME = "username";

    // TODO: Rename and change types of parameters
    private int mProd_id;
    private TextView productTextViewDetailTextView, priceTextViewDetailTextView;

    private Product product;

    public ProductDetailsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProd_id = getArguments().getInt(ARG_PROD_ID);
            System.out.println("Details " + mProd_id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_product_details, container, false);
        GlobalDAO globalDAO = new GlobalDAO(v.getContext());
        product = globalDAO.findProductById(String.valueOf(mProd_id));
        productTextViewDetailTextView = v.findViewById(R.id.productTextViewDetailTextView);
        priceTextViewDetailTextView = v.findViewById(R.id.priceTextViewDetailTextView);
        productTextViewDetailTextView.setText(product.getName());
        priceTextViewDetailTextView.setText(String.valueOf(product.getPrice()));
        return v;
    }

}