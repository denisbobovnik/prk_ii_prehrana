package si.um.feri.prk.objekti;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.serial.SerialException;

public class Prehrana {
	private int id_prehrana;
	private String naslovPrehrane;
	private Blob thumbnail;
	private String content;
	private ArrayList<Program> prehranskiProgrami;
	private String tipSlike;
	
	
	public Prehrana() {
		super();
	}
	
	public Prehrana(int id_prehrana, String naslovPrehrane) {
		super();
		this.id_prehrana = id_prehrana;
		this.naslovPrehrane = naslovPrehrane;
	}

	public int getId_prehrana() {
		return id_prehrana;
	}

	public void setId_prehrana(int id_prehrana) {
		this.id_prehrana = id_prehrana;
	}

	public String getNaslovPrehrane() {
		return naslovPrehrane;
	}

	public void setNaslovPrehrane(String naslovPrehrane) {
		this.naslovPrehrane = naslovPrehrane;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ArrayList<Program> getPrehranskiProgrami() {
		return prehranskiProgrami;
	}

	public void setPrehranskiProgrami(ArrayList<Program> prehranskiProgrami) {
		this.prehranskiProgrami = prehranskiProgrami;
	}

	public String getTipSlike() {
		return tipSlike;
	}

	public void setTipSlike(String tipSlike) {
		this.tipSlike = tipSlike;
	}
	
	
}