package dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bo.cinemas.Cinema;
import bo.cinemas.Film;
import bo.cinemas.Salle;
import bo.cinemas.Seance;
import bo.personnes.Administrateur;
import bo.personnes.Client;
import bo.personnes.Gerant;
import bo.personnes.Personne;
import bo.util.Adresse;


public class PersonneDAOJdbcImpl implements PersonneDAO {
	private DataSource dataSource;
	private final String EXIST_PERSONNE = "select * from personnes p "
			+ " inner join adresses a on p.id_adresse = a.id" + " where p.email= ? and p.mdp = ?";
	private final String SELECT_BY_ID = "select * from personnes p "
			+ " inner join adresses a on p.id_adresse = a.id" + " where p.id = ?";

	private final String INSERT_PERSONNE = "insert into personnes (nom, prenom, id_adresse, mdp, id_role, email, date_naissance) "
			+ " values (?, ?, ?, ?, ?,?, ?)";

	private final String UPDATE_PERSONNE = "update personnes set nom = ?, prenom = ?, id_adresse = ?, mdp = ?, id_role = ?, email = ?, date_naissance =?"
			+ " where id = ?";

	private final String SELECT_ALL = "select * from personnes p "
			+ " inner join adresses a on p.id_adresse = a.id order by nom";
	private final String DELETE = "delete from personnes where id = ?";

	// gerant
	private final String SELECT_GERANT = "select * from personnes p inner join adresses a on a.id = p.id_adresse"
			+ "			 where role='Gerant' and  p.id not in (select id_gerant from cinemas)"
			+ "			 union (select * from personnes p inner join adresses a on a.id = p.id_adresse where p.id=? )";

	// reservations
	private final String SELECT_CLIENT_INSTCRITS = "select * from reservations where id_seance = ?";

	private final String SELECT_RESERVATIONS = "select * from reservations where id_personne = ?";

	private final String INSERT_RESERVATION = "insert into reservations (id_seance, id_personne, nb_places) values (?,?,?)";

	private final String EXIST_RESERVATION = "select * from reservations where id_seance=? and id_personne=?";

	private final String UPDATE_RESERVATIONS = "update reservations set nb_places = ? where id_personne = ? and id_seance = ?";

	private final String SELECT_HISTORIQUE_RESERVATIONS = "SELECT * " + "FROM reservations r "
			+ "inner join seances se  on r.id_seance = se.id " + "inner join films f on se.id_film = f.id "
			+ "WHERE r.id_personne = ?  order by se.date_seance, se.heure";

	public PersonneDAOJdbcImpl() {
		Context context;
		try {
			context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/admin");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Personne existPersonne(String email, String motDePasse) {
		Personne maPersonne = null;
		try {
			Connection cnx = dataSource.getConnection();
			PreparedStatement pst = cnx.prepareStatement(EXIST_PERSONNE);
			pst.setString(1, email);
			pst.setString(2, motDePasse);

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
//				Role monRole = new Role();
//				monRole.setId(rs.getInt("r.id"));
//				monRole.setLibelle(rs.getString("r.libelle"));
				String role = rs.getString("role");
				Adresse monAdresse = new Adresse();
				monAdresse.setId(rs.getInt("a.id"));
				monAdresse.setNumero(rs.getInt("a.numero_voie"));
				monAdresse.setTypeRue(rs.getString("a.type_voie"));
				monAdresse.setNomRue(rs.getString("a.nom_voie"));
				monAdresse.setCpo(rs.getString("a.code_postal"));
				monAdresse.setVille(rs.getString("a.ville"));

				switch (role) {
				case "Client":
					maPersonne = new Client();
					maPersonne.setId(rs.getInt("p.id"));
					maPersonne.setNom(rs.getString("nom"));
					maPersonne.setPrenom(rs.getString("prenom"));
					maPersonne.setEmail(rs.getString("email"));
					maPersonne.setMotdePasse(rs.getString("mdp"));
					maPersonne.setAdresse(monAdresse);
					
					maPersonne.setDateNaissance(rs.getDate("date_naissance").toLocalDate());
					break;
				case "Gerant":
					maPersonne = new Gerant();
					maPersonne.setId(rs.getInt("p.id"));
					maPersonne.setNom(rs.getString("nom"));
					maPersonne.setPrenom(rs.getString("prenom"));
					maPersonne.setEmail(rs.getString("email"));
					maPersonne.setMotdePasse(rs.getString("mdp"));
					maPersonne.setAdresse(monAdresse);
				
					maPersonne.setDateNaissance(rs.getDate("date_naissance").toLocalDate());
					break;
				case "Administrateur":
					maPersonne = new Administrateur();
					maPersonne.setId(rs.getInt("p.id"));
					maPersonne.setNom(rs.getString("nom"));
					maPersonne.setPrenom(rs.getString("prenom"));
					maPersonne.setEmail(rs.getString("email"));
					maPersonne.setMotdePasse(rs.getString("mdp"));
					maPersonne.setAdresse(monAdresse);
					
					maPersonne.setDateNaissance(rs.getDate("date_naissance").toLocalDate());
					break;
				default:
					throw new Exception("Pas de role créer pour cette personne");
				}
			}
			cnx.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return maPersonne;
	}

	@Override
	public Personne selectById(int id) {
		Personne maPersonne = null;
		try {
			Connection cnx = dataSource.getConnection();
			PreparedStatement pst = cnx.prepareStatement(SELECT_BY_ID);
			pst.setInt(1, id);

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
//				Role monRole = new Role();
//				monRole.setId(rs.getInt("r.id"));
//				monRole.setLibelle(rs.getString("r.libelle"));
				String role = rs.getString("role");
				Adresse monAdresse = new Adresse();
				monAdresse.setId(rs.getInt("a.id"));
				monAdresse.setNumero(rs.getInt("a.numero_voie"));
				monAdresse.setTypeRue(rs.getString("a.type_voie"));
				monAdresse.setNomRue(rs.getString("a.nom_voie"));
				monAdresse.setCpo(rs.getString("a.code_postal"));
				monAdresse.setVille(rs.getString("a.ville"));

				switch (role) {
				case "Client":
					maPersonne = new Client();
					maPersonne.setId(rs.getInt("p.id"));
					maPersonne.setNom(rs.getString("nom"));
					maPersonne.setPrenom(rs.getString("prenom"));
					maPersonne.setEmail(rs.getString("email"));
					maPersonne.setMotdePasse(rs.getString("mdp"));
					maPersonne.setAdresse(monAdresse);
					
					maPersonne.setDateNaissance(rs.getDate("date_naissance").toLocalDate());
					break;
				case "Gerant":
					maPersonne = new Gerant();
					maPersonne.setId(rs.getInt("p.id"));
					maPersonne.setNom(rs.getString("nom"));
					maPersonne.setPrenom(rs.getString("prenom"));
					maPersonne.setEmail(rs.getString("email"));
					maPersonne.setMotdePasse(rs.getString("mdp"));
					maPersonne.setAdresse(monAdresse);
				
					maPersonne.setDateNaissance(rs.getDate("date_naissance").toLocalDate());
					break;
				case "Administrateur":
					maPersonne = new Administrateur();
					maPersonne.setId(rs.getInt("p.id"));
					maPersonne.setNom(rs.getString("nom"));
					maPersonne.setPrenom(rs.getString("prenom"));
					maPersonne.setEmail(rs.getString("email"));
					maPersonne.setMotdePasse(rs.getString("mdp"));
					maPersonne.setAdresse(monAdresse);
					
					maPersonne.setDateNaissance(rs.getDate("date_naissance").toLocalDate());
					break;
				default:
					throw new Exception("Pas de role créer pour cette personne");
				}
			}
			cnx.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return maPersonne;
	}

	@Override
	public void insertPersonne(Personne personne) {
		try {
			Connection cnx = dataSource.getConnection();
			PreparedStatement pst = cnx.prepareStatement(INSERT_PERSONNE, PreparedStatement.RETURN_GENERATED_KEYS);
			pst.setString(1, personne.getNom());
			pst.setString(2, personne.getPrenom());

			pst.setInt(3, personne.getAdresse().getId());
			pst.setString(4, personne.getMotdePasse());
//			pst.setString(5, personne.getRole());
			pst.setString(6, personne.getEmail());
			pst.setDate(7, Date.valueOf(personne.getDateNaissance()));
			
			// executeUpdate est utilis� lorsque je modifie le contenu de la BDD
			pst.executeUpdate();

			// A partir d'ici, les �tapes permettent de r�cup�rer l'id auto-g�n�r� par la
			// BDD
			ResultSet rs = pst.getGeneratedKeys();

			if (rs.next()) {
				personne.setId(rs.getInt(1));
			}

			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updatePersonne(Personne personne) {
		try {
			Connection cnx = dataSource.getConnection();
			PreparedStatement pst = cnx.prepareStatement(UPDATE_PERSONNE);
			pst.setString(1, personne.getNom());
			pst.setString(2, personne.getPrenom());

			pst.setInt(3, personne.getAdresse().getId());
			pst.setString(4, personne.getMotdePasse());
//			pst.setString(5, personne.getRole());
			pst.setString(6, personne.getEmail());
			pst.setDate(7, Date.valueOf(personne.getDateNaissance()));
			pst.setInt(8, personne.getId());

			// executeUpdate est utilis� lorsque je modifie le contenu de la BDD
			pst.executeUpdate();

			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Personne> selectAll() {
		List<Personne> personnes = new ArrayList<>();
		try {
			Connection cnx = dataSource.getConnection();
			PreparedStatement pst = cnx.prepareStatement(SELECT_ALL);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Personne maPersonne = null;
//				Role monRole = new Role();
////				monRole.setId(rs.getInt("r.id"));
//				monRole.setLibelle(rs.getString("r.libelle"));
				String role = rs.getString("role");
				Adresse monAdresse = new Adresse();
				monAdresse.setId(rs.getInt("a.id"));
				monAdresse.setNumero(rs.getInt("a.numero_voie"));
				monAdresse.setTypeRue(rs.getString("a.type_voie"));
				monAdresse.setNomRue(rs.getString("a.nom_voie"));
				monAdresse.setCpo(rs.getString("a.code_postal"));
				monAdresse.setVille(rs.getString("a.ville"));

				switch (role) {
				case "Client":
					maPersonne = new Client();
					maPersonne.setId(rs.getInt("p.id"));
					maPersonne.setNom(rs.getString("nom"));
					maPersonne.setPrenom(rs.getString("prenom"));
					maPersonne.setDateNaissance(rs.getDate("date_naissance").toLocalDate());
					maPersonne.setEmail(rs.getString("email"));
					maPersonne.setMotdePasse(rs.getString("mdp"));
					maPersonne.setAdresse(monAdresse);
				
					break;
				case "Gerant":
					maPersonne = new Gerant();
					maPersonne.setId(rs.getInt("p.id"));
					maPersonne.setNom(rs.getString("nom"));
					maPersonne.setPrenom(rs.getString("prenom"));
					maPersonne.setDateNaissance(rs.getDate("date_naissance").toLocalDate());
					maPersonne.setEmail(rs.getString("email"));
					maPersonne.setMotdePasse(rs.getString("mdp"));
					maPersonne.setAdresse(monAdresse);
					
					break;
				case "Administrateur":
					maPersonne = new Administrateur();
					maPersonne.setId(rs.getInt("p.id"));
					maPersonne.setNom(rs.getString("nom"));
					maPersonne.setPrenom(rs.getString("prenom"));
					maPersonne.setDateNaissance(rs.getDate("date_naissance").toLocalDate());
					maPersonne.setEmail(rs.getString("email"));
					maPersonne.setMotdePasse(rs.getString("mdp"));
					maPersonne.setAdresse(monAdresse);
					
					break;
				default:
					throw new Exception("Pas de role créer pour cette personne");
				}
				personnes.add(maPersonne);
			}
			cnx.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return personnes;
	}

	@Override
	public void deletePersonne(int id) {
		Connection cnx;
		try {
			cnx = dataSource.getConnection();

			PreparedStatement pst = cnx.prepareStatement(DELETE);

			pst.setInt(1, id);

			// executeUpdate est utilis� lorsque je modifie le contenu de la BDD
			pst.executeUpdate();
			cnx.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// GERANT
	@Override
	public List<Personne> selectGerants(int id) {
		// TODO Auto-generated method stub
		List<Personne> gerants = new ArrayList<>();

		Connection cnx;

		try {
			cnx = dataSource.getConnection();
			PreparedStatement pst = cnx.prepareStatement(SELECT_GERANT);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Gerant gerant = new Gerant();
				gerant.setId(rs.getInt("id"));
				gerant.setNom(rs.getString("nom"));
				gerant.setPrenom(rs.getString("prenom"));
				gerants.add(gerant);
			}

			cnx.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return gerants;
	}

	// reservations

	@Override
	public void insertReservation(int idSeance, int idClient, int nbPlaces) {
		Connection cnx;
		try {
			cnx = dataSource.getConnection();
			PreparedStatement pst = cnx.prepareStatement(INSERT_RESERVATION);
			pst.setInt(1, idSeance);
			pst.setInt(2, idClient);
			pst.setInt(3, nbPlaces);

			pst.executeUpdate();

			cnx.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public Map<Client, Integer> selectClientsInscrits(int idSeance) {
		Map<Client, Integer> clientsInscrits = new HashMap<>();
		Connection cnx;
		try {
			cnx = dataSource.getConnection();
			PreparedStatement pst = cnx.prepareStatement(SELECT_CLIENT_INSTCRITS);
			pst.setInt(1, idSeance);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Client monClient = new Client();
				monClient.setId(rs.getInt("id_personne"));
				int nbPlaces = rs.getInt("nb_places");
				clientsInscrits.put(monClient, nbPlaces);
			}
			cnx.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clientsInscrits;
	}

	@Override
	public void selectMesReservations(Client client) {

		Map<Seance, Integer> reservations = new HashMap();
		Connection cnx;
		try {
			cnx = dataSource.getConnection();
			PreparedStatement pst = cnx.prepareStatement(SELECT_RESERVATIONS);
			pst.setInt(1, client.getId());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Seance maSeance = new Seance();
				maSeance.setId(rs.getInt("id_seance"));
				int nb_places = rs.getInt("nb_places");
				reservations.put(maSeance, nb_places);
			}

			client.setReservations(reservations);
			cnx.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean existReservationByIdSeanceIdClient(int idSeance, int idClient) {
		boolean bExist = false;

		Connection cnx;

		try {
			cnx = dataSource.getConnection();
			PreparedStatement pst = cnx.prepareStatement(EXIST_RESERVATION);
			pst.setInt(1, idSeance);
			pst.setInt(2, idClient);

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				bExist = true;
			}
			cnx.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bExist;
	}

	@Override
	public void updateReservations(int idSeance, int idClient, int nbPlaces) {
		Connection cnx;

		try {
			cnx = dataSource.getConnection();
			PreparedStatement pst = cnx.prepareStatement(UPDATE_RESERVATIONS);
			pst.setInt(1, nbPlaces);
			pst.setInt(2, idClient);
			pst.setInt(3, idSeance);

			pst.executeUpdate();

			cnx.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Map<Seance, Integer> selectHistoriqueReservation(int idClient) {
		Connection cnx;
		Map<Seance, Integer> reservations = new HashMap<>();
		try {
			cnx = dataSource.getConnection();
			PreparedStatement pst = cnx.prepareStatement(SELECT_HISTORIQUE_RESERVATIONS);
			pst.setInt(1, idClient);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
//				Cinema monCinema = new Cinema();
//				monCinema.setId(rs.getInt("c.id"));
//				monCinema.setNom(rs.getString("c.nom"));
//				monCinema.setAffiche(rs.getString("affiche"));
//				
//
//				Salle maSalle = new Salle();
//				maSalle.setId(rs.getInt("sa.id"));
//				maSalle.setNumero(rs.getInt("sa.numero"));
//				maSalle.setNombreDePlaces(rs.getInt("sa.nb_places"));

				Seance maSeance = new Seance();
				maSeance.setId(rs.getInt("se.id"));
				maSeance.setHeureDebut(LocalDateTime.of(rs.getDate("se.date_seance").toLocalDate(),
						rs.getTime("se.heure").toLocalTime()));
				maSeance.setNbInscrits(rs.getInt("se.nb_inscrits"));
				Film monFilm = new Film();
				monFilm.setId(rs.getInt("f.id"));
				monFilm.setNom(rs.getString("f.titre"));
				monFilm.setDescription(rs.getString("f.description"));
				monFilm.setAffiche(rs.getString("f.affiche"));
				monFilm.setDuree(rs.getInt("f.duree"));
				monFilm.setAgeMinimum(rs.getInt("f.age_minimum"));
				maSeance.setFilm(monFilm);
//				maSalle.getSeances().add(maSeance);
//				monCinema.salles.add(maSalle);
				
				int nbPlaces = rs.getInt("r.nb_places");
				reservations.put(maSeance, nbPlaces);

			}

			cnx.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reservations;
	}

}
