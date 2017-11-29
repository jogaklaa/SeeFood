package com.ash.fish.seafood;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {


    private Context mCtx;
    private List<Product> productList;

    public ProductsAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.product_list, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        //loading the image
        Glide.with(mCtx)
                .load(product.getImageUrl())
                .into(holder.imageView);

        holder.textViewNegative.setText(String.valueOf(product.getNegative()));
        holder.textViewPositive.setText(String.valueOf(product.getPositive()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

//        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        TextView textViewNegative, textViewPositive;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            textViewNegative = itemView.findViewById(R.id.textViewNegative);
            textViewPositive = itemView.findViewById(R.id.textViewPositive);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}