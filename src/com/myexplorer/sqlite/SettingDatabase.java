package com.myexplorer.sqlite;

import com.myexplorer.lib.DatabaseInfo;

import android.content.Context;
import android.database.Cursor;

public class SettingDatabase {
	
	private Context mContext;
	
	private final String TABLE_NAME = "History"; 
	private String sql;
	
	// ������䣬ÿ�ζ���ʹ��
	private String createSql = "create table " + TABLE_NAME
			+ " (webname varchar(100), website varchar(100))";
	
	public SettingDatabase(Context mContext) {
		this.mContext = mContext;
	}
	
	// ��ȡ�������ݿ����Ϣ
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
	
	// �����ݷ��뱾�����ݿ�
	public void write() {
		DatabaseOperation db = new DatabaseOperation(mContext);
		sql = createSql;
		db.open(DatabaseInfo.DATABASE_NAME, sql);
		
		sql = "delete from " + TABLE_NAME;
		db.delete(sql);
		
		// �������ݿ���
		sql = "insert into " + TABLE_NAME;
		sql += " " + "(email, name) values ";
//		sql += "('" + UserInfo.email + "',";
//		sql += " '" + UserInfo.userName + "')";
		db.insert(sql);
		
		db.close();
	}
	
	// ɾ�����ݿ�
	public void delete() {
		DatabaseOperation db = new DatabaseOperation(mContext);
		sql = createSql;
		db.open(DatabaseInfo.DATABASE_NAME, sql);
		
		sql = "delete from " + TABLE_NAME;
		db.delete(sql);
		
		db.close();
	}
	
}
