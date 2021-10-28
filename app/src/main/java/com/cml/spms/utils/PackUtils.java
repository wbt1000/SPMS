package com.cml.spms.utils;

import android.content.ContentValues;
import android.database.Cursor;

import com.cml.spms.dbhelper.AssetOpenHelper;
import com.cml.spms.domain.Asset;

public class PackUtils {
    public static ContentValues getContentValuesByAsset(Asset asset){
        ContentValues contentValues = new ContentValues();

        contentValues.put(AssetOpenHelper.AssetTable.INDEX,asset.getIndex());
        contentValues.put(AssetOpenHelper.AssetTable.NAME,asset.getName());
        contentValues.put(AssetOpenHelper.AssetTable.TYPE,asset.getType());
        contentValues.put(AssetOpenHelper.AssetTable.IMAGE,asset.getImage());
        contentValues.put(AssetOpenHelper.AssetTable.PRICE,asset.getPrice());
        contentValues.put(AssetOpenHelper.AssetTable.COUNT,asset.getCount());
        contentValues.put(AssetOpenHelper.AssetTable.CATEGORY,asset.getCategory());

        return contentValues;
    }

    public static Asset getAssetByCursor(Cursor cursor){
        Asset asset = new Asset();
        asset.setIndex(cursor.getInt(cursor.getColumnIndex(AssetOpenHelper.AssetTable.INDEX)));
        asset.setName(cursor.getString(cursor.getColumnIndex(AssetOpenHelper.AssetTable.NAME)));
        asset.setType(cursor.getString(cursor.getColumnIndex(AssetOpenHelper.AssetTable.TYPE)));
        asset.setCategory(cursor.getString(cursor.getColumnIndex(AssetOpenHelper.AssetTable.CATEGORY)));
        asset.setImage(cursor.getString(cursor.getColumnIndex(AssetOpenHelper.AssetTable.IMAGE)));
        asset.setCount(cursor.getInt(cursor.getColumnIndex(AssetOpenHelper.AssetTable.COUNT)));
        asset.setPrice(cursor.getString(cursor.getColumnIndex(AssetOpenHelper.AssetTable.PRICE)));
        return asset;
    }
}
