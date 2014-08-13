package ca.nemriovD.dans.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
/*****************************************************************
 * @author 100423722
 * Useless class that I could have put into SQLHelper, created this
 * because of online tutorial code found at when I was having issues
 * with my content provider, turns out the source of my problems were
 * in my parameters when calling the function
 * http://www.vogella.com/articles/AndroidSQLite/article.html
 */
public class NoteTable {
	public static final String NOTES_TABLE = "Notes";
	public static final String COL_ID = "_ID";
	public static final String COL_TITLE = "Title";
	public static final String COL_DATE = "Date";
	public static final String COL_CONTENT = "Content";

	private static final String DB_CREATE = "create table " + NOTES_TABLE + 
			"( " + 
			COL_ID + " integer primary key autoincrement, " + 
			COL_TITLE + " text not null, " + 
			COL_DATE + " text, " + 
			COL_CONTENT + " text " +
			");";

	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(DB_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		database.execSQL("DROP TABLE IF EXISTS " + NOTES_TABLE);
		onCreate(database);
	}

}
