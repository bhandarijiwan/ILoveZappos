package com.Challenge.Zappos.productdetail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.Challenge.Zappos.R;
import com.Challenge.Zappos.data.Product;
import com.Challenge.Zappos.databinding.ProductDetailFragmentBinding;
import com.Challenge.Zappos.utils.NetworkImageLoaderHelper;
import com.Challenge.Zappos.utils.ProductInfoHelper;
import com.android.volley.toolbox.ImageLoader;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;


/**
 * Created by jiwanbhandari on 2/2/17.
 */

public class ProductDetailFragment extends Fragment implements ProductDetailContract.View, BaseSliderView.OnSliderClickListener {

    private ProductDetailContract.Presenter mPresenter;

    private ProductDetailFragmentBinding binding;

    private SliderLayout mSlider;

    private ShareActionProvider mShareActionProvider;

    public ProductDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstceItems) {
        super.onCreate(savedInstceItems);
        mShareActionProvider =new ShareActionProvider(getActivity());
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
        setHasOptionsMenu(true);

        return binding.getRoot();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){

        MenuItem item = menu.add("Share");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        MenuItemCompat.setActionProvider(item,mShareActionProvider);
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
        mShareActionProvider.setShareIntent(ProductInfoHelper.getShareableIntent(product));
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start(); // start the presenter
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
