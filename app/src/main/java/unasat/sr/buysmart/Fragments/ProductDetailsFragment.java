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
import unasat.sr.buysmart.Entities.User;
import unasat.sr.buysmart.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductDetailsFragment} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "productName";
    private static final String ARG_PARAM2 = "productPrice";
    private static final String ARG_PARAM3 = "username";
    private static final String ARG_PARAM4 = "productId";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private int mParam4;
    private TextView productTextViewDetailTextView, priceTextViewDetailTextView;
    private Button orderBtn;

    public ProductDetailsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getInt(ARG_PARAM4);
            System.out.println(mParam3);
            System.out.println(mParam4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_product_details, container, false);
        productTextViewDetailTextView = v.findViewById(R.id.productTextViewDetailTextView);
        priceTextViewDetailTextView = v.findViewById(R.id.priceTextViewDetailTextView);
        productTextViewDetailTextView.setText(mParam1);
        priceTextViewDetailTextView.setText(mParam2);
        orderBtn = v.findViewById(R.id.orderBtn);
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrder(v);
            }
        });
        return v;
    }

    public void addOrder(View view) {
        Context context = view.getContext();
        GlobalDAO globalDAO = new GlobalDAO(context);
        User user = globalDAO.findByUsername(mParam3);
        if(user != null) {
            Order order = new Order();
            order.setCustomerId(user.getUserId());
            order.setProductId(mParam4);
            Date date = new Date();
            order.setOrderedDate("Now");
            globalDAO.addOrder(order);
            System.out.println("Added order for " + mParam4);
        } else {
            System.out.println("Failed to add ordered.");
        }
    }
}