package org.elis.manutenzione.service.definition;

import org.elis.manutenzione.dto.request.*;
import org.elis.manutenzione.dto.response.GetManutenzioneDTO;
import org.elis.manutenzione.dto.response.GetManutenzioniDTO;
import org.elis.manutenzione.dto.response.ListaManutenzioniResponse;
import org.elis.manutenzione.dto.response.ManutenzioneDTO;
import org.elis.manutenzione.model.entity.Manutentore;
import org.elis.manutenzione.model.entity.Manutenzione;
import org.elis.manutenzione.model.entity.Residente;
import org.elis.manutenzione.model.entity.Utente;

import java.util.List;

/**
 * Interfaccia del servizio per la gestione delle manutenzioni.
 * Questa interfaccia definisce i metodi per le operazioni relative alle manutenzioni,
 * come la richiesta, il filtraggio, l'accettazione, la riparazione, il rifiuto, il feedback e la modifica.
 */
public interface ManutenzioneService {

        /**
         * Richiede una nuova manutenzione.
         *
         * @param richiediManutenzioneRequest DTO contenente i dati della manutenzione da richiedere.
         * @param r                           Il residente che richiede la manutenzione.
         * @return L'oggetto Manutenzione appena creato e salvato.
         */
        public Manutenzione richiediManutenzione(RichiediManutenzioneRequest richiediManutenzioneRequest, Residente r);


        /**
         * Filtra una manutenzione e la assegna ad un manutentore.
         *
         * @param filtraManutenzioneRequest DTO contenente i dati per il filtraggio della manutenzione.
         * @return L'oggetto Manutenzione dopo il filtraggio e l'assegnazione.
         */
        public Manutenzione filtraManutenzione(FiltraManutenzioneRequest filtraManutenzioneRequest);


        /**
         * Segna una manutenzione come riparata.
         *
         * @param riparaManutenzioneRequest DTO contenente i dati per segnare la manutenzione come riparata.
         * @param m                        Il manutentore che ha riparato la manutenzione.
         * @return L'oggetto Manutenzione dopo la riparazione.
         */
        public Manutenzione riparaManutenzione(RiparaManutenzioneRequest riparaManutenzioneRequest, Manutentore m);


        /**
         * Rifiuta una manutenzione.
         *
         * @param rifiutaManutenzioneRequest DTO contenente i dati per il rifiuto della manutenzione.
         * @param u                         L'utente (manutentore o supervisore) che rifiuta la manutenzione.
         * @return L'oggetto Manutenzione dopo il rifiuto.
         */
        public Manutenzione rifiutaManutenzione(RifiutaManutenzioneRequest rifiutaManutenzioneRequest, Utente u);

        /**
         * Accetta una manutenzione.
         *
         * @param accettaManutenzioneRequest DTO contenente i dati per l'accettazione della manutenzione.
         * @param m                        Il manutentore che accetta la manutenzione.
         * @return L'oggetto Manutenzione dopo l'accettazione.
         */
        public Manutenzione accettaManutenzione(AccettaManutenzioneRequest accettaManutenzioneRequest, Manutentore m);


        /**
         * Recupera una lista di manutenzioni filtrate in base ai criteri specificati.
         *
         * @param request DTO contenente i criteri di filtraggio.
         * @param u       L'utente che sta richiedendo la lista di manutenzioni.
         * @return Una lista di oggetti Manutenzione filtrata.
         */
        public List<ManutenzioneDTO> getManutenzioneFiltrata(FiltroManutenzione request, Utente u);

        /**
         * Fornisce un feedback su una manutenzione riparata.
         *
         * @param feedbackManutenzioneRequest DTO contenente i dati del feedback.
         * @param r                           Il residente che fornisce il feedback.
         * @return true se il feedback è stato registrato con successo, false altrimenti.
         */
        public boolean feedbackManutenzione(FeedbackManutenzioneRequest feedbackManutenzioneRequest, Residente r);

        /**
         * Modifica una manutenzione in stato Richiesto.
         *
         * @param request DTO contenente i dati della manutenzione da modificare.
         * @param r       Il residente che ha richiesto la manutenzione.
         * @return L'oggetto Manutenzione modificato.
         */
        public Manutenzione modificaManutenzione(ModificaManutenzioneRequest request, Residente r);

        public List<GetManutenzioniDTO> getManutenzioni(Utente u);

        public GetManutenzioneDTO getManutenzioneById(Long id, Utente u);
}