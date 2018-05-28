package si.um.feri.prk.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.prk.objekti.Recept;
import si.um.feri.prk.objekti.Enota;
import si.um.feri.prk.objekti.Sestavine;
import si.um.feri.prk.objekti.Alergeni;

public class ReceptDAO {

	DataSource baza;
	Logger log=LoggerFactory.getLogger(ReceptDAO.class);
	private AlergeniDAO aD = AlergeniDAO.getInstance();
	private SestavineDAO sD = SestavineDAO.getInstance();
	private EnotaDAO eD = EnotaDAO.getInstance();
		
	private static ReceptDAO instance=null;
	public static synchronized ReceptDAO getInstance() {		
		if (instance==null) instance=new ReceptDAO();
		return instance;
	}
		
	private ReceptDAO() {
		log.info("ReceptDAO: ReceptDAO ");
		try {
			baza=(DataSource)new InitialContext().lookup("java:jboss/datasources/prk_ii_prehrana");	
			kreirajTabele();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public void kreirajTabele() throws Exception {
		log.info("ReceptDAO: kreirajTabele ");
		Connection conn=null;
		try {
			conn=baza.getConnection();
			conn.createStatement().execute("CREATE TABLE IF NOT EXISTS Recept(id_recept int not null auto_increment primary key, ime varchar(100) not null, dolzinaPriprave int not null,steviloPorcij int not null, opis varchar(9999) not null, slika longblob not null, linkVideo varchar(700) not null, kalorije double not null, tipSlike varchar(20) not null, datumDodajanja timestamp not null, sladkorji double not null)");
			} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
		
	public void pobrisiTabele() throws Exception {
		log.info("ReceptDAO: pobrisiTabele ");
		Connection conn=null;
		try {
			conn=baza.getConnection();
			conn.createStatement().execute("DROP TABLE IF EXISTS Recept CASCADE");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
		
	public void shrani(Recept r) throws Exception {
		log.info("ReceptDAO: shranjujem " + r);
		Connection conn=null;
		try {
			conn=baza.getConnection();

			PreparedStatement ps = conn.prepareStatement("INSERT INTO Recept(id_recept, ime, dolzinaPriprave, steviloPorcij, opis, slika, linkVideo, kalorije, tipSlike, datumDodajanja, sladkorji) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, r.getId_recept());
			ps.setString(2, r.getIme());
			ps.setInt(3, r.getDolzinaPriprave());
			ps.setInt(4, r.getSteviloPorcij());
			ps.setString(5, r.getOpis());
			ps.setBinaryStream(6, r.getSlika().getBinaryStream());
			ps.setString(7, r.getLinkVideo());
			ps.setDouble(8, r.getKalorije());
			ps.setString(9, r.getTipSlike());
			ps.setTimestamp(10, new Timestamp(r.getDatumDodajanja().getTimeInMillis()));
			ps.setDouble(11, r.getSladkorji());
				
			ps.executeUpdate();
				
			for(Alergeni a : r.getAlergeni())
				aD.shrani(a);
				
			for(Sestavine s : r.getSestavine())
				sD.shrani(s);
			
			for(Enota e : r.getEnote())
				eD.shrani(e);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public int shraniInVrniID(Recept r) throws Exception {
		log.info("ReceptDAO: shranjujem " + r);
		Connection conn=null;
		int generiranID = 0;
		try {
			conn=baza.getConnection();

			PreparedStatement ps = conn.prepareStatement("INSERT INTO Recept(id_recept, ime, dolzinaPriprave, steviloPorcij, opis, slika, linkVideo, kalorije, tipSlike, datumDodajanja, sladkorji) VALUES (?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, r.getId_recept());
			ps.setString(2, r.getIme());
			ps.setInt(3, r.getDolzinaPriprave());
			ps.setInt(4, r.getSteviloPorcij());
			ps.setString(5, r.getOpis());
			ps.setBinaryStream(6, r.getSlika().getBinaryStream());
			ps.setString(7, r.getLinkVideo());
			ps.setDouble(8, r.getKalorije());
			ps.setString(9, r.getTipSlike());
			ps.setTimestamp(10, new Timestamp(r.getDatumDodajanja().getTimeInMillis()));
			ps.setDouble(11, r.getSladkorji());
				
			ps.executeUpdate();
				
			for(Alergeni a : r.getAlergeni())
				aD.shrani(a);
				
			for(Sestavine s : r.getSestavine())
				sD.shrani(s);
			
			for(Enota e : r.getEnote())
				eD.shrani(e);
			
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()){
				generiranID=rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return generiranID;
	}
	
	public Recept najdi(int id_recept) throws Exception {
		log.info("ReceptDAO: najdi " + id_recept);
		Recept ret = null;
		Connection conn=null;
		try {
			conn=baza.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Recept WHERE id_recept=?");
			ps.setInt(1, id_recept);
			ResultSet rs = ps.executeQuery();
				
			while(rs.next()) {
				ret = new Recept(rs.getInt("id_recept"), rs.getInt("dolzinaPriprave"), rs.getInt("steviloPorcij"), rs.getDouble("sladkorji"), rs.getString("ime"), rs.getString("opis"), rs.getString("linkVideo"), rs.getString("tipSlike"), null, rs.getDouble("kalorije"));
				
				Blob blob = rs.getBlob("slika");
				int blobLength = (int) blob.length();  
				byte[] blobAsBytes = blob.getBytes(1, blobLength);
					
				ret.setSlika(blobAsBytes);
					
				ret.setAlergeni(aD.najdiVsePoReceptu(ret.getId_recept()));
				ret.setSestavine(sD.najdiVsePoReceptu(ret.getId_recept()));
				ret.setEnote(eD.najdiVsePoReceptu(ret.getId_recept()));
				
				ret.getDatumDodajanja().setTimeInMillis(rs.getTimestamp("datumDodajanja").getTime());
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return ret;
	}
		
	public ArrayList<Recept> vrniVse() throws Exception {
		log.info("ReceptDAO: vrniVse ");
		ArrayList<Recept> seznam = new ArrayList<Recept>();
		
		Connection conn=null;
		try {
			conn=baza.getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Recept");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Recept recept = new Recept(rs.getInt("id_recept"), rs.getInt("dolzinaPriprave"), rs.getInt("steviloPorcij"), rs.getDouble("sladkorji"), rs.getString("ime"), rs.getString("opis"), rs.getString("linkVideo"), rs.getString("tipSlike"), null, rs.getDouble("kalorije"));
					
				Blob blob = rs.getBlob("slika");
				int blobLength = (int) blob.length();  
				byte[] blobAsBytes = blob.getBytes(1, blobLength);
					
				recept.setSlika(blobAsBytes);
					
				recept.setAlergeni(aD.najdiVsePoReceptu(recept.getId_recept()));
				recept.setSestavine(sD.najdiVsePoReceptu(recept.getId_recept()));
				recept.setEnote(eD.najdiVsePoReceptu(recept.getId_recept()));
				
				recept.getDatumDodajanja().setTimeInMillis(rs.getTimestamp("datumDodajanja").getTime());
					
				seznam.add(recept);
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