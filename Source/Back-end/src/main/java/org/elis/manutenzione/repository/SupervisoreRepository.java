package org.elis.manutenzione.repository;

import org.elis.manutenzione.model.entity.Supervisore;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Interfaccia repository per la gestione dei supervisori nel database.
 * Estende {@link JpaRepository} per fornire metodi di accesso ai dati per l'entità {@link Supervisore}.
 */
public interface SupervisoreRepository extends JpaRepository<Supervisore, String> {

    /**
     * Trova tutti i supervisori che non sono bloccati.
     *
     * @return Una lista di {@link Supervisore} che non sono bloccati.
     */
    public List<Supervisore> findAllByBloccatoIsFalse();
}