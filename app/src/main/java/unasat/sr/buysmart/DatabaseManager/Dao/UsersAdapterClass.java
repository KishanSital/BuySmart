package unasat.sr.buysmart.DatabaseManager.Dao;

import android.app.Activity;
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
import unasat.sr.buysmart.Fragments.MyDialogFragment;
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

        holder.button_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringName = holder.editText_userName.getText().toString();
                String stringEmail = holder.editText_Password.getText().toString();

                if (!databaseHelperClass.checkUser(stringName) ){

                    databaseHelperClass.updateUsers(new User(stringName,stringEmail));
                    notifyDataSetChanged();
                    ((FragmentActivity) context).finish();
                    context.startActivity(((FragmentActivity) context).getIntent());

                } else {
                    CharSequence text = "Username already taken";
                    Toast toast = Toast.makeText(v.getContext(), text, Toast.LENGTH_LONG);
                    toast.show();
                }


            }
        });

        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelperClass.deleteUser(usersclass.getUsername());
                users.remove(position);
                notifyDataSetChanged();
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
        Button button_Edit;
        Button button_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

          //  textViewID = itemView.findViewById(R.id.text_id);
            editText_userName = itemView.findViewById(R.id.edittext_name);
            editText_Password = itemView.findViewById(R.id.edittext_email);
            button_delete = itemView.findViewById(R.id.button_delete);
            button_Edit = itemView.findViewById(R.id.button_edit);

        }
    }
}
