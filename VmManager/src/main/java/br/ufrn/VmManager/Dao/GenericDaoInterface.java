package br.ufrn.VmManager.Dao;

import java.util.List;



import br.ufrn.VmManager.Exceptions.DaoException;


public interface GenericDaoInterface <T>{

public void save(T entity) throws DaoException;
	
	public void delete(T entity) throws DaoException;
	
	public List<T> listAll(Class<T> clas) throws DaoException; 
	
	public void update(T entity) throws DaoException;
	
	public T search(T entity) throws DaoException;
}
