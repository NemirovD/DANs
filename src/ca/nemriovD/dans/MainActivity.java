package ca.nemriovD.dans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ca.nemriovD.dans.contentprovider.DANsConP;
import ca.nemriovD.dans.database.NoteTable;
import ca.nemriovD.dans.database.SQLHelper;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.graphics.drawable.ScaleDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;


/***********************
 * 
 * @author David Nemirovsky
 * This activity is the home activity that is shown at startup
 * it will list all notes in the database and if a list item
 * is clicked it will take you to the corresponding note.
 * If the plus sign is clicked a the add note dialog will
 * appear.
 */
public class MainActivity extends Activity {
	ArrayList<Note> notes;
	ArrayList<Map<String, String>> data;
	ListView listView;
	SimpleAdapter ad;
	boolean opened = false;
	public final static String ID = "_ID";

	private OnItemClickListener noteSelected = new OnItemClickListener() {
		public void onItemClick(AdapterView parent, View v, int position,
				long id) {
			showNote(position);
		}
	};
	
	/*************
	 * Sends relevant data to the DiplayNote class
	 * So that it can display the right note
	 * @param position
	 */
	public void showNote(int position) {
		Intent intent = new Intent(this, DiplayNote.class);
		intent.putExtra(ID, notes.get(position).getID());
		startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		opened = true;

		notes = getListFromDB();

		data = new ArrayList<Map<String, String>>();
		data = convertForList(notes,data);
		
		ad = new SimpleAdapter(this, data,
                R.layout.list_item_layout,
                new String[] {"title", "date"},
                new int[] {android.R.id.text1,
                           android.R.id.text2});

		listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(ad);
		listView.setOnItemClickListener(noteSelected);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@SuppressLint("NewApi")
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.add_item:
			AddItemDialog ad = new AddItemDialog();
			ad.isMain = true;
			ad.show(this.getFragmentManager(), "");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	/************
	 * Function created to increase readability
	 * @param column
	 * @param cursor
	 * @return String that's in database
	 */

	private String grabCol(String col, Cursor c) {
		return c.getString(c.getColumnIndex(col));
	}
	
	
	/***********
	 * Made to grab all Notes from the database
	 * @return ArrayList of Notes
	 */
	private ArrayList<Note> getListFromDB() {
		String[] projection = { NoteTable.COL_ID, NoteTable.COL_TITLE,
				NoteTable.COL_DATE, NoteTable.COL_CONTENT };
		Uri uri = DANsConP.CONTENT_URI;
		String sel = null;
		String[] selArgs = null;
		String sortOrder = null;

		ArrayList<Note> notes = new ArrayList<Note>();
		Cursor c = getContentResolver().query(uri, projection, sel, selArgs,
				sortOrder);
		for (c.moveToFirst(); c.getPosition() < c.getCount(); c.moveToNext()) {
			String istr = grabCol(NoteTable.COL_ID, c);
			String tstr = grabCol(NoteTable.COL_TITLE, c);
			String dstr = grabCol(NoteTable.COL_DATE, c);
			String cstr = grabCol(NoteTable.COL_CONTENT, c);
			notes.add(new Note(tstr, dstr, cstr, istr));
		}
		return notes;
	}
	
	
	/***************
	 * updates the listview upon return from a
	 * dialog or another activity
	 */
	@SuppressLint("NewApi")
	protected void updateList(){
		data.clear();
		notes.clear();
		notes = getListFromDB();
		ad.notifyDataSetChanged();
		data = convertForList(notes,data);
		ad = new SimpleAdapter(this, data,
                R.layout.list_item_layout,
                new String[] {"title", "date"},
                new int[] {android.R.id.text1,
                           android.R.id.text2});
		ad.notifyDataSetChanged();
	}
	
	/***************
	 * Sets up the note data so that the date and the title can be shown in the listview
	 * @param notes
	 * @param data
	 * @return data for the listview
	 */
	private ArrayList<Map<String, String>> convertForList(ArrayList<Note> notes, ArrayList<Map<String, String>> data){
		for (int i = 0; i < notes.size(); ++i) {
		    Map<String, String> datum = new HashMap<String, String>(2);
		    datum.put("title", notes.get(i).getTitle());
		    datum.put("date", notes.get(i).getCreated());
		    data.add(datum);
		}
		return data;
	}
	
	
	/*******************************************************************
	 * created so that list will update after coming back from backstack
	 */
	@Override
	protected void onResume(){
		super.onResume();
		if(opened){
			listView.setAdapter(ad);
			updateList();
		}
	}
}
