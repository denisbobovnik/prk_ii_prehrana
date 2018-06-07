package si.um.feri.prk.jsfbeans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.prk.blockchain.BlockStorage;
import si.um.feri.prk.dao.CiljDAO;
import si.um.feri.prk.dao.ClanekDAO;
import si.um.feri.prk.dao.ReceptDAO;
import si.um.feri.prk.objekti.Recept;

@ManagedBean(name="IndexJSFBean")
@SessionScoped
public class IndexJSFBean {
	Logger log=LoggerFactory.getLogger(IndexJSFBean.class);
	private ReceptDAO rD = ReceptDAO.getInstance();
	private ClanekDAO cD = ClanekDAO.getInstance();
	private CiljDAO ciD = CiljDAO.getInstance();
	
	public ReceptDAO getrD() {
		return rD;
	}
	public void setrD(ReceptDAO rD) {
		this.rD = rD;
	}
	public ClanekDAO getcD() {
		return cD;
	}
	public void setcD(ClanekDAO cD) {
		this.cD = cD;
	}
	public CiljDAO getCiD() {
		return ciD;
	}
	public void setCiD(CiljDAO ciD) {
		this.ciD = ciD;
	}
}