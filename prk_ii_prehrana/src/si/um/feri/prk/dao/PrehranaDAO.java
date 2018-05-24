package si.um.feri.prk.dao;


import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.prk.objekti.Prehrana;
import si.um.feri.prk.objekti.Program;


public class PrehranaDAO {
	
	DataSource baza;
	Logger log=LoggerFactory.getLogger(PrehranaDAO.class);
	private ProgramDAO pD = ProgramDAO.getInstance();
	
	private static PrehranaDAO instance=null;
	public static synchronized PrehranaDAO getInstance() {		
		if (instance==null) instance=new PrehranaDAO();
		return instance;
	}
	
	private PrehranaDAO() {
		log.info("PrehranaDAO: PrehranaDAO ");
		try {
			baza=(DataSource)new InitialContext().lookup("java:jboss/datasources/prk_ii_prehrana");	
			kreirajTabele();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void kreirajTabele() throws Exception {
		log.info("PrehranaDAO: kreirajTabele ");
		Connection conn=null;
		try {
			conn=baza.getConnection();
			conn.createStatement().execute("CREATE TABLE IF NOT EXISTS Prehrana(id_prehrana int not null auto_increment primary key, naslovPrehrane varchar(200) not null, thumbnail longblob not null, content varchar(9999) not null, tipSlike varchar(20) not null)");
			} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public void pobrisiTabele() throws Exception {
		log.info("PrehranaDAO: pobrisiTabele ");
		Connection conn=null;
		try {
			conn=baza.getConnection();
			conn.createStatement().execute("DROP TABLE IF EXISTS Prehrana CASCADE");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public Prehrana najdi(int id_prehrana) throws SQLException {
		log.info("PrehranaDAO: najdi " + id_prehrana);
		Prehrana ret = null;
		Connection conn=null;
		try {
			conn=baza.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Prehrana WHERE id_prehrana=?");
			ps.setInt(1, id_prehrana);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				ret = new Prehrana(rs.getInt("id_prehrana"), rs.getString("naslovPrehrane"), rs.getString("content"), rs.getString("tipSlike"), null);
				
				Blob blob = rs.getBlob("thumbnail");
				int blobLength = (int) blob.length();  
				byte[] blobAsBytes = blob.getBytes(1, blobLength);
					
				ret.setThumbnail(blobAsBytes);
				
				ret.setPrehranskiProgrami(pD.najdiVsePoPrehrani(ret.getId_prehrana()));				
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return ret;
	}
	
	public void shrani(Prehrana p)throws Exception {
		log.info("PrehranaDAO: shranjujem " + p);
		Connection conn=null;
		try {
			conn=baza.getConnection();
			if(najdi(p.getId_prehrana()) != null) {
				//že obstaja, update
			} else { //ne obstaja, insert
				PreparedStatement ps = conn.prepareStatement("INSERT INTO Prehrana(id_prehrana, naslovPrehrane, thumbnail, content, tipSlike) VALUES (?,?,?,?,?)");
		        ps.setInt(1, p.getId_prehrana());
		        ps.setString(2, p.getNaslovPrehrane());
		        ps.setBinaryStream(3, p.getThumbnail().getBinaryStream());
		        ps.setString(4, p.getContent());
		        ps.setString(5, p.getTipSlike());
		        
				ps.executeUpdate();
				
				for(Program pr : p.getPrehranskiProgrami())
					pD.shrani(pr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public ArrayList<Prehrana> vrniVse() throws Exception {
		log.info("PrehranaDAO: vrniVse ");
		ArrayList<Prehrana> seznam = new ArrayList<Prehrana>();
	
		Connection conn=null;
		try {
			conn=baza.getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Prehrana");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Prehrana prehrana = new Prehrana(rs.getInt("id_prehrana"), rs.getString("naslovPrehrane"), rs.getString("content"), rs.getString("tipSlike"), null);
				
				Blob blob = rs.getBlob("thumbnail");
				int blobLength = (int) blob.length();  
				byte[] blobAsBytes = blob.getBytes(1, blobLength);
				
				prehrana.setThumbnail(blobAsBytes);
				
				prehrana.setPrehranskiProgrami(pD.najdiVsePoPrehrani(prehrana.getId_prehrana()));

				seznam.add(prehrana);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return seznam;
	}

	public ProgramDAO getpD() {
		return pD;
	}
	public void setpD(ProgramDAO pD) {
		this.pD = pD;
	}	
}