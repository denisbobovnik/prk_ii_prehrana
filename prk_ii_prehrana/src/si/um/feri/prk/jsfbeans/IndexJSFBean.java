package si.um.feri.prk.jsfbeans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.prk.blockchain.BlockStorage;
import si.um.feri.prk.dao.ClanekDAO;
import si.um.feri.prk.dao.ReceptDAO;
import si.um.feri.prk.objekti.Recept;

@ManagedBean(name="IndexJSFBean")
@SessionScoped
public class IndexJSFBean {
	
	Logger log=LoggerFactory.getLogger(IndexJSFBean.class);
	private ReceptDAO rD = ReceptDAO.getInstance();
	private ClanekDAO cD = ClanekDAO.getInstance();
	private BlockStorage bS = BlockStorage.getInstance();
	
	public Recept vrniNajpogostejsi() throws Exception {
		Recept ret = rD.najdi(bS.vrniNajboljPogostoZauzitoHrano());
		return ret;
	}
}