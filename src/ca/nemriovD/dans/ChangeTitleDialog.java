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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/********************************
 * 
 * @author David Nemirovsky
 * 
 * This class creates a popup that allows the user to
 * change the title of the note.
 * if the title is null the dialog will do nothing
 */
public class ChangeTitleDialog extends DialogFragment {
	private LayoutInflater inflater;
	private View view;

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		inflater = getActivity().getLayoutInflater();
		view = inflater.inflate(R.layout.fragment_add_item, null);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setView(view);
		builder.setMessage("Would you like to change the title?");
		builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				EditText et = (EditText) view.findViewById(R.id.AddItemET);
				
				String title = et.getText().toString();
				String[] words = title.split(" ");
				if (words.length==0 || title.equals("") || title.contains("\n")) {
					Toast toast = Toast.makeText(getActivity(), "Cannot have a title without text or newline characters", Toast.LENGTH_LONG);
					toast.show();
					return;
				} else {
					((DiplayNote) getActivity()).title = title;
					((DiplayNote) getActivity()).setTitle(title);
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
