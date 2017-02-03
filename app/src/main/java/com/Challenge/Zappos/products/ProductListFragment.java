package com.Challenge.Zappos.products;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Challenge.Zappos.BR;
import com.Challenge.Zappos.R;
import com.Challenge.Zappos.data.Product;
import com.Challenge.Zappos.databinding.ProductListFragmentBinding;
import com.Challenge.Zappos.databinding.ProductListItemBinding;
import com.Challenge.Zappos.util.NetworkImageLoaderHelper;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiwanbhandari on 1/31/17.
 */

public class ProductListFragment extends Fragment implements
        SearchView.OnQueryTextListener,SearchView.OnCloseListener,
        ProductsContract.View {

    private static final String TAG = "ProductListFragment";

    private ProductsContract.Presenter mPresenter;

    private RecyclerView mrecylerView;

    private SearchView mSearchView;

    private ProductListAdapter mAdapter;

    private LinearLayoutManager linearLayoutManager;

    public ProductListFragment(){}

    @Override
    public void onActivityCreated(Bundle savedInstaceItems){
        super.onActivityCreated(savedInstaceItems);
        Log.e(TAG,"onActivityCreated Called !!");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState){

        mAdapter = new ProductListAdapter(getActivity(),new ArrayList<Product>());

        ProductListFragmentBinding binding = DataBindingUtil.inflate(inflater,R.layout.product_list_fragment,container,false);


        binding.recylerView.setAdapter(mAdapter);

        binding.recylerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

//        binding.setVariable(BR.presenter,mPresenter);

        setHasOptionsMenu(true);

        Log.e(TAG,"onCreateView  Called !!");

        return binding.getRoot();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Place an action bar item for searching.
        MenuItem item = menu.add("Search");
        item.setIcon(android.R.drawable.ic_menu_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM
                | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        mSearchView = new SearchView(getActivity());
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnCloseListener(this);
        mSearchView.setIconifiedByDefault(true);
        item.setActionView(mSearchView);
        Log.e("ProductListFragment","OnCreateOptionsMenu called!!!!");
    }

    @Override
    public void setPresenter(ProductsContract.Presenter presenter){
        mPresenter = presenter;
    }

    @Override
    public void onResume(){
        super.onResume();
        mPresenter.start();
    }

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mPresenter.loadProducts(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }



    @Override
    public void showProducts(List<Product> products) {

        mAdapter.replaceData(products);
    }


    private class ProductListAdapter extends RecyclerView.Adapter<ViewHolder>{


        private List<Product> mProducts;

        public ProductListAdapter(Context context, ArrayList<Product> products) {

            this.mProducts = products;

        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

            ProductListItemBinding productBinding = ProductListItemBinding.inflate(layoutInflater,parent,false);
            final ViewHolder vh = new ViewHolder(productBinding);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(mProducts.get(position));
        }

        @Override
        public int getItemCount() {
           return mProducts.size();
        }

        public void replaceData(List<Product> products){
            mProducts = products;
            notifyDataSetChanged();
        }
    }


    private  class ViewHolder extends RecyclerView.ViewHolder{

        private final ProductListItemBinding productBinding;

        public ViewHolder(ProductListItemBinding productBinding) {
            super(productBinding.getRoot());
            this.productBinding=productBinding;
        }

        public void bind(Product product){
            this.productBinding.setProduct(product);
            this.productBinding.setPresenter(mPresenter);
            this.productBinding.executePendingBindings();
        }
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(NetworkImageView networkImageView,String url){
        ImageLoader imgLoader = NetworkImageLoaderHelper.getInstance(networkImageView.getContext()).getMbitMapLoader();
        networkImageView.setDefaultImageResId(R.drawable.ic_perm_media_black);
        networkImageView.setImageUrl(url,imgLoader);
    }
}
