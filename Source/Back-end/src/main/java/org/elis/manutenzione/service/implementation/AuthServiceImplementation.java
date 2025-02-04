package org.elis.manutenzione.service.implementation;

import org.elis.manutenzione.dto.request.CambioPasswordRequest;
import org.elis.manutenzione.dto.request.LoginRequest;
import org.elis.manutenzione.handler.BadRequestException;
import org.elis.manutenzione.handler.NoContentException;
import org.elis.manutenzione.model.entity.Utente;
import org.elis.manutenzione.repository.UtenteRepository;
import org.elis.manutenzione.service.definition.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementazione dell'interfaccia {@link AuthService} che fornisce i metodi per l'autenticazione
 * e la gestione delle password degli utenti.
 */
@Service
public class AuthServiceImplementation implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Costruttore della classe.
     *
     * @param authenticationManager Gestore dell'autenticazione.
     * @param utenteRepository     Repository per l'accesso ai dati degli utenti.
     * @param passwordEncoder      Encoder per la codifica delle password.
     */
    public AuthServiceImplementation(AuthenticationManager authenticationManager, UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * {@inheritDoc}
     * Effettua il login di un utente verificando le credenziali fornite.
     *
     * @param request DTO contenente le credenziali dell'utente (email e password).
     * @return L'oggetto {@link Utente} se le credenziali sono corrette.
     * @throws NoContentException Se l'utente non viene trovato o è bloccato.
     * @throws BadRequestException Se le credenziali sono errate.
     */
    @Override
    public Utente login(LoginRequest request) {
        Utente u = utenteRepository.findByEmailAndBloccatoIsFalse(request.getEmail()).orElse(null);
        if (u == null)
            throw new BadRequestException("Utente non trovato o bloccato");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (Exception e) {
            throw new BadRequestException("Credenziali errate");
        }
        return u;
    }

    /**
     * {@inheritDoc}
     * Implementazione del metodo per cambiare la password di un utente.
     *
     * @param request DTO contenente le password vecchia, nuova e di conferma.
     * @param u       L'utente che sta cambiando la password.
     * @return true se il cambio password è avvenuto con successo.
     * @throws BadRequestException Se le password non coincidono o se la password vecchia è errata.
     */
    @Override
    public boolean cambioPassword(CambioPasswordRequest request, Utente u) {
        if (!request.getNuovaPassword().equals(request.getConfermaPassword()))
            throw new BadRequestException("le password non coincidono");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(u.getEmail(), request.getUltimaPassword())
            );
        } catch (Exception e) {
            throw new BadRequestException("Credenziali errate");
        }
        u.setPassword(null); // Imposta la password a null prima di aggiornarla (workaround per un bug di JPA)
        u.setPassword(passwordEncoder.encode(request.getNuovaPassword()));
        utenteRepository.save(u);
        return true;
    }
}