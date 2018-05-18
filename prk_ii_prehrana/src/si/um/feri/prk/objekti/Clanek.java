package si.um.feri.prk.objekti;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.sql.rowset.serial.SerialException;

public class Clanek {
	private int clanek_id;
	private String user_username;
	private String title, content;
	private Calendar datumClanka;
	private Blob thumbnail;
	
	public Clanek() {
		super();
		this.datumClanka = new GregorianCalendar();
	}
	public Clanek(String user_username, String title, String content) {
		super();
		this.user_username = user_username;
		this.title = title;
		this.content = content;
		this.datumClanka = new GregorianCalendar();
	}
	public Clanek(int clanek_id, String user_username, String title, String content) {
		super();
		this.clanek_id = clanek_id;
		this.user_username = user_username;
		this.title = title;
		this.content = content;
		this.datumClanka = new GregorianCalendar();
	}
	public Clanek(int clanek_id, String user_username, String title, String content, Blob thumbnail) {
		super();
		this.clanek_id = clanek_id;
		this.user_username = user_username;
		this.title = title;
		this.content = content;
		this.thumbnail = thumbnail;
		this.datumClanka = new GregorianCalendar();
	}

	public int getClanek_id() {
		return clanek_id;
	}
	public void setClanek_id(int clanek_id) {
		this.clanek_id = clanek_id;
	}
	public void setThumbnail(Blob thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getUser_username() {
		return user_username;
	}
	public void setUser_username(String user_username) {
		this.user_username = user_username;
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
	public Blob getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(byte[] iS) {
		try {
			this.thumbnail = new javax.sql.rowset.serial.SerialBlob(iS);
		} catch (SerialException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	public Calendar getDatumClanka() {
		return datumClanka;
	}
	public void setDatumClanka(Calendar datumClanka) {
		this.datumClanka = datumClanka;
	}
	
	@Override
	public String toString() {
		return "Clanek [clanek_id=" + clanek_id + ", user_username=" + user_username + ", title=" + title + ", content="
				+ content + ", datumClanka=" + datumClanka + ", thumbnail=" + thumbnail + "]";
	}
}