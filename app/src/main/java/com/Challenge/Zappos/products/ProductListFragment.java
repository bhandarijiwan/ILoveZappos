package com.Challenge.Zappos.products;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
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
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.Challenge.Zappos.R;
import com.Challenge.Zappos.data.Product;
import com.Challenge.Zappos.databinding.ProductListFragmentBinding;
import com.Challenge.Zappos.databinding.ProductListItemBinding;
import com.Challenge.Zappos.productdetail.ProductDetailActivity;
import com.Challenge.Zappos.utils.NetworkImageLoaderHelper;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by jiwanbhandari on 1/31/17.
 */

public class ProductListFragment extends Fragment implements
       ProductsContract.View,View.OnClickListener, SearchView.OnCloseListener{

    private static final String TAG = "ProductListFragment";

    private ProductsContract.Presenter mPresenter;


    private SearchView mSearchView;

    private MenuItem mSearchmenu;

    private ProductListAdapter mAdapter;

    private AnimatedVectorDrawable searchToback;

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

        setHasOptionsMenu(true);

        Log.e(TAG,"onCreateView  Called !!");

        return binding.getRoot();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {


        searchToback = (AnimatedVectorDrawable) ContextCompat
                .getDrawable(getActivity(), R.drawable.avd_search_to_back);

        // Place an action bar item for searching.
        mSearchmenu = menu.add("Search");
        mSearchmenu.setIcon(R.drawable.ic_search_24dp);
        mSearchmenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM
                | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        mSearchView = new SearchView(getActivity());
        mSearchView.setId(R.id.searchview);
        mSearchView.setOnCloseListener(this);
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setAlpha(1.0f);
        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        mSearchmenu.setActionView(mSearchView);
        mSearchView.setOnSearchClickListener(this);

        Log.e("ProductListFragment","OnCreateOptionsMenu called!!!!");

    }

    @Override
    public void setPresenter(ProductsContract.Presenter presenter){
        mPresenter = presenter;
    }

    @Override
    public void showProductDetailUi(View view, Product product) {
        Intent intent = new Intent(this.getActivity(), ProductDetailActivity.class);
        intent.putExtra("product",product);

        View sharedView = view.findViewById(R.id.thumbnail);

        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(getActivity(),sharedView,"thumbnail_picture");
        getActivity().startActivity(intent,options.toBundle());
    }

    @Override
    public void onAddRemoveCart(View view,int n) {
        String snackbarMessage=" Item removed from cart !!";
        if(n==0){
            ((ImageButton)view).setImageResource(R.drawable.cart_off);
            snackbarMessage ="Item added to cart !!";
        }
        else((ImageButton)view).setImageResource(R.drawable.cart);
        Snackbar.make(view,snackbarMessage, Snackbar.LENGTH_SHORT).show();

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

    public void passQuery(String query){
        mSearchView.setQuery(query,false);
        mPresenter.loadProducts(query);
    }

    @Override
    public void showProducts(List<Product> products) {

        mAdapter.replaceData(products);
    }

    @Override
    public void onClick(View v) {
            MenuItemCompat.expandActionView(mSearchmenu);
            mSearchView.setPressed(true);
            mSearchView.setScaleX(0.0f);
            mSearchView.animate().scaleX(1.0f)
                    .setDuration(500)
                    .setInterpolator(AnimationUtils.loadInterpolator(getActivity(),android.R.anim.linear_interpolator))
                    .start();

    }
    private class ProductListAdapter extends RecyclerView.Adapter<ViewHolder>{


        private List<Product> mProducts;

        private int lastPosition = -1;

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
            if(position>lastPosition)
                holder.itemView.startAnimation(AnimationUtils.loadAnimation(getActivity(),android.R.anim.slide_in_left));
            lastPosition=position;
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
