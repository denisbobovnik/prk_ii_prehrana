package si.um.feri.prk.jsfbeans;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.itextpdf.text.Image;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import si.um.feri.prk.dao.ReceptDAO;
import si.um.feri.prk.objekti.Alergeni;
import si.um.feri.prk.objekti.Recept;
import si.um.feri.prk.objekti.Sestavine;
@ManagedBean(name="PdfJSFBean")
@RequestScoped
public class PdfJSFBean {
	
	private static SimpleDateFormat sdf=new SimpleDateFormat("dd. MM. yyyy HH.mm.ss.SSS");
	private ReceptDAO rD = ReceptDAO.getInstance();
	private Recept recept = null;
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	
	public void createPDF(int id) throws Exception {
		recept = rD.najdi(id);
		String imeDatoteke = vrniBrezSumnikov(recept.getIme()) + "-" + sdf.format(new GregorianCalendar().getTime()) + ".pdf";
		
		Document dokument = new Document();
		
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + imeDatoteke + "\"");
		context.responseComplete();
		PdfWriter.getInstance(dokument, response.getOutputStream());
		
		dokument.addAuthor("ePrehrana");
		dokument.addCreationDate();
		dokument.addTitle("Natisnjen izbran recept");
		
		dokument.open();
	
		Paragraph dolzina = new Paragraph("Dolzina priprave: " + recept.getDolzinaPriprave() + " minut", smallBold);
		Paragraph stPorcij = new Paragraph("Stevilo porcij: " + recept.getSteviloPorcij(), smallBold);
		Paragraph kalorije = new Paragraph("Stevilo kalorij: " + round(recept.getKalorije(), 2) + "kcal", smallBold);
		Paragraph sladkorji = new Paragraph("Kolicina sladkorja: " + round(recept.getSladkorji(), 2) + "g", smallBold);
		
		String alergeni = "";
		for(Alergeni a : recept.getAlergeni())
			alergeni += vrniBrezSumnikov(a.getIme_alergena()) + ", ";
		
		Paragraph alerg = new Paragraph("Alergeni: " + alergeni, smallBold);
		String opiss = recept.getOpis().replaceAll("<br /><br />", "\n").replaceAll("<br />", "\n");
		Paragraph opis = new Paragraph(vrniBrezSumnikov(opiss));
		
	    PdfPTable table = new PdfPTable(4);
	    PdfPCell c1 = new PdfPCell(new Phrase("Ime", smallBold));
	    c1.setHorizontalAlignment(Element.ALIGN_LEFT);
	    table.addCell(c1);
	    c1 = new PdfPCell(new Phrase("Kolicina", smallBold));
	    c1.setHorizontalAlignment(Element.ALIGN_LEFT);
	    table.addCell(c1);
	    c1 = new PdfPCell(new Phrase("Sladkorji", smallBold));
	    c1.setHorizontalAlignment(Element.ALIGN_LEFT);
	    table.addCell(c1);
	    c1 = new PdfPCell(new Phrase("Kalorije", smallBold));
	    c1.setHorizontalAlignment(Element.ALIGN_LEFT);
	    table.addCell(c1);
	    table.setHeaderRows(1);
	    for(Sestavine s : recept.getSestavine()) {
	    	table.addCell(vrniBrezSumnikov(s.getIme()));
	    	table.addCell(vrniBrezSumnikov(round(s.getKolicina(), 2) + s.getEnota_kolicine()));
	    	table.addCell(vrniBrezSumnikov(round(s.getSladkorji(), 2) + "g"));
	    	table.addCell(vrniBrezSumnikov(round(s.getKalorije(), 2) + "kcal"));
	    }

		addPhoto(dokument);
		
		Paragraph preface = new Paragraph();
        dokument.add(new Paragraph(" "));
        preface.add(new Paragraph(vrniBrezSumnikov(recept.getIme()), catFont));
        preface.add(new Paragraph("- " + vrniBrezSumnikov(recept.getKategorija()), smallBold));
		dokument.add(preface);
	
		dokument.add(new Paragraph(" "));
	    dokument.add(new Paragraph("Sestavine", smallBold));
	    dokument.add(new Paragraph(" "));
	    dokument.add(table);
	    
		dokument.add(new Paragraph(" "));
		dokument.add(dolzina);
		dokument.add(stPorcij);
		dokument.add(kalorije);
		dokument.add(sladkorji);
		dokument.add(alerg);
				
	    dokument.add(new Paragraph(" "));
	    dokument.add(new Paragraph("Postopek", smallBold));
		dokument.add(opis);
		
		dokument.close();
	}
    private void addPhoto(Document dokument) throws DocumentException, MalformedURLException, IOException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = request.getRequestURL().toString();
        int odreziTu = url.indexOf("/faces");
        String zacetek = url.substring(0, odreziTu);
        
        String logoURL = zacetek + "/img/logo.png";
        Image logo = Image.getInstance(new URL(logoURL));
        
    	String imageUrl = zacetek + "/ImageServlet?klic=recept&id=" + recept.getId_recept();
        Image image = Image.getInstance(new URL(imageUrl));
        image.scaleAbsolute(310, 270);
        
        dokument.add(logo);
        dokument.add(new Paragraph(" "));
        dokument.add(image);
    }
    private String vrniBrezSumnikov(String input) {
    	input = input.replaceAll("Š", "S");
    	input = input.replaceAll("È", "C");
    	input = input.replaceAll("Ž", "Z");
    	input = input.replaceAll("š", "s");
    	input = input.replaceAll("è", "c");
    	input = input.replaceAll("ž", "z");
    	return input;
    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}