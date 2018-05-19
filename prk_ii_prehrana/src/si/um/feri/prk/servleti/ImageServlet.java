package si.um.feri.prk.servleti;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("ImageServlet")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ImageServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int clanek_id = Integer.parseInt(request.getParameter("id"));
		
		
		System.out.println(clanek_id);
		
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
}