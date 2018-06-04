package si.um.feri.prk.objekti;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Namig {
	private int id_namig;
	private String vsebina;
	private String naslov;
	private Calendar datumNamiga;
	
	public Namig() {
		super();
		datumNamiga = new GregorianCalendar();
		
	}
	
	public Namig(String naslov,String vsebina) {
		this.naslov=naslov;
		this.vsebina=vsebina;
		datumNamiga = new GregorianCalendar();
		
	}
	public Namig(int id_namig,String naslov,String vsebina) {
		this.id_namig=id_namig;
		this.naslov=naslov;
		this.vsebina=vsebina;
		datumNamiga = new GregorianCalendar();
		
	}

	public int getId_namig() {
		return id_namig;
	}

	public void setId_namig(int id_namig) {
		this.id_namig = id_namig;
	}

	public String getVsebina() {
		return vsebina;
	}

	public void setVsebina(String vsebina) {
		this.vsebina = vsebina;
	}

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public Calendar getDatumNamiga() {
		return datumNamiga;
	}

	public void setDatumNamiga(Calendar datumNamiga) {
		this.datumNamiga = datumNamiga;
	}
	

}
