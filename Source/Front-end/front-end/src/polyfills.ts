/***************************************************************************************************
 * BROWSER POLYFILLS
 */

/**
 * Per supportare alcune librerie che si aspettano di essere eseguite in ambiente Node.js,
 * definiamo alcune variabili globali.
 */

// Definisci `global` per le librerie che lo richiedono
(window as any).global = window;


/***************************************************************************************************
 * ANGULAR POLYFILLS
 */

/**
 * Questi polyfill sono necessari per supportare Angular stesso.
 */

// Per supportare browser meno recenti, inclusi IE11
import 'zone.js'; // Importa Zone.js necessario per Angular

/***************************************************************************************************
 * APPLICATION IMPORTS
 */

/**
 * Qui puoi aggiungere altri polyfill specifici per la tua applicazione.
 */
