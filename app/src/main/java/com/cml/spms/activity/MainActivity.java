package com.cml.spms.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cml.spms.R;
import com.cml.spms.dbhelper.AssetOpenHelper;
import com.cml.spms.domain.Asset;
import com.cml.spms.provider.AssetProvider;
import com.cml.spms.utils.PackUtils;
import com.cml.spms.utils.ToastUtils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Asset> assets;
    private List<String> keywords;
    private List<String> matched_keywords;

    private SearchView searchView;

    private ListView searchList;
    private SearchListAdapter searchListAdapter;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private ImageButton btnDragNavigation;
    private ImageButton btnAddAsset;

    private RecyclerView assetList;
    private RecyclerViewAdapter assetListAdapter;


    private ContentResolver contentResolver = null;

    private MyContentObserver myContentObserver = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        initView();
        initData();
        initListener();

    }

    private void initView() {
        assetList = findViewById(R.id.asset_list);
        assetList.setLayoutManager(new LinearLayoutManager(this));

        searchView = findViewById(R.id.search_view);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("请输入关键词");

        searchList = findViewById(R.id.search_list);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        btnDragNavigation = findViewById(R.id.btn_drag_navigation);
        btnAddAsset = findViewById(R.id.btn_add_asset);

    }

    private void initData() {

        contentResolver = getContentResolver();

        assets = new ArrayList<Asset>();
        keywords = new ArrayList<String>();

        refreshDataSet();
        matched_keywords = new ArrayList<String>();

        assetListAdapter = new RecyclerViewAdapter(this);
        assetList.setAdapter(assetListAdapter);

        searchListAdapter = new SearchListAdapter(this);
        searchList.setAdapter(searchListAdapter);

    }

    private void initListener() {

        btnDragNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //todo: 这里肯定要改,改成跳转到一个专门显示搜索结果的fragment
                ToastUtils.showToastSafe(MainActivity.this,"queryText:"+query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                matched_keywords.clear();
                if(!newText.isEmpty()){
                    for(String str:keywords){
                        if(str.contains(newText)){
                            matched_keywords.add(str);
                        }
                    }
                }
                searchListAdapter.notifyDataSetChanged();
                return true;
            }
        });
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String string = searchListAdapter.getItem(position).toString();
                searchView.setQuery(string,true);
            }
        });

        myContentObserver = new MyContentObserver(new Handler());
        contentResolver.registerContentObserver(AssetProvider.URI_ASSETS,false,myContentObserver);
    }

    @Override
    protected void onDestroy() {
        contentResolver.unregisterContentObserver(myContentObserver);
        super.onDestroy();
    }

    /**
     * re-query the assets and notify adapter the change
     */
    private class MyContentObserver extends ContentObserver {
        public MyContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, @Nullable Uri uri) {
            if(uri!=null&&uri.equals(AssetProvider.URI_ASSETS)){
                refreshDataSet();
                assetListAdapter.notifyDataSetChanged();
                //todo: 暂时先这样，优化看警告
            }
            super.onChange(selfChange, uri);
        }
    }

    private class SearchListAdapter extends BaseAdapter{

        private LayoutInflater layoutInflater;

        public SearchListAdapter(Context context) {
            super();
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            int count = matched_keywords.size();
            if(count<=10){
                return count;
            }else{
                return 10;
            }
        }

        @Override
        public Object getItem(int position) {
            return matched_keywords.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView = layoutInflater.inflate(R.layout.search_list_item,parent,false);
            }
            TextView textView = convertView.findViewById(R.id.tv_search_list_item);
            textView.setText(matched_keywords.get(position));
            return convertView;
        }
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

        private LayoutInflater inflater;

        public RecyclerViewAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.asset_list_item,null);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//            holder.assetImageView.setImageURI(Uri.parse(assets.get(position).getImage()));
            holder.assetIndexView.setText(assets.get(position).getIndex().toString());
            holder.assetNameView.setText(assets.get(position).getName());

            holder.itemView.setTag(assets.get(position).getIndex());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jumpToAssetDetails((Integer) holder.itemView.getTag());
                }
            });
        }

        @Override
        public int getItemCount() {
            return assets.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            private ImageView assetImageView;
            private TextView assetIndexView;
            private TextView assetNameView;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                assetImageView = itemView.findViewById(R.id.asset_image);
                assetIndexView = itemView.findViewById(R.id.asset_index);
                assetNameView = itemView.findViewById(R.id.asset_name);
            }
        }
    }

    private void jumpToAssetDetails(Integer assetIndex){
        Intent intent = new Intent(MainActivity.this,AssetDetailsActivity.class);
        intent.putExtra("assetIndex",assetIndex);
        startActivity(intent);
    }

    private void refreshDataSet() {
        assets.clear();
        keywords.clear();
        Cursor cursor = contentResolver.query(AssetProvider.URI_ASSETS, null, null,null,null);
        if(cursor!=null){
            while (cursor.moveToNext()){
                Asset asset = PackUtils.getAssetByCursor(cursor);
                assets.add(asset);
                keywords.add(cursor.getString(cursor.getColumnIndex(AssetOpenHelper.AssetTable.NAME)));
            }
            cursor.close();
        }
    }
}