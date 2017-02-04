package com.Challenge.Zappos.productdetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.Challenge.Zappos.R;
import com.Challenge.Zappos.databinding.ProductDetailActivityBinding;

/**
 * Created by jiwanbhandari on 2/2/17.
 */

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceItems){
        super.onCreate(savedInstanceItems);
        ProductDetailActivityBinding binding =
                ProductDetailActivityBinding.inflate(getLayoutInflater());
        binding.toolbar.setTitle("Product Detail");
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /*Initialize the View (Fragment) */
        ProductDetailFragment productDetailFragment = (ProductDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if(productDetailFragment!=null){
            productDetailFragment = new ProductDetailFragment();
            getSupportFragmentManager().beginTransaction().
                    add(R.id.contentFrame,productDetailFragment).commit();
        }

        /*create the product detail presenter, don't need to maintain a reference to it because the
        * View will keep a reference.*/
         new ProductDetailPresenter();

    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;

    }
}
