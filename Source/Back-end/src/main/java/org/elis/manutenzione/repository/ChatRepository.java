package org.elis.manutenzione.repository;

import org.elis.manutenzione.model.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

/**
 * Interfaccia repository per la gestione delle chat nel database.
 * Estende {@link JpaRepository} per fornire metodi di accesso ai dati per l'entità {@link Chat}.
 */
public interface ChatRepository extends JpaRepository<Chat, Long> {

	/**
	 * Trova tutte le chat a cui partecipa un utente con il nome utente specificato.
	 *
	 * @param username Il nome utente dell'utente.
	 * @return Una lista di {@link Chat} a cui partecipa l'utente.
	 */
	@Query("select c from Chat c where c.utenteUno.email= :username or c.utenteDue.email= :username")
	List<Chat> findAllByUsername(String username);

	/**
	 * Trova la chat tra due utenti con i nomi utente specificati.
	 *
	 * @param usernameUno Il nome utente del primo utente.
	 * @param usernameDue Il nome utente del secondo utente.
	 * @return Un {@link Optional} contenente la {@link Chat} tra i due utenti, se presente.
	 */
	@Query("select c from Chat c where (c.utenteUno.email= :usernameUno and c.utenteDue.email= :usernameDue) or (c.utenteUno.email= :usernameDue and c.utenteDue.email= :usernameUno)")
	Optional<Chat> findAllByUsername(String usernameUno, String usernameDue);

}