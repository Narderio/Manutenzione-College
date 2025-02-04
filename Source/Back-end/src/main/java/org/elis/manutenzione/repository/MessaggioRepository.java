package org.elis.manutenzione.repository;

import org.elis.manutenzione.model.entity.Messaggio;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaccia repository per la gestione dei messaggi nel database.
 * Estende {@link JpaRepository} per fornire metodi di accesso ai dati per l'entità {@link Messaggio}.
 */
public interface MessaggioRepository extends JpaRepository<Messaggio, Long> {

}