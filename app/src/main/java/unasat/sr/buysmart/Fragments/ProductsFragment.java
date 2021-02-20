package unasat.sr.buysmart.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import unasat.sr.buysmart.DatabaseManager.Dao.ProductAdapter;
import unasat.sr.buysmart.Entities.Product;
import unasat.sr.buysmart.R;

public class ProductsFragment extends Fragment {

    RecyclerView recyclerViewProd;

    public ProductsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View pView = inflater.inflate(R.layout.fragment_products, container, false);;
        recyclerViewProd = pView.findViewById(R.id.recyclerViewProd);

        List<Product> productList = getData();

        ProductAdapter productAdapter = new ProductAdapter(pView.getContext(), productList);
        recyclerViewProd.setAdapter(productAdapter);
        recyclerViewProd.setLayoutManager(new LinearLayoutManager(pView.getContext()));
        return pView;
    }

    // Sample data for RecyclerView
    private List<Product> getData()
    {
        List<Product> list = new ArrayList<>();
        list.add(new Product("Schoenen"));
        list.add(new Product("Tassen"));
        list.add(new Product("Meubles"));
        list.add(new Product("Devices"));
        return list;
    }
}