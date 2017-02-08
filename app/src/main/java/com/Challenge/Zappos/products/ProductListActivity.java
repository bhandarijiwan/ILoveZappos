/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.Challenge.Zappos.products;


import android.animation.Animator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.app.SearchManager;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.tool.DataBindingBuilder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.animation.AnimationUtils;

import com.Challenge.Zappos.R;
import com.Challenge.Zappos.databinding.ProductListActivityBinding;

import java.util.HashSet;


/** Activity for displaying the search result.
 *  Most work happens in the View (fragment) and the activity itself
 *  just initializes and glues the View and Presenter.
 */
public class ProductListActivity extends AppCompatActivity {

    private final static String TAG = "ProductListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductListActivityBinding binding = DataBindingUtil.setContentView(this,R.layout.product_list_activity);
        setSupportActionBar(binding.toolbar);

        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.disableTransitionType(LayoutTransition.DISAPPEARING);
        binding.toolbar.setLayoutTransition(layoutTransition);


        ProductListFragment productListFragment= (ProductListFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        /*Reattach the fragment if it hasn't already been attached.*/
        if(productListFragment==null){
            productListFragment = new ProductListFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.contentFrame,productListFragment).commit();
        }


        // set the onclick listner to fab here because fab is part of activity not the fragment.
        binding.fab.setOnClickListener(productListFragment); //TODO consider moving the fab to fragment

        /*Create a new presenter, don't need to hold reference to it because the View will have one. */
        new ProductsPresenter(getSupportLoaderManager(),productListFragment,this);

    }
    @Override
    protected void onNewIntent(Intent intent){
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
        ProductListFragment productListFragment=
                (ProductListFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
            String query = intent.getStringExtra(SearchManager.QUERY);
            productListFragment.passQuery(query);
        }
    }
}