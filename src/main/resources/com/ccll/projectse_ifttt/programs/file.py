import sys

# Chiedi all'utente di inserire il risultato
risposta = 1

# Verifica se la risposta è corretta
try:
    if int(risposta) == 10:
        print("Risposta corretta!")
        sys.exit(0)  # Exit code 0 per successo
    else:
        print("Risposta errata!")
        sys.exit(1)  # Exit code 1 per errore
except ValueError:
    print("Errore: per favore inserisci un numero valido.")
    sys.exit(1)  # Exit code 1 per errore