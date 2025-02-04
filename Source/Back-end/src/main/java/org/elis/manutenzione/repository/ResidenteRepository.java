package org.elis.manutenzione.repository;

import org.elis.manutenzione.model.entity.Residente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**
 * Interfaccia repository per la gestione dei residenti nel database.
 * Estende {@link JpaRepository} per fornire metodi di accesso ai dati per l'entità {@link Residente}.
 */
public interface ResidenteRepository extends JpaRepository<Residente, String> {

    /**
     * Trova tutti i residenti che non sono bloccati.
     *
     * @return Una lista di {@link Residente} che non sono bloccati.
     */
    public List<Residente> findAllByBloccatoIsFalse();

    /**
     * Trova tutti i residenti che non sono bloccati e che vogliono ricevere le email opzionali sulle manutenzioni.
     *
     * @return Una lista di {@link Residente} che non sono bloccati e che vogliono ricevere le email opzionali sulle manutenzioni.
     */
    public List<Residente> findAllByBloccatoIsFalseAndEmailBloccateIsFalse();

    /**
     * Trova tutti i residenti che appartengono a un determinato nucleo abitativo.
     *
     * @param nomeNucleo Il nome del nucleo abitativo.
     * @return Una lista di {@link Residente} che appartengono al nucleo abitativo specificato.
     */
    @Query("SELECT r FROM Residente r JOIN r.luoghi l WHERE l.nucleo = :nomeNucleo")
    public List<Residente> findByLuoghiNucleoNome(String nomeNucleo);

    /**
     * Trova un residente in base alla sua email, assicurandosi che non sia bloccato.
     *
     * @param email L'email del residente.
     * @return Il {@link Residente} corrispondente all'email specificata, se non bloccato.
     */
    public Residente findByEmailAndBloccatoIsFalse(String email);
}