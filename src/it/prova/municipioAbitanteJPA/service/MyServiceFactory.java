package it.prova.municipioAbitanteJPA.service;

import it.prova.municipioAbitanteJPA.dao.MyDaoFactory;
import it.prova.municipioAbitanteJPA.service.abitante.AbitanteService;
import it.prova.municipioAbitanteJPA.service.abitante.AbitanteServiceImpl;
import it.prova.municipioAbitanteJPA.service.municipio.MunicipioService;
import it.prova.municipioAbitanteJPA.service.municipio.MunicipioServiceImpl;

public class MyServiceFactory {

	// rendiamo le istanze restituite SINGLETON
	private static AbitanteService abitanteServiceInstance = null;
	private static MunicipioService municipioServiceInstance = null;

	public static AbitanteService getAbitanteServiceInstance() {
		if (abitanteServiceInstance == null) {
			abitanteServiceInstance = new AbitanteServiceImpl();
			abitanteServiceInstance.setAbitanteDAO(MyDaoFactory.getAbitanteDAOInstance());
		}
		return abitanteServiceInstance;
	}

	public static MunicipioService getMunicipioServiceInstance() {
		if (municipioServiceInstance == null) {
			municipioServiceInstance = new MunicipioServiceImpl();
			municipioServiceInstance.setMunicipioDAO(MyDaoFactory.getMunicipioDAOInstance());
		}
		return municipioServiceInstance;
	}

}
