package si.um.feri.prk.jsfbeans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.um.feri.prk.dao.ClanekDAO;
import si.um.feri.prk.objekti.Clanek;

@ManagedBean(name="ClanekJSFBean")
@SessionScoped
public class ClanekJSFBean implements Serializable {
	
	Logger log=LoggerFactory.getLogger(ClanekJSFBean.class);
	private Clanek c = new Clanek();
	private ClanekDAO cD = ClanekDAO.getInstance();
	
	public void dodajClanek() {
		try {
			cD.shrani(c);
			c = new Clanek();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Clanek getC() {
		return c;
	}
	public void setC(Clanek c) {
		this.c = c;
	}
}