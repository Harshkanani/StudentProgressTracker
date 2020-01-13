package com.spt.studentprogresstracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="SPT";
    public static final String TABLE_NAME="StudentSubject";
    public static final String TABLE_NAME1="UserInfo";

    public DatabaseHelper(Context context)
    {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " (subcode TEXT,subname TEXT)");
        sqLiteDatabase.execSQL("create table " + TABLE_NAME1 + " (s_enrollment TEXT,s_name TEXT,s_type TEXT,s_gender TEXT,s_email_id TEXT,s_phone_no TEXT,s_batch TEXT,s_sem TEXT,s_photo TEXT,c_id TEXT,c_name TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME + "'");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME1 + "'");

        onCreate(sqLiteDatabase);
    }

    public void addStudentSubject(String[] subcode,String[] subname)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        for(int i=0;i<subcode.length;i++) {
            cv.put("subcode", subcode[i]);
            cv.put("subname", subname[i]);
            db.insert(TABLE_NAME, null, cv);
        }
    }

    public void addUserInfo(String s_enrollment,String s_name,String s_type,String s_gender,String s_email_id,String s_phone_no,String s_batch,String s_sem,String s_photo,String cid,String c_name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("s_enrollment",s_enrollment);
        cv.put("s_name",s_name);
        cv.put("s_type",s_type);
        cv.put("s_gender",s_gender);
        cv.put("s_email_id",s_email_id);
        cv.put("s_phone_no",s_phone_no);
        cv.put("s_batch",s_batch);
        cv.put("s_sem",s_sem);
        cv.put("s_photo",s_photo);
        cv.put("c_id",cid);
        cv.put("c_name",c_name);
        db.insert(TABLE_NAME1,null,cv);
    }

    public Cursor getStudentSubject()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String query="select * from "+TABLE_NAME;
        Cursor data=db.rawQuery(query,null);
        return data;
    }

    public Cursor getUserInfo()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor data=db.rawQuery("select * from "+TABLE_NAME1,null);
        return data;
    }

    public void deleteStudentSubject()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME);
    }

    public void deleteUserInfo()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME1);
    }
}
