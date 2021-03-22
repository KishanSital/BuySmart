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
import unasat.sr.buysmart.DatabaseManager.Dao.UsersAdapterClassUser;
import unasat.sr.buysmart.Entities.Product;
import unasat.sr.buysmart.Entities.User;
import unasat.sr.buysmart.R;

public class ProductsFragment extends Fragment {
    private static final String ARG_USERNAME = "username";

    private String mParam1;
    private String mParam2;
    private TextView productTextViewDetailTextView, priceTextViewDetailTextView;
    RecyclerView recyclerViewProd;

    public ProductsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle!= null) {
             mParam1 = bundle.getString("username");
            System.out.println(mParam1);
        } else {
            System.out.println("Bundle is null");
        }

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_USERNAME);
            System.out.println(mParam1);
        } else {
            System.out.println("getArguments is null");
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

    private List<Product> getData(Context context) {
        GlobalDAO globalDAO = new GlobalDAO(context);
        List<Product> list;
        list = globalDAO.getAllProduct();
        return list;
    }
}