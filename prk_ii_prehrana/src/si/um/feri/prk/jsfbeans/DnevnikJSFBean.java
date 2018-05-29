package si.um.feri.prk.jsfbeans;

import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.um.feri.prk.dao.CiljDAO;
import si.um.feri.prk.dao.PrehranaDAO;
import si.um.feri.prk.dao.ReceptDAO;
import si.um.feri.prk.dao.SestavineDAO;
import si.um.feri.prk.dao.ZauzitaHranaDAO;
import si.um.feri.prk.objekti.Cilj;
import si.um.feri.prk.objekti.Prehrana;
import si.um.feri.prk.objekti.Recept;
import si.um.feri.prk.objekti.ZauzitaHrana;

@ManagedBean(name="DnevnikJSFBean")
@SessionScoped
public class DnevnikJSFBean {

	private Logger log=LoggerFactory.getLogger(DnevnikJSFBean.class);
	private ArrayList<String> mozniCilji = new ArrayList<String>() {{
	    add("1x na teden jej hrano kategorije X");
	    add("Na dan pojej za X kalorij");
	    add("Spij X litrov vode na dan");
	    add("Na dan zaužij X gramov sladkorja");
	    add("X obrokov na dan");
	}};
	private PrehranaDAO pD = PrehranaDAO.getInstance();
	private CiljDAO cD = CiljDAO.getInstance();
	private ReceptDAO rD = ReceptDAO.getInstance();
	private SestavineDAO sD = SestavineDAO.getInstance();
	private ZauzitaHranaDAO zhD = ZauzitaHranaDAO.getInstance();
	private ArrayList<String> kategorijeHrane = new ArrayList<String>();
	private String izbranCilj, izbranaKategorija;
	private Double steviloKalorij, kolicinaVode, kolicinaSladkorja;
	private Integer steviloObrokov;
	
	private String tipZauziteHrane;
	private ZauzitaHrana zH = new ZauzitaHrana();
	
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
	public String getTipZauziteHrane() {
		return tipZauziteHrane;
	}
	public void setTipZauziteHrane(String tipZauziteHrane) {
		this.tipZauziteHrane = tipZauziteHrane;
	}
	public ZauzitaHrana getzH() {
		return zH;
	}
	public void setzH(ZauzitaHrana zH) {
		this.zH = zH;
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
	public ZauzitaHranaDAO getZhD() {
		return zhD;
	}
	public void setZhD(ZauzitaHranaDAO zhD) {
		this.zhD = zhD;
	}
	
	public void dolociTipVnosaZauziteHrane(String tip) {
		tipZauziteHrane = tip;
	}
	public void nastaviKategorije() throws Exception {
		kategorijeHrane.clear();
		for(Recept r : rD.vrniVse())
			kategorijeHrane.add(r.getKategorija());
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
	public void razveljaviVnosHrane() {
		tipZauziteHrane = null;
		zH = new ZauzitaHrana();
	}
	public void dodajZauzitoHrano() throws Exception {
		FacesContext context = FacesContext.getCurrentInstance();
		zH.setUser_username(context.getExternalContext().getRemoteUser());
		zH.setVrednost(tipZauziteHrane);
		zhD.shrani(zH);
		zH = new ZauzitaHrana();
		tipZauziteHrane = null;
	}
	public void dodajZauzitoHranoVODA(int kolicina) throws Exception {
		FacesContext context = FacesContext.getCurrentInstance();
		ZauzitaHrana voda = new ZauzitaHrana(0, kolicina, context.getExternalContext().getRemoteUser(), "voda");
		zhD.shrani(voda);
	}
}