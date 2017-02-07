package com.Challenge.Zappos.productdetail;

import android.animation.LayoutTransition;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import com.Challenge.Zappos.R;
import com.Challenge.Zappos.data.Product;
import com.Challenge.Zappos.databinding.ProductDetailActivityBinding;
import com.Challenge.Zappos.databinding.ProductListActivityBinding;

/**
 * Created by jiwanbhandari on 2/2/17.
 */

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceItems){
        super.onCreate(savedInstanceItems);
        ProductDetailActivityBinding binding =
                DataBindingUtil.setContentView(this,R.layout.product_detail_activity);
        setSupportActionBar(binding.toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Product product = (Product) getIntent().getSerializableExtra("product");
        /*Initialize the View (Fragment) */
        ProductDetailFragment productDetailFragment = (ProductDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.detailcontentFrame);

        if(productDetailFragment==null){
            productDetailFragment = new ProductDetailFragment();
            getSupportFragmentManager().beginTransaction().
                    add(R.id.detailcontentFrame,productDetailFragment).commit();
            Bundle bundle = new Bundle();
            bundle.putSerializable("product",product);
            productDetailFragment.setArguments(bundle);
        }
        /*create the product detail presenter, don't need to maintain a reference to it because the
        * View will keep a reference.*/
        new ProductDetailPresenter((Product) getIntent().getSerializableExtra("product"),productDetailFragment);

    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;

    }

}
