package cartzy.iflexicon.com.cartzy.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cartzy.iflexicon.com.cartzy.Models.User;
import cartzy.iflexicon.com.cartzy.R;
import cartzy.iflexicon.com.cartzy.database.user_operations;
import cartzy.iflexicon.com.cartzy.util.CircleTransform;
import cartzy.iflexicon.com.cartzy.util.FriendsFunctions;

/**
 * Created by HIS on 10/28/2016.
 */

public class SearchFriendsAdapter extends  RecyclerView.Adapter<SearchFriendsAdapter.MyViewHolder> implements Filterable {
    private List<User> original_items = new ArrayList<>();
    private List<User> filtered_items = new ArrayList<>();
    private ItemFilter mFilter = new ItemFilter();
    private Context ctx;

    // for item click listener
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, User obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView email;
        public ImageView photo;
        public MaterialRippleLayout lyt_parent;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            email = (TextView) itemView.findViewById(R.id.email);
            photo = (ImageView)itemView.findViewById(R.id.pfofile_image);
            lyt_parent = (MaterialRippleLayout) itemView.findViewById(R.id.lyt_parent);
        }
    }

    public Filter getFilter() {
        return mFilter;
    }

    public SearchFriendsAdapter(Context ctx, List<User> items) {
        this.ctx = ctx;
        this.original_items = items;
        this.filtered_items = items;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_row_friend, parent, false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        final User user = filtered_items.get(position);
        holder.name.setText(user.getUername());
        holder.email.setText(user.getEmail());
        /*holder.photo.setImageURI(user.getPhoto());*/
        //set image view
        if(user.getPhoto() != null) {
            Picasso.with(ctx).load(user.getPhoto())
                    .transform(new CircleTransform())
                    .into(holder.photo);
        }
        setAnimation(holder.lyt_parent, position);

        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, user, position);
                    final User user = filtered_items.get(position);

                    final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder.setMessage(R.string.dialog)
                            .setCancelable(true)
                            .setPositiveButton(R.string.send, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(@SuppressWarnings("unused")final DialogInterface dialogInterface,@SuppressWarnings("unused") int i) {
                                    Toast.makeText(ctx, "Friend request sent to"+user.getUername(), Toast.LENGTH_SHORT).show();
                                    new user_operations().sendRequset(user);
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(@SuppressWarnings("unused")final DialogInterface dialogInterface,@SuppressWarnings("unused") int i) {
                                  dialogInterface.cancel();
                                }
                            });
                    final AlertDialog alert = builder.create();
                    alert.show();


                }
               /* Intent i = new Intent(ctx, ActivityCategoryDetails.class);
                i.putExtra(ActivityCategoryDetails.EXTRA_OBJCT, c);
                ctx.startActivity(i);*/

            }
        });
    }

    //Here is the key method to apply the animation
    private int lastPosition = -1;

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(ctx, R.anim.slide_in_bottom);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
            return filtered_items.size();
        }

        private class ItemFilter extends Filter {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString().toLowerCase();
                FilterResults results = new FilterResults();
                final List<User> list = original_items;
                final List<User> result_list = new ArrayList<>(list.size());
                for (int i = 0; i < list.size(); i++) {
                    String str_name = list.get(i).getUername();
                    String str_email = list.get(i).getEmail();
                    if (str_name.toLowerCase().contains(query) || str_email.toLowerCase().contains(query)) {
                        result_list.add(list.get(i));
                    }
                }
                results.values = result_list;
                results.count = result_list.size();
                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filtered_items = (List<User>) results.values;
                notifyDataSetChanged();
            }
        }
    }

