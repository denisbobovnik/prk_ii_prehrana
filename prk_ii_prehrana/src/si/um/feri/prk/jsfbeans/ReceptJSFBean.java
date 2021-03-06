package si.um.feri.prk.jsfbeans;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.ByteStreams;
import com.sun.jndi.toolkit.url.Uri;

import si.um.feri.prk.dao.AlergeniDAO;
import si.um.feri.prk.dao.ReceptDAO;
import si.um.feri.prk.dao.SestavineDAO;
import si.um.feri.prk.objekti.Alergeni;
import si.um.feri.prk.objekti.Recept;
import si.um.feri.prk.objekti.Sestavine;
import sun.net.util.URLUtil;

@ManagedBean(name="ReceptJSFBean")
@SessionScoped
public class ReceptJSFBean {
	
	Logger log=LoggerFactory.getLogger(ClanekJSFBean.class);
	private Recept r = new Recept();
	private Sestavine s = new Sestavine();
	private UploadedFile thumbnail;
	private ReceptDAO rD = ReceptDAO.getInstance();
	private SestavineDAO sD = SestavineDAO.getInstance();
	private Recept izbranRecept = new Recept();
	private String alergeniPrivremeni;
	private AlergeniDAO aD = AlergeniDAO.getInstance();
	private int id_trenutnega_recepta;
	private ArrayList<Sestavine> sestavineTrenutnegaRecepta = new ArrayList<Sestavine>();
	private Recept urejenRecept = new Recept();
	private StringBuilder alergeniZVejicami;
	
	private String izbranaKategorijaReceptov;
	
	public ArrayList<String> vrniVseKategorijeReceptov() throws Exception {
		ArrayList<Recept> vsiRecepti = rD.vrniVse();
		ArrayList<String> ret = new ArrayList<String>();
		for(Recept r : vsiRecepti)
			if(!ret.contains(r.getKategorija()))
				ret.add(r.getKategorija());
		return ret;
	}
	
	public ArrayList<Recept> vrniRecepteZaPrikaz() throws Exception {
		ArrayList<Recept> ret = new ArrayList<Recept>();
		if(izbranaKategorijaReceptov.equals("Vsi"))
			return rD.vrniVse();
		else
			for(Recept rec : rD.vrniVse())
				if(rec.getKategorija().equals(izbranaKategorijaReceptov))
					ret.add(rec);
		return ret;
	}
	
	public void ponastaviKategorijo() {
		izbranaKategorijaReceptov = null;
	}
	
	public void nastaviKategorijo(String kategorija) {
		izbranaKategorijaReceptov = kategorija;
	}
	
	public String getIzbranaKategorijaReceptov() {
		return izbranaKategorijaReceptov;
	}

	public void setIzbranaKategorijaReceptov(String izbranaKategorijaReceptov) {
		this.izbranaKategorijaReceptov = izbranaKategorijaReceptov;
	}

	public void dodajRecept() {
		try {
			if(!isUploadedFileEmpty()) {
				String str = thumbnail.getFileName();
				if(str.contains(".")) {
					String ext = str.substring(str.lastIndexOf('.'), str.length());
					if(ext.equalsIgnoreCase(".jpg")||(ext.equalsIgnoreCase(".png"))||(ext.equalsIgnoreCase(".jpeg"))||(ext.equalsIgnoreCase(".gif"))) {
						nastaviTipSlike(ext);
						r.setSlika(thumbnail.getContents());
						
						r.setOpis(r.getOpis().replaceAll("\n","<br />"));
						
						int id = rD.shraniInVrniID(r);
						id_trenutnega_recepta = id;
						
						ArrayList<String> alergeni = razreziZvejicoArrayList(alergeniPrivremeni);
						ArrayList<Alergeni> alergeniVnos = new ArrayList<Alergeni>();
						for(String s : alergeni)
							alergeniVnos.add(new Alergeni(0, id, s));
							
						for(Alergeni a : alergeniVnos)
							aD.shrani(a);
						
						alergeniPrivremeni = "";
						r = new Recept();
					}
				}
			} else {
				r.setTipSlike("image/jpeg");
				
				InputStream iStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/img/default-tall.jpg");
				r.setSlika(ByteStreams.toByteArray(iStream));
				
				r.setOpis(r.getOpis().replaceAll("\n","<br />"));
				
				int id = rD.shraniInVrniID(r);
				id_trenutnega_recepta = id;

				ArrayList<String> alergeni = razreziZvejicoArrayList(alergeniPrivremeni);
				ArrayList<Alergeni> alergeniVnos = new ArrayList<Alergeni>();
				for(String s : alergeni)
					alergeniVnos.add(new Alergeni(0, id, s));
					
				for(Alergeni a : alergeniVnos)
					aD.shrani(a);
				
				alergeniPrivremeni="";
				r = new Recept();
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage errorMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Napaka nalaganja!", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, errorMsg);
		}
	}
	
	public boolean isURL(String url) {
	    try {
	        new URL(url);
	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}
	
	public boolean isUploadedFileEmpty() {
		return thumbnail == null || thumbnail.getSize() == 0;
	}
	
	public String vrniIme(int recept_id_enote) throws Exception {
		Recept r = rD.najdi(recept_id_enote);
		return r.getIme();
	}
	
	public void urediRecept(int id) throws Exception {
		urejenRecept = rD.najdi(id);
		urejenRecept.setOpis(urejenRecept.getOpis().replaceAll("<br />", "\n"));
	}
	
	public void posodobiRecept() throws Exception {
		try {
			if(!isUploadedFileEmpty()) {
				String str = thumbnail.getFileName();
				if(str.contains(".")) {
					String ext = str.substring(str.lastIndexOf('.'), str.length());
					if(ext.equalsIgnoreCase(".jpg")||(ext.equalsIgnoreCase(".png"))||(ext.equalsIgnoreCase(".jpeg"))||(ext.equalsIgnoreCase(".gif"))) {
						nastaviTipSlikeUPDATE(ext);
						urejenRecept.setSlika(thumbnail.getContents());
						
						urejenRecept.setOpis(urejenRecept.getOpis().replaceAll("\n","<br />"));
						
						rD.posodobi(urejenRecept);					
						urejenRecept = new Recept();
					}
				}
			} else {
				urejenRecept.setTipSlike("image/jpeg");
				
				InputStream iStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/img/default-tall.jpg");
				urejenRecept.setSlika(ByteStreams.toByteArray(iStream));
				
				urejenRecept.setOpis(urejenRecept.getOpis().replaceAll("\n","<br />"));
				
				rD.posodobi(urejenRecept);					
				urejenRecept = new Recept();
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage errorMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Napaka nalaganja!", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, errorMsg);
		}
	}
	
	public String zlepiAlergene(ArrayList<Alergeni> seznam) {
		String ret = "";
		for(Alergeni a : seznam)
			ret += a.getIme_alergena() + ", ";
		return ret;
	}
	
	public void dodajSestavino() throws Exception {
		sestavineTrenutnegaRecepta.add(s);
		s = new Sestavine();	
	}
	
	public void dodajSestavineReceptu() throws Exception {
		for(Sestavine s : sestavineTrenutnegaRecepta) {
			s.setTk_recept_id(id_trenutnega_recepta);
			sD.shrani(s);
		}
		
		double kalorijeSkupaj = 0;
		double sladkorjiSkupaj = 0;
		
		for(Sestavine s : sestavineTrenutnegaRecepta) {
			kalorijeSkupaj += s.getKalorije();
			sladkorjiSkupaj += s.getSladkorji();
		}
		
		Recept dodaneKalorijeInSladkorji = rD.najdi(id_trenutnega_recepta);
		dodaneKalorijeInSladkorji.setKalorije(kalorijeSkupaj);
		dodaneKalorijeInSladkorji.setSladkorji(sladkorjiSkupaj);
		
		rD.posodobi(dodaneKalorijeInSladkorji);
		
		sestavineTrenutnegaRecepta.clear(); //da "po�isti array sestavin, za naslednji recept"
	}
	
    public ArrayList<String> razreziZvejicoArrayList(String str) {
    	StringTokenizer st2 = new StringTokenizer(str, ",");
    	ArrayList<String> vrnjeno = new ArrayList<String>();
		while (st2.hasMoreElements()) {
			vrnjeno.add(st2.nextToken());
		}
		return vrnjeno;
    }

	public void izberiRecept(int recept_id) {
		try {
			izbranRecept = rD.najdi(recept_id);
		} catch (Exception e) {
			e.printStackTrace();
		};
	}
	
	private void nastaviTipSlike(String ext) {
		ext = ext.substring(1, ext.length());
		if(ext.equals("jpg")) {
			r.setTipSlike("image/jpeg");
		}
		else {
			r.setTipSlike("image/"+ext.toLowerCase());
		}
	}
	
	private void nastaviTipSlikeUPDATE(String ext) {
		ext = ext.substring(1, ext.length());
		if(ext.equals("jpg")) {
			urejenRecept.setTipSlike("image/jpeg");
		}
		else {
			urejenRecept.setTipSlike("image/"+ext.toLowerCase());
		}
	}
	
	public String trimContent121(String content) {
		if(content.length()<121) {
			return content;
		} else {
			content = content.substring(0, 121);
			return content;
		}
	}
	
	public ArrayList<Sestavine> vrniVsehSestavine(int recept_id){
		recept_id = izbranRecept.getId_recept();
		ArrayList<Sestavine> sestavine = null;
		if(sestavine == null) {
			try {
				sestavine = SestavineDAO.getInstance().najdiVsePoReceptu(recept_id);
			}
			catch (Exception e ) {
				sestavine = new ArrayList<>();
			}
		}
		return sestavine;
	}
	
	public ArrayList<Alergeni> vrniVsehAlergene(int recept_id){
		recept_id = izbranRecept.getId_recept();
		ArrayList<Alergeni> alergeni = null;
		if(alergeni == null) {
			try {
				alergeni = AlergeniDAO.getInstance().najdiVsePoReceptu(recept_id);
			}
			catch (Exception e ) {
				alergeni = new ArrayList<>();
			}
		}
		return alergeni;
	}

	public Recept getR() {
		return r;
	}

	public void setR(Recept r) {
		this.r = r;
	}

	public Sestavine getS() {
		return s;
	}

	public void setS(Sestavine s) {
		this.s = s;
	}

	public UploadedFile getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(UploadedFile thumbnail) {
		this.thumbnail = thumbnail;
	}

	public ReceptDAO getrD() {
		return rD;
	}

	public void setrD(ReceptDAO rD) {
		this.rD = rD;
	}

	public SestavineDAO getsD() {
		return sD;
	}

	public void setsD(SestavineDAO sD) {
		this.sD = sD;
	}
	public Recept getIzbranRecept() {
		return izbranRecept;
	}
	public void setIzbranRecept(Recept izbranRecept) {
		this.izbranRecept = izbranRecept;
	}
	public String getAlergeniPrivremeni() {
		return alergeniPrivremeni;
	}
	public void setAlergeniPrivremeni(String alergeniPrivremeni) {
		this.alergeniPrivremeni = alergeniPrivremeni;
	}
	public AlergeniDAO getaD() {
		return aD;
	}
	public void setaD(AlergeniDAO aD) {
		this.aD = aD;
	}
	public int getId_trenutnega_recepta() {
		return id_trenutnega_recepta;
	}
	public void setId_trenutnega_recepta(int id_trenutnega_recepta) {
		this.id_trenutnega_recepta = id_trenutnega_recepta;
	}
	public ArrayList<Sestavine> getSestavineTrenutnegaRecepta() {
		return sestavineTrenutnegaRecepta;
	}
	public void setSestavineTrenutnegaRecepta(ArrayList<Sestavine> sestavineTrenutnegaRecepta) {
		this.sestavineTrenutnegaRecepta = sestavineTrenutnegaRecepta;
	}
	public Recept getUrejenRecept() {
		return urejenRecept;
	}

	public void setUrejenRecept(Recept urejenRecept) {
		this.urejenRecept = urejenRecept;
	}
	public StringBuilder getAlergeniZVejicami() {
		return alergeniZVejicami;
	}

	public void setAlergeniZVejicami(StringBuilder alergeniZVejicami) {
		this.alergeniZVejicami = alergeniZVejicami;
	}
	
	

}