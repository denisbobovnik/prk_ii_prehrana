package si.um.feri.prk.objekti;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.serial.SerialException;

public class Prehrana {
	private int id_prehrana;
	private String naslovPrehrane, content, tipSlike;
	private Blob thumbnail;
	private ArrayList<Program> prehranskiProgrami = new ArrayList<Program>();	
	
	public Prehrana() {
		super();
	}
	public Prehrana(int id_prehrana, String naslovPrehrane, String content, String tipSlike, Blob thumbnail) {
		super();
		this.id_prehrana = id_prehrana;
		this.naslovPrehrane = naslovPrehrane;
		this.content = content;
		this.tipSlike = tipSlike;
		this.thumbnail = thumbnail;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTipSlike() {
		return tipSlike;
	}
	public void setTipSlike(String tipSlike) {
		this.tipSlike = tipSlike;
	}
	public Blob getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(Blob thumbnail) {
		this.thumbnail = thumbnail;
	}
	public ArrayList<Program> getPrehranskiProgrami() {
		return prehranskiProgrami;
	}
	public void setPrehranskiProgrami(ArrayList<Program> prehranskiProgrami) {
		this.prehranskiProgrami = prehranskiProgrami;
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
	
	@Override
	public String toString() {
		return "Prehrana [id_prehrana=" + id_prehrana + ", naslovPrehrane=" + naslovPrehrane + ", content=" + content
				+ ", tipSlike=" + tipSlike + ", thumbnail=" + thumbnail + "]";
	}
}