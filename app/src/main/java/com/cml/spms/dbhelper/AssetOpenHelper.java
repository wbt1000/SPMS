package com.cml.spms.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import androidx.annotation.Nullable;

public class AssetOpenHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    public static final String  db_name = "db_twms";//不应该在这里定义?吗？
    public static final String T_ASSETS = "t_assets";

    public static class AssetTable implements BaseColumns{
        public static final String INDEX = "assetID";
        public static final String NAME = "name";
        public static final String TYPE = "type";
        public static final String IMAGE = "image";
        public static final String COUNT = "count";
        public static final String PRICE = "price";
        public static final String CATEGORY = "category";
    }

    public AssetOpenHelper(@Nullable Context context) {//@Nullable?
        super(context, db_name, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + T_ASSETS + "(" +
                AssetTable._ID + " integer primary key autoincrement," +
                AssetTable.INDEX + " integer, " +
                AssetTable.NAME + " text, " +
                AssetTable.TYPE + " text, " +
                AssetTable.IMAGE + " text, " +
                AssetTable.PRICE + " text, "+
                AssetTable.COUNT + " integer, "+
                AssetTable.CATEGORY + " text" +
                ")";
        db.execSQL(sql);
        Log.i("DataBase","onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("DataBase","onUpgrade");
    }


}
