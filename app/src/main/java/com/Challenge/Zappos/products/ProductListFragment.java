package com.Challenge.Zappos.products;

import android.content.Context;
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

import com.Challenge.Zappos.R;
import com.Challenge.Zappos.data.Product;
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

    private ArrayList<Product> mProducts;

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
        View root = inflater.inflate(R.layout.product_list_fragment,container,false);
        mAdapter = new ProductListAdapter(getActivity(),new ArrayList<Product>());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mrecylerView = (RecyclerView) root.findViewById(R.id.recylerView);
        mrecylerView.setAdapter(mAdapter);
        mrecylerView.setLayoutManager(linearLayoutManager);

        setHasOptionsMenu(true);

        Log.e(TAG,"onCreateView  Called !!");
        return root;
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
            View view = layoutInflater.inflate(R.layout.product_list_item,parent,false);

            final ViewHolder vh = new ViewHolder(view);

            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mTextView.setText(mProducts.get(position).toString());
            holder.mThumbnail.setImageUrl(mProducts.get(position).getThumbnailImageUrl(),
                    NetworkImageLoaderHelper.getInstance(ProductListFragment.this.getActivity()).getMbitMapLoader());
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


    private static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTextView;
        public NetworkImageView mThumbnail;
        public ViewHolder(View view) {
            super(view);
            mThumbnail=(NetworkImageView)view.findViewById(R.id.thumbnail);
            mTextView = (TextView)view.findViewById(R.id.list_item_text);
        }
    }
}
