package it.polito.tdp.poweroutages.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import it.polito.tdp.poweroutages.model.Adiacenza;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class PowerOutagesDAO {
	
	public List<Nerc> loadAllNercs(Map<Integer,Nerc> idMapNerc) {

		String sql = "SELECT id, value FROM Nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				if(!idMapNerc.containsKey(n.getId()))
					idMapNerc.put(n.getId(), n);
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	public List<Adiacenza> getArchi(Map<Integer, Nerc> idMapNerc){
		String sql="SELECT nr.nerc_one n1, nr.nerc_two n2, COUNT(DISTINCT YEAR(p1.date_event_began), MONTH(p1.date_event_began)) as peso " + 
				"FROM poweroutages p1, poweroutages p2, nercrelations nr " + 
				"WHERE nr.nerc_one=p1.nerc_id AND nr.nerc_two=p2.nerc_id " + 
				"GROUP BY nr.nerc_one, nr.nerc_two";
		List<Adiacenza> adiacenze=new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Adiacenza a= new Adiacenza(idMapNerc.get(res.getInt("n1")),idMapNerc.get(res.getInt("n2")),res.getDouble("peso"));
				adiacenze.add(a);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return adiacenze;
	}
	public List<PowerOutage> getAllOutages(Map<Integer, Nerc> idMapNerc){
		String sql="SELECT * " + 
				"FROM PowerOutages";
		List<PowerOutage> outages=new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				PowerOutage po=new PowerOutage(res.getInt("id"),idMapNerc.get(res.getInt("nerc_id")), 
						LocalDateTime.of(res.getDate("date_event_began").toLocalDate(), res.getTime("date_event_began").toLocalTime()),
						LocalDateTime.of(res.getDate("date_event_finished").toLocalDate(), res.getTime("date_event_finished").toLocalTime()));
				outages.add(po);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return outages;
	}
}
