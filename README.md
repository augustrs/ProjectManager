## ProjektManager

ProjektManager er en webapplikation der er designet til at hjælpe med at adminstrere projekter, opgaver og subprojekter. 

## Funktioner
* Log in
* Opret bruger
* Opret et projekt
* Opret et subprojekt
* Opret opgaver
* Opdater status på opgaver
* Slette opgaver
* Tilføje/fjerne medarbejder til opgaver
* Se tildelte opgaver


ProjectManager giver brugerne lov til at oprette, redigere og slette projekter og opgaver samt uddele opgaver til medarbejder.

## Anvendte teknologier
* Java: Primært programmeringssprog.
* Spring Boot: Rammeværk til opbygning af applikationen.
* Thymeleaf: Skabelonmotor til rendering af views.
* JUnit: Testrammeværk.
* Maven: Bygningsautomatiseringsværktøj.
* MySQL: Databasehåndteringssystem

## Opsætning og installation

Denne sektion vil give dig instruktioner til at installere ProjektManager. 

Liste af teknologier der er nødvendige for projektet kan køre:
* Java 17
* MySQL Community Server 8.4.0
* Maven 4.0.0
* Spring 6.0.11
* Spring Boot 3.2.5
* Thymeleaf 3.2.5


Trin til at køre programmet
1. Git klon https://github.com/augustrs/ProjectManager.
2. Naviger til filen ‘application.properties’ under ‘src/main/resources’ og ændre “spring.profiles.active=prod” til at være lig med “dev”.
3. Ændre "application-dev.properties", til dit brugernavn og adgangskode fra det miljø du har oprettet databasen i, så programmet kan tilgå databasen.
4. Hent SQL-scripts ned fra ‘src/main/resources/static’ som indeholder skemaet og testdata - vi anbefaler at oprette databasen i MySQL Workbench, men andre alternativer fungere fint.
5. Kør programmet - husk at angive port hvis du har andre programmer kørende lokalt på dette tidspunkt (Tomcat bruger automatisk server-port: 8080).
6. Åbn din webbrowser og naviger dig til den angivne port programmet køre på.
7. Nyd dit nye projektstyringsværktøj.

## Bidrag 
hvis du ønsker at hjælpe med at udvikle ProjectManager se [CONTRIBUTE.MD]([https://github.com/augustrs/ProjectManager/commit/9d1a95cb71105803dc4af4643699faf8b39a4d2b](https://github.com/augustrs/ProjectManager/blob/9d1a95cb71105803dc4af4643699faf8b39a4d2b/docs/CONTRIBUTE.md)).
