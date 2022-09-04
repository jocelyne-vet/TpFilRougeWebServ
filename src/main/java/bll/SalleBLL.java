package bll;

import java.util.HashMap;
import java.util.Map;

import bo.cinemas.Salle;
import dal.GenericDAO;
import dal.GenericDAOHibernateImpl;


public class SalleBLL {
	
	private GenericDAO<Salle> dao;
	
	public SalleBLL() {
		
		dao = new GenericDAOHibernateImpl<>(Salle.class);
	}
	
	public void insert(Salle salle) {
		
		dao.insert(salle);
	}
	
	public void update(Salle salle) {
		dao.update(salle);
	}
	
	public void deleteById(int id) {
		dao.delete(id);
	}
	
	public Salle selectById(int id) {
		return dao.findById(id);
	}
	
	public boolean existNumeroSalle(int numero, int idCinema) {
		Map<String, Object> critere = new HashMap<>();
		critere.put("varId", idCinema);
		critere.put("varNumero",numero);
		Salle s =  dao.selectQueryWithParam("existNumero", critere);
		boolean bExist=false;
		if(s!=null) {
			bExist=true;
		}
		return bExist;
	}
	
//	public Salle selectSalleSeanceFilmById(int id) {
//		return daoG.selectSalleSeanceFilmById(id);
//	}
}
