package si.um.feri.prk.jsfbeans;

import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.um.feri.prk.dao.CiljDAO;
import si.um.feri.prk.dao.PrehranaDAO;
import si.um.feri.prk.objekti.Cilj;
import si.um.feri.prk.objekti.Prehrana;

@ManagedBean(name="DnevnikJSFBean")
@SessionScoped
public class DnevnikJSFBean {

	Logger log=LoggerFactory.getLogger(DnevnikJSFBean.class);
	ArrayList<String> mozniCilji = new ArrayList<String>() {{
	    add("1x na teden jej hrano kategorije X");
	    add("Na dan pojej za X kalorij");
	    add("Spij X litrov vode na dan");
	    add("Na dan zaužij X gramov sladkorja");
	    add("X obrokov na dan");
	}};
	PrehranaDAO pD = PrehranaDAO.getInstance();
	CiljDAO cD = CiljDAO.getInstance();
	ArrayList<String> kategorijeHrane = new ArrayList<String>();
	
	private String izbranCilj, izbranaKategorija;
	private Double steviloKalorij, kolicinaVode, kolicinaSladkorja;
	private Integer steviloObrokov;
	
	public ArrayList<String> getMozniCilji() {
		return mozniCilji;
	}
	public void setMozniCilji(ArrayList<String> mozniCilji) {
		this.mozniCilji = mozniCilji;
	}
	public Logger getLog() {
		return log;
	}
	public void setLog(Logger log) {
		this.log = log;
	}
	public String getIzbranCilj() {
		return izbranCilj;
	}
	public void setIzbranCilj(String izbranCilj) {
		this.izbranCilj = izbranCilj;
	}
	public ArrayList<String> getKategorijeHrane() {
		return kategorijeHrane;
	}
	public void setKategorijeHrane(ArrayList<String> kategorijeHrane) {
		this.kategorijeHrane = kategorijeHrane;
	}
	public PrehranaDAO getpD() {
		return pD;
	}
	public void setpD(PrehranaDAO pD) {
		this.pD = pD;
	}
	public String getIzbranaKategorija() {
		return izbranaKategorija;
	}
	public void setIzbranaKategorija(String izbranaKategorija) {
		this.izbranaKategorija = izbranaKategorija;
	}	
	public CiljDAO getcD() {
		return cD;
	}
	public void setcD(CiljDAO cD) {
		this.cD = cD;
	}
	public Double getSteviloKalorij() {
		return steviloKalorij;
	}
	public void setSteviloKalorij(Double steviloKalorij) {
		this.steviloKalorij = steviloKalorij;
	}
	public Double getKolicinaVode() {
		return kolicinaVode;
	}
	public void setKolicinaVode(Double kolicinaVode) {
		this.kolicinaVode = kolicinaVode;
	}
	public Double getKolicinaSladkorja() {
		return kolicinaSladkorja;
	}
	public void setKolicinaSladkorja(Double kolicinaSladkorja) {
		this.kolicinaSladkorja = kolicinaSladkorja;
	}
	public Integer getSteviloObrokov() {
		return steviloObrokov;
	}
	public void setSteviloObrokov(Integer steviloObrokov) {
		this.steviloObrokov = steviloObrokov;
	}
	
	public void nastaviKategorije() throws Exception {
		kategorijeHrane.clear();
		for(Prehrana p : pD.vrniVse())
			kategorijeHrane.add(p.getNaslovPrehrane());
	}
	public void dodajCilj() throws Exception {
		FacesContext context = FacesContext.getCurrentInstance();
		String user_username = context.getExternalContext().getRemoteUser();
		if(izbranCilj.equals("1x na teden jej hrano kategorije X")) {
			cD.shrani(new Cilj(0, user_username, izbranCilj, izbranaKategorija));
			izbranaKategorija = null;
		} else if(izbranCilj.equals("Na dan pojej za X kalorij")) {
			cD.shrani(new Cilj(0, user_username, izbranCilj, "" + steviloKalorij));
			steviloKalorij = null;
		} else if(izbranCilj.equals("Spij X litrov vode na dan")) {
			cD.shrani(new Cilj(0, user_username, izbranCilj, "" + kolicinaVode));
			kolicinaVode = null;
		} else if(izbranCilj.equals("Na dan zaužij X gramov sladkorja")) {
			cD.shrani(new Cilj(0, user_username, izbranCilj, "" + kolicinaSladkorja));
			kolicinaSladkorja = null;
		} else if(izbranCilj.equals("X obrokov na dan")) {
			cD.shrani(new Cilj(0, user_username, izbranCilj, "" + steviloObrokov));
			steviloObrokov = null;
		}
		izbranCilj = null;
	}
	public void razveljaviDodajanje() {
		if(izbranCilj.equals("1x na teden jej hrano kategorije X")) {
			izbranaKategorija = null;
		} else if(izbranCilj.equals("Na dan pojej za X kalorij")) {
			steviloKalorij = null;
		} else if(izbranCilj.equals("Spij X litrov vode na dan")) {
			kolicinaVode = null;
		} else if(izbranCilj.equals("Na dan zaužij X gramov sladkorja")) {
			kolicinaSladkorja = null;
		} else if(izbranCilj.equals("X obrokov na dan")) {
			steviloObrokov = null;
		}
		izbranCilj = null;
	}
}