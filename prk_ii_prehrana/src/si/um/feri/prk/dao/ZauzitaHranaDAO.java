package si.um.feri.prk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.prk.objekti.ZauzitaHrana;

public class ZauzitaHranaDAO {

	DataSource baza;
	Logger log=LoggerFactory.getLogger(ZauzitaHranaDAO.class);
	
	private static ZauzitaHranaDAO instance=null;
	public static synchronized ZauzitaHranaDAO getInstance() {		
		if (instance==null) instance=new ZauzitaHranaDAO();
		return instance;
	}

	private ZauzitaHranaDAO() {
		log.info("ZauzitaHranaDAO: ZauzitaHranaDAO ");
		try {
			baza=(DataSource)new InitialContext().lookup("java:jboss/datasources/prk_ii_prehrana");	
			kreirajTabele();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void kreirajTabele() throws Exception {
		log.info("ZauzitaHranaDAO: kreirajTabele ");
		Connection conn=null;
		try {
			conn=baza.getConnection();
			conn.createStatement().execute("CREATE TABLE IF NOT EXISTS ZauzitaHrana(id_zauzitaHrana int not null auto_increment primary key, tk_recept_sestavina_id int not null, user_username varchar(100) not null, datumZauzitja timestamp not null, vrednost varchar(100) not null)");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public void pobrisiTabele() throws Exception {
		log.info("ZauzitaHranaDAO: pobrisiTabele ");
		Connection conn=null;
		try {
			conn=baza.getConnection();
			conn.createStatement().execute("DROP TABLE IF EXISTS ZauzitaHrana CASCADE");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public ZauzitaHrana najdi(int id_zauzitaHrana) throws Exception {
		log.info("ZauzitaHranaDAO: najdi " + id_zauzitaHrana);
		ZauzitaHrana ret = null;
		Connection conn=null;
		try {
			conn=baza.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ZauzitaHrana WHERE id_zauzitaHrana=?");
			ps.setInt(1, id_zauzitaHrana);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				ret = new ZauzitaHrana(rs.getInt("id_zauzitaHrana"), rs.getInt("tk_recept_sestavina_id"), rs.getString("user_username"), rs.getString("vrednost"));
				ret.getDatumZauzitja().setTimeInMillis(rs.getTimestamp("datumZauzitja").getTime());
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return ret;
	}
	
	
	public void shrani(ZauzitaHrana zH) throws Exception {
		log.info("ZauzitaHranaDAO: shranjujem " + zH);
		Connection conn=null;
		try {
			conn=baza.getConnection();
			if(najdi(zH.getId_zauzitaHrana()) != null) {
				//zauzita hrana z id-jem že obstaja...pohandle-at
			} else {

				PreparedStatement ps = conn.prepareStatement("INSERT INTO ZauzitaHrana(tk_recept_sestavina_id, user_username, datumZauzitja, vrednost) VALUES (?,?,?,?)");
				
				ps.setInt(1, zH.getTk_recept_sestavina_id());
				ps.setString(2, zH.getUser_username());
				ps.setTimestamp(3, new Timestamp(zH.getDatumZauzitja().getTimeInMillis()));
				ps.setString(4, zH.getVrednost());

				ps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public void izbrisi(int id_zauzitaHrana) throws Exception {
		log.info("ZauzitaHranaDAO: izbrisi " + id_zauzitaHrana);
		Connection conn=null;
		try {
			conn=baza.getConnection();
			PreparedStatement ps = conn.prepareStatement("DELETE FROM ZauzitaHrana WHERE id_zauzitaHrana=?");
			ps.setInt(1, id_zauzitaHrana);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public ArrayList<ZauzitaHrana> vrniVse() throws Exception {
		log.info("ZauzitaHranaDAO: vrniVse ");
		ArrayList<ZauzitaHrana> seznam = new ArrayList<ZauzitaHrana>();
	
		Connection conn=null;
		try {
			conn=baza.getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ZauzitaHrana");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ZauzitaHrana zauzitaHrana = new ZauzitaHrana(rs.getInt("id_zauzitaHrana"), rs.getInt("tk_recept_sestavina_id"), rs.getString("user_username"), rs.getString("vrednost"));
				zauzitaHrana.getDatumZauzitja().setTimeInMillis(rs.getTimestamp("datumZauzitja").getTime());
				seznam.add(zauzitaHrana);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return seznam;
	}
	
	public ArrayList<ZauzitaHrana> vrniVseZaPrijavljenega() throws Exception {
		log.info("ZauzitaHranaDAO: vrniVseZaPrijavljenega ");
		ArrayList<ZauzitaHrana> seznam = new ArrayList<ZauzitaHrana>();
		
		FacesContext context = FacesContext.getCurrentInstance();
		String username = context.getExternalContext().getRemoteUser();
	
		Connection conn=null;
		try {
			conn=baza.getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ZauzitaHrana WHERE user_username=?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ZauzitaHrana zauzitaHrana = new ZauzitaHrana(rs.getInt("id_zauzitaHrana"), rs.getInt("tk_recept_sestavina_id"), rs.getString("user_username"), rs.getString("vrednost"));
				zauzitaHrana.getDatumZauzitja().setTimeInMillis(rs.getTimestamp("datumZauzitja").getTime());
				seznam.add(zauzitaHrana);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return seznam;
	}
}