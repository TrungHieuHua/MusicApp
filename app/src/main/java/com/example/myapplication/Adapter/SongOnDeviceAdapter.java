package com.example.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.myapplication.Fragment.MusicFragment;
import com.example.myapplication.Model.Favorite;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class SongOnDeviceAdapter extends ArrayAdapter<Favorite>  {

    MusicFragment musicFragment;
    Activity activity;
    ViewHolder viewHolder;
    private IOnDelete listener;

    public interface IOnDelete {
        void  delete(Favorite favorite);
    }

    public SongOnDeviceAdapter(Activity activity, int resource, List<Favorite> objects, IOnDelete i) {
        super(activity, resource, objects);
        this.activity = activity;
        this.listener = i;
    }

    static class ViewHolder {
        TextView tvMusicListIndex, tvTenBaiHatMusicList, tvTenCaSiMusicList, txtOptionsMenu;
        ImageView imgTopsong;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_song, null);
            viewHolder = new ViewHolder();
            viewHolder.tvMusicListIndex = convertView.findViewById(R.id.tvMusicListIndex);
            viewHolder.tvTenBaiHatMusicList = convertView.findViewById(R.id.tvTenBaiHatMusicList);
            viewHolder.tvTenCaSiMusicList = convertView.findViewById(R.id.tvTenCaSiMusicList);
            viewHolder.imgTopsong = convertView.findViewById(R.id.imageViewtop);
            viewHolder.txtOptionsMenu = convertView.findViewById(R.id.txtOptionsMenu);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Favorite song = getItem(position);
        assert song != null;
        Glide.with(getContext()).load(song.getSong().getImage()).error(R.drawable.song).into(viewHolder.imgTopsong);

        viewHolder.txtOptionsMenu.setOnClickListener(view -> {
            //creating a popup menu
            PopupMenu popup = new PopupMenu(view.getContext(), view);
            //inflating menu from xml resource
            popup.inflate(R.menu.menu_favorite);
            popup.setGravity(Gravity.NO_GRAVITY);
            MenuItem menuRemovePlaylist = popup.getMenu().findItem(R.id.item_remove_from_playlist);
            //adding click listener
            popup.setOnMenuItemClickListener(menuItem1 -> {
                switch (menuItem1.getItemId()) {
                    case R.id.item_remove_from_playlist:
                        listener.delete(song);
                        return true;
                }
                return false;
            });
            //displaying the popup
            popup.show();
        });


        viewHolder.tvMusicListIndex.setText(String.valueOf(position + 1));
        viewHolder.tvTenBaiHatMusicList.setText(song.getSong().getName());
        viewHolder.tvTenCaSiMusicList.setText(song.getSong().getSinger());
        return convertView;
    }
}