package unasat.sr.buysmart.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import unasat.sr.buysmart.DatabaseManager.Dao.GlobalDAO;
import unasat.sr.buysmart.DatabaseManager.Dao.OrderAdapterClass;

import unasat.sr.buysmart.Entities.Order;

import unasat.sr.buysmart.Entities.User;
import unasat.sr.buysmart.R;


public class OrdersFragment extends Fragment {


    private static final String ARG_USERNAME = "username";
    private static final String ARG_USER_ID = "userId";

    private String mUsername;
    private int mId;
    RecyclerView recyclerViewOrder;


    public OrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mUsername = bundle.getString(ARG_USERNAME);
            mId = bundle.getInt(ARG_USER_ID);
            System.out.println("Order list Id="+ mId+ " username= " + mUsername);
        } else{
            System.out.println("OrdersFragment, no list id and username found");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View pView = inflater.inflate(R.layout.fragment_orders, container, false);;
        recyclerViewOrder = pView.findViewById(R.id.recyclerViewOrder);
        recyclerViewOrder.setLayoutManager(new LinearLayoutManager(pView.getContext()));
        recyclerViewOrder.setHasFixedSize(true);

        List<Order> orderList = getData(pView.getContext(), mUsername);

        if (orderList.size() > 0) {

            OrderAdapterClass orderAdapterClass = new OrderAdapterClass(pView.getContext(), orderList);
            recyclerViewOrder.setAdapter(orderAdapterClass);
        } else {
            Bundle bundle1 = new Bundle();
            bundle1.putString("error",getString(R.string.error_orders_list));
            MyDialogFragment myDialogFragment = new MyDialogFragment();
            myDialogFragment.setArguments(bundle1);
            myDialogFragment.show(getFragmentManager(),"Fragment");
        }

             recyclerViewOrder.setLayoutManager(new LinearLayoutManager(pView.getContext()));

        return pView;
    }

    private List<Order> getData(Context context, String username) {
        GlobalDAO globalDAO = new GlobalDAO(context);
        List<Order> list;
        User user = globalDAO.findByUsername(username);
        if (globalDAO.checkIfUserAdmin(user.getUsername(), user.getPassword())){
            list = globalDAO.findAllOrders();
        }else {
            list = globalDAO.findAllOrdersByUsername(username);
        }
        return list;
    }
}