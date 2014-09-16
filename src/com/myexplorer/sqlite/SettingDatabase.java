package com.myexplorer.sqlite;

import com.myexplorer.lib.DatabaseInfo;

import android.content.Context;
import android.database.Cursor;

public class SettingDatabase {
	
	private Context mContext;
	
	private final String TABLE_NAME = "History"; 
	private String sql;
	
	// 建表语句，每次都会使用
	private String createSql = "create table " + TABLE_NAME
			+ " (webname varchar(100), website varchar(100))";
	
	public SettingDatabase(Context mContext) {
		this.mContext = mContext;
	}
	
	// 读取本地数据库的信息
	public void read() {
		DatabaseOperation db = new DatabaseOperation(mContext);
		sql = createSql;
		db.open(DatabaseInfo.DATABASE_NAME, sql);
		
		sql = "select * from " + TABLE_NAME;
		Cursor cursor = db.find(sql);
		
		while (cursor.moveToNext()) {
//			UserInfo.email = cursor.getString(cursor.getColumnIndex("email"));
//			UserInfo.userName = cursor.getString(cursor.getColumnIndex("name"));
		}
		
		db.close();
	}
	
	// 将数据放入本地数据库
	public void write() {
		DatabaseOperation db = new DatabaseOperation(mContext);
		sql = createSql;
		db.open(DatabaseInfo.DATABASE_NAME, sql);
		
		sql = "delete from " + TABLE_NAME;
		db.delete(sql);
		
		// 插入数据库中
		sql = "insert into " + TABLE_NAME;
		sql += " " + "(email, name) values ";
//		sql += "('" + UserInfo.email + "',";
//		sql += " '" + UserInfo.userName + "')";
		db.insert(sql);
		
		db.close();
	}
	
	// 删除数据库
	public void delete() {
		DatabaseOperation db = new DatabaseOperation(mContext);
		sql = createSql;
		db.open(DatabaseInfo.DATABASE_NAME, sql);
		
		sql = "delete from " + TABLE_NAME;
		db.delete(sql);
		
		db.close();
	}
	
}
