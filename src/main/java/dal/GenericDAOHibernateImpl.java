package dal;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;



public class GenericDAOHibernateImpl<T> implements GenericDAO<T> {

	private EntityManagerFactory emf;
	private Class<T> item;

	public GenericDAOHibernateImpl(Class<T> item) {
		emf = Persistence.createEntityManagerFactory("user_bdd");
		this.item = item;
	}

	@Override
	public List<T> findAll(String requete) {
		EntityManager em = emf.createEntityManager();

		TypedQuery<T> query = em.createNamedQuery(requete, item);
		List<T> resultat = query.getResultList();
		em.close();
		return resultat;

	}

	@Override
	public T findById(int id) {
		EntityManager em = emf.createEntityManager();
		T t = em.find(item, id);
		em.close();
		return t;
	}

	@Override
	public void insert(T t) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		transaction.begin();
		try {
			em.persist(t);

			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();

			transaction.rollback();
		}
		em.close();

	}

	@Override
	public void update(T t) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		// Debut de la transaction
		transaction.begin();
		try {

			em.merge(t);
			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
			// En cas d'echec, on "rollback" -> on annule la modification aupres de la bdd
			transaction.rollback();
		}
		em.close();

	}

	@Override
	public void delete(T t) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		// Debut de la transaction
		transaction.begin();
		try {

			em.remove(em.merge(t));

			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			// En cas d'echec, on "rollback" -> on annule la modification aupres de la bdd
			transaction.rollback();
		}
		em.close();
	}

	@Override
	public void delete(int id) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		// Debut de la transaction
		transaction.begin();
		try {
			String requete = "Delete From " + item.getName() + " p WHERE id = :id";
			Query query = em.createQuery(requete);
			query.setParameter("id", id);
			query.executeUpdate();

			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			// En cas d'echec, on "rollback" -> on annule la modification aupres de la bdd
			transaction.rollback();
		}
		em.close();

	}

	@Override
	public List<T> findWithParam(String requete, Map<String, Object> param) {
		EntityManager em = emf.createEntityManager();

		TypedQuery<T> query = em.createNamedQuery(requete, item);
		for(var entry : param.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		
		List<T> resultat = query.getResultList();
		em.close();
		return resultat;
	}

	@Override
	public T selectQueryWithParam(String requete, Map<String, Object> param) {
		EntityManager em = emf.createEntityManager();

		TypedQuery<T> query = em.createNamedQuery(requete, item);
		for(var entry : param.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		T t;
		List<T> resultat = query.getResultList();
		if (resultat == null || resultat.isEmpty()) {
	        t = null;
	    }else {
	    	t = resultat.get(0);
	    }

		em.close();
		return t;
	}

	@Override
	public void majQuery(String requete) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		// Debut de la transaction
		transaction.begin();
		try {
			Query query = em.createQuery(requete);
			query.executeUpdate();

			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			// En cas d'echec, on "rollback" -> on annule la modification aupres de la bdd
			transaction.rollback();
		}
		em.close();
		
	}

}
