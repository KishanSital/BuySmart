package unasat.sr.buysmart.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import unasat.sr.buysmart.DatabaseManager.Dao.GlobalDAO;
import unasat.sr.buysmart.DatabaseManager.Dao.OrderAdapterClass;
import unasat.sr.buysmart.DatabaseManager.Dao.ProductAdapter;
import unasat.sr.buysmart.Entities.Order;
import unasat.sr.buysmart.Entities.Product;
import unasat.sr.buysmart.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USERNAME = "username";

    // TODO: Rename and change types of parameters
    private String mUsername;

    RecyclerView recyclerViewOrder;


    public OrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUsername = getArguments().getString(ARG_USERNAME);
            System.out.println("Order list " + mUsername);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View pView = inflater.inflate(R.layout.fragment_orders, container, false);;
        recyclerViewOrder = pView.findViewById(R.id.recyclerViewOrder);

        List<Order> orderList = getData(pView.getContext(), mUsername);

        OrderAdapterClass orderAdapterClass = new OrderAdapterClass(pView.getContext(), orderList);
        recyclerViewOrder.setAdapter(orderAdapterClass);
        recyclerViewOrder.setLayoutManager(new LinearLayoutManager(pView.getContext()));
        return pView;
    }

    private List<Order> getData(Context context, String username) {
        GlobalDAO globalDAO = new GlobalDAO(context);
        List<Order> list;
        list = globalDAO.findAllOrdersByUsername(username);
        return list;
    }
}