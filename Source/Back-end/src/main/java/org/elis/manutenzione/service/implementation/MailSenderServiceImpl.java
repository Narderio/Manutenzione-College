package org.elis.manutenzione.service.implementation;

import org.elis.manutenzione.dto.response.EmailDTO;
import org.elis.manutenzione.handler.BadGatewayException;
import org.elis.manutenzione.model.entity.Manutentore;
import org.elis.manutenzione.model.entity.Residente;
import org.elis.manutenzione.model.entity.Supervisore;
import org.elis.manutenzione.model.entity.Utente;
import org.elis.manutenzione.repository.SupervisoreRepository;
import org.elis.manutenzione.service.definition.MailSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione del servizio per l'invio di email.
 * Questa classe gestisce la logica di business per l'invio di email di notifica
 * in diversi scenari del sistema di manutenzione.
 * Utilizza {@link JavaMailSender} per l'invio delle email.
 */
@Service
public class MailSenderServiceImpl implements MailSenderService {

    /**
     * L'indirizzo email del mittente configurato.
     */
    @Value("${spring.mail.username}")
    private String mittente;

    private final SupervisoreRepository supervisoreRepository;

    private final JavaMailSender sender;

    /**
     * Costruttore della classe.
     * Inizializza le dipendenze necessarie per l'invio di email.
     *
     * @param supervisoreRepository Il repository per la gestione dei supervisori, per il recupero dei destinatari.
     * @param sender              Il servizio per l'invio di email, fornito da Spring.
     */
    public MailSenderServiceImpl(SupervisoreRepository supervisoreRepository, JavaMailSender sender) {
        this.supervisoreRepository = supervisoreRepository;
        this.sender = sender;
    }

    /**
     * {@inheritDoc}
     * Invia email ai supervisori per notificare la nuova richiesta di manutenzione.
     */
    @Override
    @Async
    public void richiediManutenzione() {
        List<Supervisore> supervisori = supervisoreRepository.findAllByBloccatoIsFalse();
        List<String> emails = new ArrayList<>();
        for (Supervisore m : supervisori) {
            emails.add(m.getEmail());
        }
        try {
            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setDestinatario(emails);
            emailDTO.setOggetto("Manutenzione richiesta");
            emailDTO.setTesto("E' stata richiesta una manutenzione. Controlla l'applicazione");
            inviaMessaggio(emailDTO);
        } catch (Exception e) {
            System.out.println("Errore nell'invio della mail");
        }
    }

    /**
     * {@inheritDoc}
     * Invia email al manutentore per notificare l'assegnazione della manutenzione.
     *
     * @param email L'email del manutentore a cui inviare la notifica.
     */
    @Override
    @Async
    public void filtraManutenzione(String email) {
        try {
            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setDestinatario(List.of(email));
            emailDTO.setOggetto("Manutenzione richiesta");
            emailDTO.setTesto("E' stata richiesta una manutenzione. Controlla l'applicazione");
            inviaMessaggio(emailDTO);
        } catch (Exception e) {
            System.out.println("Errore nell'invio della mail");
        }
    }

    /**
     * {@inheritDoc}
     * Invia email ai residenti per notificare l'accettazione della manutenzione.
     *
     * @param residenti La lista dei residenti a cui inviare la notifica.
     * @param data      La data prevista per la manutenzione.
     */
    @Override
    @Async
    public void accettaManutenzione(List<Residente> residenti, LocalDate data) {
        List<String> emails = new ArrayList<>();
        for (Residente r : residenti) {
            emails.add(r.getEmail());
        }

        try {
            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setDestinatario(emails);
            emailDTO.setOggetto("Manutenzione accettata");
            emailDTO.setTesto("La manutenzione è stata accettata dal manutentore. La data prevista di riparazione è " + data);
            inviaMessaggio(emailDTO);
        } catch (Exception e) {
            System.out.println("Errore nell'invio della mail");
        }
    }

    /**
     * {@inheritDoc}
     * Invia email ai residenti e ai supervisori per notificare la riparazione della manutenzione.
     *
     * @param residente Il residente che ha richiesto la manutenzione.
     */
    @Override
    @Async
    public void riparaManutenzione(Residente residente) {
        List<Supervisore> supervisori = supervisoreRepository.findAllByBloccatoIsFalse();
        List<String> emails = new ArrayList<>();
        if (!residente.getEmailBloccate())
            emails.add(residente.getEmail());
        for (Supervisore s : supervisori) {
            emails.add(s.getEmail());
        }
        try {
            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setDestinatario(emails);
            emailDTO.setOggetto("Manutenzione riparata");
            emailDTO.setTesto("La manutenzione è stata riparata. Ricordati di lasciare un feedback");
            inviaMessaggio(emailDTO);
        } catch (Exception e) {
            System.out.println("Errore nell'invio della mail");
        }
    }

    /**
     * {@inheritDoc}
     * Invia email ai supervisori per notificare il rifiuto di una manutenzione da parte di un manutentore.
     *
     * @param u L'utente che ha rifiutato la manutenzione (manutentore).
     */
    @Override
    @Async
    public void rifiutaManutenzione(Utente u) {
        List<Supervisore> supervisori = supervisoreRepository.findAllByBloccatoIsFalse();
        List<String> emails = new ArrayList<>();
        if (u instanceof Manutentore) {
            for (Supervisore s : supervisori) {
                emails.add(s.getEmail());
            }
        }
        if (!emails.isEmpty()) {
            try {
                EmailDTO emailDTO = new EmailDTO();
                emailDTO.setDestinatario(emails);
                emailDTO.setOggetto("Manutenzione rifiutata");
                emailDTO.setTesto("La manutenzione è stata rifiutata. Controlla l'applicazione");
                inviaMessaggio(emailDTO);
            } catch (Exception e) {
                System.out.println("Errore nell'invio della mail");
            }
        }
    }

    /**
     * {@inheritDoc}
     * Invia una email generica.
     *
     * @param emailDTO DTO contenente i dati dell'email da inviare (destinatari, oggetto, testo).
     */
    @Override
    @Async
    public void inviaMessaggio(EmailDTO emailDTO) {
        SimpleMailMessage messaggio = new SimpleMailMessage();
        messaggio.setFrom(mittente);
        messaggio.setTo(emailDTO.getDestinatario().toArray(new String[0])); // Converte la lista di destinatari in un array di stringhe
        messaggio.setSubject(emailDTO.getOggetto());
        messaggio.setText(emailDTO.getTesto());
        sender.send(messaggio);
    }

    /**
     * {@inheritDoc}
     * Invia una email di conferma registrazione.
     *
     * @param u  L'utente registrato.
     * @param passwordGenerata La password generata per l'utente.
     */
    @Override
    public void registrazione(Utente u, String passwordGenerata){
        String mailText= "Benvenuto "+u.getNome()+" "+u.getCognome()+"\n"
                + "La tua email è: "+u.getEmail()+"\n"
                + "La tua password è: "+passwordGenerata;
        try {
            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setDestinatario(List.of(u.getEmail()));
            emailDTO.setOggetto("Registrazione");
            emailDTO.setTesto(mailText);
            inviaMessaggio(emailDTO);
        } catch (Exception e) {
            throw new BadGatewayException("Errore nell'invio della mail, utente non registrato");
        }
    }

    /**
     * {@inheritDoc}
     * Invia una email di recupero password.
     *
     * @param email            L'email dell'utente che ha dimenticato la password.
     * @param passwordGenerata La nuova password generata.
     */
    @Override
    public void passwordDimenticata(String email, String passwordGenerata) {
        // Prepara l'email con la nuova password
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setOggetto("Recupero password");
        emailDTO.setDestinatario(List.of(email));
        emailDTO.setTesto("La tua nuova password è: " + passwordGenerata + ". Ti consigliamo di cambiarla al primo accesso.");
        // Invia l'email con la nuova password
        try {
            inviaMessaggio(emailDTO);
        } catch (Exception e) {
            throw new BadGatewayException("Errore nell'invio della mail");
        }

    }
}