package org.elis.manutenzione.service.definition;

import org.elis.manutenzione.dto.request.CambioPasswordRequest;
import org.elis.manutenzione.dto.request.LoginRequest;
import org.elis.manutenzione.model.entity.Utente;

/**
 * Interfaccia che definisce i metodi per l'autenticazione e la gestione delle password degli utenti.
 */
public interface AuthService {

    /**
     * Effettua il login di un utente.
     *
     * @param loginRequest DTO contenente le credenziali dell'utente (email e password).
     * @return L'oggetto {@link Utente} se le credenziali sono corrette, altrimenti null.
     */
    public Utente login(LoginRequest loginRequest);

    /**
     * Cambia la password di un utente.
     *
     * @param cambioPasswordRequest DTO contenente la vecchia password, la nuova password e la conferma della nuova password.
     * @param u                     L'utente che sta cambiando la password.
     * @return true se il cambio password è avvenuto con successo, false altrimenti.
     */
    public boolean cambioPassword(CambioPasswordRequest cambioPasswordRequest, Utente u);

}