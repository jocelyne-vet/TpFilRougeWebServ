package bll;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import bo.cinemas.Cinema;

import dal.GenericDAO;
import dal.GenericDAOHibernateImpl;


public class CinemaBLL {
	
	private GenericDAO<Cinema> dao; 

	
	public CinemaBLL() {
		dao = new GenericDAOHibernateImpl<>(Cinema.class);
	}
	
	public Cinema selectByIdCritere(int id, String critere){
		Map<String, Object> mapCritere = new HashMap<>();
		mapCritere.put("varNom", "%"+critere+"%");
		mapCritere.put("varId", id);
		Cinema cinema = dao.selectQueryWithParam("findFilmsByCinemaByCritere", mapCritere);
		return cinema;
	}
	
	public List<Cinema> selectCinemasCritere(String critere){
			
		Map<String, Object> mapCritere = new HashMap<>();
		mapCritere.put("varNom", "%"+critere+"%");
		mapCritere.put("varVille","%"+ critere+"%");
		List<Cinema> cinemas = dao.findWithParam("findCinemasByCritere", mapCritere);
		
		return cinemas;
	}
	
	public Cinema selectById(int id){
		return dao.findById(id);
		
	}
	
	public Cinema selectByIdGerant(int idGerant){
		Map<String, Object> mapCritere = new HashMap<>();

		mapCritere.put("varId",idGerant);
		Cinema cinema = dao.selectQueryWithParam("findFilmsByCinemaByIdGerant", mapCritere);
		
		return cinema;
	}
	
	public List<Cinema> selectAll( ){
		return dao.findAll("findAllCinema");
	}
	
	public void insert(Cinema cinema) {
		dao.insert(cinema);
	}
	
	public void update(Cinema cinema) {
		dao.update(cinema);
	}
	
	public void delete(int id) {
		dao.delete(id);
	}
	
	public Cinema selectCinemaById(int id) {
		return dao.findById(id);
	}
}
