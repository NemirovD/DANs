package ca.nemriovD.dans;

import java.text.DateFormat;
import java.util.Date;
/*******************************
 * @author 100423722
 * Note class that's not really
 * neccesary anymore but is kept
 * so that I do not have to completely
 * recreate the app.
 * mostly stores content and gets the
 * current time for use in the database
 */
public class Note {

	public String title;
	public String created;
	public String content;
	public String ID;
	
	static DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, 
			 DateFormat.SHORT); 
	
	public Note(String title, String content){
		this.title = title;
		this.created = String.valueOf(df.format(new Date()));
		this.content = content;
		this.ID = null;
	}
	
	public Note(String title, String created, String content, String ID){
		this.title = title;
		this.created = created;
		this.content = content;
		this.ID = ID;
	}
	
	public String toString(){
		return title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}
	
	public String getID() {
		return ID;
	}
	
	public void setID(String ID) {
		this.ID = ID;
	}
}
