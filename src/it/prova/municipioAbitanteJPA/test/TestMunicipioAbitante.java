package it.prova.municipioAbitanteJPA.test;

import java.util.List;

import org.hibernate.LazyInitializationException;

import it.prova.municipioAbitanteJPA.dao.EntityManagerUtil;
import it.prova.municipioAbitanteJPA.model.Abitante;
import it.prova.municipioAbitanteJPA.model.Municipio;
import it.prova.municipioAbitanteJPA.service.MyServiceFactory;
import it.prova.municipioAbitanteJPA.service.abitante.AbitanteService;
import it.prova.municipioAbitanteJPA.service.municipio.MunicipioService;

public class TestMunicipioAbitante {

	public static void main(String[] args) {

		MunicipioService municipioService = MyServiceFactory.getMunicipioServiceInstance();
		AbitanteService abitanteService = MyServiceFactory.getAbitanteServiceInstance();

		try {

			// ora con il service posso fare tutte le invocazioni che mi servono
			System.out.println(
					"In tabella Municipio ci sono " + municipioService.listAllMunicipi().size() + " elementi.");

			testInserisciMunicipio(municipioService);
			System.out.println(
					"In tabella Municipio ci sono " + municipioService.listAllMunicipi().size() + " elementi.");

			testInserisciAbitante(municipioService, abitanteService);
			System.out.println(
					"In tabella Municipio ci sono " + municipioService.listAllMunicipi().size() + " elementi.");

			testRimozioneAbitante(municipioService, abitanteService);
			System.out.println(
					"In tabella Municipio ci sono " + municipioService.listAllMunicipi().size() + " elementi.");

			// testCercaTuttiGliAbitantiConNome(municipioService, abitanteService);
			System.out.println(
					"In tabella Municipio ci sono " + municipioService.listAllMunicipi().size() + " elementi.");

			// Implemento gli altri test
			// testCercaTuttiAbitantiConCognome(municipioService, abitanteService);
			
			testCercaTuttiGliAbitantiConCodiceMunicipioInizaCon(abitanteService);

			testCercaTuttiConDescrizioneIniziaCon(municipioService);
			
			testCercaTuttiIMunicipiConMinorenni(municipioService);
			
			testLazyInitExc(municipioService, abitanteService);

		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			// questa ?? necessaria per chiudere tutte le connessioni quindi rilasciare il
			// main
			EntityManagerUtil.shutdown();
		}

	}

	private static void testInserisciMunicipio(MunicipioService municipioService) throws Exception {
		System.out.println(".......testInserisciMunicipio inizio.............");
		// creo nuovo municipio
		Municipio nuovoMunicipio = new Municipio("Municipio III", "III", "Via dei Nani");
		if (nuovoMunicipio.getId() != null)
			throw new RuntimeException("testInserisciMunicipio fallito: record gi?? presente ");

		// salvo
		municipioService.inserisciNuovo(nuovoMunicipio);
		// da questa riga in poi il record, se correttamente inserito, ha un nuovo id
		// (NOVITA' RISPETTO AL PASSATO!!!)
		if (nuovoMunicipio.getId() == null)
			throw new RuntimeException("testInserisciMunicipio fallito ");

		System.out.println(".......testInserisciMunicipio fine: PASSED.............");
	}

	private static void testInserisciAbitante(MunicipioService municipioService, AbitanteService abitanteService)
			throws Exception {
		System.out.println(".......testInserisciAbitante inizio.............");

		// creo nuovo abitante ma prima mi serve un municipio
		List<Municipio> listaMunicipiPresenti = municipioService.listAllMunicipi();
		if (listaMunicipiPresenti.isEmpty())
			throw new RuntimeException("testInserisciAbitante fallito: non ci sono municipi a cui collegarci ");

		Abitante nuovoAbitante = new Abitante("Pluto", "Plutorum", 77, "Via Lecce");
		// lo lego al primo municipio che trovo
		nuovoAbitante.setMunicipio(listaMunicipiPresenti.get(0));

		// salvo il nuovo abitante
		abitanteService.inserisciNuovo(nuovoAbitante);

		// da questa riga in poi il record, se correttamente inserito, ha un nuovo id
		// (NOVITA' RISPETTO AL PASSATO!!!)
		if (nuovoAbitante.getId() == null)
			throw new RuntimeException("testInserisciAbitante fallito ");

		// il test fallisce anche se non ?? riuscito a legare i due oggetti
		if (nuovoAbitante.getMunicipio() == null)
			throw new RuntimeException("testInserisciAbitante fallito: non ha collegato il municipio ");

		System.out.println(".......testInserisciAbitante fine: PASSED.............");
	}

	private static void testRimozioneAbitante(MunicipioService municipioService, AbitanteService abitanteService)
			throws Exception {
		System.out.println(".......testRimozioneAbitante inizio.............");

		// inserisco un abitante che rimuover??
		// creo nuovo abitante ma prima mi serve un municipio
		List<Municipio> listaMunicipiPresenti = municipioService.listAllMunicipi();
		if (listaMunicipiPresenti.isEmpty())
			throw new RuntimeException("testRimozioneAbitante fallito: non ci sono municipi a cui collegarci ");

		Abitante nuovoAbitante = new Abitante("Pietro", "Mitraglia", 33, "Via del Mare");
		// lo lego al primo municipio che trovo
		nuovoAbitante.setMunicipio(listaMunicipiPresenti.get(0));

		// salvo il nuovo abitante
		abitanteService.inserisciNuovo(nuovoAbitante);

		Long idAbitanteInserito = nuovoAbitante.getId();
		abitanteService.rimuovi(idAbitanteInserito);
		// proviamo a vedere se ?? stato rimosso
		if (abitanteService.caricaSingoloAbitante(idAbitanteInserito) != null)
			throw new RuntimeException("testRimozioneAbitante fallito: record non cancellato ");
		System.out.println(".......testRimozioneAbitante fine: PASSED.............");
	}

	private static void testCercaTuttiGliAbitantiConNome(MunicipioService municipioService,
			AbitanteService abitanteService) throws Exception {
		System.out.println(".......testCercaTuttiGliAbitantiConNome inizio.............");

		// inserisco un paio di abitanti di test
		// prima mi serve un municipio
		List<Municipio> listaMunicipiPresenti = municipioService.listAllMunicipi();
		if (listaMunicipiPresenti.isEmpty())
			throw new RuntimeException(
					"testCercaTuttiGliAbitantiConNome fallito: non ci sono municipi a cui collegarci ");

		Abitante nuovoAbitante = new Abitante("Mariotto", "Bassi", 27, "Via Lucca");
		Abitante nuovoAbitante2 = new Abitante("Mariotto", "Nato", 37, "Via Roma");
		// lo lego al primo municipio che trovo
		nuovoAbitante.setMunicipio(listaMunicipiPresenti.get(0));
		nuovoAbitante2.setMunicipio(listaMunicipiPresenti.get(0));

		// salvo i nuovi abitante
		abitanteService.inserisciNuovo(nuovoAbitante);
		abitanteService.inserisciNuovo(nuovoAbitante2);

		// ora mi aspetto due 'Mario'
		if (abitanteService.cercaTuttiGliAbitantiConNome("Mariotto").size() != 4)
			throw new RuntimeException("testCercaTuttiGliAbitantiConNome fallito: numero record inatteso ");

		// clean up code
		abitanteService.rimuovi(nuovoAbitante.getId());
		abitanteService.rimuovi(nuovoAbitante2.getId());

		System.out.println(".......testCercaTuttiGliAbitantiConNome fine: PASSED.............");
	}

	private static void testCercaTuttiAbitantiConCognome(MunicipioService municipioService,
			AbitanteService abitanteService) throws Exception {
		System.out.println(".......testCercaTuttiAbitantiConCognome inizio.............");

		// inserisco un paio di abitanti di test
		// prima mi serve un municipio
		List<Municipio> listaMunicipiPresenti = municipioService.listAllMunicipi();
		if (listaMunicipiPresenti.isEmpty())
			throw new RuntimeException(
					"testCercaTuttiAbitantiConCognome fallito: non ci sono municipi a cui collegarci ");

		Abitante nuovoAbitante = new Abitante("Mariotto", "Bassi", 27, "Via Lucca");
		Abitante nuovoAbitante2 = new Abitante("Mariotto", "Bassi", 37, "Via Roma");
		// lo lego al primo municipio che trovo
		nuovoAbitante.setMunicipio(listaMunicipiPresenti.get(0));
		nuovoAbitante2.setMunicipio(listaMunicipiPresenti.get(0));

		// salvo i nuovi abitante
		abitanteService.inserisciNuovo(nuovoAbitante);
		abitanteService.inserisciNuovo(nuovoAbitante2);

		// ora mi aspetto due 'Mario'
		if (abitanteService.cercaTuttiGliAbitantiConCognome("Bassi").size() != 4)
			throw new RuntimeException("testCercaTuttiAbitantiConCognome fallito: numero record inatteso ");

		// clean up code
		abitanteService.rimuovi(nuovoAbitante.getId());
		abitanteService.rimuovi(nuovoAbitante2.getId());

		System.out.println(".......testCercaTuttiAbitantiConCognome fine: PASSED.............");

	}

	private static void testCercaTuttiGliAbitantiConCodiceMunicipioInizaCon(AbitanteService abitanteService)
			throws Exception {
		System.out.println(".......testCercaTuttiGliAbitantiConCodiceMunicipioInizaCon inizio.............");

		List<Abitante> result = abitanteService.cercaTuttiGliAbitantiConCodiceMunicipioInizaCon("I");

		if (result.size() == 0)
			throw new RuntimeException("ERRORE:testCercaTuttiGliAbitantiConCodiceMunicipioInizaCon FAILED");

		for (Abitante abitanteItem : result) {
			System.out.println(abitanteItem.getNome() + " " + abitanteItem.getCognome());
		}

		System.out.println(".......testCercaTuttiGliAbitantiConCodiceMunicipioInizaCon fine: PASSED.............");

	}
	
	
	private static void testCercaTuttiConDescrizioneIniziaCon(MunicipioService municipioService) throws Exception {
		
		System.out.println(".......testCercaTuttiConDescrizioneIniziaCon inizio.............");

		List<Municipio> result = municipioService.cercaTuttiConDescrizioneIniziaCon("Muni");

		if (result.size() == 0)
			throw new RuntimeException("testCercaTuttiConDescrizioneIniziaCon FAILED");

		for (Municipio municipioItem : result) {
			System.out.println(municipioItem.getDescrizione()+ " "+ municipioItem.getCodice());
		}

		System.out.println(".......testCercaTuttiConDescrizioneIniziaCon fine: PASSED.............");
		
	}

	
	private static void testCercaTuttiIMunicipiConMinorenni(MunicipioService municipioService) throws Exception {
		System.out.println(".......testCercaTuttiIMunicipiConMinorenni inizio.............");

		List<Municipio> result = municipioService.cercaTuttiIMunicipiConMinorenni();

		if (result.size() == 0)
			throw new RuntimeException("testCercaTuttiIMunicipiConMinorenni FAILED");

		for (Municipio municipioItem : result) {
			System.out.println(municipioItem.getDescrizione()+ " "+ municipioItem.getCodice());
		}

		System.out.println(".......testCercaTuttiIMunicipiConMinorenni fine: PASSED.............");
		
		
	}
	
	private static void testLazyInitExc(MunicipioService municipioService, AbitanteService abitanteService)
			throws Exception {
		System.out.println(".......testLazyInitExc inizio.............");

		// prima mi serve un municipio
		List<Municipio> listaMunicipiPresenti = municipioService.listAllMunicipi();
		if (listaMunicipiPresenti.isEmpty())
			throw new RuntimeException("testLazyInitExc fallito: non ci sono municipi a cui collegarci ");

		Municipio municipioSuCuiFareIlTest = listaMunicipiPresenti.get(0);
		// se interrogo la relazione devo ottenere un'eccezione visto che sono LAZY
		try {
			municipioSuCuiFareIlTest.getAbitanti().size();
			// se la riga sovrastante non da eccezione il test fallisce
			throw new RuntimeException("testLazyInitExc fallito: eccezione non lanciata ");
		} catch (LazyInitializationException e) {
			// 'spengo' l'eccezione per il buon fine del test
		}
		// una LazyInitializationException in quanto il contesto di persistenza ?? chiuso
		// se usiamo un caricamento EAGER risolviamo...dipende da cosa ci serve!!!
		// municipioService.caricaSingoloMunicipioConAbitanti(...);
		System.out.println(".......testLazyInitExc fine: PASSED.............");
	}
	


}
