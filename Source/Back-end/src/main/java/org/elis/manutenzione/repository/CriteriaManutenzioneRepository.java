package org.elis.manutenzione.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.elis.manutenzione.dto.request.FiltroManutenzione;
import org.elis.manutenzione.dto.response.ListaManutenzioniResponse;
import org.elis.manutenzione.dto.response.ManutenzioneDTO;
import org.elis.manutenzione.mapper.ManutenzioneMapper;
import org.elis.manutenzione.model.entity.Manutentore;
import org.elis.manutenzione.model.entity.Manutenzione;
import org.elis.manutenzione.model.entity.Residente;
import org.elis.manutenzione.model.entity.Utente;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository che utilizza l'API Criteria di JPA per eseguire query di ricerca filtrate su {@link Manutenzione}.
 */
@Repository
public class CriteriaManutenzioneRepository {

    private final EntityManager entityManager;
    private final ManutenzioneRepository manutenzioneRepository;
    private final ManutenzioneMapper manutenzioneMapper;

    /**
     * Costruttore della classe.
     *
     * @param entityManager L'{@link EntityManager} utilizzato per interagire con il database.
     */
    public CriteriaManutenzioneRepository(EntityManager entityManager, ManutenzioneRepository manutenzioneRepository, ManutenzioneMapper manutenzioneMapper) {
        this.entityManager = entityManager;
        this.manutenzioneRepository = manutenzioneRepository;
        this.manutenzioneMapper = manutenzioneMapper;
    }

    /**
     * Restituisce una lista di {@link Manutenzione} filtrata in base ai criteri specificati nel {@link FiltroManutenzione}
     * e all'{@link Utente} che effettua la richiesta.
     *
     * @param request La richiesta di filtro contenente i criteri di ricerca.
     * @param u       L'utente che effettua la richiesta.
     * @return Una lista di {@link Manutenzione} filtrata.
     */
    public List<ManutenzioneDTO> getManutenzioneFiltrata(FiltroManutenzione request, Utente u) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Manutenzione> query = cb.createQuery(Manutenzione.class);
        Root<Manutenzione> root = query.from(Manutenzione.class);
        List<Predicate> predicates = new ArrayList<>();
        List<Manutenzione> manutenzioni= new ArrayList<>();

        // Aggiunta dei predicati di filtro in base ai criteri specificati nella richiesta
        if (request.getDescrizione() != null && !request.getDescrizione().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("descrizione")), "%" + request.getDescrizione().toLowerCase() + "%"));
        }
        if (request.getDataRichiesta() != null && !request.getDataRichiesta().isEmpty()) {
            predicates.add(cb.like(cb.function("DATE_FORMAT", String.class, root.get("dataRichiesta"), cb.literal("%Y-%m-%d")), "%" + request.getDataRichiesta() + "%"));
        }
        if (request.getDataRiparazione() != null && !request.getDataRiparazione().isEmpty()) {
            predicates.add(cb.like(cb.function("DATE_FORMAT", String.class, root.get("dataRiparazione"), cb.literal("%Y-%m-%d")), "%" + request.getDataRiparazione() + "%"));
        }
        if (request.getDataPrevista() != null && !request.getDataPrevista().isEmpty()) {
            predicates.add(cb.like(cb.function("DATE_FORMAT", String.class, root.get("dataPrevista"), cb.literal("%Y-%m-%d")), "%" + request.getDataPrevista() + "%"));
        }
        if (request.getPriorita() > 0 && request.getPriorita() <= 5) {
            predicates.add(cb.equal(root.get("priorita"), request.getPriorita()));
        }
        if (request.getNome() != null && !request.getNome().isEmpty()) {
            predicates.add(cb.like(root.get("nome"), "%" + request.getNome() + "%"));
        }

        // Aggiunta di predicati specifici in base al tipo di utente
        if (u instanceof Residente) {
            Residente r = (Residente) u;
            predicates.add(cb.equal(root.get("utenteRichiedente"), r));
        } else if (u instanceof Manutentore) {
            Manutentore m = (Manutentore) u;
            predicates.add(cb.equal(root.get("manutentore").get("email"), m.getEmail()));
        } else {
            if (request.getManutentore() != null && !request.getManutentore().isEmpty()) {
                predicates.add(cb.like(root.get("manutentore").get("email"), "%" + request.getManutentore().toLowerCase() + "%"));
            }
            if (request.getUtenteRichiedente() != null && !request.getUtenteRichiedente().isEmpty()) {
                predicates.add(cb.like(root.get("utenteRichiedente").get("email"), "%" + request.getUtenteRichiedente().toLowerCase() + "%"));
            }
        }

        if (request.getStatoManutenzione() != null && !request.getStatoManutenzione().isEmpty()) {
            predicates.add(cb.equal(root.get("statoManutenzione"), request.getStatoManutenzione()));
        }

        if(!predicates.isEmpty()) { // Costruzione della query con i predicati
            query.where(predicates.toArray(new Predicate[predicates.size()]));
            TypedQuery<Manutenzione> typedQuery = entityManager.createQuery(query);
            manutenzioni= typedQuery.getResultList();
            List<ManutenzioneDTO> response = manutenzioneMapper.toManutenzioneDTO(manutenzioni);
            return response;
        }
        //nel caso di supervisore e admin ritorna tutte le manutenzioni se non è stata inserito nessun filtro
        manutenzioni= manutenzioneRepository.findAll();
        List<ManutenzioneDTO> response = manutenzioneMapper.toManutenzioneDTO(manutenzioni);
        return response;
    }
}