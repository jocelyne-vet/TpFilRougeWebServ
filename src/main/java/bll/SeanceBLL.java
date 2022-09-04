package bll;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bo.cinemas.Seance;
import dal.GenericDAO;
import dal.GenericDAOHibernateImpl;


public class SeanceBLL {
	
	
	private GenericDAO<Seance> dao;
	
	public SeanceBLL() {
		dao = new GenericDAOHibernateImpl<>(Seance.class);
	}
	
	public List<Seance> selectAllSeancesByFilm(int idFilm, int idCinema){
		
		Map<String,Object> critere = new HashMap<>();
		critere.put("varId", idFilm);
		return dao.findWithParam("findSeancesByFilm", critere);
	}
	
	public Seance selectById(int idseance) {
		return dao.findById(idseance);
	}

	public void updateSeance(int idSeance, int nbPlaces) {
		//dao.updateSeance(idSeance,nbPlaces);
		
	}
	
	public List<Seance> selectAllSeanceFilmBySalleId(int idFilm, int idSalle){
		
		Map<String,Object> critere = new HashMap<>();
		critere.put("varIdSalle", idSalle);
		critere.put("varIdFilm", idFilm);
		return dao.findWithParam("findAllSeancesBySalleByFilm", critere);
	}
	
	public void update(Seance seance) {
		dao.update(seance);
		
	}
	
	public void insert(Seance seance) {
		dao.insert(seance);
		
	}
	
	public void delete(int id) {
		dao.delete(id);
		
	}

	public void deleteSeancesByFilm(int idFilm) {
		String requete = "Delete from Seance p where p.film.id ="+idFilm;
		dao.majQuery(requete);
	}
	
	public void deleteSeancesBySalle(int idSalle) {
		String requete = "Delete from Seance p where p.salle.id ="+idSalle;
		dao.majQuery(requete);
	}
}
