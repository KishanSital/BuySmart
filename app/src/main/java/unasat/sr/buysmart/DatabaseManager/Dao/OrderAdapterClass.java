package unasat.sr.buysmart.DatabaseManager.Dao;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import unasat.sr.buysmart.Activities.ProductListActivity;
import unasat.sr.buysmart.Entities.Order;
import unasat.sr.buysmart.Entities.Product;
import unasat.sr.buysmart.Entities.Product2;
import unasat.sr.buysmart.Entities.User;
import unasat.sr.buysmart.Fragments.ProductDetailsFragment;
import unasat.sr.buysmart.R;

public class OrderAdapterClass extends RecyclerView.Adapter<OrderAdapterClass.OrderViewHolder> {

    Context context;
    List<Order> orderList;
    private  GlobalDAO databaseHelperClass;

    public OrderAdapterClass(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
        databaseHelperClass = new GlobalDAO(context);

    }

    @NonNull
    @Override
    public OrderAdapterClass.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.order_item_list, parent, false);
        return new OrderAdapterClass.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        //final Order orderClass = orderList.get(position);
        if(orderList != null) {
           Product2 product =  databaseHelperClass.findProduct2ById((orderList.get(position).getProductId()) + "");
            byte[] image = product.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            holder.imageView.setImageBitmap(bitmap);
            holder.productOTextView.setText(String.valueOf(/*orderList.get(position).getId()*/ "Product name : "+product.getName()));
            holder.priceOTextView.setText(String.valueOf(/*orderList.get(position).getProductId()*/"Product price : SRD "+ product.getPrice()));
            holder.dateTextView.setText(orderList.get(position).getOrderedDate());
            holder.userTextView.setText("Order placed by : "+orderList.get(position).getCustomerName());
        } else {
            holder.productOTextView.setText("Order something");
        }

        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelperClass.deleteOrder(orderList.get(position).getId());
                orderList.remove(position);
                notifyDataSetChanged();


            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{
        TextView productOTextView, priceOTextView, dateTextView, userTextView;
        ImageView imageView;
        ConstraintLayout orderItemLayout;
        Button button_delete;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            productOTextView = itemView.findViewById(R.id.productOTextView);
            priceOTextView = itemView.findViewById(R.id.priceOTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            imageView = itemView.findViewById(R.id.imageView);
            orderItemLayout = itemView.findViewById(R.id.orderItemLayout);
            button_delete = itemView.findViewById(R.id.btnDeleteOrder);
            userTextView = itemView.findViewById(R.id.UserOrderTextView);

        }
    }
}
