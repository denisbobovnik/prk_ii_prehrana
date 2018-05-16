package si.um.feri.prk.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.primefaces.model.UploadedFile;
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
		try {
			baza=(DataSource)new InitialContext().lookup("java:jboss/datasources/prk_ii_prehrana");	
			kreirajTabele();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void kreirajTabele() throws Exception {
		Connection conn=null;
		try {
			conn=baza.getConnection();
			conn.createStatement().execute("CREATE TABLE IF NOT EXISTS Clanek(clanek_id int not null auto_increment primary key, title varchar(100) not null, content varchar(9999) not null, user_id int, thumbnail longblob not null)");
			//zgor še not null dodat
			//to spodaj priredit za user_id
			//conn.createStatement().execute("ALTER TABLE Meritev ADD CONSTRAINT IDpovezanegaClana FOREIGN KEY (idClanaTeMeritve) REFERENCES Clan(idClana) ON DELETE CASCADE");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public void pobrisiTabele() throws Exception {
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
		log.info("ClanekDAO: iščem " + clanek_id);
		Clanek ret = null;
		Connection conn=null;
		try {
			conn=baza.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Clanek WHERE clanek_id=?");
			ps.setInt(1, clanek_id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				ret = new Clanek(rs.getInt("user_id"), rs.getString("title"), rs.getString("content"));
				ret.setClanek_id(clanek_id);
				
				Blob blob = rs.getBlob("thumbnail");
				int blobLength = (int) blob.length();  
				byte[] blobAsBytes = blob.getBytes(1, blobLength);
				
				ret.setThumbnail(blobAsBytes);
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
				PreparedStatement ps = conn.prepareStatement("INSERT INTO Clanek(title, content, user_id, thumbnail) VALUES (?,?,?,?)");
				ps.setString(1, c.getTitle());
				ps.setString(2, c.getContent());
				ps.setInt(3, c.getUser_id());
				ps.setBinaryStream(4, c.getThumbnail().getBinaryStream());
				ps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
}