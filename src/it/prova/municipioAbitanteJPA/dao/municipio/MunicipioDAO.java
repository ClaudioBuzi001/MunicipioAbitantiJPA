package it.prova.municipioAbitanteJPA.dao.municipio;

import java.util.List;

import it.prova.municipioAbitanteJPA.dao.IBaseDAO;
import it.prova.municipioAbitanteJPA.model.Municipio;

public interface MunicipioDAO extends IBaseDAO<Municipio> {
	
	public Municipio getEagerAbitanti(Long id) throws Exception;

	public List<Municipio> findAllByAbitantiMinorenni() throws Exception;

	public List<Municipio> findAllByDescrizioneIniziaCon(String iniziale) throws Exception;

}
