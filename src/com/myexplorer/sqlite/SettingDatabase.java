package com.myexplorer.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.myexplorer.lib.DatabaseInfo;
import com.myexplorer.lib.Variable;

public class SettingDatabase {
	
	private Context mContext;
	
	private final String TABLE_NAME = "Setting"; 
	private String sql;
	
	public SettingDatabase(Context mContext) {
		this.mContext = mContext;
	}
	
	// ��ȡ�������ݿ����Ϣ
	public void read() {
		DatabaseOperation db = new DatabaseOperation(mContext);
		db.open(DatabaseInfo.DATABASE_NAME);
		
		sql = "select * from " + TABLE_NAME;
		Cursor cursor = db.find(sql);
		
		Variable.originChecks = new boolean[Variable.settingNum];

		if (cursor.moveToNext()) {
			for (int i = 1; i <= Variable.settingNum; i++) {
				if (cursor.getInt(cursor.getColumnIndex("s" + i)) == 1)
					Variable.originChecks[i-1] = true;
				else
					Variable.originChecks[i-1] = false;
			}
		}
		else {
			for (int i = 1; i <= Variable.settingNum; i++) {
					Variable.originChecks[i-1] = false;
			}
		}
		
		db.close();
	}
	
	// �����ݷ��뱾�����ݿ�
	public void write(boolean[] checks) {
		DatabaseOperation db = new DatabaseOperation(mContext);
		db.open(DatabaseInfo.DATABASE_NAME);
		
		List<Integer> che = new ArrayList<Integer>();
		for (int i = 0; i < checks.length; i++) {
			if (checks[i])
				che.add(1);
			else
				che.add(0);
		}
		
		// �������ݿ���
		sql = "insert into " + TABLE_NAME;
		sql += " " + "(s1, s2, s3) values ";
		sql += "(" + che.get(0) + ",";
		sql += " " + che.get(1) + ",";
		sql += " " + che.get(2) + ")";
		db.insert(sql);		
		db.close();
	}	
	
	// ɾ��ȫ������
	public void delete() {
		DatabaseOperation db = new DatabaseOperation(mContext);
		db.open(DatabaseInfo.DATABASE_NAME);
		
		sql = "delete from " + TABLE_NAME;
		db.delete(sql);
		
		db.close();
	}
}
