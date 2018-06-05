package si.um.feri.prk.rest;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.prk.dao.ReceptDAO;
import si.um.feri.prk.jsfbeans.ReceptJSFBean;
import si.um.feri.prk.objekti.Recept;
import javax.ws.rs.PathParam;
import java.io.Serializable;
import java.util.ArrayList;

import javax.el.ELContext;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.Application;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;

@RequestScoped
@Path("/eprehrana")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PrehranaResource implements Serializable {

	Logger log=LoggerFactory.getLogger(PrehranaResource.class);
	private ReceptDAO rD = ReceptDAO.getInstance();
	
	@Context
    private UriInfo context;

	@GET
	@Path("/recepti")
	public Response vrniVseRecepte() throws Exception {
		log.info("PrehranaResource: vrniVseRecepte");
		ArrayList<Recept> vsiRecepti = rD.vrniVse();
		ArrayList<ReceptJSON> vsiZaPoslat = new ArrayList<ReceptJSON>();
		for(Recept r : vsiRecepti) {
			ReceptJSON temp = new ReceptJSON(r.getId_recept(), r.getIme(), r.getOpis(), r.getKategorija(), r.getKalorije(), r.getSladkorji());
			temp.setSestavine(r.getSestavine());
			temp.setAlergeni(r.getAlergeni());
			vsiZaPoslat.add(temp);
		}
		return Response.ok(vsiZaPoslat).build();
	}
}