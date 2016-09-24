package br.ufrn.VmManager.VmController;

import java.util.List;

import org.apache.log4j.Logger;

import br.ufrn.VmManager.CloudDriver.CloudDriverInterface;
import br.ufrn.VmManager.Dao.VirtualMachineDao;
import br.ufrn.VmManager.Dao.VirtualMachineDaoInterface;
import br.ufrn.VmManager.Exceptions.DaoException;
import br.ufrn.VmManager.model.IpAddress;
import br.ufrn.VmManager.model.VirtualMachine;
import br.ufrn.VmManager.model.VmMetric;

public class VmController {

	private static final VmController VM_CONTROLLER = new VmController();
	private VmsProperties properties;
	private CloudDriverInterface cloudDriver;
	private VirtualMachineDaoInterface VmDao;

	private Logger logger = Logger.getLogger(VmController.class);

	private VmController() {
		properties = VmsProperties.getInstance();
		VmDao = new VirtualMachineDao();
	}
	
	public static VmController getInstance(){
		return VM_CONTROLLER;
	}

	public void analyzeMetrics(String VmIpAddress, VmMetric metric) {

		try {
			VirtualMachine vm = VmDao.search(new VirtualMachine(new IpAddress(
					VmIpAddress, null), null, null, null, null));

			if (vm != null) {

				vm.setVmMetric(metric);
				VmDao.update(vm);

				List<VirtualMachine> vms = VmDao.listAll(VirtualMachine.class);

				if (vms != null && vms.size() > 0) {
					float averageCpuUsage = 0, averageMemoryUsage = 0;

					for (VirtualMachine v : vms) {

						averageCpuUsage += v.getVmMetric().getCpuUsage();
						averageMemoryUsage += v.getVmMetric().getMemoryUsage();

					}

					averageCpuUsage = averageCpuUsage / vms.size();
					averageMemoryUsage = averageMemoryUsage / vms.size();

					if (averageCpuUsage > properties.getMaxCpuUsage()) {
						logger.info("limiar maximo para uso de CPU atingido: "+vm);
						alocateVmResources();
					} else if (averageMemoryUsage < properties.getMinCpuUsage()) {
						logger.info("limiar mínimo para uso de CPU atingido: "+vm);
						releaseVmResources();
					} else if (averageMemoryUsage > properties
							.getMaxMemoryUsage()) {
						logger.info("limiar maximo para uso de memória atingido: "+vm);
						alocateVmResources();
					} else if (averageMemoryUsage < properties
							.getMinMemoryUsage()) {
						logger.info("limiar mínimo para uso de memória atingido: "+vm);
						releaseVmResources();
					}
				}

			}

		} catch (DaoException e) {

			e.printStackTrace();
		}

	}

	private void alocateVmResources() {
		logger.info("alocando recursos");
	}

	private void releaseVmResources() {
		logger.info("liberando recursos");
	}

}
