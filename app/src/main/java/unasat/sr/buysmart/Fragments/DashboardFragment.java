package unasat.sr.buysmart.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import unasat.sr.buysmart.DatabaseManager.Dao.GlobalDAO;
import unasat.sr.buysmart.Entities.User;
import unasat.sr.buysmart.R;


public class DashboardFragment extends Fragment {

    private TextView username;
    private TextView nationality;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v =  inflater.inflate(R.layout.fragment_dashboard, container, false);
        username = (TextView) v.findViewById(R.id.username);
        nationality = (TextView) v.findViewById(R.id.nationality);

        Bundle bundle = getArguments();

        if (bundle!= null){
            String usernameString = bundle.getString("username") ;
            System.out.println(usernameString);
            GlobalDAO financialDAO = new GlobalDAO(getActivity());
            User user = financialDAO.findByUsername(usernameString);
            System.out.println(user.toString());
            username.setText( user.getUsername());
            nationality.setText( user.getNationality());

        }

        return v;
    }


}