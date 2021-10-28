package com.cml.spms.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cml.spms.dbhelper.AssetOpenHelper;

public class AssetProvider extends ContentProvider {
    public static final String AUTHORITIES = AssetProvider.class.getCanonicalName();//得到完整路径
    public static UriMatcher uriMatcher;
    public static Uri URI_ASSETS= Uri.parse("content://"+AUTHORITIES+"/assets");
    private static final int MATCH_CODE = 100;

    AssetOpenHelper assetOpenHelper = null;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITIES,"/assets",MATCH_CODE);
    }

    @Override
    public boolean onCreate() {
        assetOpenHelper = new AssetOpenHelper(this.getContext());
        Log.i("Provider","onCreate");
        return assetOpenHelper !=null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if(uriMatcher.match(uri)== MATCH_CODE){
            SQLiteDatabase db = assetOpenHelper.getWritableDatabase();
            long id = db.insert(AssetOpenHelper.T_ASSETS,"",values);
            if(id!=-1){
                uri = ContentUris.withAppendedId(uri,id);
                getContext().getContentResolver().notifyChange(AssetProvider.URI_ASSETS,null);
            }
        }
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleteCount = 0;
        if(uriMatcher.match(uri)== MATCH_CODE){
            SQLiteDatabase db = assetOpenHelper.getWritableDatabase();
            deleteCount = db.delete(AssetOpenHelper.T_ASSETS,selection,selectionArgs);
            getContext().getContentResolver().notifyChange(AssetProvider.URI_ASSETS,null);
        }
        return deleteCount;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int updateCount = 0;
        if(uriMatcher.match(uri)== MATCH_CODE){
            SQLiteDatabase db = assetOpenHelper.getWritableDatabase();
            updateCount = db.update(AssetOpenHelper.T_ASSETS,values,selection,selectionArgs);
            getContext().getContentResolver().notifyChange(AssetProvider.URI_ASSETS,null);
        }
        return updateCount;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        if(uriMatcher.match(uri)== MATCH_CODE){
            SQLiteDatabase db = assetOpenHelper.getReadableDatabase();
            cursor = db.query(AssetOpenHelper.T_ASSETS,projection,selection,selectionArgs,null,null,sortOrder);
        }
        return cursor;
    }
}
