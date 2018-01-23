'##README'

#Documentation : 

        1) Scénario - Introduction :
    Le projet tourne autour de trois axes majeurs : Le Server (Java, websocket), Le Maître du jeu (JavaFX), et le joueur (JS Angular Ionic).
    Les joueurs sur leur application multiplatforme sont en relation directe avec le Server d'où ils tirent les énigmes à résoudre, la progression des membres de eur team, etc...
    Le Maître du jeu est de même relié avec le Server, qui lui indiquent les joueurs qui ont besoin d'aide. Le MJ peut alors leur répondre directement en créant une notification textuelle sur leur périphérique.
     
        2) Lancement du projet :
    Tout d'abord, il faut lancer le server afin que tous les périphériques se synchronisent avec les bonnes données. On lance le server en exécutant son main associé.
    Il faut ensuite lancer 1 ou plusieurs application afin de créer une partie, solo ou en équipe. Cela crée une requête au server qui enregistre une nouvelle partie, avec la durée impartie, la liste des énigmes à résoudre ainsi que la liste des participants.
    Enfin, on lance un Maître du jeu, relié à cete équipe qui peut récupérer la liste des membres affectés à cette équipe et les aider séparément.
    