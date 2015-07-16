package pl.droidsonroids.bootcamp.yo.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import pl.droidsonroids.bootcamp.yo.Constants;
import pl.droidsonroids.bootcamp.yo.api.ApiService;
import pl.droidsonroids.bootcamp.yo.model.User;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class UserListAdapter extends RecyclerView.Adapter<UserItemViewVolder> {

    private List<User> mUserList = Collections.emptyList();
    private SharedPreferences mSharedPreferences;
    private String mSenderName;

    @Override
    public UserItemViewVolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_list_item_1, viewGroup, false);
//        mSharedPreferences = view.getContext().getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        return new UserItemViewVolder(view);
    }

    @Override
    public void onBindViewHolder(UserItemViewVolder userItemViewVolder, final int i) {

        userItemViewVolder.bindData(mUserList.get(i), false);

        userItemViewVolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();
                final int selfId = mSharedPreferences.getInt(Constants.KEY_USER_ID, 0);
                if (selfId == 0) {
                    Toast.makeText(context, "registration not complete", Toast.LENGTH_SHORT).show();
                    return;
                }
                ApiService.API_SERVICE.postYo(mUserList.get(i).getId(), selfId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Toast.makeText(context, "message sent", Toast.LENGTH_SHORT).show();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public void refreshUserList(List<User> userList) {
        this.mUserList = userList;
        Collections.sort(userList);
        notifyDataSetChanged();
    }

}
