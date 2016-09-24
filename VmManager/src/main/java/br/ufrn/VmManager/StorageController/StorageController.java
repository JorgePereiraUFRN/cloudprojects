package br.ufrn.VmManager.StorageController;

import java.util.List;

import org.apache.log4j.Logger;
import org.omg.CosNaming.IstringHelper;

import br.ufrn.VmManager.Dao.StorageMetricDaoInterface;
import br.ufrn.VmManager.Dao.StorageMetricsDao;
import br.ufrn.VmManager.Exceptions.DaoException;
import br.ufrn.VmManager.VmController.VmController;
import br.ufrn.VmManager.model.StorageMetric;

public class StorageController {
	
	private static final StorageController STORAGE_CONTROLLER = new StorageController();
	private static StorageMetricDaoInterface storageMetricDao;
	private StorageProperties properties;
	
	private Logger logger = Logger.getLogger(StorageController.class);
	
	private StorageController(){
		properties = StorageProperties.getInstance();
		storageMetricDao = new StorageMetricsDao();
	}
	
	public static StorageController getInstance(){
		return STORAGE_CONTROLLER;
	}
	
	
	public void analyzeStorageMetrics(String vmIpAddress, StorageMetric metric){
		
		try {
			StorageMetric storageMetric = storageMetricDao.search(new StorageMetric(vmIpAddress, null, null));
			
			if(storageMetric != null){
				
				
				storageMetricDao.update(storageMetric);
				
				
				List<StorageMetric> stMetrics = storageMetricDao.listAll(StorageMetric.class);
				
				if(stMetrics != null && stMetrics.size() > 0){
					
					float storageThresold = 0;
					for(StorageMetric stm : stMetrics){
						
						storageThresold += stm.getStorageUsage();
					}
					
					storageThresold = storageThresold / stMetrics.size();
					
					if(storageThresold > properties.getStorageThreshold()){
						alocateStorage();
					}
				}
				
			}
			
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void alocateStorage(){
		logger.info("alocando mais espaço para armazenamento");
	}

}
