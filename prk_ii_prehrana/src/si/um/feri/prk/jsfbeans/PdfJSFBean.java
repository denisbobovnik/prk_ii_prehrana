package si.um.feri.prk.jsfbeans;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import si.um.feri.prk.dao.ReceptDAO;
import si.um.feri.prk.objekti.Recept;
@ManagedBean(name="PdfJSFBean")
@SessionScoped
public class PdfJSFBean {
	public void createPDF(int id) throws Exception {
		System.out.println("SADASDA");
		ReceptDAO rD = ReceptDAO.getInstance();
		Recept recept = new Recept();
		recept = rD.najdi(id);
		String imeDatoteke = recept.getIme() + ".pdf";
		Document dokument = new Document();
		PdfWriter.getInstance(dokument, new FileOutputStream(imeDatoteke));
		dokument.open();
		
		Paragraph ime = new Paragraph("IME RECEPTA: "+recept.getIme());
		Paragraph stPorcij = new Paragraph("ŠTEVILO PORCIJ: " + recept.getSteviloPorcij());
		Paragraph dolzina = new Paragraph("DOLŽINA PRIPRAVE" + recept.getDolzinaPriprave());
		Paragraph kalorije = new Paragraph("KALORIJE: " + recept.getKalorije());
		Paragraph sladkorji = new Paragraph("SLADKORJI: " + recept.getSladkorji());
		String alergeni="";
		for(int i = 0; i<recept.getAlergeni().size(); i++) {
			if(i<recept.getAlergeni().size()-1) {
				alergeni += recept.getAlergeni().get(i).getIme_alergena();
			}else {
				alergeni += recept.getAlergeni().get(i).getIme_alergena() + ", ";
			}
		}
		Paragraph alerg = new Paragraph("ALERGENI: " + alergeni);
		String sest = "";
		for(int i = 0; i<recept.getSestavine().size(); i++) {
			if(i<recept.getAlergeni().size()-1) {
				sest += recept.getSestavine().get(i).getIme() + " " +  recept.getSestavine().get(i).getKolicina() +  recept.getSestavine().get(i).getEnota_kolicine();
			}else {
				sest += recept.getSestavine().get(i).getIme() + " " +  recept.getSestavine().get(i).getKolicina() +  recept.getSestavine().get(i).getEnota_kolicine() + ", ";
			}
		}
		Paragraph sestavine = new Paragraph("SESTAVINE: " + sest);
		Paragraph opis = new Paragraph("OPIS: " + recept.getOpis());
		
		dokument.add(ime);
		dokument.add(stPorcij);
		dokument.add(dolzina);
		dokument.add(kalorije);
		dokument.add(sladkorji);
		dokument.add(alerg);
		dokument.add(sestavine);
		dokument.add(opis);
		
		dokument.close();
		
		
		
	}

}
