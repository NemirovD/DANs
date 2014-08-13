package ca.nemriovD.dans;

import ca.nemriovD.dans.contentprovider.DANsConP;
import ca.nemriovD.dans.database.NoteTable;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/********************************
 * 
 * @author David Nemirovsky
 * 
 *         This class creates a popup that allows the user to create a new note
 *         and set it have a certain title if the title is null the dialog will
 *         do nothing
 */
public class AddItemDialog extends DialogFragment {
	protected boolean isMain = false;
	private LayoutInflater inflater;
	private View view;

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		inflater = getActivity().getLayoutInflater();
		view = inflater.inflate(R.layout.fragment_add_item, null);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setView(view);
		builder.setMessage("Would you like Add a New Note?");
		builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				EditText et = (EditText) view.findViewById(R.id.AddItemET);

				String title = et.getText().toString();
				String[] words = title.split(" ");
				if (words.length == 0 || title.equals("") || title.contains("\n")) {
					Toast toast = Toast.makeText(getActivity(),
							"Cannot have a title without text or newline characters",
							Toast.LENGTH_LONG);
					toast.show();
					return;
				} else {
					Note nat = new Note(title, "");

					ContentValues values = new ContentValues();
					values.put(NoteTable.COL_TITLE, nat.getTitle());
					values.put(NoteTable.COL_DATE, nat.getCreated());
					values.put(NoteTable.COL_CONTENT, nat.getContent());
					Uri index = getActivity().getContentResolver().insert(
							DANsConP.CONTENT_URI, values);
					if (isMain) {
						((MainActivity) getActivity()).updateList();
					} else {
						Intent intent = new Intent(getActivity(),
								DiplayNote.class);
						intent.putExtra(MainActivity.ID,
								index.getLastPathSegment());
						startActivity(intent);
						getActivity().finish();
					}
				}
			}
		});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// User cancelled the dialog
					}
				});
		// Create the AlertDialog object and return it
		return builder.create();
	}

}
