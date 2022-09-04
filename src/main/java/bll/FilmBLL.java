package bll;

import java.util.List;

import bo.cinemas.Film;

import dal.GenericDAO;
import dal.GenericDAOHibernateImpl;

public class FilmBLL {
	
	private GenericDAO<Film> dao;
	
	public FilmBLL() {
		dao = new GenericDAOHibernateImpl<>(Film.class);
	}

	public void insert(Film film) {
		dao.insert(film);
	}
	
	public void update(Film film) {
		dao.update(film);
	}
	
	public void delete(int id) {
		dao.delete(id);
	}
	
	public void deleteFilmBySalle(int idSalle) {
		
		String requete = "DELETE from Film p where p.id in (select p.film.id from Seance p join p.salle where p.salle.id ="+idSalle+")";
		dao.majQuery(requete);
		//dao.deleteFilmBySalle(idSalle);
	}

	public List<Film> selectAll() {
		return dao.findAll("findAllFilm");
	}
	
	public Film selectById(int id) {
		return dao.findById(id);
	}
}
