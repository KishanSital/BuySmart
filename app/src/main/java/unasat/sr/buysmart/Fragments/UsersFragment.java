package unasat.sr.buysmart.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentController;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import unasat.sr.buysmart.DatabaseManager.Dao.GlobalDAO;
import unasat.sr.buysmart.DatabaseManager.Dao.UsersAdapterClass;
import unasat.sr.buysmart.DatabaseManager.Dao.UsersAdapterClassUser;
import unasat.sr.buysmart.Entities.User;
import unasat.sr.buysmart.R;


public class UsersFragment extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_users,container, false);

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        recyclerView.setHasFixedSize(true);
        Bundle bundle = getArguments();
        if (bundle!= null) {
            String usernameString = bundle.getString("username");
            System.out.println(usernameString);
            GlobalDAO globalDAO = new GlobalDAO(v.getContext());
            User user = globalDAO.findByUsername(usernameString);
            List<User> userList = new ArrayList<User>();
            userList.add(user);
            if (userList.size() > 0) {
                UsersAdapterClassUser usersAdapterClass = new UsersAdapterClassUser(userList, v.getContext());
                recyclerView.setAdapter(usersAdapterClass);
            }
        }else {
                GlobalDAO globalDAO = new GlobalDAO(v.getContext());
                List<User> users = globalDAO.getAllUser();
                if (users.size() > 0){
                    UsersAdapterClass usersAdapterClass = new UsersAdapterClass(users,v.getContext());
                    recyclerView.setAdapter(usersAdapterClass);
                }else {
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("error",getString(R.string.error_message_users));
                    MyDialogFragment myDialogFragment = new MyDialogFragment();
                    myDialogFragment.setArguments(bundle1);
                    myDialogFragment.show(getFragmentManager(),"Fragment");
                }
            }
        return v;
    }
}