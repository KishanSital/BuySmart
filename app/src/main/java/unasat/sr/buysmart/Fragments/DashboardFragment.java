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
    private TextView email;
    private TextView password;
    private TextView firstname;
    private TextView lastname;
    private TextView phonenumber1;
    private TextView phonenumber2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v =  inflater.inflate(R.layout.fragment_dashboard, container, false);
        username = (TextView) v.findViewById(R.id.username);
        nationality = (TextView) v.findViewById(R.id.nationality);
        email = (TextView) v.findViewById(R.id.email);
        password = (TextView) v.findViewById(R.id.password);
        firstname = (TextView) v.findViewById(R.id.firstname);
        lastname = (TextView) v.findViewById(R.id.lastname);
        phonenumber1 = (TextView) v.findViewById(R.id.phonenumber1);
        phonenumber2 = (TextView) v.findViewById(R.id.phonenumber2);

        Bundle bundle = getArguments();

        if (bundle!= null){
            String usernameString = bundle.getString("username") ;
            System.out.println(usernameString);
            GlobalDAO globalDAO = new GlobalDAO(getActivity());
            User user = globalDAO.findByUsername(usernameString);
            System.out.println(user.toString());
            username.setText( user.getUsername());
            nationality.setText( user.getNationality());
            email.setText( user.getEmail());
            password.setText( user.getPassword());
            firstname.setText( user.getFirstname());
            lastname.setText( user.getLastname());
            phonenumber1.setText(  user.toStringPhone1());
            phonenumber2.setText(user.toStringPhone2());

        }

        return v;
    }


}