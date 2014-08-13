package ca.nemriovD.dans.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/*******************************
 * @author 100423722
 * Used to create the database
 * and helps get the database in
 * the content provider
 */
public class SQLHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "notes.db";
	private static final int DB_VERNO = 1;

	public SQLHelper(Context context) {
		super(context, DB_NAME, null, DB_VERNO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		NoteTable.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		NoteTable.onUpgrade(db, oldVersion, newVersion);
	}
}