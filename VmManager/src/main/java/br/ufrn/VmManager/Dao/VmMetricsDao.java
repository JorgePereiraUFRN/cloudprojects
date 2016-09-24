package br.ufrn.VmManager.Dao;

import br.ufrn.VmManager.model.VmMetric;

public class VmMetricsDao extends GenericDaoDb4o<VmMetric> implements VmMetricDaoInterface {

	public VmMetricsDao() {
		super(VmMetric.class.getSimpleName());
		
	}

	

}
