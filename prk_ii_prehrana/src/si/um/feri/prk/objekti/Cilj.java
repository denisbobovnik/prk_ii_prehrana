package si.um.feri.prk.objekti;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Cilj {
	private int id_cilj;
	private String user_username, tip, vrednost;
	private Calendar datumZastavitve;
	
	public Cilj() {
		super();
		datumZastavitve = new GregorianCalendar();
	}
	public Cilj(int id_cilj, String user_username, String tip, String vrednost) {
		super();
		this.id_cilj = id_cilj;
		this.user_username = user_username;
		this.tip = tip;
		this.vrednost = vrednost;
		datumZastavitve = new GregorianCalendar();
	}
	
	public int getId_cilj() {
		return id_cilj;
	}
	public void setId_cilj(int id_cilj) {
		this.id_cilj = id_cilj;
	}
	public String getUser_username() {
		return user_username;
	}
	public void setUser_username(String user_username) {
		this.user_username = user_username;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}	
	public Calendar getDatumZastavitve() {
		return datumZastavitve;
	}
	public void setDatumZastavitve(Calendar datumZastavitve) {
		this.datumZastavitve = datumZastavitve;
	}
	public String getVrednost() {
		return vrednost;
	}
	public void setVrednost(String vrednost) {
		this.vrednost = vrednost;
	}
	
	@Override
	public String toString() {
		return "Cilj [id_cilj=" + id_cilj + ", user_username=" + user_username + ", tip=" + tip + ", vrednost="
				+ vrednost + ", datumZastavitve=" + datumZastavitve + "]";
	}
}