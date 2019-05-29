package cartzy.iflexicon.com.cartzy.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import cartzy.iflexicon.com.cartzy.Models.User;
import cartzy.iflexicon.com.cartzy.R;
import cartzy.iflexicon.com.cartzy.database.user_operations;
import cartzy.iflexicon.com.cartzy.util.CircleTransform;

/**
 * Created by HIS on 11/7/2016.
 */

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.MyViewHolder> {

    // for item click listener
    private ItemClick mItemClickListener;
    public List<User> usersList;
    public Context ctx;

    public interface ItemClick {
        void onItemClick(View view, User obj, int position);
    }
    public void setOnItemClickListener(final ItemClick mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView email;
        public ImageView photo,add,remove;
        public LinearLayout lyt_parent;


        public MyViewHolder(View v) {
            super(v);
            name = (TextView)v.findViewById(R.id.name);
            email = (TextView)v.findViewById(R.id.email);
            photo = (ImageView)v.findViewById(R.id.pfofile_image);
            lyt_parent = (LinearLayout) v.findViewById(R.id.lyt_parent);
            add = (ImageView)v.findViewById(R.id.add);
            remove = (ImageView)v.findViewById(R.id.remove);
        }

    }



    public NotificationsAdapter(Context ctx, List<User> usersList) {
        this.ctx= ctx;
        this.usersList = usersList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_friend_row,parent,false);
        return  new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        final User user = usersList.get(position);
        holder.name.setText(user.getUername());
        holder.email.setText(user.getEmail());

        if(user.getPhoto() != null) {
            Picasso.with(ctx).load(user.getPhoto())
                    .transform(new CircleTransform())
                    .into(holder.photo);
        }

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(view, user, position);
                    final User user = usersList.get(position);
                    Toast.makeText(ctx, user.getUername()+"Accepted as your friend", Toast.LENGTH_SHORT).show();
                    new user_operations().addToFriendList(user);
                }
               

            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, user, position);
                    final User user = usersList.get(position);
                    Toast.makeText(ctx, "Friend added"+user.getId()+""+user.getUername(), Toast.LENGTH_SHORT).show();
                    new user_operations().removeFromRequests(user);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }
}
