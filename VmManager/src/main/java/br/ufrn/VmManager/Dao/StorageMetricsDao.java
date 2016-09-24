package br.ufrn.VmManager.Dao;


import br.ufrn.VmManager.model.StorageMetric;

public class StorageMetricsDao extends GenericDaoDb4o<StorageMetric> implements StorageMetricDaoInterface {

	public StorageMetricsDao() {
		super(StorageMetric.class.getSimpleName());
		
	}

	

}
