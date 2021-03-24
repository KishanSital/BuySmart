package unasat.sr.buysmart.Fragments;

import android.content.Context;
import android.database.Cursor;
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
import unasat.sr.buysmart.DatabaseManager.Dao.UsersAdapterClass;
import unasat.sr.buysmart.DatabaseManager.Dao.UsersAdapterClassUser;
import unasat.sr.buysmart.Entities.Product;
import unasat.sr.buysmart.Entities.Product2;
import unasat.sr.buysmart.Entities.User;
import unasat.sr.buysmart.R;

public class ProductsFragment extends Fragment {
    private static final String ARG_USERNAME = "username";

    private String mParam1;
    private String mParam2;
    private TextView productTextViewDetailTextView, priceTextViewDetailTextView;
    RecyclerView recyclerViewProd;
    private Context context;
    private ArrayList<Product2> list;


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

        list = new ArrayList<>();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//         Inflate the layout for this fragment
        View pView = inflater.inflate(R.layout.fragment_products, container, false);;
        recyclerViewProd = pView.findViewById(R.id.recyclerViewProd);
        recyclerViewProd.setLayoutManager(new LinearLayoutManager(pView.getContext()));
        recyclerViewProd.setHasFixedSize(true);

       // List<Product> productList = getData(pView.getContext());
        GlobalDAO globalDAO = new GlobalDAO(pView.getContext());
        Cursor cursor = globalDAO.getProduct2("SELECT * FROM "+ GlobalDAO.PRODUCT2_TABLE);
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int price = Integer.parseInt(cursor.getString(2));
            int productTypeId = cursor.getInt(3);
            byte[] image = cursor.getBlob(4);
            //int id, String name, int price, int productTypeId, byte[] image
            list.add(new Product2(id,name, price,productTypeId, image ));
        }

        if (list.size() > 0){
            ProductAdapter productAdapter = new ProductAdapter(pView.getContext(), list);
            recyclerViewProd.setAdapter(productAdapter);
        }else {
            Bundle bundle1 = new Bundle();
            bundle1.putString("error",getString(R.string.error_products));
            MyDialogFragment myDialogFragment = new MyDialogFragment();
            myDialogFragment.setArguments(bundle1);
            myDialogFragment.show(getFragmentManager(),"Fragment");

        }

       // List<Product2> product2List = getData(pView.getContext());

        return pView;
    }

   /* private List<Product> getData(Context context) {
        GlobalDAO globalDAO = new GlobalDAO(context);
        List<Product> list;
        list = globalDAO.getAllProduct();
        return list;
    }*/
}