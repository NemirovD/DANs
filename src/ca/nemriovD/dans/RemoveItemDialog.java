package ca.nemriovD.dans;

import ca.nemriovD.dans.contentprovider.DANsConP;
import ca.nemriovD.dans.database.NoteTable;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
/*************************
 * @author 100423722
 * Dialog that makes sure that the user
 * wants to remove the item from the list
 */
public class RemoveItemDialog extends DialogFragment {
	DiplayNote activity;
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		activity = (DiplayNote)getActivity();
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);

		builder.setMessage("Delete Note?")
				.setPositiveButton("Delete",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								((DiplayNote)getActivity()).deleteNote();
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// User cancelled the dialog
							}
						});
		// Create the AlertDialog object and return it
		return builder.create();
	}
}
