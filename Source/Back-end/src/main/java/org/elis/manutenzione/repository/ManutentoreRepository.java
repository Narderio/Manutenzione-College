package org.elis.manutenzione.repository;

import org.elis.manutenzione.model.entity.Manutentore;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Interfaccia repository per la gestione dei manutentori nel database.
 * Estende {@link JpaRepository} per fornire metodi di accesso ai dati per l'entità {@link Manutentore}.
 */
public interface ManutentoreRepository extends JpaRepository<Manutentore, String> {

    /**
     * Trova un manutentore in base alla sua email, assicurandosi che non sia bloccato.
     *
     * @param email L'email del manutentore.
     * @return Il {@link Manutentore} corrispondente all'email specificata, se non bloccato.
     */
    public Manutentore findByEmailAndBloccatoIsFalse(String email);

    /**
     * Trova tutti i manutentori che non sono bloccati.
     *
     * @return Una lista di {@link Manutentore} che non sono bloccati.
     */
    public List<Manutentore> findAllByBloccatoIsFalse();

}