package com.ajtech.shopassistant.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ajtech.shopassistant.AddProductActivity;
import com.ajtech.shopassistant.Models.Product;
import com.ajtech.shopassistant.NavActivity;
import com.ajtech.shopassistant.R;
import com.ajtech.shopassistant.db_helper.DBHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    private ArrayList<Product> products;
    private Context context;
    private DBHandler dbHandler;
    public ProductAdapter(ArrayList<Product> products, Context context, DBHandler dbHandler){
        this.products = products;
        this.context = context;
        this.dbHandler = dbHandler;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int i) {
        Product model = products.get(i);
        holder.position.setText(model.getPosition());
        holder.category.setText(model.getCategory());
        holder.name.setText(model.getName());
        holder.desc.setText(model.getDesc());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddProductActivity.class);
                intent.putExtra("datas",model.getId()+","+model.getName()+","+model.getDesc()+","+model.getCategory()+","+model.getPosition());
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHandler.deleteProduct(model.getId());

                Intent intent = new Intent(context, NavActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);

                Snackbar.make(view, "Successfully deleted.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
class ProductViewHolder extends RecyclerView.ViewHolder{
    public ImageView delete, edit;
    public TextView name, desc, category, position;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        delete = itemView.findViewById(R.id.delete_item);
        edit = itemView.findViewById(R.id.edit_item);
        name = itemView.findViewById(R.id.item_name);
        desc = itemView.findViewById(R.id.item_desc);
        category = itemView.findViewById(R.id.item_category);
        position = itemView.findViewById(R.id.item_position);
    }
}