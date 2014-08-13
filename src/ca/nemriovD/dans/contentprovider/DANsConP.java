package ca.nemriovD.dans.contentprovider;

import ca.nemriovD.dans.database.SQLHelper;
import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/******************************
 * @author David Nemirovsky
 * Content provider class
 * mostly just calls the database
 * All functions are just overrides
 */

public class DANsConP extends ContentProvider {
	SQLHelper db;
	SQLiteDatabase dbwrite;

	private static final String AUTHORITY = "ca.nemirovD.dans.contentprovider";
	private static final String BASE_PATH = "notes";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH);

	// TODO ADD NOTIFIES
	@Override
	public boolean onCreate() {
		db = new SQLHelper(getContext());
		dbwrite = db.getWritableDatabase();
		return false;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase dbwrite = db.getWritableDatabase();
		int del = dbwrite.delete(uri.getLastPathSegment(), selection,
				selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return del;
	}

	@Override
	public String getType(Uri arg0) {
		// not needed didn't use
		// don't even understand what the return is supposed to be
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase dbwrite = db.getWritableDatabase();
		long index = dbwrite.insert(uri.getLastPathSegment(), null, values);

		getContext().getContentResolver().notifyChange(uri, null);

		return Uri.parse(uri.getPath() + "/" + index);
	}

	@SuppressLint("NewApi")
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		dbwrite = db.getWritableDatabase();
		SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();

		// Set the table we're querying.
		qBuilder.setTables(uri.getLastPathSegment());
		Cursor c = qBuilder.query(dbwrite, projection, selection,
				selectionArgs, null, null, sortOrder);

		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase dbwrite = db.getWritableDatabase();
		int up = dbwrite.update(uri.getLastPathSegment(), values, selection,
				selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return up;
	}
}
