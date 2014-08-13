package ca.nemriovD.dans;

import ca.nemriovD.dans.contentprovider.DANsConP;
import ca.nemriovD.dans.database.NoteTable;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

/*************************
 * @author David Nemirovsky Class used to display the notes
 */
public class DiplayNote extends Activity {
	Note[] notes;
	String title;
	String content;
	String ID;
	Uri index;
	boolean deleted;

	String[] projection = { NoteTable.COL_ID, NoteTable.COL_TITLE,
			NoteTable.COL_DATE, NoteTable.COL_CONTENT };
	Uri uri = DANsConP.CONTENT_URI;
	String sel;
	String[] selArgs = null;
	String sortOrder = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diplay_note);
		setupActionBar();
		deleted = false;

		Intent intent = getIntent();
		ID = intent.getStringExtra(MainActivity.ID);
		sel = "_ID=" + ID;

		Cursor c = getContentResolver().query(uri, projection, sel, selArgs,
				sortOrder);
		c.moveToFirst();
		title = grabCol(NoteTable.COL_TITLE, c);
		content = grabCol(NoteTable.COL_CONTENT, c);
		setTitle(title);

		EditText et = (EditText) findViewById(R.id.noteDisplay);

		et.setText(content);
	}

	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setHomeButtonEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.diplay_note, menu);
		return true;
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// When home button is clicked dialog pops up that allows the user
			// to change the title
			(new ChangeTitleDialog()).show(this.getFragmentManager(), "");
			return true;
		case R.id.add:
			AddItemDialog ad = new AddItemDialog();
			ad.isMain = false;
			ad.show(this.getFragmentManager(), "");
			return true;
		case R.id.remove_item:
			(new RemoveItemDialog()).show(this.getFragmentManager(), "");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private String grabCol(String col, Cursor c) {
		return c.getString(c.getColumnIndex(col));
	}

	/*****************************
	 * Forces the note to be saved whenever the Activity leaves the screen
	 */
	@Override
	protected void onPause() {
		super.onPause();
		if (deleted) {
			Toast toast = Toast.makeText(this, "Note Deleted", Toast.LENGTH_LONG);
			toast.show();
		} else {
			Toast toast = Toast.makeText(this, "Note Saved", Toast.LENGTH_LONG);
			toast.show();
		}
		saveNote();
	}

	/****************************
	 * Function that saves the note to our database
	 */
	private void saveNote() {
		content = ((EditText) findViewById(R.id.noteDisplay)).getText()
				.toString();
		ContentValues values = new ContentValues();
		values.put(NoteTable.COL_TITLE, title);
		values.put(NoteTable.COL_CONTENT, content);
		String where = NoteTable.COL_ID + "=" + ID;
		getContentResolver().update(DANsConP.CONTENT_URI, values, where, null);
	}

	/*************************************
	 * Function that deletes the currently viewed note
	 */
	protected void deleteNote() {
		String where = NoteTable.COL_ID + "=" + ID;
		getContentResolver().delete(DANsConP.CONTENT_URI, where, null);
		deleted = true;
		Intent in = new Intent(this, MainActivity.class);
		NavUtils.navigateUpTo(this, in);
	}

}
