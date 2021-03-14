package unasat.sr.buysmart.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import unasat.sr.buysmart.DatabaseManager.Dao.GlobalDAO;
import unasat.sr.buysmart.DatabaseManager.Dao.ProductAdapter;
import unasat.sr.buysmart.Entities.Product;
import unasat.sr.buysmart.R;

public class ProductsFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USERNAME = "username";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView productTextViewDetailTextView, priceTextViewDetailTextView;
    RecyclerView recyclerViewProd;

    public ProductsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_USERNAME);
//            mParam2 = getArguments().getString(ARG_PARAM2);
            System.out.println(mParam1);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//         Inflate the layout for this fragment
        View pView = inflater.inflate(R.layout.fragment_products, container, false);;
        recyclerViewProd = pView.findViewById(R.id.recyclerViewProd);

        List<Product> productList = getData(pView.getContext());

        ProductAdapter productAdapter = new ProductAdapter(pView.getContext(), productList);
        recyclerViewProd.setAdapter(productAdapter);
        recyclerViewProd.setLayoutManager(new LinearLayoutManager(pView.getContext()));
        return pView;
    }

    // Sample data for RecyclerView
    private List<Product> getData(Context context) {
        GlobalDAO globalDAO = new GlobalDAO(context);
        List<Product> list = new ArrayList<>();
        list = globalDAO.getAllProduct();
        return list;
    }
}