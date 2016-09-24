package br.ufrn.VmManager.Dao;

import java.util.List;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import br.ufrn.VmManager.Exceptions.DaoException;

public abstract class GenericDaoDb4o<T> implements GenericDaoInterface<T> {

	private ObjectContainer db = null;
	private String dbName;

	public GenericDaoDb4o(String dbName) {
		this.dbName = dbName;
	}

	protected synchronized void openDb() throws DaoException {
		try {
			if (db == null) {
				db = Db4o.openFile(dbName);
			}
		} catch (Exception e) {
			throw new DaoException(e.getMessage());
		}
	}

	protected synchronized void closeDb() throws DaoException {
		try {
			// db.close();
		} catch (Exception e) {
			throw new DaoException(e.getMessage());
		}
	}

	public synchronized void save(T entity) throws DaoException {
		try {
			openDb();
			db.set(entity);
			db.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		} finally {
			closeDb();
		}
	}

	public synchronized void delete(T entity) throws DaoException {
		try {
			openDb();
			db.delete(entity);
			db.commit();
		} catch (Exception e) {

			e.printStackTrace();
			throw new DaoException(e.getMessage());
		} finally {
			closeDb();
		}
	}

	public synchronized void update(T entity) throws DaoException {
		save(entity);

	}

	public synchronized List<T> listAll(Class<T> clas) throws DaoException {
		try {
			openDb();
			ObjectSet<T> objs = db.get(clas);
			return objs.subList(0, objs.size());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		} finally {
			closeDb();
		}

	}

	public T search(T entity) throws DaoException {
		try {
			openDb();
			ObjectSet<T> objs = db.get(entity);
			if (objs != null) {
				if (objs.size() > 1) {
					throw new DaoException(
							"mais de um objeto satisfazem os criterios de busca!");
				} else if (objs.size() > 0) {
					return objs.next();
				}

			}
			return null;

		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		} finally {
			closeDb();
		}
	}

}
