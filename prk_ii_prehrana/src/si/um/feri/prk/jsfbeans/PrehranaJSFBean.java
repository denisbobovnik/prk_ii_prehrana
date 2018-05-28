package si.um.feri.prk.jsfbeans;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.um.feri.prk.dao.ClanekDAO;
import si.um.feri.prk.dao.PrehranaDAO;
import si.um.feri.prk.objekti.Clanek;
import si.um.feri.prk.objekti.Prehrana;
import si.um.feri.prk.objekti.Program;

@ManagedBean(name="PrehranaJSFBean")
@SessionScoped
public class PrehranaJSFBean {
	
	Logger log=LoggerFactory.getLogger(ClanekJSFBean.class);
	private Prehrana p = new Prehrana();
	private UploadedFile thumbnail;
	private PrehranaDAO pD = PrehranaDAO.getInstance();
	private Prehrana izbranaPrehrana = new Prehrana();
	private String username; //USERNAME UPORABNIKA
	
	public void dodajPrehrano() {
		try {
			String str = thumbnail.getFileName();
			if(str.contains(".")) {
				String ext = str.substring(str.lastIndexOf('.'), str.length());
				if(ext.equalsIgnoreCase(".jpg")||(ext.equalsIgnoreCase(".png"))||(ext.equalsIgnoreCase(".jpeg"))||(ext.equalsIgnoreCase(".gif"))) {
					nastaviTipSlike(ext);
					p.setThumbnail(thumbnail.getContents());
					
					FacesContext context = FacesContext.getCurrentInstance();
					String username = context.getExternalContext().getRemoteUser(); //USERNAME UPORABNIKA
					String vloga = getUserRole(); //ROLE / VLOGA UPORABNIKA
					
					
					pD.shrani(p);
					p = new Prehrana();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage errorMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Napaka nalaganja!", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, errorMsg);
		}
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
	private String getUserRole(){
		FacesContext context = FacesContext.getCurrentInstance();
		String[] vloge = {"ADMINISTRATOR", "STROKOVNJAK", "POSAMEZNIK"};
		String ret = "";
		for(String s : vloge)
			if(context.getExternalContext().isUserInRole(s))
				ret = s;
		return ret;
	}
	public ArrayList<Program> vrniPersonaliziranePrograme() throws Exception {
		FacesContext context = FacesContext.getCurrentInstance();
		username = context.getExternalContext().getRemoteUser();
		ArrayList<Program> vsiZaToPrehrano = pD.getpD().najdiVsePoPrehrani(izbranaPrehrana.getId_prehrana());
		ArrayList<Program> ret = new ArrayList<Program>();
		for(Program pr : vsiZaToPrehrano)
			if(pr.getTipPrograma().equals("personaliziran"))
				if(username.equals(pr.getUser_username()))
					ret.add(pr);
		return ret;
	}
	public void ponovnoPreveriPrijavo() {
		FacesContext context = FacesContext.getCurrentInstance();
		username = context.getExternalContext().getRemoteUser();
	}
	public void izberiPrehrano(int prehrana_id) {
		log.info("PrehranaJSFBean: izberiPrehrano");
		try {
			izbranaPrehrana = pD.najdi(prehrana_id);
		} catch (Exception e) {
			e.printStackTrace();
		};
	}
	
	public String trimContent121(String content) {
		if(content.length()<121) {
			return content;
		} else {
			content = content.substring(0, 121);
			return content;
		}
	}
	
	public Prehrana getP() {
		return p;
	}
	public void setP(Prehrana p) {
		this.p = p;
	}
	public UploadedFile getThumbnail() {
		return thumbnail;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setThumbnail(UploadedFile thumbnail) {
		this.thumbnail = thumbnail;
	}
	public PrehranaDAO getpD() {
		return pD;
	}
	public void setpD(PrehranaDAO pD) {
		this.pD = pD;
	}
	public Prehrana getIzbranaPrehrana() {
		return izbranaPrehrana;
	}
	public void setIzbranaPrehrana(Prehrana izbranaPrehrana) {
		this.izbranaPrehrana = izbranaPrehrana;
	}
	
}