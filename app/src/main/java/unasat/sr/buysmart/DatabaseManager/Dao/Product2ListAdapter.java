package unasat.sr.buysmart.DatabaseManager.Dao;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import unasat.sr.buysmart.Entities.Product;
import unasat.sr.buysmart.Entities.Product2;
import unasat.sr.buysmart.R;


public class Product2ListAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<Product2> product2List;

    public Product2ListAdapter(Context context, int layout, ArrayList<Product2> product2List) {
        this.context = context;
        this.layout = layout;
        this.product2List = product2List;
    }

    @Override
    public int getCount() {
        return product2List.size();
    }

    @Override
    public Object getItem(int position) {
        return product2List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtName, txtPrice;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtName = (TextView) row.findViewById(R.id.txtName);
            holder.txtPrice = (TextView) row.findViewById(R.id.txtPrice);
            holder.imageView = (ImageView) row.findViewById(R.id.imgFood);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Product2 product2 = product2List.get(position);
        holder.txtName.setText("Product naam: "+product2.getName());
        holder.txtPrice.setText("Product prijs  : SRD"+product2.InttoString(product2.getPrice())+",-");
        byte[] image = product2.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
