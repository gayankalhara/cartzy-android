package cartzy.iflexicon.com.cartzy.Adapters;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

import cartzy.iflexicon.com.cartzy.MainActivity;
import cartzy.iflexicon.com.cartzy.Models.Shops;
import cartzy.iflexicon.com.cartzy.R;
import cartzy.iflexicon.com.cartzy.fragments.MyListsFragment;
import cartzy.iflexicon.com.cartzy.fragments.ShopFragment;

/**
 * Created by Udesh on 10/28/2016.
 */

public class ShopsAdapter extends RecyclerView.Adapter<ShopsAdapter.MyViewHolder>{

    private Context context;
    public List<Shops> shopsList;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView shop_name;
        public TextView shop_vicinity;
        public ImageView shop_icon;


        public MyViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            shop_name = (TextView) v.findViewById(R.id.shop_name);
            shop_vicinity = (TextView) v.findViewById(R.id.shop_vicinity);
            shop_icon = (ImageView) v.findViewById(R.id.shop_icon);

        }

        @Override
        public void onClick(View view) {
            //Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();

        }
    }

        public ShopsAdapter(Context context,List<Shops> shopsList) {
            this.context = context;
            this.shopsList = shopsList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.shops_list_row,parent,false);
            return new MyViewHolder(itemview);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            Shops shop = shopsList.get(position);
            holder.shop_name.setText(shop.getName());
            //if(!shop.getVicinity().equals(null))
            holder.shop_vicinity.setText(shop.getVicinity());
           // holder.shop_icon.setText(shop.getIcon());
            Picasso.with(context).load(shop.getIcon()).into(holder.shop_icon);



        }

        @Override
        public int getItemCount() {
            return shopsList.size();
        }

    public void removeAt(int position) {
        this.shopsList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
}
