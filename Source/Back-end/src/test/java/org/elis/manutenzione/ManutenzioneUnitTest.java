package org.elis.manutenzione;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.elis.manutenzione.dto.request.*;
import org.elis.manutenzione.model.entity.Luogo;
import org.elis.manutenzione.model.entity.Residente;
import org.elis.manutenzione.model._enum.Ruolo;
import org.elis.manutenzione.model.entity.Utente;
import org.elis.manutenzione.repository.LuogoRepository;
import org.elis.manutenzione.repository.ResidenteRepository;
import org.elis.manutenzione.repository.UtenteRepository;
import org.elis.manutenzione.security.TokenUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = ManutenzioneApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc(print = MockMvcPrint.LOG_DEBUG,printOnlyOnFailure = false)
class ManutenzioneUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LuogoRepository luogoRepository;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private ResidenteRepository residenteRepository;


    @Order(1)
    @Test
    @WithUserDetails("nar@03.com")
    public void getManutentori() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/getManutentori"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }



    @Order(1)
    @Test
    @WithUserDetails("nar@03.com")
    public void getUtenti() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/all/getUtenti"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(1)
    @Test
    @WithUserDetails("nar@03.com")
    public void getResidenti() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/getResidenti"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(1)
    @Test
    @WithUserDetails("nar@03.com")
    public void getSupervisori() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/getSupervisori"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(1)
    @Test
    @WithUserDetails("nar@03.com")
    public void getAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/getAdmin"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(1)
    @Test
    @WithUserDetails("nar@03.com")
    public void testRegistrazioneNull() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/registrazione")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(1)
    @Test
    @WithUserDetails("gor@03.com")
    public void testRegistrazioneNonAutorizzato() throws Exception {
        RegistrazioneRequest registrazioneRequest = new RegistrazioneRequest();
        registrazioneRequest.setCognome("Rossi");
        registrazioneRequest.setNome("Mario");
        registrazioneRequest.setEmail("ros@03.com");
        registrazioneRequest.setRuolo(Ruolo.Residente);
        registrazioneRequest.setDataDiNascita(LocalDate.of(2003, 1, 4));
        registrazioneRequest.setBloccato(false);
        String json = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(registrazioneRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/registrazione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Order(1)
    @Test
    @WithUserDetails("nar@03.com")
    public void testRegistrazioneEmailMancante() throws Exception {
        RegistrazioneRequest registrazioneRequest = new RegistrazioneRequest();
        registrazioneRequest.setCognome("Rossi");
        registrazioneRequest.setNome("Mario");
        registrazioneRequest.setRuolo(Ruolo.Residente);
        registrazioneRequest.setDataDiNascita(LocalDate.of(2003, 1, 4));
        registrazioneRequest.setBloccato(false);
        String json = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(registrazioneRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/registrazione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(1)
    @Test
    @WithUserDetails("nar@03.com")
    public void testRegistrazioneNomeMancante() throws Exception {
        RegistrazioneRequest registrazioneRequest = new RegistrazioneRequest();
        registrazioneRequest.setCognome("Rossi");
        registrazioneRequest.setEmail("ros@03.com");
        registrazioneRequest.setRuolo(Ruolo.Residente);
        registrazioneRequest.setDataDiNascita(LocalDate.of(2003, 1, 4));
        registrazioneRequest.setBloccato(false);
        String json = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(registrazioneRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/registrazione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(1)
    @Test
    @WithUserDetails("nar@03.com")
    public void testRegistrazioneCognomeMancante() throws Exception {
        RegistrazioneRequest registrazioneRequest = new RegistrazioneRequest();
        registrazioneRequest.setNome("Mario");
        registrazioneRequest.setEmail("ros@03.com");
        registrazioneRequest.setRuolo(Ruolo.Residente);
        registrazioneRequest.setDataDiNascita(LocalDate.of(2003, 1, 4));
        registrazioneRequest.setBloccato(false);
        String json = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(registrazioneRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/registrazione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(1)
    @Test
    @WithUserDetails("nar@03.com")
    public void testRegistrazioneRuoloMancante() throws Exception {
        RegistrazioneRequest registrazioneRequest = new RegistrazioneRequest();
        registrazioneRequest.setCognome("Rossi");
        registrazioneRequest.setNome("Mario");
        registrazioneRequest.setEmail("ros@03.com");
        registrazioneRequest.setDataDiNascita(LocalDate.of(2003, 1, 4));
        registrazioneRequest.setBloccato(false);
        String json = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(registrazioneRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/registrazione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(1)
    @Test
    @WithUserDetails("nar@03.com")
    public void testRegistrazioneDataMancante() throws Exception {
        RegistrazioneRequest registrazioneRequest = new RegistrazioneRequest();
        registrazioneRequest.setCognome("Rossi");
        registrazioneRequest.setNome("Mario");
        registrazioneRequest.setEmail("ros@03.com");
        registrazioneRequest.setRuolo(Ruolo.Residente);
        registrazioneRequest.setBloccato(false);
        String json = new ObjectMapper().writeValueAsString(registrazioneRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/registrazione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(1)
    @Test
    @WithUserDetails("nar@03.com")
    public void testRegistrazioneGiustoResidente() throws Exception {
        RegistrazioneRequest registrazioneRequest = new RegistrazioneRequest();
        registrazioneRequest.setCognome("Rossi");
        registrazioneRequest.setNome("Mario");
        registrazioneRequest.setEmail("ros@03.com");
        registrazioneRequest.setRuolo(Ruolo.Residente);
        registrazioneRequest.setDataDiNascita(LocalDate.of(2003, 1, 4));
        registrazioneRequest.setBloccato(false);
        String json = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(registrazioneRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/registrazione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(1)
    @Test
    @WithUserDetails("nar@03.com")
    public void testRegistrazioneGiustoAdmin() throws Exception {
        RegistrazioneRequest registrazioneRequest = new RegistrazioneRequest();
        registrazioneRequest.setCognome("Rossi");
        registrazioneRequest.setNome("Mario");
        registrazioneRequest.setEmail("rosAdmin@03.com");
        registrazioneRequest.setRuolo(Ruolo.Admin);
        registrazioneRequest.setDataDiNascita(LocalDate.of(2003, 1, 4));
        registrazioneRequest.setBloccato(false);
        String json = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(registrazioneRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/registrazione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(1)
    @Test
    @WithUserDetails("nar@03.com")
    public void testRegistrazioneGiustoSupervisore() throws Exception {
        RegistrazioneRequest registrazioneRequest = new RegistrazioneRequest();
        registrazioneRequest.setCognome("Rossi");
        registrazioneRequest.setNome("Mario");
        registrazioneRequest.setEmail("rosSupervisore@03.com");
        registrazioneRequest.setRuolo(Ruolo.Supervisore);
        registrazioneRequest.setDataDiNascita(LocalDate.of(2003, 1, 4));
        registrazioneRequest.setBloccato(false);
        String json = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(registrazioneRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/registrazione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(1)
    @Test
    @WithUserDetails("nar@03.com")
    public void testRegistrazioneManutentoreTipoMancante() throws Exception {
        RegistrazioneRequest registrazioneRequest = new RegistrazioneRequest();
        registrazioneRequest.setCognome("Rossi");
        registrazioneRequest.setNome("Mario");
        registrazioneRequest.setEmail("rosManutentoreee@03.com");
        registrazioneRequest.setRuolo(Ruolo.Manutentore);
        registrazioneRequest.setDataDiNascita(LocalDate.of(2003, 1, 4));
        registrazioneRequest.setBloccato(false);
        String json = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(registrazioneRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/registrazione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(1)
    @Test
    @WithUserDetails("nar@03.com")
    public void testRegistrazioneGiustoManutentore() throws Exception {
        RegistrazioneRequest registrazioneRequest = new RegistrazioneRequest();
        registrazioneRequest.setCognome("Rossi");
        registrazioneRequest.setNome("Mario");
        registrazioneRequest.setEmail("rosManutentore@03.com");
        registrazioneRequest.setRuolo(Ruolo.Manutentore);
        registrazioneRequest.setTipoManutentore("Elettricista");
        registrazioneRequest.setDataDiNascita(LocalDate.of(2003, 1, 4));
        registrazioneRequest.setBloccato(false);
        String json = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(registrazioneRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/registrazione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(2)
    @Test
    @WithUserDetails("nar@03.com")
    public void testRegistrazioneGiaRegistrato() throws Exception {
        RegistrazioneRequest registrazioneRequest = new RegistrazioneRequest();
        registrazioneRequest.setCognome("Rossi");
        registrazioneRequest.setNome("Mario");
        registrazioneRequest.setEmail("ros@03.com");
        registrazioneRequest.setRuolo(Ruolo.Residente);
        registrazioneRequest.setDataDiNascita(LocalDate.of(2003, 1, 4));
        registrazioneRequest.setBloccato(false);
        String json = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(registrazioneRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/registrazione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(2)
    @Test
    public void testLoginSbagliato1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(2)
    @Test
    public void testLoginEmailMancante() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("prova");
        String json = new ObjectMapper().writeValueAsString(loginRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(2)
    @Test
    public void testLoginPasswordMancante() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("nar@03.com");
        String json = new ObjectMapper().writeValueAsString(loginRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }


    @Order(3)
    @Test
    public void testLoginSbagliato2() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("nar@03.com");
        loginRequest.setPassword("prov");
        String json = new ObjectMapper().writeValueAsString(loginRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(4)
    @Test
    public void testLoginGiustoAdmin() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("nar@03.com");
        loginRequest.setPassword("prova");
        String json = new ObjectMapper().writeValueAsString(loginRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(4)
    @Test
    public void testLoginGiustoSupervisore() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("lab@03.com");
        loginRequest.setPassword("prova");
        String json = new ObjectMapper().writeValueAsString(loginRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }


    @Order(4)
    @Test
    public void testLoginGiustoManutentore() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("nap2@03.com");
        loginRequest.setPassword("prova");
        String json = new ObjectMapper().writeValueAsString(loginRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }


    @Order(4)
    @Test
    public void testLoginGiustoResidente() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("gor@03.com");
        loginRequest.setPassword("prova");
        String json = new ObjectMapper().writeValueAsString(loginRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(5)
    @Test
    @WithUserDetails("nar@03.com")
    public void testEliminaUtenteGiusto() throws Exception {
        EliminaUtenteRequest r = new EliminaUtenteRequest();
        r.setEmail("ros@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/eliminaUtente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(6)
    @Test
    @WithUserDetails("nar@03.com")
    public void testRegistrazioneBloccato() throws Exception {
        RegistrazioneRequest registrazioneRequest = new RegistrazioneRequest();
        registrazioneRequest.setCognome("Rossi");
        registrazioneRequest.setNome("Mario");
        registrazioneRequest.setEmail("ros@03.com");
        registrazioneRequest.setRuolo(Ruolo.Residente);
        registrazioneRequest.setDataDiNascita(LocalDate.of(2003, 1, 4));
        registrazioneRequest.setBloccato(false);
        String json = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(registrazioneRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/registrazione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }


    @Order(5)
    @Test
    @WithUserDetails("nar@03.com")
    public void testRichiediManutenzioneNonAutorizzato() throws Exception {
        RichiediManutenzioneRequest r = new RichiediManutenzioneRequest();
        r.setDescrizione("prova");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/residente/richiediManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Order(6)
    @Test
    @WithUserDetails("gor@03.com")
    public void testRichiediManutenzioneLuogoErrato() throws Exception {
        RichiediManutenzioneRequest r = new RichiediManutenzioneRequest();
        r.setDescrizione("Il bagno è rotto");
        r.setIdLuogo(0);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/residente/richiediManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(6)
    @Test
    @WithUserDetails("gor@03.com")
    public void testRichiediManutenzioneDescrizioneMancante() throws Exception {
        RichiediManutenzioneRequest r = new RichiediManutenzioneRequest();
        r.setIdLuogo(1);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/residente/richiediManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(6)
    @Test
    @WithUserDetails("gor@03.com")
    public void testRichiediManutenzioneLuogoNonTrovato() throws Exception {
        RichiediManutenzioneRequest r = new RichiediManutenzioneRequest();
        r.setDescrizione("Il bagno è rotto");
        r.setIdLuogo(100);
        r.setNome("prova");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/residente/richiediManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Order(6)
    @Test
    @WithUserDetails("gor@03.com")
    public void testRichiediManutenzioneGiusto() throws Exception {
        RichiediManutenzioneRequest r = new RichiediManutenzioneRequest();
        r.setDescrizione("Il bagno è rotto");
        r.setIdLuogo(1);
        r.setNome("prova");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/residente/richiediManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(7)
    @Test
    @WithUserDetails("gor@03.com")
    public void testRichiediManutenzioneGiaRichiesta() throws Exception {
        RichiediManutenzioneRequest r = new RichiediManutenzioneRequest();
        r.setDescrizione("Il bagno è rotto");
        r.setIdLuogo(1);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/residente/richiediManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(7)
    @Test
    @WithUserDetails("gor@03.com")
    public void filtraManutenzioneNonAutorizzato() throws Exception {
        FiltraManutenzioneRequest r = new FiltraManutenzioneRequest();
        r.setIdManutenzione(1);
        r.setPriorita(2);
        r.setEmailManutentore("nap@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/supervisore/filtraManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Order(8)
    @Test
    @WithUserDetails("lab@03.com")
    public void filtraManutenzioneManutentoreErrato() throws Exception {
        FiltraManutenzioneRequest r = new FiltraManutenzioneRequest();
        r.setIdManutenzione(1);
        r.setPriorita(2);
        r.setEmailManutentore("nar@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/supervisore/filtraManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Order(9)
    @Test
    @WithUserDetails("lab@03.com")
    public void filtraManutenzionePrioritaErrato() throws Exception {
        FiltraManutenzioneRequest r = new FiltraManutenzioneRequest();
        r.setIdManutenzione(1);
        r.setPriorita(0);
        r.setEmailManutentore("nap@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/supervisore/filtraManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(10)
    @Test
    @WithUserDetails("lab@03.com")
    public void filtraManutenzioneIdErrato() throws Exception {
        FiltraManutenzioneRequest r = new FiltraManutenzioneRequest();
        r.setIdManutenzione(10);
        r.setPriorita(1);
        r.setEmailManutentore("nap@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/supervisore/filtraManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Order(10)
    @Test
    @WithUserDetails("lab@03.com")
    public void filtraManutenzioneIdMancante() throws Exception {
        FiltraManutenzioneRequest r = new FiltraManutenzioneRequest();
        r.setPriorita(2);
        r.setEmailManutentore("nap@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/supervisore/filtraManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(10)
    @Test
    @WithUserDetails("lab@03.com")
    public void filtraManutenzioneManutentoreMancante() throws Exception {
        FiltraManutenzioneRequest r = new FiltraManutenzioneRequest();
        r.setIdManutenzione(3);
        r.setPriorita(2);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/supervisore/filtraManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(11)
    @Test
    @WithUserDetails("lab@03.com")
    public void filtraManutenzioneGiusto() throws Exception {
        FiltraManutenzioneRequest r = new FiltraManutenzioneRequest();
        r.setIdManutenzione(3);
        r.setPriorita(2);
        r.setEmailManutentore("nap@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/supervisore/filtraManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(12)
    @Test
    @WithUserDetails("lab@03.com")
    public void accettaManutenzioneNonAutorizzato() throws Exception {
        AccettaManutenzioneRequest r = new AccettaManutenzioneRequest();
        r.setIdManutenzione(3);
        r.setDataPrevista(LocalDate.of(2025, 12, 31));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/manutentore/accettaManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Order(13)
    @Test
    @WithUserDetails("nap@03.com")
    public void accettaManutenzioneIdErrato() throws Exception {
        AccettaManutenzioneRequest r = new AccettaManutenzioneRequest();
        r.setIdManutenzione(10);
        r.setDataPrevista(LocalDate.of(2025, 12, 31));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/manutentore/accettaManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Order(13)
    @Test
    @WithUserDetails("nap@03.com")
    public void accettaManutenzioneIdMancante() throws Exception {
        AccettaManutenzioneRequest r = new AccettaManutenzioneRequest();
        r.setIdManutenzione(0);
        r.setDataPrevista(LocalDate.of(2025, 12, 31));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/manutentore/accettaManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(14)
    @Test
    @WithUserDetails("nap@03.com")
    public void accettaManutenzioneDataErrata() throws Exception {
        AccettaManutenzioneRequest r = new AccettaManutenzioneRequest();
        r.setIdManutenzione(1);
        r.setDataPrevista(LocalDate.of(2021, 12, 31));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/manutentore/accettaManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(14)
    @Test
    @WithUserDetails("nap@03.com")
    public void accettaManutenzioneDataMancante() throws Exception {
        AccettaManutenzioneRequest r = new AccettaManutenzioneRequest();
        r.setIdManutenzione(1);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/manutentore/accettaManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(14)
    @Test
    @WithUserDetails("nap@03.com")
    public void accettaManutenzioneStatoErrato() throws Exception {
        AccettaManutenzioneRequest r = new AccettaManutenzioneRequest();
        r.setIdManutenzione(1);
        r.setDataPrevista(LocalDate.of(2024, 12, 12));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/manutentore/accettaManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(12)
    @Test
    @WithUserDetails("nap2@03.com")
    public void accettaManutenzioneManutentoreErrato() throws Exception {
        AccettaManutenzioneRequest r = new AccettaManutenzioneRequest();
        r.setIdManutenzione(3);
        r.setDataPrevista(LocalDate.of(2025, 12, 31));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/manutentore/accettaManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }


    @Order(15)
    @Test
    @WithUserDetails("nap@03.com")
    public void accettaManutenzioneGiusto() throws Exception {
        AccettaManutenzioneRequest r = new AccettaManutenzioneRequest();
        r.setIdManutenzione(3);
        r.setDataPrevista(LocalDate.of(2027, 12, 12));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/manutentore/accettaManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(16)
    @Test
    @WithUserDetails("nar@03.com")
    public void riparaManutenzioneNonAutorizzato() throws Exception {
        RiparaManutenzioneRequest r = new RiparaManutenzioneRequest();
        r.setIdManutenzione(1);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/manutentore/riparaManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Order(17)
    @Test
    @WithUserDetails("nap2@03.com")
    public void riparaManutenzioneManutentoreErrato() throws Exception {
        RiparaManutenzioneRequest r = new RiparaManutenzioneRequest();
        r.setIdManutenzione(3);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/manutentore/riparaManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Order(18)
    @Test
    @WithUserDetails("nap2@03.com")
    public void riparaManutenzioneIdErrato() throws Exception {
        RiparaManutenzioneRequest r = new RiparaManutenzioneRequest();
        r.setIdManutenzione(2);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/manutentore/riparaManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(18)
    @Test
    @WithUserDetails("nap2@03.com")
    public void riparaManutenzioneIdErratoe() throws Exception {
        RiparaManutenzioneRequest r = new RiparaManutenzioneRequest();
        r.setIdManutenzione(9999999);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/manutentore/riparaManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(18)
    @Test
    @WithUserDetails("nap2@03.com")
    public void riparaManutenzioneIdMancante() throws Exception {
        RiparaManutenzioneRequest r = new RiparaManutenzioneRequest();
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/manutentore/riparaManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(19)
    @Test
    @WithUserDetails("nap@03.com")
    public void riparaManutenzioneGiusto() throws Exception {
        RiparaManutenzioneRequest r = new RiparaManutenzioneRequest();
        r.setIdManutenzione(3);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/manutentore/riparaManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(20)
    @Test
    @WithUserDetails("lab@03.com")
    public void filtraManutenzioneGiusto2() throws Exception {
        FiltraManutenzioneRequest r = new FiltraManutenzioneRequest();
        r.setIdManutenzione(2);
        r.setPriorita(2);
        r.setEmailManutentore("nap@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/supervisore/filtraManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(21)
    @Test
    @WithUserDetails("nap@03.com")
    public void rifiutaManutenzioneIdErrato() throws Exception {
        RiparaManutenzioneRequest r = new RiparaManutenzioneRequest();
        r.setIdManutenzione(10);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/rifiutaManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Order(21)
    @Test
    @WithUserDetails("nap@03.com")
    public void rifiutaManutenzioneIdMancante() throws Exception {
        RiparaManutenzioneRequest r = new RiparaManutenzioneRequest();
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/rifiutaManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(22)
    @Test
    @WithUserDetails("nar@03.com")
    public void rifiutaManutenzioneNonAutorizzato() throws Exception {
        RiparaManutenzioneRequest r = new RiparaManutenzioneRequest();
        r.setIdManutenzione(2);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/manutentore/riparaManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Order(23)
    @Test
    @WithUserDetails("nap2@03.com")
    public void rifiutaManutenzioneManutentoreErrato() throws Exception {
        RiparaManutenzioneRequest r = new RiparaManutenzioneRequest();
        r.setIdManutenzione(2);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/rifiutaManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Order(24)
    @Test
    @WithUserDetails("nap@03.com")
    public void rifiutaManutenzioneGiusto() throws Exception {
        RiparaManutenzioneRequest r = new RiparaManutenzioneRequest();
        r.setIdManutenzione(2);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/rifiutaManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(25)
    @Test
    @WithUserDetails("gor@03.com")
    public void feedbackManutenzioneGiusto1() throws Exception {
        FeedbackManutenzioneRequest r = new FeedbackManutenzioneRequest();
        r.setIdManutenzione(3);
        r.setRisolto(true);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/residente/feedbackManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(25)
    @Test
    @WithUserDetails("gor@03.com")
    public void testRichiediManutenzioneAutorizzato2() throws Exception {
        RichiediManutenzioneRequest r = new RichiediManutenzioneRequest();
        r.setDescrizione("Il bagno è rotto");
        r.setIdLuogo(2);
        r.setNome("prova1");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/residente/richiediManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(26)
    @Test
    @WithUserDetails("lab@03.com")
    public void filtraManutenzioneGiusto3() throws Exception {
        FiltraManutenzioneRequest r = new FiltraManutenzioneRequest();
        r.setIdManutenzione(4);
        r.setPriorita(2);
        r.setEmailManutentore("nap@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/supervisore/filtraManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(27)
    @Test
    @WithUserDetails("nap@03.com")
    public void accettaManutenzioneGiusto2() throws Exception {
        AccettaManutenzioneRequest r = new AccettaManutenzioneRequest();
        r.setIdManutenzione(4);
        r.setDataPrevista(LocalDate.of(2027, 12, 13));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/manutentore/accettaManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(28)
    @Test
    @WithUserDetails("nap@03.com")
    public void riparaManutenzioneGiusto2() throws Exception {
        RiparaManutenzioneRequest r = new RiparaManutenzioneRequest();
        r.setIdManutenzione(4);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/manutentore/riparaManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(29)
    @Test
    @WithUserDetails("gor@03.com")
    public void feedbackManutenzioneGiusto2() throws Exception {
        FeedbackManutenzioneRequest r = new FeedbackManutenzioneRequest();
        r.setIdManutenzione(4);
        r.setRisolto(false);
        r.setProblema("Il problema non è stato risolto");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/residente/feedbackManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(29)
    @Test
    @WithUserDetails("gor@03.com")
    public void aggiungiLuogoNonAutorizzato() throws Exception {
        AggiungiLuogoRequest r = new AggiungiLuogoRequest();
        r.setNome("Colonna 3");
        r.setCapienza(4);
        r.setTipo("Stanza");
        r.setNucleo("Colonna");
        r.setPiano(4);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/aggiungiLuogo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Order(29)
    @Test
    @WithUserDetails("nar@03.com")
    public void aggiungiLuogoNull() throws Exception {
        AggiungiLuogoRequest r = null;
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/aggiungiLuogo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(29)
    @Test
    @WithUserDetails("nar@03.com")
    public void aggiungiLuogoSenzaNome() throws Exception {
        AggiungiLuogoRequest r = new AggiungiLuogoRequest();
        r.setCapienza(4);
        r.setTipo("Stanza");
        r.setNucleo("Colonna");
        r.setPiano(4);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/aggiungiLuogo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(29)
    @Test
    @WithUserDetails("nar@03.com")
    public void aggiungiLuogoSenzaTipo() throws Exception {
        AggiungiLuogoRequest r = new AggiungiLuogoRequest();
        r.setNome("Colonna 3");
        r.setCapienza(4);
        r.setNucleo("Colonna");
        r.setPiano(4);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/aggiungiLuogo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(29)
    @Test
    @WithUserDetails("nar@03.com")
    public void aggiungiLuogoTipoErrato() throws Exception {
        AggiungiLuogoRequest r = new AggiungiLuogoRequest();
        r.setNome("Colonna 3");
        r.setCapienza(4);
        r.setTipo("Prova");
        r.setNucleo("Colonna");
        r.setPiano(4);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/aggiungiLuogo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(29)
    @Test
    @WithUserDetails("nar@03.com")
    public void aggiungiLuogoStanzaSenzaNucleo() throws Exception {
        AggiungiLuogoRequest r = new AggiungiLuogoRequest();
        r.setNome("Colonna 3");
        r.setCapienza(4);
        r.setTipo("Stanza");
        r.setPiano(4);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/aggiungiLuogo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(29)
    @Test
    @WithUserDetails("nar@03.com")
    public void aggiungiLuogoPianoErrato() throws Exception {
        AggiungiLuogoRequest r = new AggiungiLuogoRequest();
        r.setNome("Colonna 3");
        r.setCapienza(4);
        r.setTipo("Stanza");
        r.setNucleo("Colonna");
        r.setPiano(10);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/aggiungiLuogo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(29)
    @Test
    @WithUserDetails("nar@03.com")
    public void aggiungiLuogoCapienzaErrata() throws Exception {
        AggiungiLuogoRequest r = new AggiungiLuogoRequest();
        r.setNome("Colonna 3");
        r.setCapienza(0);
        r.setTipo("Stanza");
        r.setNucleo("Colonna");
        r.setPiano(4);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/aggiungiLuogo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(30)
    @Test
    @WithUserDetails("nar@03.com")
    public void aggiungiLuogoGiusto() throws Exception {
        AggiungiLuogoRequest r = new AggiungiLuogoRequest();
        r.setNome("Colonna 3");
        r.setCapienza(4);
        r.setTipo("Stanza");
        r.setNucleo("Colonna");
        r.setPiano(4);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/aggiungiLuogo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(1)
    @Test
    @WithUserDetails("nar@03.com")
    public void modificaStanzaNonTrovata() throws Exception {
        ModificaStanzaRequest r = new ModificaStanzaRequest();
        r.setIdStanza(100);
        r.setCapienza(4);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/modificaStanza")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Order(1)
    @Test
    @WithUserDetails("nar@03.com")
    public void modificaStanza() throws Exception {
        ModificaStanzaRequest r = new ModificaStanzaRequest();
        r.setIdStanza(1);
        r.setCapienza(4);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/modificaStanza")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }



    @Order(31)
    @Test
    @WithUserDetails("nar@03.com")
    public void aggiungiLuogoGiaEsistente() throws Exception {
        AggiungiLuogoRequest r = new AggiungiLuogoRequest();
        r.setNome("Colonna 3");
        r.setCapienza(4);
        r.setTipo("Stanza");
        r.setNucleo("Colonna");
        r.setPiano(4);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/aggiungiLuogo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(31)
    @Test
    @WithUserDetails("gor@03.com")
    public void aggiungiResidenteInStanzaNonAutorizzato() throws Exception {
        AggiungiResidenteInStanzaRequest r = new AggiungiResidenteInStanzaRequest();
        r.setEmail("gor@03.com");
        r.setStanza("Colonna 3");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/aggiungiResidenteInStanza")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Order(31)
    @Test
    @WithUserDetails("nar@03.com")
    public void aggiungiResidenteInStanzaEmailMancante() throws Exception {
        AggiungiResidenteInStanzaRequest r = new AggiungiResidenteInStanzaRequest();
        r.setStanza("Colonna 3");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/aggiungiResidenteInStanza")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(31)
    @Test
    @WithUserDetails("nar@03.com")
    public void aggiungiResidenteInStanzaStanzaMancante() throws Exception {
        AggiungiResidenteInStanzaRequest r = new AggiungiResidenteInStanzaRequest();
        r.setEmail("gor@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/aggiungiResidenteInStanza")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(31)
    @Test
    @WithUserDetails("nar@03.com")
    public void aggiungiResidenteInStanzaEmailErrata() throws Exception {
        AggiungiResidenteInStanzaRequest r = new AggiungiResidenteInStanzaRequest();
        r.setEmail("gora@03.com");
        r.setStanza("Colonna 3");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/aggiungiResidenteInStanza")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Order(31)
    @Test
    @WithUserDetails("nar@03.com")
    public void aggiungiResidenteInStanzaRuoloErrato() throws Exception {
        AggiungiResidenteInStanzaRequest r = new AggiungiResidenteInStanzaRequest();
        r.setEmail("nar@03.com");
        r.setStanza("Colonna 3");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/aggiungiResidenteInStanza")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(31)
    @Test
    @WithUserDetails("nar@03.com")
    public void aggiungiResidenteInStanzaStanzaErrata() throws Exception {
        AggiungiResidenteInStanzaRequest r = new AggiungiResidenteInStanzaRequest();
        r.setEmail("gor@03.com");
        r.setStanza("Colonna 5");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/aggiungiResidenteInStanza")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Order(32)
    @Test
    @WithUserDetails("nar@03.com")
    public void aggiungiResidenteInStanzaGiusto() throws Exception {
        AggiungiResidenteInStanzaRequest r = new AggiungiResidenteInStanzaRequest();
        r.setEmail("gor@03.com");
        r.setStanza("Colonna 3");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/aggiungiResidenteInStanza")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(33)
    @Test
    @WithUserDetails("gor@03.com")
    public void getLuoghi() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/residente/getStanza"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(33)
    @Test
    @WithUserDetails("nar@03.com")
    public void aggiungiResidenteInStanzaRipetuto() throws Exception {
        AggiungiResidenteInStanzaRequest r = new AggiungiResidenteInStanzaRequest();
        r.setEmail("gor@03.com");
        r.setStanza("Colonna 3");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/aggiungiResidenteInStanza")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(31)
    @Test
    @WithUserDetails("nar@03.com")
    public void aggiungiLuogoBagno() throws Exception {
        AggiungiLuogoRequest r = new AggiungiLuogoRequest();
        r.setNome("Bagno");
        r.setCapienza(4);
        r.setTipo("Bagno");
        r.setNucleo("Colonna");
        r.setPiano(4);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/aggiungiLuogo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }


    @Order(31)
    @Test
    @WithUserDetails("nar@03.com")
    public void testGetLuoghi() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/all/getLuoghi"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(32)
    @Test
    @WithUserDetails("nar@03.com")
    public void aggiungiLuogoGiusto2() throws Exception {
        AggiungiLuogoRequest r = new AggiungiLuogoRequest();
        r.setNome("Giardino");
        r.setCapienza(4);
        r.setTipo("LuogoComune");
        r.setNucleo("Colonna");
        r.setPiano(0);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/aggiungiLuogo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(32)
    @Test
    @WithUserDetails("gor@03.com")
    public void eliminaLuogoNonAutorizzato() throws Exception {
        EliminaLuogoRequest r = new EliminaLuogoRequest();
        r.setLuogoId(5);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/eliminaLuogo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Order(32)
    @Test
    @WithUserDetails("nar@03.com")
    public void eliminaLuogoNull() throws Exception {
        EliminaLuogoRequest r = null;
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/eliminaLuogo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(32)
    @Test
    @WithUserDetails("nar@03.com")
    public void eliminaLuogoIdErrato() throws Exception {
        EliminaLuogoRequest r = new EliminaLuogoRequest();
        r.setLuogoId(0);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/eliminaLuogo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(32)
    @Test
    @WithUserDetails("nar@03.com")
    public void eliminaLuogoIdErrato2() throws Exception {
        EliminaLuogoRequest r = new EliminaLuogoRequest();
        r.setLuogoId(100);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/eliminaLuogo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Order(1)
    @Test
    @WithUserDetails("nar@03.com")
    public void eliminaLuogoIdErrato3() throws Exception {
        EliminaLuogoRequest r = new EliminaLuogoRequest();
        r.setLuogoId(100);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/eliminaLuogo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Order(32)
    @Test
    @WithUserDetails("nar@03.com")
    public void eliminaLuogoStanza() throws Exception {
        EliminaLuogoRequest r = new EliminaLuogoRequest();
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/eliminaLuogo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }


    @Order(33)
    @Test
    @WithUserDetails("nar@03.com")
    public void eliminaLuogoGiusto() throws Exception {
        EliminaLuogoRequest r = new EliminaLuogoRequest();
        r.setLuogoId(5);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/eliminaLuogo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(34)
    @Test
    @WithUserDetails("nar@03.com")
    public void aggiungiLuogoGiusto3() throws Exception {
        AggiungiLuogoRequest r = new AggiungiLuogoRequest();
        r.setNome("Giardino");
        r.setCapienza(4);
        r.setTipo("LuogoComune");
        r.setNucleo("Colonna");
        r.setPiano(0);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/aggiungiLuogo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(35)
    @Test
    @WithUserDetails("nar@03.com")
    public void testFiltroManutenzioni4() throws Exception {
        FiltroManutenzione r = new FiltroManutenzione();
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/all/filtroManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(35)
    @Test
    @WithUserDetails("nar@03.com")
    public void testFiltroManutenzioni() throws Exception {
        FiltroManutenzione r = new FiltroManutenzione();
        //r.setDataPrevista("2024-12-12");
        r.setManutentore("nap@03.com");
        r.setDescrizione("Il bagno è rotto");
        r.setPriorita(2);
        r.setStatoManutenzione("Riparata");
        //r.setDataRichiesta("2024-12-12");
        //r.setDataRiparazione("2024-12-12");
        r.setUtenteRichiedente("gor@03.com");
        r.setNome("prova1");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/all/filtroManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Order(35)
    @Test
    @WithUserDetails("nap@03.com")
    public void testFiltroManutenzioni2() throws Exception {
        FiltroManutenzione r = new FiltroManutenzione();
        //r.setDataPrevista("2024-12-12");
        r.setManutentore("nap@03.com");
        r.setDescrizione("Il bagno è rotto");
        r.setPriorita(2);
        r.setStatoManutenzione("Riparata");
        //r.setDataRichiesta("2024-12-12");
        //r.setDataRiparazione("2024-12-12");
        r.setUtenteRichiedente("gor@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/all/filtroManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Order(35)
    @Test
    @WithUserDetails("gor@03.com")
    public void testFiltroManutenzioni3() throws Exception {
        FiltroManutenzione r = new FiltroManutenzione();
        //r.setDataPrevista("2024-12-12");
        r.setManutentore("nap@03.com");
        r.setDescrizione("Il bagno è rotto");
        r.setPriorita(2);
        r.setStatoManutenzione("Riparata");
        //r.setDataRichiesta("2024-12-12");
        //r.setDataRiparazione("2024-12-12");
        r.setUtenteRichiedente("gor@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/all/filtroManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Order(31)
    @Test
    @WithUserDetails("nar@03.com")
    public void aggiungiResidenteInStanzaGiusto2() throws Exception {
        AggiungiResidenteInStanzaRequest r = new AggiungiResidenteInStanzaRequest();
        r.setEmail("ilmitico79@gmail.com");
        r.setStanza("Colonna 1");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/aggiungiResidenteInStanza")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(36)
    @Test
    @WithUserDetails("gor@03.com")
    public void testRichiediManutenzioneGiusto3() throws Exception {
        RichiediManutenzioneRequest r = new RichiediManutenzioneRequest();
        r.setDescrizione("Il bagno è rotto");
        List<Luogo> luoghi = luogoRepository.findAll();
        r.setIdLuogo(6);
        r.setNome("prova2");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/residente/richiediManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(37)
    @Test
    @WithUserDetails("gor@03.com")
    public void testModificaManutenzioneNonTrovata() throws Exception {
        ModificaManutenzioneRequest r = new ModificaManutenzioneRequest();
        r.setIdManutenzione(5000);
        r.setDescrizione("Descrizione modificata");
        r.setNome("Nome modificato");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/residente/modificaManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Order(37)
    @Test
    @WithUserDetails("ilmitico79@gmail.com")
    public void testModificaManutenzioneNonAutorizzato() throws Exception {
        ModificaManutenzioneRequest r = new ModificaManutenzioneRequest();
        r.setIdManutenzione(5);
        r.setDescrizione("Descrizione modificata");
        r.setNome("Nome modificato");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/residente/modificaManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Order(37)
    @Test
    @WithUserDetails("gor@03.com")
    public void testModificaManutenzione() throws Exception {
        ModificaManutenzioneRequest r = new ModificaManutenzioneRequest();
        r.setIdManutenzione(5);
        r.setDescrizione("Descrizione modificata");
        r.setNome("Nome modificato");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/residente/modificaManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }


    @Order(38)
    @Test
    @WithUserDetails("lab@03.com")
    public void testFiltraManutenzioneGiusto4() throws Exception {
        FiltraManutenzioneRequest r = new FiltraManutenzioneRequest();
        r.setIdManutenzione(5);
        r.setPriorita(2);
        r.setEmailManutentore("nap@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/supervisore/filtraManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }


    @Order(39)
    @Test
    @WithUserDetails("gor@03.com")
    public void testModificaManutenzioneStatoErrato() throws Exception {
        ModificaManutenzioneRequest r = new ModificaManutenzioneRequest();
        r.setIdManutenzione(5);
        r.setDescrizione("Descrizione modificata");
        r.setNome("Nome modificato");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/residente/modificaManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    //verifica la manutenzione di un luogo comune
    @Order(39)
    @Test
    @WithUserDetails("nap@03.com")
    public void testAccettaManutenzioneGiusto3() throws Exception {
        AccettaManutenzioneRequest r = new AccettaManutenzioneRequest();
        r.setIdManutenzione(5);
        r.setDataPrevista(LocalDate.of(2027, 12, 14));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/manutentore/accettaManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(40)
    @Test
    @WithUserDetails("nar@03.com")
    public void testFeedbackManutenzioneNonAutorizzato() throws Exception {
        FeedbackManutenzioneRequest r = new FeedbackManutenzioneRequest();
        r.setIdManutenzione(3);
        r.setRisolto(true);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/residente/feedbackManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Order(40)
    @Test
    @WithUserDetails("gor@03.com")
    public void testFeedbackManutenzioneIdMancante() throws Exception {
        FeedbackManutenzioneRequest r = new FeedbackManutenzioneRequest();
        r.setRisolto(true);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/residente/feedbackManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(40)
    @Test
    @WithUserDetails("gor@03.com")
    public void testFeedbackManutenzioneIdErrato() throws Exception {
        FeedbackManutenzioneRequest r = new FeedbackManutenzioneRequest();
        r.setIdManutenzione(100);
        r.setRisolto(true);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/residente/feedbackManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Order(40)
    @Test
    @WithUserDetails("gor@03.com")
    public void testFeedbackManutenzioneIdErrato2() throws Exception {
        FeedbackManutenzioneRequest r = new FeedbackManutenzioneRequest();
        r.setIdManutenzione(1);
        r.setRisolto(true);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/residente/feedbackManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(40)
    @Test
    @WithUserDetails("gor@03.com")
    public void testFeedbackManutenzioneProblemaMancante() throws Exception {
        FeedbackManutenzioneRequest r = new FeedbackManutenzioneRequest();
        r.setIdManutenzione(3);
        r.setRisolto(false);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/residente/feedbackManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(40)
    @Test
    @WithUserDetails("ilmitico79@gmail.com")
    public void testFeedbackManutenzioneResidenteErrato() throws Exception {
        FeedbackManutenzioneRequest r = new FeedbackManutenzioneRequest();
        r.setIdManutenzione(3);
        r.setRisolto(true);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/residente/feedbackManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Order(41)
    @Test
    @WithUserDetails("gor@03.com")
    public void testFeedbackManutenzioneGiusto() throws Exception {
        FeedbackManutenzioneRequest r = new FeedbackManutenzioneRequest();
        r.setIdManutenzione(3);
        r.setRisolto(true);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/residente/feedbackManutenzione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(41)
    @Test
    @WithUserDetails("nar@03.com")
    public void testRegistrazioneGiustoResidente2() throws Exception {
        RegistrazioneRequest registrazioneRequest = new RegistrazioneRequest();
        registrazioneRequest.setCognome("Rossi");
        registrazioneRequest.setNome("Mario");
        registrazioneRequest.setEmail("ros2@03.com");
        registrazioneRequest.setRuolo(Ruolo.Residente);
        registrazioneRequest.setDataDiNascita(LocalDate.of(2003, 1, 4));
        registrazioneRequest.setBloccato(false);
        String json = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(registrazioneRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/registrazione")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(42)
    @Test
    @WithUserDetails("nar@03.com")
    public void testAggiornaRuoloGiàAvuto1() throws Exception {
        AggiornaRuoloRequest r = new AggiornaRuoloRequest();
        r.setEmail("lab@03.com");
        r.setRuoloNuovo(Ruolo.Supervisore);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/aggiornaRuolo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(42)
    @Test
    @WithUserDetails("nar@03.com")
    public void testAggiornaRuoloGiàAvuto2() throws Exception {
        AggiornaRuoloRequest r = new AggiornaRuoloRequest();
        r.setEmail("gor@03.com");
        r.setRuoloNuovo(Ruolo.Residente);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/aggiornaRuolo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(42)
    @Test
    @WithUserDetails("nar@03.com")
    public void testAggiornaRuoloGiàAvuto3() throws Exception {
        AggiornaRuoloRequest r = new AggiornaRuoloRequest();
        r.setEmail("nap@03.com");
        r.setRuoloNuovo(Ruolo.Manutentore);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/aggiornaRuolo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(42)
    @Test
    @WithUserDetails("nar@03.com")
    public void testAggiornaRuoloGiàAvuto4() throws Exception {
        AggiornaRuoloRequest r = new AggiornaRuoloRequest();
        r.setEmail("nar@03.com");
        r.setRuoloNuovo(Ruolo.Admin);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/aggiornaRuolo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(42)
    @Test
    @WithUserDetails("nar@03.com")
    public void testAggiornaRuoloGiusto() throws Exception {
        AggiornaRuoloRequest r = new AggiornaRuoloRequest();
        r.setEmail("rosAdmin@03.com");
        r.setRuoloNuovo(Ruolo.Supervisore);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/aggiornaRuolo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(43)
    @Test
    @WithUserDetails("nar@03.com")
    public void testAggiornaRuoloNoAdmin() throws Exception {
        AggiornaRuoloRequest r = new AggiornaRuoloRequest();
        r.setEmail("nar@03.com");
        r.setRuoloNuovo(Ruolo.Supervisore);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/aggiornaRuolo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(43)
    @Test
    @WithUserDetails("nar@03.com")
    public void testAggiornaRuoloEmailMancante() throws Exception {
        AggiornaRuoloRequest r = new AggiornaRuoloRequest();
        r.setRuoloNuovo(Ruolo.Supervisore);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/aggiornaRuolo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(43)
    @Test
    @WithUserDetails("nar@03.com")
    public void testAggiornaRuoloRuoloMancante() throws Exception {
        AggiornaRuoloRequest r = new AggiornaRuoloRequest();
        r.setEmail("nar@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/aggiornaRuolo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(43)
    @Test
    @WithUserDetails("nar@03.com")
    public void testAggiornaRuoloEmailErrata() throws Exception {
        AggiornaRuoloRequest r = new AggiornaRuoloRequest();
        r.setEmail("provaaa@03.com");
        r.setRuoloNuovo(Ruolo.Supervisore);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/aggiornaRuolo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Order(44)
    @Test
    @WithUserDetails("nar@03.com")
    public void testAggiornaRuoloGiusto2() throws Exception {
        AggiornaRuoloRequest r = new AggiornaRuoloRequest();
        r.setEmail("rosAdmin@03.com");
        r.setRuoloNuovo(Ruolo.Manutentore);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/aggiornaRuolo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(45)
    @Test
    @WithUserDetails("nar@03.com")
    public void testAggiornaRuoloGiusto3() throws Exception {
        AggiornaRuoloRequest r = new AggiornaRuoloRequest();
        r.setEmail("rosAdmin@03.com");
        r.setRuoloNuovo(Ruolo.Residente);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/aggiornaRuolo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(46)
    @Test
    @WithUserDetails("nar@03.com")
    public void testAggiornaRuoloGiusto4() throws Exception {
        AggiornaRuoloRequest r = new AggiornaRuoloRequest();
        r.setEmail("rosAdmin@03.com");
        r.setRuoloNuovo(Ruolo.Admin);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/aggiornaRuolo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(45)
    @Test
    @WithUserDetails("nar@03.com")
    public void testCambioPasswordPasswordMancante1() throws Exception {
        CambioPasswordRequest r = new CambioPasswordRequest();
        r.setNuovaPassword("prova2");
        r.setConfermaPassword("prova2");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/cambioPassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(45)
    @Test
    @WithUserDetails("nar@03.com")
    public void testCambioPasswordmancante2() throws Exception {
        CambioPasswordRequest r = new CambioPasswordRequest();
        r.setUltimaPassword("prova");
        r.setConfermaPassword("prova2");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/cambioPassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(45)
    @Test
    @WithUserDetails("nar@03.com")
    public void testCambioPasswordMancante3() throws Exception {
        CambioPasswordRequest r = new CambioPasswordRequest();
        r.setUltimaPassword("prova");
        r.setNuovaPassword("prova2");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/cambioPassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(45)
    @Test
    @WithUserDetails("nar@03.com")
    public void testCambioPasswordNonCoincidenti() throws Exception {
        CambioPasswordRequest r = new CambioPasswordRequest();
        r.setUltimaPassword("prova");
        r.setNuovaPassword("prova123");
        r.setConfermaPassword("prova223");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/cambioPassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(45)
    @Test
    @WithUserDetails("nar@03.com")
    public void testCambioPasswordErrata() throws Exception {
        CambioPasswordRequest r = new CambioPasswordRequest();
        r.setUltimaPassword("prova1");
        r.setNuovaPassword("prova233");
        r.setConfermaPassword("prova233");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/cambioPassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(45)
    @Test
    @WithUserDetails("nar@03.com")
    public void testCambioPasswordGiusto() throws Exception {
        CambioPasswordRequest r = new CambioPasswordRequest();
        r.setUltimaPassword("prova");
        r.setNuovaPassword("12345678");
        r.setConfermaPassword("12345678");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/cambioPassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }


    @Order(46)
    @Test
    @WithUserDetails("gor@03.com")
    public void eliminaResidenteDaStanzaNonAutorizzato() throws Exception {
        EliminaResidenteDaStanzaRequest r = new EliminaResidenteDaStanzaRequest();
        r.setEmail("gor@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/eliminaResidenteDaStanza")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Order(46)
    @Test
    @WithUserDetails("nar@03.com")
    public void eliminaResidenteDaStanzaEmailMancante() throws Exception {
        EliminaResidenteDaStanzaRequest r = new EliminaResidenteDaStanzaRequest();
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/eliminaResidenteDaStanza")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(46)
    @Test
    @WithUserDetails("nar@03.com")
    public void eliminaResidenteDaStanzaEmailErrata() throws Exception {
        EliminaResidenteDaStanzaRequest r = new EliminaResidenteDaStanzaRequest();
        r.setEmail("goraaaaa@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/eliminaResidenteDaStanza")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Order(46)
    @Test
    @WithUserDetails("nar@03.com")
    public void eliminaResidenteDaStanzaGiusto() throws Exception {
        EliminaResidenteDaStanzaRequest r = new EliminaResidenteDaStanzaRequest();
        r.setEmail("gor@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/eliminaResidenteDaStanza")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(47)
    @Test
    @WithUserDetails("nar@03.com")
    public void eliminaResidenteDaStanzaRipetuto() throws Exception {
        EliminaResidenteDaStanzaRequest r = new EliminaResidenteDaStanzaRequest();
        r.setEmail("gor@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/eliminaResidenteDaStanza")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Order(48)
    @Test
    @WithUserDetails("gor@03.com")
    public void testEliminaResidenteNonAutorizzato() throws Exception {
        EliminaUtenteRequest r = new EliminaUtenteRequest();
        r.setEmail("gor@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/eliminaUtente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Order(48)
    @Test
    @WithUserDetails("nar@03.com")
    public void testEliminaResidenteEmailMancante() throws Exception {
        EliminaUtenteRequest r = new EliminaUtenteRequest();
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/eliminaUtente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(49)
    @Test
    @WithUserDetails("nar@03.com")
    public void testEliminaSupervisore() throws Exception {
        EliminaUtenteRequest r = new EliminaUtenteRequest();
        r.setEmail("lab@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/eliminaUtente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }


    @Order(49)
    @Test
    @WithUserDetails("nar@03.com")
    public void testEliminaManutentore() throws Exception {
        EliminaUtenteRequest r = new EliminaUtenteRequest();
        r.setEmail("nap@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/eliminaUtente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(49)
    @Test
    @WithUserDetails("nar@03.com")
    public void testEliminaResidente() throws Exception {
        EliminaUtenteRequest r = new EliminaUtenteRequest();
        r.setEmail("gor@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/eliminaUtente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(50)
    @Test
    @WithUserDetails("nar@03.com")
    public void testEliminaAdmin() throws Exception {
        EliminaUtenteRequest r = new EliminaUtenteRequest();
        r.setEmail("rosAdmin@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/eliminaUtente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(51)
    @Test
    @WithUserDetails("nar@03.com")
    public void testEliminaAdminUltimo() throws Exception {
        EliminaUtenteRequest r = new EliminaUtenteRequest();
        r.setEmail("nar@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/eliminaUtente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(1)
    @Test
    @WithUserDetails("gor@03.com")
    public void testInviaMessaggio() throws Exception {
        InviaMessaggioDTO r = new InviaMessaggioDTO();
        r.setTesto("Ciao come stai?");
        r.setUsernameDestinatario("lab@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/messaggio/invia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(2)
    @Test
    @WithUserDetails("lab@03.com")
    public void testEliminaMessaggioNonAutorizzato() throws Exception {
        EliminaMessaggioDTO r = new EliminaMessaggioDTO();
        r.setId(1);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/chat/eliminaMessaggio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Order(2)
    @Test
    @WithUserDetails("lab@03.com")
    public void testModificaMessaggioNonAutorizzato() throws Exception {
        ModificaMessaggioDTO r = new ModificaMessaggioDTO();
        r.setId(1);
        r.setTesto("prova modifica");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/chat/modificaMessaggio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Order(3)
    @Test
    @WithUserDetails("gor@03.com")
    public void testModificaMessaggioAutorizzato() throws Exception {
        ModificaMessaggioDTO r = new ModificaMessaggioDTO();
        r.setId(1);
        r.setTesto("prova modifica");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/chat/modificaMessaggio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(3)
    @Test
    @WithUserDetails("gor@03.com")
    public void testModificaMessaggioNonTrovato() throws Exception {
        ModificaMessaggioDTO r = new ModificaMessaggioDTO();
        r.setId(1000);
        r.setTesto("prova modifica");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.patch("/chat/modificaMessaggio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Order(3)
    @Test
    @WithUserDetails("gor@03.com")
    public void testEliminaMessaggioNonTrovato() throws Exception {
        EliminaMessaggioDTO r = new EliminaMessaggioDTO();
        r.setId(1000);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/chat/eliminaMessaggio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Order(3)
    @Test
    @WithUserDetails("gor@03.com")
    public void testEliminaMessaggioAutorizzato() throws Exception {
        EliminaMessaggioDTO r = new EliminaMessaggioDTO();
        r.setId(1);
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/chat/eliminaMessaggio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(1)
    @Test
    @WithUserDetails("gor@03.com")
    public void testInviaMessaggioASeStesso() throws Exception {
        InviaMessaggioDTO r = new InviaMessaggioDTO();
        r.setTesto("Ciao come stai?");
        r.setUsernameDestinatario("gor@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/messaggio/invia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Order(1)
    @Test
    @WithUserDetails("gor@03.com")
    public void testInviaMessaggioDestinatarioNonTrovato() throws Exception {
        InviaMessaggioDTO r = new InviaMessaggioDTO();
        r.setTesto("Ciao come stai?");
        r.setUsernameDestinatario("labbbbb@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/messaggio/invia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Order(1)
    @Test
    @WithUserDetails("gor@03.com")
    public void testInviaMessaggioResidenteNonAutorizzato() throws Exception {
        InviaMessaggioDTO r = new InviaMessaggioDTO();
        r.setTesto("Ciao come stai?");
        r.setUsernameDestinatario("nap@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/messaggio/invia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Order(1)
    @Test
    @WithUserDetails("lab@03.com")
    public void testInviaMessaggioSupervisoreNonAutorizzato() throws Exception {
        InviaMessaggioDTO r = new InviaMessaggioDTO();
        r.setTesto("Ciao come stai?");
        r.setUsernameDestinatario("nar@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/messaggio/invia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Order(1)
    @Test
    @WithUserDetails("nap@03.com")
    public void testInviaMessaggioManutentoreNonAutorizzato() throws Exception {
        InviaMessaggioDTO r = new InviaMessaggioDTO();
        r.setTesto("Ciao come stai?");
        r.setUsernameDestinatario("gor@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/messaggio/invia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Order(1)
    @Test
    @WithUserDetails("nar@03.com")
    public void testInviaMessaggioAdminNonAutorizzato() throws Exception {
        InviaMessaggioDTO r = new InviaMessaggioDTO();
        r.setTesto("Ciao come stai?");
        r.setUsernameDestinatario("nap@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/messaggio/invia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Order(2)
    @Test
    @WithUserDetails("gor@03.com")
    public void testGetChat() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/chat/lab@03.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(2)
    @Test
    @WithUserDetails("gor@03.com")
    public void testGetChatErrato() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/chat/labbbbb@03.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Order(2)
    @Test
    @WithUserDetails("gor@03.com")
    public void testGetAllChat() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/chat/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(2)
    @Test
    @WithUserDetails("nar@03.com")
    public void testDeleteUtenteNonTrovato() throws Exception {
        EliminaUtenteRequest r = new EliminaUtenteRequest();
        r.setEmail("ciao@g.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/eliminaUtente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Order(52)
    @Test
    @WithUserDetails("nar@03.com")
    public void testGetLuoghiComuni() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/getLuoghiComuni")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(52)
    @Test
    @WithUserDetails("nar@03.com")
    public void testGetStanze() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/getStanze")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(52)
    @Test
    @WithUserDetails("nar@03.com")
    public void testGetBagni() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/getBagni")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void testAccessoConTokenValido() throws Exception {
        // Recupera l'utente dal database
        Residente utente = residenteRepository.findByEmailAndBloccatoIsFalse("ilmitico79@gmail.com");
        // Genera un token JWT per l'utente
        String token = tokenUtil.generaToken(utente);
        RichiediManutenzioneRequest r = new RichiediManutenzioneRequest();
        r.setDescrizione("rotto");
        r.setIdLuogo(1);
        r.setNome("prova4");
        String json = new ObjectMapper().writeValueAsString(r);
        // Verifica che una richiesta con un token JWT valido venga accettata
        mockMvc.perform(MockMvcRequestBuilders.post("/residente/richiediManutenzione")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(1)
    @Test
    public void testPasswordDimenticata() throws Exception {
        PasswordDimenticataRequest r = new PasswordDimenticataRequest();
        r.setEmail("nap@03.com");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/passwordDimenticata")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(1)
    @Test
    @WithUserDetails("nar@03.com")
    public void testGetBagni2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/getBagni")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Test
    public void testAccessoNonAutorizzato() throws Exception {
        // Verifica che una richiesta a un endpoint protetto senza token JWT venga rifiutata
        mockMvc.perform(MockMvcRequestBuilders.get("/residente/richiediManutenzione"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }


    @Test
    public void testAccessoConRuoloErrato() throws Exception {
        // Genera un token JWT per un utente con ruolo non autorizzato
        Utente u = utenteRepository.findByEmail("nar@03.com"); // Assicurati che "nap@03.com" abbia un ruolo diverso da Residente
        String token = tokenUtil.generaToken(u);

        // Verifica che una richiesta con un token JWT valido ma con un ruolo non autorizzato venga rifiutata
        mockMvc.perform(MockMvcRequestBuilders.get("/residente/richiediManutenzione")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void testFiltroJWT() throws Exception {
        // Crea un utente di test
        Utente utente = utenteRepository.findByEmail("nar@03.com"); // Assicurati che "nar@03.com" esista

        // Genera un token JWT per l'utente
        String token = tokenUtil.generaToken(utente);

        // Verifica che il filtro JWT estragga correttamente l'utente dal token
        Utente u2 = tokenUtil.getUtenteFromToken(token);
        assert (utente.getEmail().equals(u2.getEmail()));

        // Verifica che una richiesta con un token JWT valido venga accettata
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/getLuoghiComuni")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("nar@03.com")
    public void testGetNuclei() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/getNuclei")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(53)
    @Test
    @WithUserDetails("gor@03.com")
    public void disiscriviEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/residente/disinscriviEmail")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(54)
    @Test
    @WithUserDetails("gor@03.com")
    public void iscriviEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/residente/iscriviEmail")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(55)
    @Test
    @WithUserDetails("nar@03.com")
    public void testGetManutenzioni() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/all/getManutenzioni")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(55)
    @Test
    @WithUserDetails("gor@03.com")
    public void testGetManutenzioniByResidente() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/all/getManutenzioni")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(55)
    @Test
    @WithUserDetails("nap@03.com")
    public void testGetManutenzioniByManutentore() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/all/getManutenzioni")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Order(56)
    @Test
    @WithUserDetails("nar@03.com")
    public void testGetManutenzione() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/all/getManutenzione/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Order(56)
    @Test
    @WithUserDetails("nar@03.com")
    public void testGetManutenzioneNonTrovata() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/all/getManutenzione/1000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }


    @Order(56)
    @Test
    @WithUserDetails("gor@03.com")
    public void testGetManutenzioneResidenteNonAutorizzato() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/all/getManutenzione/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    public void testLoginUtenteNonTrovato() throws Exception {
        LoginRequest r = new LoginRequest();
        r.setEmail("ciaoooo@gmail.com");
        r.setPassword("ciaoooo");
        String json = new ObjectMapper().writeValueAsString(r);
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

}

