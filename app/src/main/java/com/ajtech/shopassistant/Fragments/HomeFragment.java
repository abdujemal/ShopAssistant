package com.ajtech.shopassistant.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajtech.shopassistant.Adapter.ProductAdapter;
import com.ajtech.shopassistant.Models.Product;
import com.ajtech.shopassistant.R;
import com.ajtech.shopassistant.db_helper.DBHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Product> products ;
    private DBHandler dbHandler;
    private EditText searchEdtxt;
    private Spinner categorySpinner;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        dbHandler = new DBHandler(getContext());
        products = dbHandler.readProducts();

        recyclerView = v.findViewById(R.id.product_rec);
        searchEdtxt = v.findViewById(R.id.search_edtxt);
        categorySpinner = v.findViewById(R.id.category_spianner);
        List<String> spinnerData = new ArrayList<String>();
        spinnerData.add("All");
        for(Product product: products){
            if (!spinnerData.contains(product.getCategory())){
                spinnerData.add(product.getCategory());
            }
        }
        categorySpinner.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,spinnerData));


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        ProductAdapter productAdapter = new ProductAdapter(products, getContext(),dbHandler);
        recyclerView.setAdapter(productAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spinnerData.get(i).equals("All")){
                    recyclerView.setAdapter(productAdapter);
                }else{
                    ArrayList<Product> sortedProduct = new ArrayList<>();
                    for(Product product : products){
                        if(product.getCategory().equals(spinnerData.get(i))){
                            sortedProduct.add(product);
                        }
                    }
                    ProductAdapter productAdapter1 = new ProductAdapter(sortedProduct,getContext(),dbHandler);
                    recyclerView.setAdapter(productAdapter1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        searchEdtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence != null){
                    ArrayList<Product> searchProducts = new ArrayList<>();
                    for (Product product : products){
                        if(product.getName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                            searchProducts.add(product);
                        }
                    }
                    ProductAdapter productAdapter1 = new ProductAdapter(searchProducts, getContext(), dbHandler);
                    recyclerView.setAdapter(productAdapter1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return v;
    }
}