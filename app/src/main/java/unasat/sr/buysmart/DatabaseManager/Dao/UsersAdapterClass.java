package unasat.sr.buysmart.DatabaseManager.Dao;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import unasat.sr.buysmart.Entities.User;
import unasat.sr.buysmart.R;

public class UsersAdapterClass extends RecyclerView.Adapter<UsersAdapterClass.ViewHolder>{


    private List<User> users;
    private Context context;
    private  GlobalDAO databaseHelperClass;


    public UsersAdapterClass(List<User> users, Context context) {
        this.users = users;
        this.context = context;
        databaseHelperClass = new GlobalDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.employee_item_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final User usersclass = users.get(position);

        holder.editText_userName.setText(usersclass.getUsername());

        holder.editText_Password.setText(usersclass.getPassword());
        holder.editText_nationality.setText(usersclass.getNationality());
        holder.editText_phonenumber2.setText(usersclass.toStringPhone2());
        holder.editText_phonenumber1.setText(usersclass.toStringPhone1());
        holder.editText_lastname.setText(usersclass.getLastname());
        holder.editText_firstname.setText(usersclass.getFirstname());
        holder.editText_email.setText(usersclass.getEmail());


        holder.button_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringName = holder.editText_userName.getText().toString();
                String stringPassword = holder.editText_Password.getText().toString();
                String stringNationality = holder.editText_nationality.getText().toString();
                String Stringphonenumber1 = holder.editText_phonenumber1.getText().toString();
                String Stringphonenumber2 = holder.editText_phonenumber2.getText().toString();
                String stringlastname = holder.editText_lastname.getText().toString();
                String stringfirstname = holder.editText_firstname.getText().toString();
                String stringEmail = holder.editText_email.getText().toString();

                    databaseHelperClass.updateUsers(new User(stringEmail,stringName, stringPassword, stringfirstname, stringlastname,  (Integer.parseInt(Stringphonenumber1.trim())),
                            (Integer.parseInt(Stringphonenumber2.trim())),stringNationality));
                    notifyDataSetChanged();
                    ((FragmentActivity) context).finish();
                    context.startActivity(((FragmentActivity) context).getIntent());

            }
        });

        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringName = holder.editText_userName.getText().toString();
                String stringPassword = holder.editText_Password.getText().toString();
                if (databaseHelperClass.checkIfUserAdmin(stringName
                        .trim(), stringPassword.trim())){
                    Toast.makeText(v.getContext(), "Sorry can't remove this user", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                   // databaseHelperClass.deleteOrder(usersclass.getUsername());
                    databaseHelperClass.deleteUser(usersclass.getUsername());
                    users.remove(position);
                    notifyDataSetChanged();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewID;
        EditText editText_userName;
        EditText editText_Password;
        EditText editText_email;
        EditText editText_firstname;
        EditText editText_lastname;
        EditText editText_phonenumber1;
        EditText editText_phonenumber2;
        EditText editText_nationality;

        Button button_Edit;
        Button button_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

          //  textViewID = itemView.findViewById(R.id.text_id);
            editText_userName = itemView.findViewById(R.id.edittext_name);
            editText_Password = itemView.findViewById(R.id.edittext_password);
            editText_email = itemView.findViewById(R.id.et_email);
            editText_firstname = itemView.findViewById(R.id.et_firstname);
            editText_lastname = itemView.findViewById(R.id.et_lastname);
            editText_phonenumber1 = itemView.findViewById(R.id.et_phoneNumber1);
            editText_phonenumber2 = itemView.findViewById(R.id.et_phoneNumber2);
            editText_nationality = itemView.findViewById(R.id.et_nationality);
            button_delete = itemView.findViewById(R.id.button_delete);
            button_Edit = itemView.findViewById(R.id.button_edit);

        }
    }
}
