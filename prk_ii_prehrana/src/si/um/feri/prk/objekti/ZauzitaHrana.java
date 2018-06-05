package si.um.feri.prk.objekti;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ZauzitaHrana implements Serializable {
	private int tk_recept_sestavina_id;
	private String user_username, vrednost;
	private Calendar datumZauzitja;
	
	public ZauzitaHrana() {
		super();
		datumZauzitja = new GregorianCalendar();
	}
	public ZauzitaHrana(int tk_recept_sestavina_id, String user_username, String vrednost) {
		super();
		this.tk_recept_sestavina_id = tk_recept_sestavina_id;
		this.user_username = user_username;
		this.vrednost = vrednost;
		datumZauzitja = new GregorianCalendar();
	}
	
	public String getVrednost() {
		return vrednost;
	}
	public void setVrednost(String vrednost) {
		this.vrednost = vrednost;
	}
	public int getTk_recept_sestavina_id() {
		return tk_recept_sestavina_id;
	}
	public void setTk_recept_sestavina_id(int tk_recept_sestavina_id) {
		this.tk_recept_sestavina_id = tk_recept_sestavina_id;
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
		return "ZauzitaHrana [tk_recept_sestavina_id=" + tk_recept_sestavina_id + ", user_username=" + user_username
				+ ", vrednost=" + vrednost + ", datumZauzitja=" + datumZauzitja + "]";
	}
}