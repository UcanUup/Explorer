package com.myexplorer.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	//数据库的版本
	private static int VERSION = 1;
	
	//创建表时的语句
	private static String createSql;
	
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	public DatabaseHelper(Context context,String name,int version){
		this(context,name,null,version);
	}
	
	public DatabaseHelper(Context context,String name, String sql){
		this(context,name,VERSION);
		createSql = sql;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(createSql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
}
