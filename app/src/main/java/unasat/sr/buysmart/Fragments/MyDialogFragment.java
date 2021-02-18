package unasat.sr.buysmart.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import unasat.sr.buysmart.R;


public class MyDialogFragment extends DialogFragment {

    private TextView errorMessage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.dialogfragment,container, false);
        errorMessage = v.findViewById(R.id.textView);
        Bundle bundle = getArguments();
        String error = bundle.getString("error");
        errorMessage.setText(error);
         return v;
    }
}