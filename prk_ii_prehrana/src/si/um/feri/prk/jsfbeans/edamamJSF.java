package si.um.feri.prk.jsfbeans;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.common.io.ByteStreams;

import si.um.feri.prk.dao.ReceptDAO;
import si.um.feri.prk.dao.SestavineDAO;
import si.um.feri.prk.objekti.Recept;
import si.um.feri.prk.objekti.Sestavine;

@ManagedBean(name="edamam")
@SessionScoped
public class edamamJSF {
public static ReceptDAO rd = ReceptDAO.getInstance();
public static SestavineDAO sd = SestavineDAO.getInstance();

	
	public void napolniBazo() {
		
		String url1="https://api.edamam.com/search?q=";
		String url2="&app_id=47163ebb&app_key=f8c22820b24f5529eaca625718062654&from=0&to=5";
		
		//System.out.println("Vnesi sestavino: ");
		//String urlBeseda=scanner.next();
		
		
		Client client = ClientBuilder.newClient();
		
		
		
		//String json = response.toString();
		//System.out.println("json: "+json);
		
		//SestavineDAO sd = SestavineDAO.getInstance();
		String[] besede = {"chicken", "milk", "yoghurt", "strawberries", "corn", "tomato"};
		
		for(int j = 0; j < besede.length; j++) {
			try {
				WebTarget target = client.target(url1+besede[j]+url2);
				Response response = target.request().get();
				String json = response.readEntity(String.class);
				System.out.println("json: "+json);
				JSONObject jobject = new JSONObject(json);
				JSONArray jarray = jobject.getJSONArray("hits");
				
				for(int k = 0; k < jarray.length(); k++) {
					System.out.println("objekt: "+jarray.getJSONObject(k));
					System.out.println("naziv: "+jarray.getJSONObject(k).getJSONObject("recipe").getString("label"));
					
					JSONObject recept = jarray.getJSONObject(k).getJSONObject("recipe");
					
					String ime = recept.getString("label");
					System.out.println("ime "+ime);
					String slika = recept.getString("image");
					double kalorije = recept.getDouble("calories");
					int dolzinaPriprave = recept.getInt("totalTime");
					System.out.println("kal: "+kalorije);
					JSONObject total = recept.getJSONObject("totalNutrients");//u gramima
					JSONObject mascobe = total.getJSONObject("FAT");
					double mascobeQ = mascobe.getDouble("quantity");
					System.out.println("mascobe: "+mascobeQ);
					JSONObject sladkor = total.getJSONObject("SUGAR");
					double sladkorQ = sladkor.getDouble("quantity");
					Calendar datumDodajanja = Calendar.getInstance();
					
					Recept r = new Recept();
					r.setIme(ime);
					r.setKalorije(kalorije);
					r.setSladkorji(sladkorQ);
					r.setDolzinaPriprave(dolzinaPriprave);
					r.setDatumDodajanja(datumDodajanja);
					r.setLinkVideo("");
					r.setOpis(recept.getString("url"));
					r.setSteviloPorcij(0);
					r.setKategorija("ostalo");
					r.setTipSlike("image/jpeg");
					InputStream iStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/img/default-tall.jpg");
					r.setSlika(ByteStreams.toByteArray(iStream));
				
					int id = rd.shraniInVrniID(r);
				
					ArrayList<Sestavine> ss = new ArrayList<Sestavine>();
					JSONArray sestavine = recept.getJSONArray("ingredients");
					for(int i = 0; i < sestavine.length(); i++) {
						JSONObject myJson = sestavine.getJSONObject(i);
						Sestavine s = new Sestavine();
						s.setEnota_kolicine("g");
						s.setKolicina(myJson.getDouble("weight"));
						s.setIme(myJson.getString("text"));
						s.setKalorije(0);
						s.setSladkorji(0);
						s.setTk_recept_id(id);
						ss.add(s);
						sd.shrani(s);
					}
				
						
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	}
}
