package si.um.feri.prk.jsfbeans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.um.feri.prk.dao.ReceptDAO;
import si.um.feri.prk.dao.SestavineDAO;
import si.um.feri.prk.objekti.Recept;
import si.um.feri.prk.objekti.Recept_Ima_Sestavino;
import si.um.feri.prk.objekti.Sestavine;

@ManagedBean(name="RecpetJSFBean")
@SessionScoped
public class ReceptJSFBean {
	
	Logger log=LoggerFactory.getLogger(ClanekJSFBean.class);
	private Recept r = new Recept();
	private Sestavine s = new Sestavine();
	private Recept_Ima_Sestavino rs = new Recept_Ima_Sestavino();
	private UploadedFile thumbnail;
	private ReceptDAO rD = ReceptDAO.getInstance();
	private SestavineDAO sD = SestavineDAO.getInstance();
	private Recept izbranRecept = new Recept();
	
	
	public void dodajRecept() {
		try {
			String str = thumbnail.getFileName();
			if(str.contains(".")) {
				String ext = str.substring(str.lastIndexOf('.'), str.length());
				if(ext.equalsIgnoreCase(".jpg")||(ext.equalsIgnoreCase(".png"))||(ext.equalsIgnoreCase(".jpeg"))||(ext.equalsIgnoreCase(".gif"))) {
					r.setSlika(thumbnail.getContents());
					rD.shrani(r);
					r = new Recept();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage errorMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Napaka nalaganja!", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, errorMsg);
		}
	}
	public void dodajSestavino() throws Exception {
		sD.shrani(s);
		s = new Sestavine();
		//KO BO NAREJEN DAO SE DODAJ DA SE DODA KOLICINA PA V XHTML PREVERI
		
	}
	public void izberiRecept(int recept_id) {
		try {
			izbranRecept = rD.najdi(recept_id);
		} catch (Exception e) {
			e.printStackTrace();
		};
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
	
	

}