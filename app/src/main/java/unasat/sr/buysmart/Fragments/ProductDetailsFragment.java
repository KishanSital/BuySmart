package unasat.sr.buysmart.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import unasat.sr.buysmart.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "productName";
    private static final String ARG_PARAM2 = "productPrice";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView productTextViewDetailTextView, priceTextViewDetailTextView;

    public ProductDetailsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
        return v;
    }
}