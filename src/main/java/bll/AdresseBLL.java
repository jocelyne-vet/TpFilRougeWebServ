package bll;

import bo.util.Adresse;

import dal.GenericDAO;
import dal.GenericDAOHibernateImpl;

public class AdresseBLL {
    private GenericDAO<Adresse> dao;
    
	public AdresseBLL() {
		dao = new GenericDAOHibernateImpl<>(Adresse.class);
	}
	
	public void insert(Adresse adresse) {
		 dao.insert(adresse);
	}
	
	public void update(Adresse adresse) {
		dao.update(adresse);
	}
	
	public void deleteByIdPersonne(int idPersonne) {
		dao.delete(idPersonne);
	}
}
