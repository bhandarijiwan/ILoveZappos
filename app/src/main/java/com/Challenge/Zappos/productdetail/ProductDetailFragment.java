package com.Challenge.Zappos.productdetail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Challenge.Zappos.R;
import com.Challenge.Zappos.data.Product;
import com.Challenge.Zappos.databinding.ProductDetailFragmentBinding;
import com.Challenge.Zappos.utils.NetworkImageLoaderHelper;
import com.android.volley.toolbox.ImageLoader;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jiwanbhandari on 2/2/17.
 */

public class ProductDetailFragment extends Fragment implements ProductDetailContract.View, BaseSliderView.OnSliderClickListener {

    private ProductDetailContract.Presenter mPresenter;
    private ProductDetailFragmentBinding binding;

    private SliderLayout mSlider;

    private Product mProduct;

    public ProductDetailFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstceItems) {
        super.onCreate(savedInstceItems);
    }

    @Override
    public void setPresenter(ProductDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {

        binding =
                DataBindingUtil.inflate(inflater, R.layout.product_detail_fragment, container, false);

        mSlider = binding.slider;
        return binding.getRoot();
    }

    @Override
    public void showProduct(Product product) {
        binding.setProduct(product);
        TextSliderView textSliderView=new TextSliderView(getActivity());
        textSliderView.setScaleType(BaseSliderView.ScaleType.CenterCrop);
        textSliderView.description(product.getProductName())
                .image(product.getThumbnailImageUrl()).bundle(new Bundle())
                .getBundle().putString("extra", product.getProductName());
        mSlider.addSlider(textSliderView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mSlider.stopAutoCycle();
        super.onStop();
    }
}
