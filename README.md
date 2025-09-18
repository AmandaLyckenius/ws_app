ToDo-App

Vi har skapat en ToDo applikation för att minska stressen under vardagen och hjälpa användare organisera
sina uppgifter. För att användaren ska få den bästa användarupplevelsen har vi skapat ett speciellt 
id för varje användare, så att man kan hitta sina egna uppgifter. Varje todo har en titel, beskrivning, ett unikt id samt 
möjlighet att markera som klar. Med det kan du också välja att ta bort, uppdatera din beskrivning, 
samt skapa nya todos. Vi har även en filtreringsfunktion där man kan filtrera fram de mest förekommande todos, och välja 
hur många todos man vill se. Vi hoppas att denna applikation kommer att underlätta livet för våra användare.

Hur man startar projektet: 
Klona vårat repo med:
git clone https://github.com/AmandaLyckenius/ws_app.git
Gå in i projektet med kommandot:
cd WS-Todo-App

Bygg projektet med docker:
docker build -t ws-todo-app .

Kör projektet med:
docker run -p 8080:8080 ws-todo-app

Nu finns appen via:
localhost:8080/

Exempel på API-anrop:
localhost:8080/users/add     (glöm inte ditt username: X -> det fungerar som ditt id när du vill se eller ändra dina todos)
localhost:8080/users/all
localhost:8080/tasks/add?username=X
localhost:8080/tasks/all/X
localhost:8080/tasks/delete?username=X&title=X
localhost:8080/tasks/setDone?username=X&title=X
localhost:8080/tasks/updateDescription?username=X&title=X
localhost:8080/tasks/top-common?limit=5
localhost:8080/tasks/search



Vi har lärt oss hur mycket webbtjänster kan hjälpa en som programmerare, och underlätta att testa sin applikation,
felsöka och kontrollera statuskoder, tex via postman och lagra data via en databas.
Vi har lärt oss mycket om databasen MongoDB och noSQL. Hur man kopplar samman en applikation med en databas,
och vikten i att all känslig information inte ska versionshanteras, utan läggas in i gitignore för att
minska risken för att obehöriga kommer åt känslig data. Vi har lärt oss hur man kan filtrera i en databas med hjälp av query, samt aggregation vid mer avancerad filtrering.

Vi har utökat våran kunskap i git och versionshantering. Vi har använt git projects för att kunna planera vårat projekt och har 
lärt oss hur vi refererar till issues vid commits. Vi har fått träna på och utvecklat våra commit-meddelanden samt lärt oss
hur vi lägger till en beskrivning till våra commits. Det har gjort att vi kan hålla våra commits korta och tydliga, men 
även få med en mer beskrivande och tydliga meddelanden. Vi har fått träna på pull requests och fortsätta använda branches. 

Vi har haft ett bra samarbete i gruppen. Vi har delat upp arbetet mellan oss och vi har lärt oss mycket av varandra.
Vi har haft bra kommunikation och alla haft viljan att bidra i projektet. Vi har lärt oss varandras sätt att arbeta och trots 
att vi ibland har olika sätt att koda, kommer vi fram till samma struktur och resultat. Detta är mycket tack vare god planering.
Ingen har halkat efter, och våra olika styrkor har kompletterat och hjälpt varandra. 