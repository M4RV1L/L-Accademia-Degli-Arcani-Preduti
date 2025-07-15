/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

import com.mycompany.progettoaccademiaarcani.model.GameState;
import com.mycompany.progettoaccademiaarcani.model.Item;
import com.mycompany.progettoaccademiaarcani.model.NPC;
import com.mycompany.progettoaccademiaarcani.model.Player;
import com.mycompany.progettoaccademiaarcani.model.Room;
import com.mycompany.util.GameSaver;
import com.mycompany.util.GameTimer;
import com.mycompany.view.GameGUI;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author marcovillano
 */
public class GameController {

    private GameState gameState;
    private GameGUI gameGUI;
    private GameTimer gameTimer;

    public GameController(GameGUI gameGUI) {
        this.gameGUI = gameGUI;
        initializeGame();
    }

    // Metodo per l'inizializzazione dello stato del gioco
    private void initializeGame() {
        // Creazione delle stanze
        Map<String, Room> rooms = new HashMap<>();

        // Dormitorio degli Spiriti - Stanza Iniziale
        Room dormitorioSpiriti = new Room("Dormitorio degli Spiriti",
                "Ti risvegli in una stanza buia, avvolta da un'aria gelida e i sussurri echeggiano tra gli antichi letti a baldacchino ricoperti di polvere. La sensazione di essere osservato è palpabile.");
        rooms.put(dormitorioSpiriti.getName(), dormitorioSpiriti);

        // Atrio Incantato
        Room atrio = new Room("Atrio Incantato",
                "Un vasto atrio con un'imponente scalinata che si innalza verso un'oscurità lontana. L'aria vibra di una magia antica, e le vetrate rotte proiettano ombre danzanti sul pavimento di marmo crepato.");
        rooms.put(atrio.getName(), atrio);

        // Corridoio degli Specchi
        Room corridoioSpecchi = new Room("Corridoio degli Specchi",
                "Un corridoio senza fine, le pareti interamente ricoperte di specchi antichi e incrinati. Ogni riflesso sembra muoversi di vita propria, distorcendo la tua immagine e sussurrando illusioni.");
        rooms.put(corridoioSpecchi.getName(), corridoioSpecchi);

        // Aula di Alchimia
        Room aulaAlchimia = new Room("Aula di Alchimia",
                "Tavoli di legno nero sono disordinatamente pieni di provette, alambicchi e mortai. L'odore acre di zolfo e erbe essiccate permea l'aria, suggerendo esperimenti interrotti bruscamente. C'è un **Mortaio Incantato** sul tavolo al centro, e una fessura nel camino.");
        aulaAlchimia.setSealedRoom(true); // Questa stanza contiene un sigillo
        rooms.put(aulaAlchimia.getName(), aulaAlchimia);

        // Biblioteca
        Room biblioteca = new Room("Biblioteca",
                "Scaffali infiniti di libri polverosi si ergono fino al soffitto, formando labirinti di conoscenza perduta. Il fruscio delle pagine sfogliate da mani invisibili è l'unico suono che rompe il silenzio, e l'odore di pergamena antica è intenso. Un **Libro Antico** e massiccio giace aperto su un leggio.");
        biblioteca.setSealedRoom(true); // Questa stanza contiene un sigillo
        rooms.put(biblioteca.getName(), biblioteca);

        // Sala delle Illusioni
        Room salaIllusioni = new Room("Sala delle Illusioni",
                "La stanza è un caleidoscopio impazzito: le pareti cambiano continuamente forma e colore, mettendo alla prova la tua percezione. Figure evanescenti appaiono e scompaiono, sfidandoti a distinguere la realtà dall'inganno. Vedi una serie di **simboli mutevoli** sulla parete nord.");
        salaIllusioni.setSealedRoom(true); // Questa stanza contiene un sigillo
        rooms.put(salaIllusioni.getName(), salaIllusioni);

        // Serre Incantate
        Room serreIncantate = new Room("Serre Incantate",
                "Piante esotiche e luminescenti crescono rigogliose sotto una volta di vetro crepato. Alcuni fiori pulsano di luce propria, e l'aria è intrisa di un profumo dolce e sconosciuto.");
        serreIncantate.setSealedRoom(true); // Questa stanza contiene un sigillo
        rooms.put(serreIncantate.getName(), serreIncantate);

        // Sala delle Prove - Stanza del Boss
        Room salaProve = new Room("Sala delle Prove",
                "Una vasta sala circolare, al centro della quale si erge un altare di cenere. L'aria qui è pesante, carica di un'energia oscura e pressante. Sembra il culmine di ogni potere dell'Accademia. Il Custode ti attende.");
        rooms.put(salaProve.getName(), salaProve);

        // Configurazione delle uscite (secondo la mappa fornita)
        dormitorioSpiriti.addExit("nord", atrio);

        atrio.addExit("sud", dormitorioSpiriti);
        atrio.addExit("ovest", salaIllusioni);
        atrio.addExit("est", serreIncantate);
        atrio.addExit("nord", corridoioSpecchi);

        salaIllusioni.addExit("est", atrio);
        // Passaggio segreto per Eloin sarà aggiunto dinamicamente

        serreIncantate.addExit("ovest", atrio);

        corridoioSpecchi.addExit("sud", atrio);
        corridoioSpecchi.addExit("ovest", aulaAlchimia);
        corridoioSpecchi.addExit("est", biblioteca);
        corridoioSpecchi.addExit("nord", salaProve); // Accesso alla Sala delle Prove

        aulaAlchimia.addExit("est", corridoioSpecchi);

        biblioteca.addExit("ovest", corridoioSpecchi);

        salaProve.addExit("sud", corridoioSpecchi);

        // Creazione e posizionamento oggetti
        Item cuoreMandragola = new Item("Cuore di Mandragola", "Un cuore pulsante di colore verde smeraldo. Sembra emettere una debole melodia e la senti vibrare nelle tue mani. Permette di comunicare con le piante.", true);
        Item diarioEloin = new Item("Diario di Eloin", "Un vecchio diario dalla copertina di cuoio consunta. Le pagine sono ingiallite e molte scritte sono sbiadite. Potrebbe contenere ricordi importanti.", true);
        Item pozioneEcoTemporale = new Item("Pozione dell'eco temporale", "Una fiala di vetro antico, il liquido all'interno brilla di una luce argentea e tremolante. Permette di vedere eventi passati.", true, true); // Consumabile
        Item chiaveRuna = new Item("Chiave a forma di runa", "Una chiave di metallo scuro, intagliata con simboli runici che sembrano brillare fiocamente. Potrebbe aprire qualcosa di molto antico.", true);
        Item occhioArcano = new Item("Occhio dell'Arcano", "Un occhio di cristallo lucido e iridescente, incastonato in un anello d'argento. Sembra scrutare oltre il velo della realtà e si dice riveli trappole e passaggi nascosti.", true);
        Item libroAntico = new Item("Libro Antico", "Un tomo gigantesco, la sua copertina è fatta di pelle scura e il titolo è illeggibile. Sembra intriso di un sapere dimenticato.", false); // Non raccoglibile, ma esaminabile
        Item mortaioIncantato = new Item("Mortaio Incantato", "Un mortaio di pietra con incisioni brillanti. Forse può essere usato per mescolare ingredienti alchemici.", false); // Non raccoglibile
        Item polvereCristalloLunare = new Item("Polvere di Cristallo Lunare", "Una polvere finissima che brilla di una luce argentea. Si dice che amplifichi le proprietà temporali.", true);
        Item simboliMutevoli = new Item("Simboli Mutevoli", "Strani simboli sulla parete che cambiano forma e colore. Sembrano parte di un enigma.", false); // Oggetto per enigma ambientale

        // Posizionamento oggetti nelle stanze
        serreIncantate.addItem(cuoreMandragola);
        dormitorioSpiriti.addItem(diarioEloin);
        aulaAlchimia.addItem(pozioneEcoTemporale);
        aulaAlchimia.addItem(mortaioIncantato); // Oggetto ambientale per puzzle alchemico
        aulaAlchimia.addItem(polvereCristalloLunare);
        biblioteca.addItem(libroAntico); // Oggetto ambientale per enigma del Prof. Malverus
        salaIllusioni.addItem(simboliMutevoli); // Oggetto ambientale per enigma della Sala Illusioni

        // Creazione NPC e posizionamento
        NPC profMalverus = new NPC("Prof. Malverus", "Uno spettro sapiente con un'aura di pergamena svolazzante attorno a sé. I suoi occhi brillano di una conoscenza millenaria. Ti guarda con un'espressione enigmatica.");
        profMalverus.setPuzzleActive(true); // Attiva l'enigma per Malverus
        biblioteca.setNpc(profMalverus);

        NPC junaSilente = new NPC("Juna la Silente", "Una giovane studentessa spettrale, i suoi vestiti sono intrecciati con rampicanti luminescenti. Non parla, ma la sua presenza comunica una profonda connessione con la vita delle piante.");
        junaSilente.setRequiredItem("Cuore di Mandragola");
        serreIncantate.setNpc(junaSilente);

        NPC custodeEloin = new NPC("Custode Eloin", "Ex sorvegliante dell'accademia, la sua figura spettrale è confusa e frammentata. Mette le mani alla testa, come se cercasse di afferrare ricordi sfuggenti.");
        custodeEloin.setRequiredItem("Diario di Eloin");
        dormitorioSpiriti.setNpc(custodeEloin);

        NPC specchioVerita = new NPC("Specchio delle Verità", "Un imponente specchio antico, incorniciato in legno scuro e intarsiato con rune. La sua superficie riflette la tua immagine, ma gli occhi nello specchio sembrano i tuoi, eppure non lo sono.");
        specchioVerita.setPuzzleActive(true); // L'interazione con lo specchio è come un puzzle di scelta
        corridoioSpecchi.setNpc(specchioVerita);

        // Inizializza il giocatore nella stanza iniziale
        Player player = new Player(gameGUI.getPlayerName(), dormitorioSpiriti); // Ottiene il nome dal GUI
        gameState = new GameState(player, rooms, System.currentTimeMillis());

        // Inizializza il timer
        gameTimer = new GameTimer(this);
        gameTimer.start(); // Avvia il timer

        gameGUI.displayMessage(dormitorioSpiriti.getLongDescription());
    }

    public void processCommand(String command) {
        String[] parts = command.toLowerCase().trim().split(" ", 2);
        String action = parts[0];
        String argument = parts.length > 1 ? parts[1] : "";

        String response = "";
        Player player = gameState.getPlayer();
        Room currentRoom = player.getCurrentRoom();

        // Sinonimi per i comandi
        if (action.equals("guarda")) {
            action = "esamina";
        }
        if (action.equals("parla")) {
            action = "interagisci";
        }

        switch (action) {
            case "vai":
                response = movePlayer(argument);
                break;
            case "esamina":
                response = examineObject(argument);
                break;
            case "raccogli":
                response = pickUpItem(argument);
                break;
            case "interagisci":
                response = interactWithEntity(argument); // Modificato per gestire NPC e oggetti interattivi
                break;
            case "usa":
                response = useItem(argument);
                break;
            case "inventario":
                response = displayInventory();
                break;
            case "salva":
                saveGame();
                response = "Partita salvata.";
                break;
            case "carica":
                loadGame();
                response = "Partita caricata.";
                break;
            case "aiuto":
                response = "Comandi disponibili: vai [direzione], esamina [oggetto/NPC], raccogli [oggetto], interagisci [NPC/Oggetto], usa [oggetto], inventario, salva, carica, aiuto.";
                break;

            case "affronta": // Questo è il comando specifico che ricevi dal JOptionPane
                response = fightAshKeeper(argument); // Passa l'intera combinazione come argomento
                break;
            default:
                response = "Comando sconosciuto. Digita 'aiuto' per i comandi disponibili.";
        }
        gameGUI.displayMessage(response);
        updateGUIState();
    }

    private String movePlayer(String direction) {
        Player player = gameState.getPlayer();
        Room currentRoom = player.getCurrentRoom();
        Room nextRoom = currentRoom.getExits().get(direction);

        if (nextRoom != null) {
            // Logica per l'accesso alla Sala delle Prove
            if (nextRoom.getName().equals("Sala delle Prove")) {
                if (player.getSealsCollected() < 4) {
                    return "Un campo di forza ti blocca. Devi raccogliere tutti e 4 i Sigilli della Conoscenza prima di affrontare il Custode!";
                } else {
                    player.setCurrentRoom(nextRoom);
                    gameGUI.displayMessage(nextRoom.getLongDescription());
                    // Inizia il combattimento con il Custode
                    gameGUI.requestSealCombination(); // Chiede la combinazione
                    return "Il Custode di Cenere si materializza davanti a te, le sue braci ardono nell'oscurità.";
                }
            }
            player.setCurrentRoom(nextRoom);
            return nextRoom.getLongDescription();
        } else {
            return "Non puoi andare in quella direzione.";
        }
    }

    private String examineObject(String objectName) {
        Player player = gameState.getPlayer();
        Room currentRoom = player.getCurrentRoom();

        // Cerca nell'inventario
        Item inventoryItem = player.getItem(objectName);
        if (inventoryItem != null) {
            return inventoryItem.getDescription();
        }

        // Cerca nella stanza
        for (Item roomItem : currentRoom.getItems()) {
            if (roomItem.getName().equalsIgnoreCase(objectName)) {
                // Gestione specifica per gli enigmi ambientali
                if (roomItem.getName().equalsIgnoreCase("Simboli Mutevoli") && currentRoom.getName().equals("Sala delle Illusioni") && !currentRoom.isPuzzleSolved()) {
                    gameGUI.displayPuzzle(roomItem.getName(), "I simboli sulla parete nord cambiano rapidamente. Solo una sequenza di tre simboli specifici rimane stabile per un istante prima di svanire. Qual è la sequenza corretta?");
                    return "Stai osservando attentamente i simboli mutevoli...";
                } else if (roomItem.getName().equalsIgnoreCase("Libro Antico") && currentRoom.getName().equals("Biblioteca")) {
                    // Puoi dare un indizio o descrivere di più
                    return "Il Libro Antico è aperto su una pagina che parla di 'verità nascoste' e 'parole che infiammano'. Sembra suggerire la risposta all'enigma del Prof. Malverus.";
                } else if (roomItem.getName().equalsIgnoreCase("Mortaio Incantato") && currentRoom.getName().equals("Aula di Alchimia")) {
                    return "Un mortaio di pietra con incisioni brillanti. Sembra pronto per una mescola. Se hai la Polvere di Cristallo Lunare e la Pozione dell'eco temporale, potresti tentare una combinazione.";
                }
                return roomItem.getDescription();
            }
        }
        // Cerca NPC
        if (currentRoom.getNpc() != null && currentRoom.getNpc().getName().equalsIgnoreCase(objectName)) {
            return currentRoom.getNpc().getDescription();
        }
        return "Non c'è niente da esaminare con quel nome qui o nel tuo inventario.";
    }

    private String pickUpItem(String itemName) {
        Player player = gameState.getPlayer();
        Room currentRoom = player.getCurrentRoom();
        Item itemToPick = null;

        for (Item item : currentRoom.getItems()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                itemToPick = item;
                break;
            }
        }

        if (itemToPick != null) {
            if (itemToPick.canBePickedUp()) {
                player.addItem(itemToPick);
                currentRoom.removeItem(itemToPick);
                return "Hai raccolto: " + itemToPick.getName() + ".";
            } else {
                return itemToPick.getName() + " non può essere raccolto.";
            }
        } else {
            return "Non vedi " + itemName + " qui.";
        }
    }

    // Metodo unico per interagire con NPC e oggetti interattivi che richiedono input
    private String interactWithEntity(String entityName) {
        Player player = gameState.getPlayer();
        Room currentRoom = player.getCurrentRoom();

        // Interazione con NPC
        NPC npc = currentRoom.getNpc();
        if (npc != null && npc.getName().equalsIgnoreCase(entityName)) {
            switch (npc.getName()) {
                case "Prof. Malverus":
                    if (npc.isPuzzleActive()) {
                        gameGUI.displayPuzzle(npc.getName(), "Prof. Malverus: \"Solo una mente acuta può decifrare questo enigma: Sono sempre affamato, devo essere sempre nutrito, il dito che tocco si trasforma rapidamente in rosso. Cosa sono?\"");
                        return "Il Prof. Malverus ti guarda con aspettativa, attendendo la tua risposta.";
                    } else if (!npc.hasSealGiven()) {
                        return "Il Prof. Malverus ha già condiviso la sua conoscenza con te."; // Già risolto, sigillo dato
                    } else {
                        return "Il Prof. Malverus annuisce. \"Hai dimostrato saggezza. Il tuo cammino è segnato. Sii forte.\"";
                    }
                case "Juna la Silente":
                    if (player.hasItem(npc.getRequiredItem())) {
                        if (!npc.hasSealGiven()) {
                            player.removeItem(player.getItem(npc.getRequiredItem())); // Rimuovi l'oggetto usato
                            player.addSeal(); // Ottieni Sigillo della Conoscenza (Serre)
                            npc.setSealGiven(true);
                            return "Juna sorride e, con un tocco delicato sulla terra, fa sbocciare un Sigillo della Conoscenza tra le radici. \"Grazie per avermi aiutata a comunicare con le mie amiche piante. Questo è per te.\"";
                        } else {
                            return "Juna ti ringrazia con un cenno, ma non ha più nulla da darti. Sembra in pace.";
                        }
                    } else {
                        return "Juna ti guarda con occhi tristi, come se cercasse qualcosa. Le sue labbra si muovono senza suono, indicando le piante attorno a te.";
                    }
                case "Custode Eloin":
                    if (player.hasItem(npc.getRequiredItem())) {
                        Room salaIllusioni = gameState.getRooms().get("Sala delle Illusioni");
                        Room corridoioSpecchi = gameState.getRooms().get("Corridoio degli Specchi");
                        if (!salaIllusioni.getExits().containsKey("nord-est")) {
                            salaIllusioni.addExit("nord-est", corridoioSpecchi); // Sblocca il passaggio segreto
                            player.removeItem(player.getItem(npc.getRequiredItem())); // Rimuovi il diario
                            return "Gli occhi di Eloin si illuminano di riconoscimento. \"Il mio diario... i ricordi stanno tornando! Ricordo un passaggio segreto nella Sala delle Illusioni che porta al Corridoio degli Specchi, a nord-est... È apparso solo dopo che ho annotato i miei timori sul Custode...\"";
                        } else {
                            return "Eloin ti ringrazia per avergli restituito i ricordi. Ti guarda con un sorriso malinconico.";
                        }
                    } else {
                        return "Eloin sembra perso nei suoi pensieri, mormora frasi sconnesse sulla sua memoria perduta e su un \"diario importante\".";
                    }
                case "Specchio delle Verità":
                    if (!npc.hasSealGiven()) { // Indica se l'interazione con lo specchio è già avvenuta
                        gameGUI.displayPuzzle(npc.getName(), "Specchio delle Verità: \"Chi sei veramente, viaggiatore? Sei un eroe, un codardo, o qualcosa di più? Rispondi con una parola che definisca la tua essenza.\"");
                        return "Lo Specchio delle Verità ti attende, la sua superficie increspata, mostrando immagini fugaci del tuo passato e futuro.";
                    } else {
                        return "Lo Specchio delle Verità ora riflette solo un'immagine statica di te. Ha già svelato le sue profondità.";
                    }
                default:
                    return npc.interact(player, "");
            }
        } // Interazione con oggetti ambientali che richiedono input
        else if (currentRoom.getItems().stream().anyMatch(item -> item.getName().equalsIgnoreCase(entityName) && !item.canBePickedUp())) {
            if (entityName.equalsIgnoreCase("Mortaio Incantato") && currentRoom.getName().equals("Aula di Alchimia") && !currentRoom.isPuzzleSolved()) {
                if (player.hasItem("Polvere di Cristallo Lunare") && player.hasItem("Pozione dell'eco temporale")) {
                    player.removeItem(player.getItem("Polvere di Cristallo Lunare"));
                    player.removeItem(player.getItem("Pozione dell'eco temporale")); // La pozione si consuma
                    player.addSeal(); // Ottieni Sigillo della Conoscenza (Alchimia)
                    currentRoom.setPuzzleSolved(true);
                    return "Hai combinato gli ingredienti nel Mortaio Incantato. Una luce abbagliante esplode e poi si ritira, lasciando al suo posto un Sigillo della Conoscenza che emana un'aura di memoria.";
                } else {
                    return "Non hai gli ingredienti giusti (Polvere di Cristallo Lunare e Pozione dell'eco temporale) per usare il Mortaio Incantato.";
                }
            } else if (entityName.equalsIgnoreCase("Simboli Mutevoli") && currentRoom.getName().equals("Sala delle Illusioni") && !currentRoom.isPuzzleSolved()) {
                gameGUI.displayPuzzle(entityName, "Stai tentando di interagire con i simboli mutevoli. Qual è la sequenza che hai decifrato? (Es: Stella-Luna-Sole)");
                return "Concentrati sulla sequenza corretta.";
            } else if (entityName.equalsIgnoreCase("Libro Antico") && currentRoom.getName().equals("Biblioteca")) {
                return "Hai provato a interagire con il Libro Antico, ma è troppo grande e pesante per essere mosso. Dovresti esaminarlo per capire la sua importanza.";
            }
        }

        return "Non c'è niente qui con cui interagire in quel modo.";
    }

    // Metodo per gestire la risposta agli enigmi/scelte
    public void solvePuzzle(String puzzleId, String answer) {
        Player player = gameState.getPlayer();
        Room currentRoom = player.getCurrentRoom();
        String response = "";

        switch (puzzleId) {
            case "Prof. Malverus":
                NPC malverus = currentRoom.getNpc();
                if (malverus != null && malverus.isPuzzleActive()) {
                    if (answer.equalsIgnoreCase("fuoco") || answer.equalsIgnoreCase("il fuoco")) {
                        response = "Corretto! Il Prof. Malverus annuisce con soddisfazione. \"La tua mente è acuta. Ecco un Sigillo della Conoscenza e l'incantesimo 'Luce Guida', che ti aiuterà a vedere attraverso l'oscurità più profonda.\"";
                        player.addSeal(); // Ottieni Sigillo della Conoscenza (Biblioteca)
                        malverus.setSealGiven(true);
                        malverus.setPuzzleActive(false); // Puzzle risolto
                    } else {
                        response = "Sbagliato. Il Prof. Malverus ti guarda con delusione. \"Non è la risposta che cerco. Pensa più a fondo.\"";
                    }
                }
                break;
            case "Specchio delle Verità":
                NPC specchio = currentRoom.getNpc();
                if (specchio != null && specchio.isPuzzleActive()) {
                    if (answer.equalsIgnoreCase("coraggioso") || answer.equalsIgnoreCase("eroe")) {
                        response = "La tua immagine nello specchio si illumina con forza. \"Hai riconosciuto la tua forza interiore. Ti dono l'Occhio dell'Arcano, che rivelerà trappole e passaggi nascosti. Possa la tua vista essere chiara.\"";
                        player.addItem(new Item("Occhio dell'Arcano", "Un occhio di cristallo lucido e iridescente. Rivela trappole e passaggi nascosti.", true));
                        player.setMoralChoice("eroe");
                        specchio.setSealGiven(true); // Indica che l'interazione è stata completata
                    } else if (answer.equalsIgnoreCase("potere") || answer.equalsIgnoreCase("dominatore") || answer.equalsIgnoreCase("ambizioso")) {
                        response = "Lo specchio si increspa con un'aura oscura. \"La tua ambizione è chiara. Ti dono la Chiave a forma di runa, che aprirà la via a un sapere proibito... ma ogni potere ha un prezzo. Prendi questa.\"";
                        player.addItem(new Item("Chiave a forma di runa", "Una chiave di metallo scuro, intagliata con simboli runici. Apre passaggi sigillati, forse anche quelli proibiti.", true));
                        player.setMoralChoice("potere");
                        specchio.setSealGiven(true);
                    } else if (answer.equalsIgnoreCase("codardo") || answer.equalsIgnoreCase("paura") || answer.equalsIgnoreCase("fuggitivo")) {
                        response = "Lo specchio si incrina leggermente. \"La tua paura ti rende cieco. Le illusioni ti avvolgeranno ancora più strettamente, impedendoti di vedere la via d'uscita. La fuga è sempre un'opzione, ma non senza conseguenze.\"";
                        player.setMoralChoice("codardo");
                        specchio.setSealGiven(true);
                    } else {
                        response = "Lo specchio si increspa, la tua immagine si distorce. \"La tua risposta è... confusa. Forse la tua vera natura ti sfugge ancora. Stai attento, le illusioni sono tante.\"";
                    }
                    specchio.setPuzzleActive(false); // L'interazione con lo specchio è una tantum
                }
                break;
            case "Simboli Mutevoli": // Enigma della Sala delle Illusioni
                if (currentRoom.getName().equals("Sala delle Illusioni") && !currentRoom.isPuzzleSolved()) {
                    if (answer.equalsIgnoreCase("stella-luna-sole")) {
                        response = "Hai inserito la sequenza corretta! Le pareti smettono di tremare e un Sigillo della Conoscenza appare al centro della stanza.";
                        player.addSeal(); // Ottieni Sigillo della Conoscenza (Illusioni)
                        currentRoom.setPuzzleSolved(true); // Indica che il puzzle della stanza è risolto
                        // Puoi rimuovere l'Item "Simboli Mutevoli" dalla stanza o cambiarne la descrizione
                    } else {
                        response = "La sequenza è sbagliata. Le illusioni si intensificano per un istante, confodendoti.";
                    }
                }
                break;
            default:
                response = "Non so come gestire la risposta per questo enigma.";
                break;
        }
        gameGUI.displayMessage(response);
        updateGUIState();
    }

    private String useItem(String itemName) {
        Player player = gameState.getPlayer();
        Item itemToUse = player.getItem(itemName);

        if (itemToUse != null) {
            String useResult = itemToUse.use(); // Chiamata al metodo generico 'use' dell'Item

            switch (itemToUse.getName()) {
                case "Pozione dell'eco temporale":
                    if (player.getCurrentRoom().getName().equals("Aula di Alchimia")) {
                        // La pozione si consuma subito
                        player.removeItem(itemToUse);
                        return "Hai bevuto la Pozione dell'eco temporale. L'Aula di Alchimia si dissolve in una visione del passato: vedi un vecchio professore mescolare 'Polvere di Cristallo Lunare' con qualcosa nel 'Mortaio Incantato', poi nascondere un sigillo dietro un mattone allentato vicino al focolare. Sembra che la 'Pozione' fosse l'ingrediente finale di quella miscela...";
                    } else {
                        return "La Pozione dell'eco temporale non sembra avere effetto qui. Forse è specifica per un luogo legato al passato.";
                    }
                case "Occhio dell'Arcano":
                    // Rivelare trappole o passaggi nascosti
                    if (player.getCurrentRoom().getName().equals("Sala delle Illusioni") && !player.getCurrentRoom().isPuzzleSolved()) {
                        return "Hai usato l'Occhio dell'Arcano. Le illusioni si diradano. La sequenza corretta dei simboli mutevoli sulla parete nord è 'Stella-Luna-Sole'. Ora sai come interagire con essi!";
                    }
                    return "Hai usato l'Occhio dell'Arcano. La stanza vibra leggermente e vedi flebili contorni di trappole e un'ombra indistinta di un passaggio segreto nella parete nord!";
                case "Chiave a forma di runa":

                    return "Hai usato la Chiave a forma di runa, ma non c'è una serratura visibile qui che corrisponda. Sembra aspettare il suo vero scopo.";
                default:
                    // Se l'item è consumabile e non ha un effetto specifico gestito, rimuovilo.
                    if (itemToUse.isConsumable()) {
                        player.removeItem(itemToUse);
                        return itemToUse.getName() + " si è consumato senza effetto.";
                    }
                    return useResult;
            }
        } else {
            return "Non hai " + itemName + " nel tuo inventario.";
        }
    }

    private String displayInventory() {
        Player player = gameState.getPlayer();
        if (player.getInventory().isEmpty()) {
            return "Il tuo inventario è vuoto.";
        } else {
            StringBuilder sb = new StringBuilder("Il tuo inventario contiene: \n");
            for (Item item : player.getInventory()) {
                sb.append("- ").append(item.getName()).append(" (").append(item.getDescription()).append(")\n");
            }
            return sb.toString();
        }
    }

    public void saveGame() {
        GameSaver.saveGame(gameState, "savegame.ser");
    }

    public void loadGame() {
        GameState loadedState = GameSaver.loadGame("savegame.ser");
        if (loadedState != null) {
            this.gameState = loadedState;
            gameGUI.displayMessage("Partita caricata con successo.");
            gameTimer.resumeTimer(gameState.getStartTime()); // Riprende il timer
            updateGUIState();
            gameGUI.displayMessage(gameState.getPlayer().getCurrentRoom().getLongDescription()); // Mostra la descrizione della stanza corrente
        } else {
            gameGUI.displayMessage("Nessun salvataggio trovato.");
        }
    }

    // Metodo per gestire il combattimento con il Custode di Cenere
    public String fightAshKeeper(String combination) {
        Player player = gameState.getPlayer();
        if (player.getSealsCollected() < 4) {
            return "Non hai tutti i Sigilli. Non puoi affrontarlo!";
        }

        // Le combinazioni finali possono essere influenzate dalle scelte del giocatore
        String playerMoralChoice = player.getMoralChoice(); // Ottieni la scelta morale

        String outcome = "";
        String lowerCaseCombination = combination.toLowerCase().trim();

        switch (lowerCaseCombination) {
            case "saggezza vita percezione memoria": // Combinazione per il finale "Equilibrio Ristabilito"
                if (playerMoralChoice.equals("eroe") || playerMoralChoice.equals("indefinito")) { // Più facile per eroi o neutrali
                    gameState.setGameFinished(true);
                    gameState.setFinalOutcome("Equilibrio Ristabilito");
                    outcome = "I Sigilli brillano di luce pura, e l'energia corrotta del Custode si disperde in un'esplosione di polvere lucente. L'Accademia si illumina, i sussurri si trasformano in dolci melodie. Hai salvato l'Accademia e liberato le anime imprigionate!";
                } else {
                    outcome = "La combinazione tenta di ristabilire l'equilibrio, ma le tue scelte passate oscurano la sua luce. Il Custode ride delle tue debolezze!";
                    // Potrebbe infliggere danno o bloccare il tentativo
                }
                break;
            case "potere assoluto corrotto": // Combinazione per il finale "Potere Corrotto"
                if (playerMoralChoice.equals("potere") || playerMoralChoice.equals("indefinito")) { // Più facile per ambiziosi o neutrali
                    gameState.setGameFinished(true);
                    gameState.setFinalOutcome("Potere Corrotto");
                    outcome = "Un'onda di oscurità si riversa dal Custode e ti avvolge completamente. Senti il suo potere fluire in te, corrompendo la tua anima ma rendendoti invincibile. Sei il nuovo guardiano oscuro dell'Accademia, destinato a custodire la sua oscurità per l'eternità.";
                } else {
                    outcome = "La tua natura ti respinge dal potere oscuro. Il Custode ti respinge con violenza!";
                    // Potrebbe infliggere danno o bloccare il tentativo
                }
                break;
            case "fuga disperata": // Combinazione per il finale "Fuga"
                if (playerMoralChoice.equals("codardo") || playerMoralChoice.equals("indefinito")) { // Più facile per i codardi o neutrali
                    gameState.setGameFinished(true);
                    gameState.setFinalOutcome("Fuga");
                    outcome = "Con un atto di disperazione, usi l'energia dei Sigilli per aprire un portale di fuga dall'Accademia. Ti ritrovi nel mondo esterno, ma l'Accademia rimane maledetta, le sue anime imprigionate e il Custode ancora al suo posto. Hai scelto la libertà, ma a quale costo?";
                } else {
                    outcome = "La via della fuga si apre, ma una forza invisibile ti trattiene. Sembra che il tuo destino sia legato a questo luogo!";
                    // Potrebbe infliggere danno o bloccare il tentativo
                }
                break;
            default:
                outcome = "La combinazione di Sigilli non ha effetto, o peggio, un'onda di cenere ti travolge! Il Custode si rafforza. Riprova con saggezza!";
                break;
        }
        return outcome;
    }

    // Metodo chiamato dal timer per aggiornare la GUI
    public void updateTime(long elapsedSeconds) {
        gameGUI.updateTimerDisplay(elapsedSeconds);
    }

    // Metodo per aggiornare lo stato della GUI dopo ogni comando
    private void updateGUIState() {
        gameGUI.updateCurrentRoom(gameState.getPlayer().getCurrentRoom().getName());
        gameGUI.updateInventoryDisplay(gameState.getPlayer().getInventory());
        gameGUI.updateSealsDisplay(gameState.getPlayer().getSealsCollected());

        if (gameState.isGameFinished()) {
            gameGUI.displayMessage("\n*** FINE DEL GIOCO: " + gameState.getFinalOutcome() + " ***\n");
            gameGUI.disableInput(); // Disabilita l'input alla fine del gioco
            gameTimer.stopTimer(); // Ferma il timer
        }
    }

    public GameState getGameState() {
        return gameState;
    }
}
