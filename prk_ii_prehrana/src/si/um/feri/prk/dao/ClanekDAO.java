package si.um.feri.prk.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.prk.objekti.Clanek;

public class ClanekDAO {
	
	DataSource baza;
	Logger log=LoggerFactory.getLogger(ClanekDAO.class);
	
	private static ClanekDAO instance=null;
	public static synchronized ClanekDAO getInstance() {		
		if (instance==null) instance=new ClanekDAO();
		return instance;
	}
	
	private ClanekDAO() {
		log.info("ClanekDAO: ClanekDAO ");
		try {
			baza=(DataSource)new InitialContext().lookup("java:jboss/datasources/prk_ii_prehrana");	
			kreirajTabele();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void kreirajTabele() throws Exception {
		log.info("ClanekDAO: kreirajTabele ");
		Connection conn=null;
		try {
			conn=baza.getConnection();
			conn.createStatement().execute("CREATE TABLE IF NOT EXISTS Clanek(clanek_id int not null auto_increment primary key, title varchar(100) not null, content varchar(9999) not null, user_username varchar(100) not null, thumbnail longblob not null, datumClanka timestamp not null, tipSlike varchar(20) not null)");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public void pobrisiTabele() throws Exception {
		log.info("ClanekDAO: pobrisiTabele ");
		Connection conn=null;
		try {
			conn=baza.getConnection();
			conn.createStatement().execute("DROP TABLE IF EXISTS Clanek CASCADE");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public Clanek najdi(int clanek_id) throws Exception {
		log.info("ClanekDAO: najdi " + clanek_id);
		Clanek ret = null;
		Connection conn=null;
		try {
			conn=baza.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Clanek WHERE clanek_id=?");
			ps.setInt(1, clanek_id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				ret = new Clanek(rs.getString("user_username"), rs.getString("title"), rs.getString("content"));
				ret.setClanek_id(clanek_id);
				
				Blob blob = rs.getBlob("thumbnail");
				int blobLength = (int) blob.length();  
				byte[] blobAsBytes = blob.getBytes(1, blobLength);
				
				ret.setThumbnail(blobAsBytes);
				
				ret.getDatumClanka().setTimeInMillis(rs.getTimestamp("datumClanka").getTime());
				
				ret.setTipSlike(rs.getString("tipSlike"));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return ret;
	}
	
	
	public void shrani(Clanek c) throws Exception {
		log.info("ClanekDAO: shranjujem " + c);
		Connection conn=null;
		try {
			conn=baza.getConnection();
			if(najdi(c.getClanek_id()) != null) {
				//clanek z id-jem že obstaja...pohandle-at
			} else {

				PreparedStatement ps = conn.prepareStatement("INSERT INTO Clanek(title, content, user_username, thumbnail, datumClanka, tipSlike) VALUES (?,?,?,?,?,?)");

				ps.setString(1, c.getTitle());
				ps.setString(2, c.getContent());
				ps.setString(3, c.getUser_username());
				ps.setBinaryStream(4, c.getThumbnail().getBinaryStream());
				ps.setTimestamp(5, new Timestamp(c.getDatumClanka().getTimeInMillis()));
				ps.setString(6, c.getTipSlike());
				ps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public void izbrisi(int clanek_id) throws Exception {
		log.info("ClanekDAO: izbrisi " + clanek_id);
		Connection conn=null;
		try {
			conn=baza.getConnection();
			PreparedStatement ps = conn.prepareStatement("DELETE FROM CLANEK WHERE clanek_id=?");
			ps.setInt(1, clanek_id);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public ArrayList<Clanek> vrniVse() throws Exception {
		log.info("ClanekDAO: vrniVse ");
		ArrayList<Clanek> seznam = new ArrayList<Clanek>();
	
		Connection conn=null;
		try {
			conn=baza.getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM CLANEK");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				Clanek clanek = new Clanek(rs.getInt("clanek_id"),rs.getString("user_username"),rs.getString("title"), rs.getString("content"), rs.getBlob("thumbnail"));
				clanek.setTipSlike(rs.getString("tipSlike"));
				
				clanek.getDatumClanka().setTimeInMillis(rs.getTimestamp("datumClanka").getTime());

				seznam.add(clanek);
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