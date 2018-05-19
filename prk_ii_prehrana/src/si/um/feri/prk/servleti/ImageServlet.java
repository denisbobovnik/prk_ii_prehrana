package si.um.feri.prk.servleti;

import java.io.IOException;
import java.sql.Blob;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import si.um.feri.prk.dao.ClanekDAO;
import si.um.feri.prk.objekti.Clanek;

@WebServlet("ImageServlet")
public class ImageServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    private ClanekDAO cD = ClanekDAO.getInstance();
	
	public ImageServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int clanek_id = Integer.parseInt(request.getParameter("id"));	
		try {
			Clanek c = cD.najdi(clanek_id);
			Blob blob = c.getThumbnail();
			int blobLength = (int) blob.length();  
			byte[] imageBytes = blob.getBytes(1, blobLength);
			blob.free();
			response.setContentType(c.getTipSlike());
			response.getOutputStream().write(imageBytes);
			response.getOutputStream().close();
		} catch (Exception e) {
			response.getWriter().write(e.getMessage());
			response.getWriter().close();
		}
	}
}