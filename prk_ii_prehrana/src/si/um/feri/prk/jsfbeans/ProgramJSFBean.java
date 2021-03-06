package si.um.feri.prk.jsfbeans;

import java.io.InputStream;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.ejb.SessionContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;

import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.ByteStreams;

import si.um.feri.prk.dao.ClanekDAO;
import si.um.feri.prk.dao.EnotaDAO;
import si.um.feri.prk.dao.PrehranaDAO;
import si.um.feri.prk.dao.ProgramDAO;
import si.um.feri.prk.dao.ReceptDAO;
import si.um.feri.prk.objekti.Clanek;
import si.um.feri.prk.objekti.Enota;
import si.um.feri.prk.objekti.Prehrana;
import si.um.feri.prk.objekti.Program;
import si.um.feri.prk.objekti.Recept;
import si.um.feri.prk.objekti.Sestavine;

@ManagedBean(name="ProgramJSFBean")
@SessionScoped
public class ProgramJSFBean {
	
	Logger log=LoggerFactory.getLogger(ProgramJSFBean.class);
	private ProgramDAO pD = ProgramDAO.getInstance();
	private PrehranaDAO prehD = PrehranaDAO.getInstance();
	private Program p = new Program();
	private Program izbranProgram = new Program();
	private UploadedFile thumbnail;
	private Enota enota = new Enota();
	private Recept izbranRecept = new Recept();
	private int idZadnjiDodaniProgram;
	private ReceptDAO rD = ReceptDAO.getInstance();
	private ArrayList<Enota> enotePrograma = new ArrayList<Enota>();
	private EnotaDAO eD = EnotaDAO.getInstance();
	private String izbranaKategorijaReceptov;
	
	public void nastaviKategorijo(String kategorija) {
		izbranaKategorijaReceptov = kategorija;
	}
	public void ponastaviKategorijo() {
		izbranaKategorijaReceptov = null;
	}
	public String getIzbranaKategorijaReceptov() {
		return izbranaKategorijaReceptov;
	}
	public void setIzbranaKategorijaReceptov(String izbranaKategorijaReceptov) {
		this.izbranaKategorijaReceptov = izbranaKategorijaReceptov;
	}
	
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


	public void dodajProgram() {
		try {
			if(!isUploadedFileEmpty()) {
				String str = thumbnail.getFileName();
				if(str.contains(".")) {
					String ext = str.substring(str.lastIndexOf('.'), str.length());
					if(ext.equalsIgnoreCase(".jpg")||(ext.equalsIgnoreCase(".png"))||(ext.equalsIgnoreCase(".jpeg"))||(ext.equalsIgnoreCase(".gif"))) {
						nastaviTipSlike(ext);
						p.setSlika(thumbnail.getContents());
						
						FacesContext context = FacesContext.getCurrentInstance();
						String username = context.getExternalContext().getRemoteUser(); //USERNAME UPORABNIKA
						
						p.setUser_username(username);
						
						String vloga = getUserRole(); //ROLE / VLOGA UPORABNIKA
						if(vloga.equals("STROKOVNJAK"))
							p.setTipPrograma("splosni");
						else
							p.setTipPrograma("personaliziran");
						
						int id = pD.shraniInVrniId(p);
						idZadnjiDodaniProgram = id;
						p = new Program();
					}
				}
			} else {
				p.setTipSlike("image/jpeg");
				
				InputStream iStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/img/default-tall.jpg");
				p.setSlika(ByteStreams.toByteArray(iStream));
				
				FacesContext context = FacesContext.getCurrentInstance();
				String username = context.getExternalContext().getRemoteUser(); //USERNAME UPORABNIKA
				String vloga = getUserRole(); //ROLE / VLOGA UPORABNIKA
				p.setUser_username(username);
				
				if(vloga.equals("STROKOVNJAK"))
					p.setTipPrograma("splosni");
				else
					p.setTipPrograma("personaliziran");
				
				int id = pD.shraniInVrniId(p);
				idZadnjiDodaniProgram = id;
				p = new Program();
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage errorMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Napaka nalaganja!", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, errorMsg);
		}
	}
	
	public boolean isUploadedFileEmpty() {
		return thumbnail == null || thumbnail.getSize() == 0;
	}
	
	private String getUserRole(){
		FacesContext context = FacesContext.getCurrentInstance();
		String[] vloge = {"ADMINISTRATOR", "STROKOVNJAK", "POSAMEZNIK"};
		String ret = "";
		for(String s : vloge)
			if(context.getExternalContext().isUserInRole(s))
				ret = s;
		return ret;
	}

	private void nastaviTipSlike(String ext) {
		ext = ext.substring(1, ext.length());
		if(ext.equals("jpg")) {
			p.setTipSlike("image/jpeg");
		}
		else {
			p.setTipSlike("image/"+ext.toLowerCase());
		}
	}
	
	public void dodajEnoto() {
		p.getEnote().add(enota);
		enota = new Enota();
	}
	
	public Recept getIzbranRecept() {
		return izbranRecept;
	}

	public void setIzbranRecept(Recept izbranRecept) {
		this.izbranRecept = izbranRecept;
	}
	public Enota getEnota() {
		return enota;
	}

	public void setEnota(Enota enota) {
		this.enota = enota;
	}
	
	public void dodajReceptNaProgram() {
		enota.setId_enota(0);
		enota.setTk_program_id(idZadnjiDodaniProgram);
		enotePrograma.add(enota);
		enota = new Enota();
	}

	public void zakljuciUrejanje() throws Exception {
		for(Enota e : enotePrograma)
			eD.shrani(e);
		enotePrograma.clear();
	}	

	public int getIdZadnjiDodaniProgram() {
		return idZadnjiDodaniProgram;
	}

	public void setIdZadnjiDodaniProgram(int idZadnjiDodaniProgram) {
		this.idZadnjiDodaniProgram = idZadnjiDodaniProgram;
	}

	public ReceptDAO getrD() {
		return rD;
	}

	public void setrD(ReceptDAO rD) {
		this.rD = rD;
	}

	public UploadedFile getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(UploadedFile thumbnail) {
		this.thumbnail = thumbnail;
	}

	public ProgramDAO getpD() {
		return pD;
	}

	public void setpD(ProgramDAO pD) {
		this.pD = pD;
	}

	public Program getP() {
		return p;
	}

	public void setP(Program p) {
		this.p = p;
	}

	public Program getIzbranProgram() {
		return izbranProgram;
	}

	public void setIzbranProgram(Program izbranProgram) {
		this.izbranProgram = izbranProgram;
	}

	public PrehranaDAO getPrehD() {
		return prehD;
	}

	public void setPrehD(PrehranaDAO prehD) {
		this.prehD = prehD;
	}

	public int getZadnjiDodaniProgram() {
		return idZadnjiDodaniProgram;
	}

	public void setZadnjiDodaniProgram(int zadnjiDodaniProgram) {
		this.idZadnjiDodaniProgram = zadnjiDodaniProgram;
	}

	public ArrayList<Prehrana> vrniVse() throws Exception {
		return prehD.vrniVse();
	}

	public void izberiProgram(int id_program) {
		log.info("ProgramJSFBean: izberiProgram");
		try {
			izbranProgram = pD.najdi(id_program);
		} catch (Exception e) {
			e.printStackTrace();
		};
	}
	

}