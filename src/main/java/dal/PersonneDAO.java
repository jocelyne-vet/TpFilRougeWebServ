package dal;

import java.util.List;
import java.util.Map;

import bo.cinemas.Cinema;
import bo.cinemas.Seance;
import bo.personnes.Client;
import bo.personnes.Personne;

public interface PersonneDAO {
	
	Personne existPersonne(String email, String motDePasse );
	Personne selectById(int id);
	void insertPersonne(Personne personne);
	void updatePersonne(Personne personne);
	List<Personne> selectAll();
	void deletePersonne(int id);
	
	// GERANT
	List<Personne> selectGerants(int id);
	
	//Reservation
	void insertReservation(int idSeance, int idClient, int nbPlaces);
	Map<Client, Integer> selectClientsInscrits(int idSeance);
	void selectMesReservations(Client client);
	boolean existReservationByIdSeanceIdClient(int idSeance, int idClient);
	void updateReservations(int idSeance, int idClient, int nbPlaces);
	Map<Seance, Integer> selectHistoriqueReservation(int idClient);
}
