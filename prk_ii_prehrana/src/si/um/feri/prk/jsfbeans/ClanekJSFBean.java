package si.um.feri.prk.jsfbeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

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
	private ArrayList<Clanek> vsiClanki;
	
	public void dodajClanek() {
		try {
			String str = thumbnail.getFileName();
			if(str.contains(".")) {
				String ext = str.substring(str.lastIndexOf('.'), str.length());
				if(ext.equalsIgnoreCase(".jpg")||(ext.equalsIgnoreCase(".png"))||(ext.equalsIgnoreCase(".jpeg"))||(ext.equalsIgnoreCase(".gif"))) {
					nastaviTipSlike(ext);
					c.setThumbnail(thumbnail.getContents());
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
	
	private void nastaviTipSlike(String ext) {
		ext = ext.substring(1, ext.length());
		if(ext.equals("jpg")) {
			c.setTipSlike("image/jpeg");
		}
		else {
			c.setTipSlike("image/"+ext.toLowerCase());
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
	
	public List<Clanek> vrniVseClanke() {
		vsiClanki=new ArrayList<>();
		if (vsiClanki!=null) {
			try {
				vsiClanki=ClanekDAO.getInstance().vrniVse();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return vsiClanki;
	}
}