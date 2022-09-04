package dal;

import java.util.List;
import java.util.Map;

public interface GenericDAO <T>{
	List<T> findAll(String requete);
	List<T> findWithParam(String requete, Map<String,Object> param);
	T findById(int id);
	void insert(T t);
	void update(T t);
	void delete(T t);
	void delete(int id);
	T selectQueryWithParam(String requete, Map<String,Object> param);
    void majQuery(String requete);
}
