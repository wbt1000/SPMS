package com.cml.spms.Provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cml.spms.dbhelper.AssetOpenHelper;
import com.cml.spms.domain.Asset;
import com.cml.spms.provider.AssetProvider;
import com.cml.spms.utils.PackUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AssetProviderTest {

    @Test
    public void TestDeleteDataBase(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        context.deleteDatabase("db_twms");
    }

    @Test
    public void TestInsert(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues contentValues = new ContentValues();
        Asset temp = new Asset();
        temp.setIndex(10);
        temp.setName("name");
        temp.setType("type");
        temp.setImage("hbc");
        temp.setPrice("55");
        temp.setCount(5);
        temp.setCategory("category");
        contentResolver.insert(AssetProvider.URI_ASSETS, PackUtils.getContentValuesByAsset(temp));
    }

    @Test
    public void TestQuery(){
        System.out.println("sb");
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(AssetProvider.URI_ASSETS,null,null,null,null);
        System.out.println(cursor!=null);
        if(cursor!=null) {
            System.out.println(cursor.getCount());
            while (cursor.moveToNext()){
                Asset asset = PackUtils.getAssetByCursor(cursor);
                System.out.println(asset.toString());
            }
        }
    }
}
