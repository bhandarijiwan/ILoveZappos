package com.Challenge.Zappos.productdetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jiwanbhandari on 2/2/17.
 */

public class ProductDetailFragment extends Fragment implements ProductDetailContract.View{

    private ProductDetailContract.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstceItems){
        super.onCreate(savedInstceItems);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        return  container;
    }

    @Override
    public void onResume(){
        super.onResume();
        mPresenter.start();
    }
}
