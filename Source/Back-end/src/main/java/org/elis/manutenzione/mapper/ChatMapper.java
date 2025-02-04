package org.elis.manutenzione.mapper;

import org.elis.manutenzione.dto.response.MessaggioDTO;
import org.elis.manutenzione.model.entity.Chat;
import org.elis.manutenzione.model.entity.Messaggio;
import org.elis.manutenzione.model.entity.Utente;
import org.springframework.stereotype.Component;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe mapper per la conversione di oggetti {@link Chat} e {@link Messaggio} in DTO {@link MessaggioDTO}.
 */
@Component
public class ChatMapper {

	/**
	 * Converte una lista di oggetti {@link Chat} in una lista di username degli utenti coinvolti nella chat.
	 *
	 * @param u L'utente corrente.
	 * @param chat La lista di chat da convertire.
	 * @return La lista di username degli utenti coinvolti nella chat.
	 */
	public List<String> toUsername(Utente u, List<Chat> chat){
		List<String> utenti=new ArrayList<>();
		for(Chat c:chat) {
			if(c.getUtenteUno().getUsername().equalsIgnoreCase(u.getUsername())) {
				utenti.add(c.getUtenteDue().getUsername()); // Corretto qui: aggiungi l'altro utente
			}else utenti.add(c.getUtenteUno().getUsername()); // Corretto qui: aggiungi l'altro utente
		}
		return utenti;
	}

	/**
	 * Converte un oggetto {@link Chat} in una lista di DTO {@link MessaggioDTO}.
	 *
	 * @param c L'oggetto Chat da convertire.
	 * @return La lista di DTO {@link MessaggioDTO}.
	 */
	public List<MessaggioDTO> toMessaggioDTOList(Chat c){
		if(c==null||c.getMessaggi()==null)return new ArrayList<>();
		return c.getMessaggi().stream()
				.sorted((c1,c2)->c1.getDataOra().compareTo(c2.getDataOra()))
				.map(m->this.toMessaggioDTO(c.getUtenteUno(),c.getUtenteDue(),m))
				.toList();

	}

	/**
	 * Converte un oggetto {@link Messaggio} in un DTO {@link MessaggioDTO}.
	 *
	 * @param u1 Il primo utente coinvolto nella chat.
	 * @param u2 Il secondo utente coinvolto nella chat.
	 * @param m L'oggetto Messaggio da convertire.
	 * @return Il DTO {@link MessaggioDTO}.
	 */
	public MessaggioDTO toMessaggioDTO(Utente u1, Utente u2, Messaggio m) {
		MessaggioDTO mDTO=new MessaggioDTO();
		mDTO.setId(m.getId());
		mDTO.setData(m.getDataOra().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
		mDTO.setMittente(m.isPrimoUtente()?u1.getUsername():u2.getUsername());
		mDTO.setNomeMittente(m.isPrimoUtente()?u1.getNome():u2.getNome());
		mDTO.setCognomeMittente(m.isPrimoUtente()?u1.getCognome():u2.getCognome());
		mDTO.setTesto(m.getTesto());
		mDTO.setIdRiferimentoMessaggio(m.getIdRiferimentoMessaggio());
		mDTO.setTipoMessaggio(m.getTipoMessaggio());
		return mDTO;
	}
}