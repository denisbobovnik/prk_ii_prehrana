package si.um.feri.prk.servleti;

import java.io.IOException;
import java.sql.Blob;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import si.um.feri.prk.dao.ClanekDAO;
import si.um.feri.prk.dao.PrehranaDAO;
import si.um.feri.prk.dao.ProgramDAO;
import si.um.feri.prk.objekti.Clanek;
import si.um.feri.prk.objekti.Prehrana;
import si.um.feri.prk.objekti.Program;

@WebServlet("ImageServlet")
public class ImageServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    private ClanekDAO cD = ClanekDAO.getInstance();
    private PrehranaDAO pD = PrehranaDAO.getInstance();
    private ProgramDAO pDD = ProgramDAO.getInstance();
	
	public ImageServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String klic = request.getParameter("klic");
		int id = Integer.parseInt(request.getParameter("id"));
		String tipSlike = null;
		byte[] bitiSlike = null;
		
		try {
			if(klic.equals("clanek")) {
				int clanek_id = id;
				Clanek c = cD.najdi(clanek_id);
				Blob blob = c.getThumbnail();
				int blobLength = (int) blob.length();  
				bitiSlike = blob.getBytes(1, blobLength);
				blob.free();
				tipSlike = c.getTipSlike();
			} else if(klic.equals("prehrana")) {
				int prehrana_id = id;
				Prehrana p = pD.najdi(prehrana_id);
				Blob blob = p.getThumbnail();
				int blobLength = (int) blob.length();  
				bitiSlike = blob.getBytes(1, blobLength);
				blob.free();
				tipSlike = p.getTipSlike();
			} else if(klic.equals("program")) {
				int program_id = id;
				Program p = pDD.najdi(program_id);
				Blob blob = p.getSlika();
				int blobLength = (int) blob.length();  
				bitiSlike = blob.getBytes(1, blobLength);
				blob.free();
				tipSlike = p.getTipSlike();
			}
		} catch (Exception e) {
			response.getWriter().write(e.getMessage());
			response.getWriter().close();
		}
		
		response.setContentType(tipSlike);
		response.getOutputStream().write(bitiSlike);
		response.getOutputStream().close();
	}
}