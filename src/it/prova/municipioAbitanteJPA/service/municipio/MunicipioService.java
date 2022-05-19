package it.prova.municipioAbitanteJPA.service.municipio;

import java.util.List;

import it.prova.municipioAbitanteJPA.dao.municipio.MunicipioDAO;
import it.prova.municipioAbitanteJPA.model.Municipio;

public interface MunicipioService {
	
	public List<Municipio> listAllMunicipi() throws Exception;

	public Municipio caricaSingoloMunicipio(Long id) throws Exception;
	
	public Municipio caricaSingoloMunicipioConAbitanti(Long id) throws Exception;

	public void aggiorna(Municipio municipioInstance) throws Exception;

	public void inserisciNuovo(Municipio municipioInstance) throws Exception;

	public void rimuovi(Municipio municipioInstance) throws Exception;

	public List<Municipio> cercaTuttiIMunicipiConMinorenni() throws Exception;

	//per injection
	public void setMunicipioDAO(MunicipioDAO municipioDAO);
}
