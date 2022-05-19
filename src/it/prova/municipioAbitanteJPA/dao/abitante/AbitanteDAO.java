package it.prova.municipioAbitanteJPA.dao.abitante;

import java.util.List;

import it.prova.municipioAbitanteJPA.dao.IBaseDAO;
import it.prova.municipioAbitanteJPA.model.Abitante;

public interface AbitanteDAO extends IBaseDAO<Abitante> {
	
	public List<Abitante> findAllByNome(String nome) throws Exception;

	public List<Abitante> findAllByCognome(String cognome) throws Exception;

	public List<Abitante> findAllByCodiceMunicipioIniziaCon(String codice) throws Exception;
	
}
