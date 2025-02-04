package org.elis.manutenzione.service.definition;

import org.elis.manutenzione.dto.request.PasswordDimenticataRequest;
import org.elis.manutenzione.dto.response.EmailDTO;
import org.elis.manutenzione.model.entity.Residente;
import org.elis.manutenzione.model.entity.Utente;
import java.time.LocalDate;
import java.util.List;

/**
 * Interfaccia del servizio per l'invio di email.
 * Questa interfaccia definisce i metodi per l'invio di email di notifica
 * in diversi scenari del sistema di manutenzione.
 */
public interface MailSenderService {

    /**
     * Invia una email generica.
     *
     * @param emailDTO DTO contenente i dati dell'email da inviare (destinatari, oggetto, testo).
     */
    void inviaMessaggio(EmailDTO emailDTO);

    /**
     * Invia email ai supervisori per notificare la nuova richiesta di manutenzione.
     */
    void richiediManutenzione();

    /**
     * Invia email al manutentore per notificare l'assegnazione della manutenzione.
     *
     * @param emailManutentore L'email del manutentore a cui inviare la notifica.
     */
    void filtraManutenzione(String emailManutentore);

    /**
     * Invia email ai residenti per notificare l'accettazione della manutenzione.
     *
     * @param residenti      La lista dei residenti a cui inviare la notifica.
     * @param dataPrevista La data prevista per la manutenzione.
     */
    void accettaManutenzione(List<Residente> residenti, LocalDate dataPrevista);

    /**
     * Invia email ai residenti e ai supervisori per notificare la riparazione della manutenzione.
     *
     * @param utenteRichiedente Il residente che ha richiesto la manutenzione.
     */
    void riparaManutenzione(Residente utenteRichiedente);

    /**
     * Invia email ai supervisori per notificare il rifiuto di una manutenzione da parte di un manutentore.
     *
     * @param u L'utente che ha rifiutato la manutenzione (manutentore).
     */
    void rifiutaManutenzione(Utente u);

    void registrazione(Utente u, String passwordGenerata);

    void passwordDimenticata(String email, String passwordGenerata);
}