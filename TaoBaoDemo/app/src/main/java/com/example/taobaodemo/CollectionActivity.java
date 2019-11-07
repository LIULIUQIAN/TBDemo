package com.example.taobaodemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.taobaodemo.adapter.AddressAdapter;
import com.example.taobaodemo.adapter.CollectionAdapter;
import com.example.taobaodemo.bean.Address;
import com.example.taobaodemo.bean.hot.Wares;
import com.example.taobaodemo.utils.CollectionData;
import com.example.taobaodemo.widget.CnToolbar;

import java.util.List;

public class CollectionActivity extends AppCompatActivity {

    private CnToolbar toolbar;
    private RecyclerView recyclerView;
    private CollectionAdapter adapter;
    private CollectionData collectionData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        collectionData = new CollectionData(this);
        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycler_view);

        adapter = new CollectionAdapter(this, collectionData.getAll());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        adapter.setCollectionLisneter(new CollectionAdapter.CollectionLisneter() {
            @Override
            public void cancelCollection(Wares wares) {

                collectionData.delete(wares);
                Toast.makeText(CollectionActivity.this,"已取消收藏",Toast.LENGTH_SHORT).show();

                adapter.clear();
                adapter.addData(collectionData.getAll());

            }
        });

    }
}
