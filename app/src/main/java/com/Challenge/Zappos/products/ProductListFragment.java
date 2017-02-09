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
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import java.util.HashSet;
import java.util.List;
/**
 * Created by jiwanbhandari on 1/31/17.
 * This is where the bulk of UI work actually happens.
 *
 * ProductListFragment implements ProductsContract.View which defines
 * an interface for the presenter to talk with it.
 */

public class ProductListFragment extends Fragment implements
        ProductsContract.View,View.OnClickListener, SearchView.OnCloseListener{

    private static final String TAG = "ProductListFragment";

    private ProductsContract.Presenter mPresenter;

    private SearchView mSearchView;

    private MenuItem mSearchmenu;

    private ProductListAdapter mAdapter;




    public ProductListFragment(){}

    @Override
    public void onActivityCreated(Bundle savedInstaceItems){
        super.onActivityCreated(savedInstaceItems);
        if(savedInstaceItems!=null && savedInstaceItems.containsKey("cart"))
        mPresenter.setCart((HashSet<Integer>) savedInstaceItems.getSerializable("cart"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState){

        mAdapter = new ProductListAdapter(getActivity(),new ArrayList<Product>());
        // We inflate out Fragment Layout here but data binding come to rescue so
        // don't have to do much at all.
        ProductListFragmentBinding binding = DataBindingUtil.inflate(inflater,R.layout.product_list_fragment,container,false);
        binding.recylerView.setAdapter(mAdapter);
        binding.recylerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        setHasOptionsMenu(true);
        return binding.getRoot();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {


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

        //TODO Move this over to the XML.
    }

    @Override
    public void setPresenter(ProductsContract.Presenter presenter){
        mPresenter = presenter;
    }

    /**
     * Called by the presenter to show the clicked product item in the next activity.
     * @param view the viewholder that was clicked. Need for animation purposes.
     * @param product product that was clicked
     */
    @Override
    public void showProductDetailUi(View view, Product product) {
        Intent intent = new Intent(this.getActivity(), ProductDetailActivity.class);
        intent.putExtra("product",product);

        View sharedView = view.findViewById(R.id.thumbnail);

        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(getActivity(),sharedView,"thumbnail_picture");
        getActivity().startActivity(intent,options.toBundle());
    }

    /**
     * Called when by the presenter when an item has been added or removed from the cart to
     * apply appropriate UI changes.
     * @param view ImageButton that was clicked
     * @param n Supplied by presenter, n=0 product added to, cart n=1 product removed from cart.
     */
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
    public void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);
        outstate.putSerializable("cart",mPresenter.getCart());
    }

    @Override
    public void onResume(){
        super.onResume();
        // every time the activity/fragment/View resumes from the backstack
        // just start the Presenter.
        mPresenter.start();
    }

    @Override
    public boolean onClose() {
       return false;
    }


    /**
     * Helper method for showing the user's spoken query in the
     * search view.
     * @param query what the user said
     */
    public void passQuery(String query){
        mSearchView.setQuery(query,false);
        mPresenter.loadProducts(query);
    }

    @Override
    public void showProducts(List<Product> products) {
        mAdapter.replaceData(products);
    }

    /**
     * Called when the FAB is clicked for the search action button is clicked
     * @param v view that was clicked
     */
    @Override
    public void onClick(View v) {

            MenuItemCompat.expandActionView(mSearchmenu); // need this for when the FAB is clicked

            mSearchView.setPressed(true);

            mSearchView.setScaleX(0.0f); // first set the scale to 0.0
            mSearchView.animate().scaleX(1.0f)
                    .setDuration(500)
                    .setInterpolator(AnimationUtils.loadInterpolator(getActivity(),android.R.anim.linear_interpolator))
                    .start();

    }

    private class ProductListAdapter extends RecyclerView.Adapter<ViewHolder>{


        private List<Product> mProducts;

        // Required for the ViewHolder animations.
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
            if(position>lastPosition){
                holder.itemView.
                        startAnimation(AnimationUtils.loadAnimation(getActivity(),android.R.anim.slide_in_left));
            lastPosition=position;
            }
        }
         @Override
        public int getItemCount() {
           return mProducts.size();
        }

        /**
         * Called by the presenter when new search results are available.
         * @param products
         */
        public void replaceData(List<Product> products){
            mProducts = products;
            notifyDataSetChanged();
        }
    }

    private  class ViewHolder extends RecyclerView.ViewHolder{

        // Each view holder holds a reference to ListItemBinding
        // so that it can be used later while binding the list item to the
        // recycler view in onBindViewHolder
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

    /** An adapter method for data binding library which will call this to load the
     *  image.
     * @param networkImageView Volley image view for showing network downloaded images
     * @param url link to download from
     */

    @BindingAdapter("imageUrl")
    public static void loadImage(NetworkImageView networkImageView,String url){
        ImageLoader imgLoader = NetworkImageLoaderHelper.getInstance(networkImageView.getContext()).getMbitMapLoader();
        networkImageView.setDefaultImageResId(R.drawable.ic_perm_media_black);
        networkImageView.setImageUrl(url,imgLoader);
    }

}
