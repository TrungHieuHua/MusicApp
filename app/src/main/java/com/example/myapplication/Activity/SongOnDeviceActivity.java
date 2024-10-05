package com.example.myapplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Adapter.SongOnDeviceAdapter;
import com.example.myapplication.Model.Favorite;
import com.example.myapplication.Model.Song;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SongOnDeviceActivity extends AppCompatActivity implements SongOnDeviceAdapter.IOnDelete {
    //    ActivityResultLauncher<Intent> activityResultLauncher;
//    String[] permission = {READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE};
    Context context;
    String[] array = new String[]{};
    ArrayList<String> arrayList;
    private ListView listView;
    private SongOnDeviceAdapter songOnDeviceAdapter;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private List<Favorite> listFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_on_device);
        listView = findViewById(R.id.lvFavorite);
        context = getApplicationContext();
        arrayList = new ArrayList<>(Arrays.asList(array));
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("favorite");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                listFavorite = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                  try {
                      Favorite favorite = ds.getValue(Favorite.class);
                      FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                      if (user == null || favorite == null) return;
                      if (favorite.getUid().equals(user.getUid())) {
                          listFavorite.add(favorite);
                      }
                  }catch (Exception e){
                      e.printStackTrace();
                  }
                }

                songOnDeviceAdapter = new SongOnDeviceAdapter(SongOnDeviceActivity.this, android.R.layout.simple_list_item_1, listFavorite, SongOnDeviceActivity.this);
                listView.setAdapter(songOnDeviceAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            ArrayList<Song> listSong = new ArrayList<>();
            for (Favorite favorite : listFavorite) {
                listSong.add(favorite.getSong());
            }
            Intent intent = new Intent(SongOnDeviceActivity.this, PlayMusicActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("songs", listSong);
            bundle.putInt("index", i);
            intent.putExtra("bundle", bundle);
            startActivity(intent);
        });
    }

    @Override
    public void delete(Favorite favorite) {
        databaseReference.child(favorite.getId()).removeValue((error, ref)
                -> Toast.makeText(context, "Delete done!", Toast.LENGTH_SHORT).show());
    }
}