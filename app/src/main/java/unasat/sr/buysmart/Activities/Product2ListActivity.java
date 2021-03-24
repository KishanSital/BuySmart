package unasat.sr.buysmart.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import unasat.sr.buysmart.DatabaseManager.Dao.GlobalDAO;
import unasat.sr.buysmart.DatabaseManager.Dao.Product2ListAdapter;
import unasat.sr.buysmart.DatabaseManager.Dao.ProductAdapter;
import unasat.sr.buysmart.Entities.Order;
import unasat.sr.buysmart.Entities.Product2;
import unasat.sr.buysmart.Entities.ProductType;
import unasat.sr.buysmart.Fragments.MyDialogFragment;
import unasat.sr.buysmart.R;

public class Product2ListActivity extends AppCompatActivity {

   private GridView gridView;
    private ArrayList<Product2> list;
    private Product2ListAdapter adapter = null;
   private  ImageView imageViewProducts2;
   private GlobalDAO globalDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);

        globalDAO = new GlobalDAO(this);

        gridView = (GridView) findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter = new Product2ListAdapter(this, R.layout.activity_products2_items, list);
        gridView.setAdapter(adapter);

        // get all data from sqlite
        Cursor cursor = globalDAO.getProduct2("SELECT * FROM "+ GlobalDAO.PRODUCT2_TABLE);
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int price = Integer.parseInt(cursor.getString(2));
            int productTypeId = cursor.getInt(3);
            byte[] image = cursor.getBlob(4);

                   //int id, String name, int price, int productTypeId, byte[] image

            list.add(new Product2(id,name, price,productTypeId, image ));
        }
        if (list.size() > 0){
            adapter.notifyDataSetChanged();
        }else {
            Bundle bundle1 = new Bundle();
            bundle1.putString("error",getString(R.string.error_products));
            MyDialogFragment myDialogFragment = new MyDialogFragment();
            myDialogFragment.setArguments(bundle1);
            myDialogFragment.show(getSupportFragmentManager(),"Fragment");
       /*     new java.util.Timer().schedule(
                    new java.util.TimerTask(){
                        @Override
                        public void run() {
                            onBackPressed();
                        }
                    }, 5000
            );*/

            return;
        }
        //adapter.notifyDataSetChanged();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(Product2ListActivity.this);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            // update
                            Cursor c = globalDAO.getProduct2("SELECT "+ GlobalDAO.PRODUCT2_ID +" FROM " + GlobalDAO.PRODUCT2_TABLE);
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            // show dialog update at here
                            showDialogUpdate(Product2ListActivity.this, arrID.get(position));

                        } else {
                            // delete
                            Cursor c = globalDAO.getProduct2("SELECT "+ GlobalDAO.PRODUCT2_ID +" FROM " + GlobalDAO.PRODUCT2_TABLE);
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));
                        }
                    }
                });
                dialog.show();

            }
        });

    }

    private void showDialogUpdate(Activity activity, final int position){

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.activity_update_products2);
        dialog.setTitle("Update");

        imageViewProducts2 = (ImageView) dialog.findViewById(R.id.imageViewFood);
        final EditText edtName = (EditText) dialog.findViewById(R.id.edtName);
        final EditText edtPrice = (EditText) dialog.findViewById(R.id.edtPrice);
        Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdate);

        // set width for dialog
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        // set height for dialog
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        imageViewProducts2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // request photo library
                ActivityCompat.requestPermissions(
                        Product2ListActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductType productType = globalDAO.findProductTypeByName("Phones");

                try {
                    globalDAO.updateProduct2(
                            edtName.getText().toString().trim(),
                            Integer.parseInt(edtPrice.getText().toString().trim()),
                            AddProductsActivity.imageViewToByte(imageViewProducts2),
                            position, productType.getProductTypeId()
                    );
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Updated successfully!!!",Toast.LENGTH_SHORT).show();
                }
                catch (Exception error) {
                    Log.e("Update error", error.getMessage());
                }
                updateProductsList();
            }
        });
    }

    private void showDialogDelete(final int idFood){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(Product2ListActivity.this);

        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to this delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<Order> list = globalDAO.findAllOrdersByProductId(idFood);
                if (list.size() > 0){
                    Toast.makeText(getApplicationContext(), getString(R.string.error_orders_placed),Toast.LENGTH_LONG).show();
                    return;
                } else {
                    try {
                        globalDAO.deleteProduct2(idFood);
                        Toast.makeText(getApplicationContext(), "Deleted successfully!!!",Toast.LENGTH_SHORT).show();
                    } catch (Exception e){
                        Log.e("error", e.getMessage());
                    }
                }
                updateProductsList();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    private void updateProductsList(){
        // get all data from sqlite
        Cursor cursor = globalDAO.getProduct2("SELECT * FROM "+ GlobalDAO.PRODUCT2_TABLE);
        list.clear();

        //int id, String name, int price, int productTypeId, byte[] image

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int price = Integer.parseInt(cursor.getString(2));
            int productTypeId = cursor.getInt(3);
            byte[] image = cursor.getBlob(4);

            list.add(new Product2(id, name, price, productTypeId, image));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {

        if(requestCode == 888){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 888 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageViewProducts2.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}