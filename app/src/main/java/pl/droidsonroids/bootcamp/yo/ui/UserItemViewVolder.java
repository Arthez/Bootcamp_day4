package pl.droidsonroids.bootcamp.yo.ui;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import pl.droidsonroids.bootcamp.yo.model.User;

public class UserItemViewVolder extends RecyclerView.ViewHolder {

    public UserItemViewVolder(View itemView) {
        super(itemView);
    }

    public void bindData(User user, boolean sender) {

        ((TextView) itemView).setText(user.getName());
        if(sender) {
            itemView.setBackgroundColor(Color.YELLOW);
        } else {
            itemView.setBackgroundColor(Color.WHITE);
        }
    }


}
