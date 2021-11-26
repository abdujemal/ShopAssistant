package com.ajtech.shopassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ajtech.shopassistant.db_helper.DBHandler;
import com.google.android.material.snackbar.Snackbar;

public class AddProductActivity extends AppCompatActivity {

    private EditText productNameEdtxt, productDecsEdtxt, productCategoryedtxt, productPsitionEdtxt;
    private Button saveBtn;
    private String[] datas;
    private String id;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        bundle = getIntent().getExtras();

        productNameEdtxt  = findViewById(R.id.product_name_edtxt);
        productDecsEdtxt = findViewById(R.id.product_desc_edtxt);
        productCategoryedtxt = findViewById(R.id.product_Category_edtxt);
        productPsitionEdtxt = findViewById(R.id.product_position_edtxt);
        saveBtn = findViewById(R.id.save_btn);

        if (bundle!=null){
            datas = bundle.get("datas").toString().split(",");
            productNameEdtxt.setText(datas[1]);
            productDecsEdtxt.setText(datas[2]);
            productCategoryedtxt.setText(datas[3]);
            productPsitionEdtxt.setText(datas[4]);
            id = datas[0];
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(view);
            }
        });


    }

    private void validate(View view) {
        if(TextUtils.isEmpty(productNameEdtxt.getText())){
            productNameEdtxt.setError("This field is required.");
            productNameEdtxt.requestFocus();
            return;
        }else if(TextUtils.isEmpty(productCategoryedtxt.getText())){
            productCategoryedtxt.setError("This field is required.");
            productCategoryedtxt.requestFocus();
            return;
        }else if(TextUtils.isEmpty(productPsitionEdtxt.getText())){
            productPsitionEdtxt.setError("This field is required.");
            productPsitionEdtxt.requestFocus();
            return;
        }else{
            saveData(view);
        }
    }

    private void saveData(View view) {
        DBHandler dbHandler = new DBHandler(AddProductActivity.this);
        String name = productNameEdtxt.getText().toString();
        String desc = "";
        if (productDecsEdtxt.getText() != null){
            desc = productDecsEdtxt.getText().toString();
        }
        String category = productCategoryedtxt.getText().toString();
        String position = productPsitionEdtxt.getText().toString();
        if (bundle!=null){
            dbHandler.updateProduct(id,name,desc,category,position);

            Snackbar.make(view, "Congratulations, You have updated your product.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else{
            dbHandler.addNewProduct(name,desc,category,position);

            Snackbar.make(view, "Congratulations, You have added your product.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }


        productDecsEdtxt.setText("");
        productNameEdtxt.setText("");
        productPsitionEdtxt.setText("");
        productCategoryedtxt.setText("");

        Intent intent = new Intent(AddProductActivity.this, NavActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}