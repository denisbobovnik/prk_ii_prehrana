package si.um.feri.prk.jsfbeans;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
import si.um.feri.prk.objekti.Sestavine;
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
	private String izbranCilj, izbranaKategorija, tipZauziteHrane;
	private Double steviloKalorij, kolicinaVode, kolicinaSladkorja;
	private Integer steviloObrokov;
	private ZauzitaHrana zH = new ZauzitaHrana();
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
	
	public String dobiDejanskoIme(Cilj cilj) {
		String tip = cilj.getTip();
		tip = tip.replaceAll("X", "'" + cilj.getVrednost() + "'");
		return tip;
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
	public String vrniVrednostKategorija(Cilj c) throws Exception {
		int stDni = daysBetween(c.getDatumZastavitve().getTime(), new GregorianCalendar().getTime()) + 1;
		if(stDni<=7) {
			String zadanaKategorija = c.getVrednost();
			for(ZauzitaHrana hrana : zhD.vrniVseZaPrijavljenega())
				if(hrana.getVrednost().equals("recept")) {
					Recept recept = rD.najdi(hrana.getTk_recept_sestavina_id());
					if(datumMedDatumoma(c.getDatumZastavitve().getTime(), new GregorianCalendar().getTime(), hrana.getDatumZauzitja().getTime()))
						if(recept.getKategorija().equals(zadanaKategorija))
							return "" + 100; //cilj je opravljen
				}
		} else {
			return null; //cilj je nerelevanten
		}
		return "" + 0; //cilj še ni opravljen
	}
	public String vrniVrednostKalorije(Cilj c) throws Exception {
		//recept ali sestavina
		double steviloKalorij = Double.parseDouble(c.getVrednost());
		double kalorijeDejansko = 0;
		if(!isSameDay(c.getDatumZastavitve(), new GregorianCalendar())) //cilj ni današnji
			kalorijeDejansko=-1;
		else { //gre za današnji dan
			for(ZauzitaHrana hrana : zhD.vrniVseZaPrijavljenega())
				if(hrana.getVrednost().equals("recept")) {
					Recept receptK = rD.najdi(hrana.getTk_recept_sestavina_id());
					if(isSameDay(c.getDatumZastavitve(), hrana.getDatumZauzitja())) //èe je blo danes zaužito, se beleži
						kalorijeDejansko += receptK.getKalorije();
				} else if(hrana.getVrednost().equals("sestavina")) {
					Sestavine sestavineK = sD.najdi(hrana.getTk_recept_sestavina_id());
					if(isSameDay(c.getDatumZastavitve(), hrana.getDatumZauzitja())) //èe je blo danes zaužito, se beleži
						kalorijeDejansko += sestavineK.getKalorije();
				}
		}
		
		if(kalorijeDejansko==-1) //gre za stari cilj, ne prikažemo
			return null;
		
		int percent = (int)((kalorijeDejansko * 100.0f) / steviloKalorij);
		if(percent>100)
			percent=100;
		return "" + percent;
	}
	public String vrniVrednostVoda(Cilj c) throws Exception {
		//le voda
		double kolicinaVode = Double.parseDouble(c.getVrednost());
		double vodeDejansko = 0;
		if(!isSameDay(c.getDatumZastavitve(), new GregorianCalendar())) //cilj ni današnji
			vodeDejansko=-1;
		else { //gre za današnji dan
			for(ZauzitaHrana hrana : zhD.vrniVseZaPrijavljenega())
				if(hrana.getVrednost().equals("voda")) {
					double zauzitaKolicina = (double) hrana.getTk_recept_sestavina_id() / 10;
					if(isSameDay(c.getDatumZastavitve(), hrana.getDatumZauzitja())) //èe je blo danes zaužito, se beleži
						vodeDejansko += zauzitaKolicina;
				}
		}
		
		if(vodeDejansko==-1) //gre za stari cilj, ne prikažemo
			return null;
		
		int percent = (int)((vodeDejansko * 100.0f) / kolicinaVode);
		if(percent>100)
			percent=100;
		return "" + percent;
	}
	public String vrniVrednostSladkorji(Cilj c) throws Exception {
		//recept ali sestavina
		double kolicinaSladkorja = Double.parseDouble(c.getVrednost());
		double sladkorjiDejansko = 0;
		if(!isSameDay(c.getDatumZastavitve(), new GregorianCalendar())) //cilj ni današnji
			sladkorjiDejansko=-1;
		else { //gre za današnji dan
			for(ZauzitaHrana hrana : zhD.vrniVseZaPrijavljenega())
				if(hrana.getVrednost().equals("recept")) {
					Recept receptK = rD.najdi(hrana.getTk_recept_sestavina_id());
					if(isSameDay(c.getDatumZastavitve(), hrana.getDatumZauzitja())) //èe je blo danes zaužito, se beleži
						sladkorjiDejansko += receptK.getSladkorji();
				} else if(hrana.getVrednost().equals("sestavina")) {
					Sestavine sestavineK = sD.najdi(hrana.getTk_recept_sestavina_id());
					if(isSameDay(c.getDatumZastavitve(), hrana.getDatumZauzitja())) //èe je blo danes zaužito, se beleži
						sladkorjiDejansko += sestavineK.getSladkorji();
				}
		}
		
		if(sladkorjiDejansko==-1) //gre za stari cilj, ne prikažemo
			return null;
		
		int percent = (int)((sladkorjiDejansko * 100.0f) / kolicinaSladkorja);
		if(percent>100)
			percent=100;
		return "" + percent;
	}
	public String vrniKolicinoVode(int stDeci) {
		return " (" + (double) stDeci / 10 + "l)";
	}
	public String formatirajDatum(Calendar c) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(c.getTime());
	}
	public String vrniVrednostSteviloObrokov(Cilj c) throws Exception {
		int steviloObrokov = Integer.parseInt(c.getVrednost());
		int steviloDejansko = 0;
		if(!isSameDay(c.getDatumZastavitve(), new GregorianCalendar())) //cilj ni današnji
			steviloDejansko=-1;
		else { //gre za današnji dan
			for(ZauzitaHrana hrana : zhD.vrniVseZaPrijavljenega())
				if(hrana.getVrednost().equals("recept"))
					if(isSameDay(c.getDatumZastavitve(), hrana.getDatumZauzitja())) //èe je blo danes zaužito, se beleži
						steviloDejansko++;
		}
		if(steviloDejansko==-1) //gre za stari cilj, ne prikažemo
			return null;
		
		int percent = (int)((steviloDejansko * 100.0f) / steviloObrokov);
		if(percent>100)
			percent=100;
		return "" + percent;
	}
    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }
	public boolean datumMedDatumoma(Date prvi, Date drugi, Date vmesni) {
		return vmesni.after(prvi) && vmesni.before(drugi);
	}
    public int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
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