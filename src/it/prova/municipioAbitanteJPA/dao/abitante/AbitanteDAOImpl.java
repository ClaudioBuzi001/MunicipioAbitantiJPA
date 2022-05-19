package it.prova.municipioAbitanteJPA.dao.abitante;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.municipioAbitanteJPA.model.Abitante;

public class AbitanteDAOImpl implements AbitanteDAO {

	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Abitante> list() throws Exception {
		return entityManager.createQuery("from Abitante", Abitante.class).getResultList();
	}

	@Override
	public Abitante get(Long id) throws Exception {
		return entityManager.find(Abitante.class, id);
	}

	@Override
	public void update(Abitante abitanteInstance) throws Exception {
		if (abitanteInstance == null) {
			throw new Exception("Problema valore in input");
		}

		abitanteInstance = entityManager.merge(abitanteInstance);
	}

	@Override
	public void insert(Abitante abitanteInstance) throws Exception {
		if (abitanteInstance == null) {
			throw new Exception("Problema valore in input");
		}

		entityManager.persist(abitanteInstance);
	}

	@Override
	public void delete(Abitante abitanteInstance) throws Exception {
		if (abitanteInstance == null) {
			throw new Exception("Problema valore in input");
		}

		entityManager.remove(entityManager.merge(abitanteInstance));
	}

	@Override
	public List<Abitante> findAllByNome(String nome) throws Exception {
		TypedQuery<Abitante> query = entityManager.createQuery("from Abitante a where a.nome like ?1", Abitante.class);
		return query.setParameter(1, nome).getResultList();
	}

	// TO DO PER ESERCIZIO
	@Override
	public List<Abitante> findAllByCognome(String cognome) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Abitante> findAllByCodiceMunicipioIniziaCon(String codice) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
