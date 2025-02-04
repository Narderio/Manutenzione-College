package org.elis.manutenzione.repository;

import org.elis.manutenzione.model.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Interfaccia repository per la gestione degli amministratori nel database.
 * Estende {@link JpaRepository} per fornire metodi di accesso ai dati per l'entità {@link Admin}.
 */
public interface AdminRepository extends JpaRepository<Admin, String> {

    /**
     * Trova tutti gli amministratori che non sono bloccati.
     *
     * @return Una lista di {@link Admin} che non sono bloccati.
     */
    public List<Admin> findAllByBloccatoIsFalse();

}