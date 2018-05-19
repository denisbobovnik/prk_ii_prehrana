package si.um.feri.prk.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.um.feri.prk.objekti.Program;

public class ProgramDAO {
	
	DataSource baza;
	Logger log=LoggerFactory.getLogger(ProgramDAO.class);
	
	private static ProgramDAO instance=null;
	public static synchronized ProgramDAO getInstance() {		
		if (instance==null) instance=new ProgramDAO();
		return instance;
	}
	
	private ProgramDAO() {
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
			conn.createStatement().execute("CREATE TABLE IF NOT EXISTS PROGRAM(id_program int not null auto_increment primary key, naslov varchar(100) not null, autor varchar(100) not null, slika longblob not null, user_id int, tk_id_prehrana int not null)");
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
			conn.createStatement().execute("DROP TABLE IF EXISTS PROGRAM CASCADE");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public Program najdi(int id_program) throws Exception{
		log.info("ProgramDAO: išèem " + id_program);
		Program ret = null;
		Connection conn=null;
		try {
			conn=baza.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Program WHERE user_id=?");
			ps.setInt(1, 0);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				ret = new Program(rs.getInt("id_program"), rs.getString("naslov"), rs.getString("autor"), rs.getBlob("slika"));
				ret.setId_program(id_program);
				
				Blob blob = rs.getBlob("slika");
				int blobLength = (int) blob.length();  
				byte[] blobAsBytes = blob.getBytes(1, blobLength);
				
				ret.setSlika(blobAsBytes);
				
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return ret;
	}
		
	
	
	public void shrani(Program p) throws Exception {
		log.info("ProgramDAO: shranjujem " + p);
		Connection conn=null;
		try {
			conn=baza.getConnection();
			if(najdi(p.getId_program()) != null) {
				
			} else {
				PreparedStatement ps = conn.prepareStatement("INSERT INTO Program(naslov, autor,slika,tk_id_prehrana,user_id) VALUES (?,?,?,?,?)");
				ps.setString(1, p.getNaslov());
				ps.setString(2, p.getAutor());
				ps.setBinaryStream(3, p.getSlika().getBinaryStream());
				ps.setInt(4, p.getTk_id_prehrana());
				//ps.setInt(5, p.getUser_id);
				
				ps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}	
}