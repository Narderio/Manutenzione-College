package org.elis.manutenzione.repository;

import org.elis.manutenzione.model.entity.Luogo;
import org.elis.manutenzione.model._enum.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Interfaccia repository per la gestione dei luoghi nel database.
 * Estende {@link JpaRepository} per fornire metodi di accesso ai dati per l'entità {@link Luogo}.
 */
public interface LuogoRepository extends JpaRepository<Luogo, Long> {

    /**
     * Trova un luogo in base al suo nome, tipo e nucleo abitativo.
     *
     * @param nome  Il nome del luogo.
     * @param tipo  Il tipo di luogo ({@link Tipo}).
     * @param nucleo Il nucleo abitativo a cui appartiene il luogo.
     * @return Il {@link Luogo} corrispondente ai criteri di ricerca.
     */
    public Luogo findByNomeAndTipoAndNucleo(String nome, Tipo tipo, String nucleo);

    /**
     * Trova un luogo in base al suo nome e tipo.
     *
     * @param nome Il nome del luogo.
     * @param tipo Il tipo di luogo ({@link Tipo}).
     * @return Il {@link Luogo} corrispondente ai criteri di ricerca.
     */
    public Luogo findByNomeAndTipo(String nome, Tipo tipo);

    /**
     * Trova tutti i luoghi di un determinato tipo.
     *
     * @param tipo Il tipo di luogo ({@link Tipo}).
     * @return Una lista di {@link Luogo} del tipo specificato.
     */
    public List<Luogo> findAllByTipo(Tipo tipo);

    /**
     * Trova un luogo in base al suo ID.
     *
     * @param id L'ID del luogo.
     * @return Il {@link Luogo} corrispondente all'ID specificato.
     */
    public Luogo findById(long id);
}