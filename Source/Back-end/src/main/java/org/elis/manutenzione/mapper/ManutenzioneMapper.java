package org.elis.manutenzione.mapper;

import org.elis.manutenzione.dto.response.*;
import org.elis.manutenzione.model.entity.Manutenzione;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe mapper per la conversione di oggetti {@link Manutenzione} in DTO.
 */
@Component
public class ManutenzioneMapper {

    /**
     * Converte un oggetto {@link Manutenzione} in un DTO {@link RichiediManutenzioneResponse}.
     *
     * @param m L'oggetto Manutenzione da convertire.
     * @return Il DTO {@link RichiediManutenzioneResponse}.
     */
    public RichiediManutenzioneResponse toRichiediManutenzioneResponse(Manutenzione m) {
        RichiediManutenzioneResponse response = new RichiediManutenzioneResponse();
        response.setId(m.getId());
        response.setDescrizione(m.getDescrizione());
        response.setLuogo(m.getLuogo().getNome());
        response.setNucleo(m.getLuogo().getNucleo());
        response.setDataRichiesta(m.getDataRichiesta());
        response.setNome(m.getNome());
        return response;
    }

    /**
     * Converte un oggetto {@link Manutenzione} in un DTO {@link FiltraManutenzioneResponse}.
     *
     * @param m L'oggetto Manutenzione da convertire.
     * @return Il DTO {@link FiltraManutenzioneResponse}.
     */
    public FiltraManutenzioneResponse toFiltraManutenzioneResponse(Manutenzione m) {
        FiltraManutenzioneResponse response = new FiltraManutenzioneResponse();
        response.setId(m.getId());
        response.setDescrizione(m.getDescrizione());
        response.setLuogo(m.getLuogo().getNome());
        response.setNucleo(m.getLuogo().getNucleo());
        response.setDataRichiesta(m.getDataRichiesta());
        response.setRichiedente(m.getUtenteRichiedente().getEmail());
        response.setManutentore(m.getManutentore().getEmail());
        response.setPriorita(m.getPriorita());
        return response;
    }

    /**
     * Converte un oggetto {@link Manutenzione} in un DTO {@link AccettaManutenzioneResponse}.
     *
     * @param m L'oggetto Manutenzione da convertire.
     * @return Il DTO {@link AccettaManutenzioneResponse}.
     */
    public AccettaManutenzioneResponse toAccettaManutenzioneResponse(Manutenzione m) {
        AccettaManutenzioneResponse response = new AccettaManutenzioneResponse();
        response.setId(m.getId());
        response.setDescrizione(m.getDescrizione());
        response.setLuogo(m.getLuogo().getNome());
        response.setNucleo(m.getLuogo().getNucleo());
        response.setDataPrevista(m.getDataPrevista());
        response.setPriorita(m.getPriorita());
        response.setNome(m.getNome());
        return response;
    }

    /**
     * Converte un oggetto {@link Manutenzione} in un DTO {@link RiparaManutenzioneResponse}.
     *
     * @param manutenzione L'oggetto Manutenzione da convertire.
     * @return Il DTO {@link RiparaManutenzioneResponse}.
     */
    public RiparaManutenzioneResponse toRiparaManutenzioneResponse(Manutenzione manutenzione) {
        RiparaManutenzioneResponse response = new RiparaManutenzioneResponse();
        response.setId(manutenzione.getId());
        response.setDescrizione(manutenzione.getDescrizione());
        response.setLuogo(manutenzione.getLuogo().getNome());
        response.setNucleo(manutenzione.getLuogo().getNucleo());
        response.setPriorita(manutenzione.getPriorita());
        response.setDataPrevista(manutenzione.getDataPrevista());
        response.setDataRiparazione(manutenzione.getDataRiparazione());
        // Rimossa la riga duplicata di setPriorita
        return response;
    }

    /**
     * Converte un oggetto {@link Manutenzione} in un DTO {@link RifiutaManutenzioneResponse}.
     *
     * @param manutenzione L'oggetto Manutenzione da convertire.
     * @return Il DTO {@link RifiutaManutenzioneResponse}.
     */
    public RifiutaManutenzioneResponse toRifiutaManutenzioneResponse(Manutenzione manutenzione) {
        RifiutaManutenzioneResponse response = new RifiutaManutenzioneResponse();
        response.setId(manutenzione.getId());
        response.setDescrizione(manutenzione.getDescrizione());
        response.setLuogo(manutenzione.getLuogo().getNome());
        response.setNucleo(manutenzione.getLuogo().getNucleo());
        response.setPriorita(manutenzione.getPriorita());
        return response;
    }

    public List<GetManutenzioniDTO> toGetManutenzioniDTO(List<Manutenzione> manutenzioni) {
        List<GetManutenzioniDTO> manutenzioniDTO = new ArrayList<>();
        for (Manutenzione m : manutenzioni) {
            GetManutenzioniDTO manutenzioneDTO = new GetManutenzioniDTO();
            manutenzioneDTO.setIdManutenzione(m.getId());
            manutenzioneDTO.setNome(m.getNome());
            manutenzioneDTO.setDescrizione(m.getDescrizione());
            manutenzioniDTO.add(manutenzioneDTO);
        }
        return manutenzioniDTO;
    }

    public static List<ManutenzioneDTO> toManutenzioneDTO(List<Manutenzione> manutenzioni){
        List<ManutenzioneDTO> response = new ArrayList<>();
        for (Manutenzione m : manutenzioni) {
            ManutenzioneDTO manutenzioneDTO = new ManutenzioneDTO();
            manutenzioneDTO.setDescrizione(m.getDescrizione());
            manutenzioneDTO.setId(m.getId());
            manutenzioneDTO.setNome(m.getNome());
            manutenzioneDTO.setStatoManutenzione(m.getStatoManutenzione());
            if(m.getManutentore()!=null)
                manutenzioneDTO.setManutentore(m.getManutentore().getEmail());
            manutenzioneDTO.setPriorita(m.getPriorita());
            manutenzioneDTO.setUtenteRichiedente(m.getUtenteRichiedente().getEmail());
            manutenzioneDTO.setDataRichiesta(m.getDataRichiesta() != null ? m.getDataRichiesta() : null);
            manutenzioneDTO.setDataPrevista(m.getDataPrevista() != null ? m.getDataPrevista() : null);
            manutenzioneDTO.setDataRiparazione(m.getDataRiparazione() != null ? m.getDataRiparazione() : null);
            manutenzioneDTO.setImmagine(m.getImmagine());
            manutenzioneDTO.setLuogo(m.getLuogo().getNome());
            response.add(manutenzioneDTO);
        }
        return response;
    }

    public GetManutenzioneDTO toGetManutenzioneDTO(Manutenzione m) {
        GetManutenzioneDTO manutenzioneDTO = new GetManutenzioneDTO();
        manutenzioneDTO.setIdManutenzione(m.getId());
        manutenzioneDTO.setUtenteRichiedente(m.getUtenteRichiedente().getEmail());
        manutenzioneDTO.setLuogo(m.getLuogo().getNome());
        manutenzioneDTO.setStato(m.getStatoManutenzione());
        manutenzioneDTO.setManutentore(m.getManutentore() != null ? m.getManutentore().getEmail() : null);
        manutenzioneDTO.setDataRichiesta(m.getDataRichiesta());
        manutenzioneDTO.setDataRiparazione(m.getDataRiparazione());
        manutenzioneDTO.setDataPrevista(m.getDataPrevista());
        manutenzioneDTO.setPriorita(m.getPriorita());
        manutenzioneDTO.setImmagine(m.getImmagine());
        manutenzioneDTO.setNome(m.getNome());
        manutenzioneDTO.setDescrizione(m.getDescrizione());
        return manutenzioneDTO;
    }
}