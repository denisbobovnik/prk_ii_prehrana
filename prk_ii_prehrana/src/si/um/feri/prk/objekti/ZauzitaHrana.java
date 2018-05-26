package si.um.feri.prk.objekti;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ZauzitaHrana {
	private int id_zauzitaHrana, tk_recept_id;
	private String user_username;
	private Calendar datumZauzitja;
	
	public ZauzitaHrana() {
		super();
		datumZauzitja = new GregorianCalendar();
	}
	public ZauzitaHrana(int id_zauzitaHrana, int tk_recept_id, String user_username) {
		super();
		this.id_zauzitaHrana = id_zauzitaHrana;
		this.tk_recept_id = tk_recept_id;
		this.user_username = user_username;
		datumZauzitja = new GregorianCalendar();
	}
	
	public int getId_zauzitaHrana() {
		return id_zauzitaHrana;
	}
	public void setId_zauzitaHrana(int id_zauzitaHrana) {
		this.id_zauzitaHrana = id_zauzitaHrana;
	}
	public int getTk_recept_id() {
		return tk_recept_id;
	}
	public void setTk_recept_id(int tk_recept_id) {
		this.tk_recept_id = tk_recept_id;
	}
	public String getUser_username() {
		return user_username;
	}
	public void setUser_username(String user_username) {
		this.user_username = user_username;
	}
	public Calendar getDatumZauzitja() {
		return datumZauzitja;
	}
	public void setDatumZauzitja(Calendar datumZauzitja) {
		this.datumZauzitja = datumZauzitja;
	}
	
	@Override
	public String toString() {
		return "ZauzitaHrana [id_zauzitaHrana=" + id_zauzitaHrana + ", tk_recept_id=" + tk_recept_id
				+ ", user_username=" + user_username + ", datumZauzitja=" + datumZauzitja + "]";
	}
}