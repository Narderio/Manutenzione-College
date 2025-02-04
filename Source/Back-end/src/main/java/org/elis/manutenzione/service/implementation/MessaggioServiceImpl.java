package org.elis.manutenzione.service.implementation;

import org.elis.manutenzione.model.entity.Messaggio;
import org.elis.manutenzione.repository.MessaggioRepository;
import org.elis.manutenzione.service.definition.MessaggioService;
import org.springframework.stereotype.Service;

/**
 * Implementazione del servizio per la gestione dei messaggi.
 */
@Service
public class MessaggioServiceImpl implements MessaggioService {

	/**
	 * Repository per l'accesso ai dati dei messaggi.
	 */
	private final MessaggioRepository repo;

    public MessaggioServiceImpl(MessaggioRepository repo) {
        this.repo = repo;
    }

    /**
	 * Aggiunge un nuovo messaggio alla chat.
	 *
	 * @param m Il messaggio da aggiungere.
	 */
	@Override
	public void aggiungiMessaggio(Messaggio m) {
		if (m.getChat() != null) {
			repo.save(m);
		}
	}

	@Override
	public Messaggio getMessaggioById(Long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public boolean eliminaMessaggio(Messaggio m) {
		if (m != null) {
			repo.delete(m);
			return true;
		}
		return false;
	}
}