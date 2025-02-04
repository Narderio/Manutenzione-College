package org.elis.manutenzione.mapper;

import org.elis.manutenzione.dto.response.*;
import org.elis.manutenzione.model.entity.Luogo;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Classe mapper per la conversione di oggetti {@link Luogo} in DTO.
 */
@Component
public class LuogoMapper {

    /**
     * Converte una lista di oggetti {@link Luogo} in una lista di DTO {@link GetLuoghiResponse}.
     *
     * @param luoghi La lista di luoghi da convertire.
     * @return La lista di DTO {@link GetLuoghiResponse}.
     */
    public List<GetLuoghiResponse> toGetLuoghiMapper(List<Luogo> luoghi) {
        List<GetLuoghiResponse> l= new ArrayList<>();
        for(Luogo luogo: luoghi){
            GetLuoghiResponse response = new GetLuoghiResponse();
            response.setId(luogo.getId());
            response.setNome(luogo.getNome());
            response.setNucleo(luogo.getNucleo());
            response.setTipo(luogo.getTipo());
            response.setPiano(luogo.getPiano());
            l.add(response);
        }
        return l;
    }

    /**
     * Converte un oggetto {@link Luogo} in un DTO {@link AggiungiLuogoResponse}.
     *
     * @param l L'oggetto Luogo da convertire.
     * @return Il DTO {@link AggiungiLuogoResponse}.
     */
    public AggiungiLuogoResponse toAggiungiLuogoResponse(Luogo l) {
        AggiungiLuogoResponse response = new AggiungiLuogoResponse();
        response.setId(l.getId());
        response.setNome(l.getNome());
        response.setTipo(l.getTipo().toString());
        response.setNucleo(l.getNucleo());
        response.setCapienza(l.getCapienza());
        return response;
    }

    /**
     * Converte una lista di oggetti {@link Luogo} in una lista di DTO {@link GetLuoghiComuniResponse}.
     *
     * @param luoghi La lista di luoghi da convertire.
     * @return La lista di DTO {@link GetLuoghiComuniResponse}.
     */
    public List<GetLuoghiComuniResponse> toGetLuoghiComuniMapper(List<Luogo> luoghi) {
        List<GetLuoghiComuniResponse> l= new ArrayList<>();
        for(Luogo luogo: luoghi){
            GetLuoghiComuniResponse response = new GetLuoghiComuniResponse();
            response.setId(luogo.getId());
            response.setNome(luogo.getNome());
            l.add(response);
        }
        return l;
    }

    /**
     * Converte una lista di oggetti {@link Luogo} in una lista di DTO {@link GetStanzeResponse}.
     *
     * @param luoghi La lista di luoghi da convertire.
     * @return La lista di DTO {@link GetStanzeResponse}.
     */
    public List<GetStanzeResponse> toGetStanzeMapper(List<Luogo> luoghi) {
        List<GetStanzeResponse> l= new ArrayList<>();
        for(Luogo luogo: luoghi){
            GetStanzeResponse response = new GetStanzeResponse();
            response.setId(luogo.getId());
            response.setNome(luogo.getNome());
            response.setNucleo(luogo.getNucleo());
            response.setPiano(luogo.getPiano());
            response.setCapienza(luogo.getCapienza());
            response.setResidenti(luogo.getResidenti().size());
            l.add(response);
        }
        return l;

    }

    /**
     * Converte una lista di oggetti {@link Luogo} in una lista di DTO {@link GetBagniResponse}.
     *
     * @param luoghi La lista di luoghi da convertire.
     * @return La lista di DTO {@link GetBagniResponse}.
     */
    public List<GetBagniResponse> toGetBagniMapper(List<Luogo> luoghi) {
        List<GetBagniResponse> l= new ArrayList<>();
        for(Luogo luogo: luoghi){
            GetBagniResponse response = new GetBagniResponse();
            response.setId(luogo.getId());
            response.setNucleo(luogo.getNucleo());
            l.add(response); // Aggiunta la riga mancante per aggiungere la response alla lista
        }
        return l;
    }

    /**
     * Converte una lista di oggetti {@link Luogo} in una lista di DTO {@link GetNucleiResponse}.
     *
     * @param luoghi La lista di luoghi da convertire.
     * @return La lista di DTO {@link GetNucleiResponse}.
     */
    public List<GetNucleiResponse> toGetNucleiMapper(List<Luogo> luoghi) {
        Set<String> nomiNuclei = new HashSet<>();
        List<GetNucleiResponse> responseList = new ArrayList<>();

        for (Luogo luogo : luoghi) {
            String nomeNucleo = luogo.getNucleo();
            // Aggiungi il nome del nucleo solo se non è già presente nel set
            if (nomeNucleo != null && nomiNuclei.add(nomeNucleo)) {
                GetNucleiResponse response = new GetNucleiResponse();
                response.setNome(nomeNucleo);
                responseList.add(response);
            }
        }

        return responseList;
    }

    /**
     * Converte un oggetto {@link Luogo} in un DTO {@link GetStanzaDTO}.
     *
     * @param stanza L'oggetto Luogo da convertire.
     * @return Il DTO {@link GetStanzaDTO}.
     */
    public GetStanzaDTO toGetStanzaMapper(Luogo stanza) {
        GetStanzaDTO response = new GetStanzaDTO();
        response.setNome(stanza.getNome());
        return response;
    }
}