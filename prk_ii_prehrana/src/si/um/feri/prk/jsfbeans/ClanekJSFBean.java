package si.um.feri.prk.jsfbeans;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.ejb.SessionContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;

import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.um.feri.prk.dao.ClanekDAO;
import si.um.feri.prk.objekti.Clanek;

@ManagedBean(name="ClanekJSFBean")
@SessionScoped
public class ClanekJSFBean {
	
	
	Logger log=LoggerFactory.getLogger(ClanekJSFBean.class);
	private Clanek c = new Clanek();
	private UploadedFile thumbnail;
	private ClanekDAO cD = ClanekDAO.getInstance();
	private Clanek izbranClanek = new Clanek();
	
	public void dodajClanek() {
		try {
			String str = thumbnail.getFileName();
			if(str.contains(".")) {
				String ext = str.substring(str.lastIndexOf('.'), str.length());
				if(ext.equalsIgnoreCase(".jpg")||(ext.equalsIgnoreCase(".png"))||(ext.equalsIgnoreCase(".jpeg"))||(ext.equalsIgnoreCase(".gif"))) {
					nastaviTipSlike(ext);
					c.setThumbnail(thumbnail.getContents());
					
					FacesContext context = FacesContext.getCurrentInstance();
					String username = context.getExternalContext().getRemoteUser(); //USERNAME UPORABNIKA
					String vloga = getUserRole(); //ROLE / VLOGA UPORABNIKA
					
					c.setUser_username(username);
					
					cD.shrani(c);
					c = new Clanek();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage errorMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Napaka nalaganja!", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, errorMsg);
		}
	}
	
	public void izberiClanek(int clanek_id) {
		log.info("ClanekJSFBean: izberiClanek");
		try {
			izbranClanek = cD.najdi(clanek_id);
		} catch (Exception e) {
			e.printStackTrace();
		};
	}
	
	private void nastaviTipSlike(String ext) {
		ext = ext.substring(1, ext.length());
		if(ext.equals("jpg")) {
			c.setTipSlike("image/jpeg");
		}
		else {
			c.setTipSlike("image/"+ext.toLowerCase());
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
	
	public String getDayOfMonth(Calendar cal) {
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		return "" + dayOfMonth;
	}
	
	public String getMonthAndYear(Calendar cal) {
		String month = new SimpleDateFormat("MMM").format(cal.getTime());
		month = month.substring(0, 1).toUpperCase() + month.substring(1);
		int year = cal.get(Calendar.YEAR);
		return "" + month + " " + year;
	}
	
	public String trimContent318(String content) {
		if(content.length()<318) {
			return content;
		} else {
			content = content.substring(0, 318);
			return content;
		}
	}
	
	public Clanek getC() {
		return c;
	}
	public void setC(Clanek c) {
		this.c = c;
	}
	public UploadedFile getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(UploadedFile thumbnail) {
		this.thumbnail = thumbnail;
	}
	public ClanekDAO getcD() {
		return cD;
	}
	public void setcD(ClanekDAO cD) {
		this.cD = cD;
	}
	public Clanek getIzbranClanek() {
		return izbranClanek;
	}
	public void setIzbranClanek(Clanek izbranClanek) {
		this.izbranClanek = izbranClanek;
	}
}