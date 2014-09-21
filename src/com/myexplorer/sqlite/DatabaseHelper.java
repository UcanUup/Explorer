package com.myexplorer.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	//数据库的版本
	private static int VERSION = 1;
	
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	public DatabaseHelper(Context context, String name, int version) {
		this(context, name, null, version);
	}
	
	public DatabaseHelper(Context context, String name) {
		this(context, name, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createSql;
		
		createSql = "create table History"
			+ " (id integer primary key, webname varchar(100), website varchar(100))";
		db.execSQL(createSql);
		
		createSql = "create table Favor"
				+ " (id integer primary key, webname varchar(100), website varchar(100))";
		db.execSQL(createSql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
}
