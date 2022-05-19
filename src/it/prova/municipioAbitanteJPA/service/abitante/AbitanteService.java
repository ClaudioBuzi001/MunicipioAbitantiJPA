package it.prova.municipioAbitanteJPA.service.abitante;

import java.util.List;

import it.prova.municipioAbitanteJPA.dao.abitante.AbitanteDAO;
import it.prova.municipioAbitanteJPA.model.Abitante;

public interface AbitanteService {
	
	public List<Abitante> listAllAbitanti() throws Exception;

	public Abitante caricaSingoloAbitante(Long id) throws Exception;

	public void aggiorna(Abitante abitanteInstance) throws Exception;

	public void inserisciNuovo(Abitante abitanteInstance) throws Exception;

	public void rimuovi(Long idAbitanteInstance) throws Exception;

	public List<Abitante> cercaTuttiGliAbitantiConNome(String nome) throws Exception;
	
	
	//Da aggiungere i metodi implementati in AbitanteDAOImpl
	public List<Abitante> cercaTuttiGliAbitantiConCognome(String cognome) throws Exception;
	
	
	//per injection
	public void setAbitanteDAO(AbitanteDAO abitanteDAO);

}
