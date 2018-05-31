package si.um.feri.prk.rest;
/*
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.um.feri.prk.dao.AlergeniDAO;
import si.um.feri.prk.dao.CiljDAO;
import si.um.feri.prk.dao.ClanekDAO;
import si.um.feri.prk.dao.EnotaDAO;
import si.um.feri.prk.dao.PrehranaDAO;
import si.um.feri.prk.dao.ProgramDAO;
import si.um.feri.prk.dao.ReceptDAO;
import si.um.feri.prk.dao.SestavineDAO;
import si.um.feri.prk.dao.ZauzitaHranaDAO;
import si.um.feri.prk.objekti.ZauzitaHrana;

import javax.ws.rs.PathParam;
import java.util.Calendar;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

@Path("/eprehrana")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PrehranaResource {

	Logger log=LoggerFactory.getLogger(PrehranaResource.class);

	private AlergeniDAO aD = AlergeniDAO.getInstance();
	private CiljDAO ciljD = CiljDAO.getInstance();
	private ClanekDAO clanekD = ClanekDAO.getInstance();
	private EnotaDAO eD = EnotaDAO.getInstance();
	private PrehranaDAO prehranaD = PrehranaDAO.getInstance();
	private ProgramDAO programD = ProgramDAO.getInstance();
	private ReceptDAO rD = ReceptDAO.getInstance();
	private SestavineDAO sD = SestavineDAO.getInstance();
	private ZauzitaHranaDAO ZHD = ZauzitaHranaDAO.getInstance();
	
	@Context
    private UriInfo context;
	
	@GET
	public Response vrniVseClane() {
		log.info("MeritveClaniResource: vrniVseClane");
		return Response.ok(cbl.vrniVse()).build();
	}
	
	@GET
	@Path("/meritveclana/{idClana}")
	public Response vrniVseMeritveClana(@PathParam("idClana") int idClana) {
		log.info("MeritveClaniResource: vrniVseMeritveClana");
		Clan ret = cbl.najdi(idClana);
		if(ret != null) {
			return Response.ok(mbl.najdiVsePoIDjuClana(idClana)).build();
		} else {
			return Response.status(403).entity("ClanaNiMogoceNajtiException").build();
		}
	}
	
	@GET
	@Path("/clan/{idClana}")
	public Response vrniClana(@PathParam("idClana") int idClana) {
		log.info("MeritveClaniResource: vrniClana");
		Clan ret = cbl.najdi(idClana);
		if (ret != null) {
			return Response.ok(ret).build();
		} else {
			return Response.status(403).entity("ClanaNiMogoceNajtiException").build();
		}
	}
	
	@GET
	@Path("/meritev/{idMeritve}")
	public Response vrniMeritev(@PathParam("idMeritve") int idMeritve) {
		log.info("MeritveClaniResource: vrniMeritev");
		Meritev ret = mbl.najdi(idMeritve);
		if (ret != null) {
			return Response.ok(ret).build();
		} else {
			return Response.status(403).entity("MeritveNiMogoceNajtiException").build();
		}
	}
	
	@POST
	@Path("/clan")
	public Response dodajClana(String json) throws JSONException {
		log.info("MeritveClaniResource: dodajClana");
		
		JSONObject obj = new JSONObject(json);
	    JSONArray geodata = obj.getJSONArray("clan");
	    JSONObject clan = geodata.getJSONObject(0);
		
		Clan c = new Clan(clan.getString("ime"), clan.getString("priimek"), clan.getString("sifra"), clan.getString("spol"));
		c.setEmail(clan.getString("email"));
		c.setClanarina(clan.getString("clanarina"));
		c.getDatumRojstva().setTimeInMillis(clan.getLong("datumRojstva"));
		cbl.dodajClana(c);
		return Response.ok(c).build();
	}
	
	@POST
	@Path("/meritev")
	public Response dodajMeritevClanu(String json) throws JSONException {
		log.info("MeritveClaniResource: dodajMeritevClanu");
		
		JSONObject obj = new JSONObject(json);
	    JSONArray geodata = obj.getJSONArray("meritev");
	    JSONObject meritev = geodata.getJSONObject(0);
	    
		Clan c = cbl.najdi(meritev.getInt("idClana"));
		if (c != null) {
			Meritev m = new Meritev(null, meritev.getDouble("visina"), meritev.getDouble("obseg"), meritev.getDouble("teza"));
			m.setClan(c);
			c.dodajMeritevClanu(m);		
			mbl.shrani(m);
			return Response.ok(m).build();
		} else {
			return Response.status(403).entity("ClanaNiMogoceNajtiException").build();
		}
	}
	
	@PUT
	@Path("/clan/{idClana}")
	public Response spremeniClana(@PathParam("idClana") int idClana, String json) throws JSONException {
		log.info("MeritveClaniResource: spremeniClana");
		
		JSONObject obj = new JSONObject(json);
	    JSONArray geodata = obj.getJSONArray("clan");
	    JSONObject clan = geodata.getJSONObject(0);
		
		Clan c = cbl.najdi(idClana);
		if(c != null) {
			c.setIme(clan.getString("ime"));
			c.setPriimek(clan.getString("priimek"));
			c.setSpol(clan.getString("spol"));
			c.getDatumRojstva().setTimeInMillis(clan.getLong("datumRojstva"));		
			c.setEmail(clan.getString("email"));
			c.setClanarina(clan.getString("clanarina"));
			cbl.posodobiClana(c);
			return Response.ok(c).build();
		} else {
			return Response.status(403).entity("ClanaNiMogoceNajtiException").build();
		}
	}
	
	@PUT
	@Path("/meritev/{idMeritve}")
	public Response spremeniMeritev(@PathParam("idMeritve") int idMeritve, String json) throws JSONException {
		log.info("MeritveClaniResource: spremeniMeritev");
		
		JSONObject obj = new JSONObject(json);
	    JSONArray geodata = obj.getJSONArray("meritev");
	    JSONObject meritev = geodata.getJSONObject(0);
	    
		Meritev m = mbl.najdi(idMeritve);
		if(m != null) {
			m.setVisina(meritev.getDouble("visina"));
			m.setObseg(meritev.getDouble("obseg"));
			m.setTeza(meritev.getDouble("teza"));
			mbl.spremeni(m);
			return Response.ok(m).build();
		} else {
			return Response.status(403).entity("MeritveNiMogoceNajtiException").build();
		}
	}
	
	@DELETE
	@Path("/clan/{idClana}")
	public Response izbrisiClana(@PathParam("idClana") int idClana) {
		log.info("MeritveClaniResource: izbrisiClana");
		Clan c = cbl.najdi(idClana);
		if(c != null) {
			cbl.izbrisi(idClana);
			return Response.ok().build();
		} else {
			return Response.status(403).entity("ClanaNiMogoceNajtiException").build();
		}
	}

	@DELETE
	@Path("/meritev/{idMeritve}")
	public Response izbrisiMeritev(@PathParam("idMeritve") int idMeritve) {
		log.info("MeritveClaniResource: izbrisiMeritev");
		Meritev m = mbl.najdi(idMeritve);
		if(m != null) {
			mbl.izbrisi(idMeritve);
			return Response.ok().build();
		} else {
			return Response.status(403).entity("MeritveNiMogoceNajtiException").build();
		}
	}
}*/