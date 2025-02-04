export const environment = {
  baseUrl: "http://localhost:5001",
  Admin: {
    baseUrl: "/admin",
    registrazione: "/registrazione",
    eliminaUtente: "/eliminaUtente",
    aggiungiResidenteInStanza: "/aggiungiResidenteInStanza",
    aggiornaRuolo: "/aggiornaRuolo",
    eliminaResidenteDaStanza: "/eliminaResidenteDaStanza",
  },
  Luogo: {
    baseUrl: "/luogo",
    aggiungiLuogo: "/aggiungiLuogo",
    getLuoghi: "/getLuoghi",
    getLuoghiResidente: "/getLuoghi",
    getBagni: "/getBagni",
    getLuoghiComuni: "/getLuoghiComuni",
    eliminaLuogo: "/eliminaLuogo",
    getStanze: "/getStanze",
    modificaStanza: "/modificaStanza",
    getStanza: "/getStanza"
  },
  Email: {
    baseUrl: "/email",
    inviaEmail: "/inviaEmail"
  },
  Manutenzione: {
    baseUrl: "/manutentore",
    richiediManutenzione: "/richiediManutenzione",
    filtraManutenzione: "/filtraManutenzione",
    accettaManutenzione: "/accettaManutenzione",
    riparaManutenzione: "/riparaManutenzione",
    rifiutaManutenzione: "/rifiutaManutenzione",
    filtroManutenzione: "/filtroManutenzione",
    feedbackManutenzioni: "/feedbackManutenzione",
    modificaManutenzione: "/modificaManutenzione",
    getManutenzioni: "/getManutenzioni",
    getManutenzione: "/getManutenzione",
  },
  Utente: {
    baseUrl: "/utente",
    getUtenti: "/getUtenti",
    getResidenti: "/getResidenti",
    getManutentori: "/getManutentori",
    getAdmin: "/getAdmin",
    getSupervisori: "/getSupervisori",
    login: "/login",
    cambioPassword: "/cambioPassword",
    passwordDimenticata: "/passwordDimenticata",
    iscriviEmail: "/iscriviEmail",
    disinscriviEmail: "/disinscriviEmail",
  },
  Chat: {
    baseUrl: "/chat",
    invia: "/invia",
    getAll: "/getAll",
    eliminaMessaggio: "/eliminaMessaggio",
    modificaMessaggio: "/modificaMessaggio",
  },
  Messaggio: {
    baseUrl: "/messaggio",
  },
  Residente: {
    baseUrl: "/residente",
  },
  All: {
    baseUrl: "/all",
  },
  Supervisore: {
    baseUrl: "/supervisore",
  }

};
