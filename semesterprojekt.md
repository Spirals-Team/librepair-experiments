# Semester projekt

## Generelle noter

## Log

### 30/04/18 (mandag, Sprint 1)
#### Udvælgelse af SCRUM masterer og log-/notefører
1. SCRUM master: Stanislav
2. SCRUM master: Alexander
3. SCRUM master: Mathias
* Note-/logfører

#### Tanker før første Sprint-planning
* Hvor mange user stories kan der nåes på et Sprint. Tankerne ligger på, at vi kan klarer 80 point på tre uger. Hvilket vil svarer til 26 point om ugen. 
* Overvejelser om estimeringspoker.

#### Sprint-planning møde (med PO)
* __Priotering__: 
* Det giver mening af søge ud fra biltype (sedan, SUV osv.)
* Forskellen er at PO ville have definereret kravene. Og hvis det havde været tilfællet havde acceptance test skulle indeholde biltyperne, da det er det eneste sted til ville have været dokumenteret. 
* Acceptance test = how to demo + data. - Forskellen på how to demo og acceptance test, konkrete data skal fremgå i acceptance test. Meget mere specificeret.
* Hvor vigtige er vores Spikes for os.
    - Spike omkring søgefunktionalitet. To versioner af views (gitter og liste). PO ville helst have listen.
* Valg af User Stories. PO mening. Søgning efter bil kommer først. Derefter sortering af resultater.
* Hvad ligger der i søgning fra andre leverandører. Forklaring var fin ifølge PO. Den ses som en mere komplet løsning.
* Hvad ligger der i User Story for mobil. (Da det er et teknisk krav SKAL den med). Ikke nogen grund til at vælge den til første Sprint.
* Sortering i pris af søgeresultater. __Pris er essentielt søgekriterer__. PO er enig. Default er prissortering for mange sider. Det er stadig vigtigt at have den med. PO vil have den med i første Sprint.
* Estimering. 70-80 point ialt for hele projektet. Dette er ok med PO.
* Gemme live til næste Sprint (Sprint 2). Da de andre gruppe også skal være færdige med deres API.
* Booking kan laves trods manglende API fra de andre grupper. PO vil gerne have denne med i første Sprint. 
* Spike setup. PO vil gerne have denne med i første Sprint. (Off the record. det er fint at have denne med. Det hjælper at den har lav estimering. PO er nemmere at forhandle med). 
* Varianter af om man laver Spike eller lader det indgå i User Stories.
* Princielt skal SCRUM master der sørge for at holde PO væk fra teamet. SCRUM masteren er en form for bindeled mellem PO og teamet. 

* Vi mødes på fredag hvor der demo'es.
* Tidspunkt. Google dok udvides med en ny fredag. 

#### Sprint-planning (uden PO)
* Hvor meget kan vi nå.
* Hvor meget tid har hver person. Estimerer det ud fra det.
* Splitte US ud i tasks.
* Kun grundig planlægning for det der besluttet.
* (HVIS DET HAR VÆRET HELT RIGTIG SPRINT PLANNING): Så var PO blevet til Sprint planning meeting og kunne spørge og se planning færdig. 
* Estimeringspoker:
    - Spike setup: 2,2,2,1. __2 timer__ er valgt. Lavet tasks.
    - Søg efter bil: 15,15,30,60. __30 timer__ er valgt. 
    - Filtrering: __5__ er valgt. 
    - Søgning: __5__ er valgt. 
    - Booking: __20__ er valgt.
* Tanker omkring at vi faktisk ikke har 30 timer grundet Sprints som er mindre end fulde fem dage. 

### 01/05/18 (tirsdag)
#### Spike - Setup
* Outsourcer er kontaktet og aftale indgået. 
* Travis CI problemer. Skyldes at frontend og backend alle laves i samme github projekt. 
* 

### 02/05/18 (onsdag)
#### Standup meeting
* tasks er klar til at blive samlet. 
* samling af tasks for ene User Story før næste begyndes
* 

### 03/05/18 (torsdag) 
#### Worked remotely
* Arbejdede alle hjemme
* Snak over Discord
* Stream af skærm
* Working developer med alle User Stories (styling blev nedprioteret)

### 04/05/18 (fredag)
#### Før Sprint Review
* Project setup
* 

#### Sprint Review
* User Story: 'Søg efter bil'
    - Mere vejledning på siden, mere information.
    - Fremvisning af labels på søge felterne
    - 

* User Story: 'Filtrering af Søgeresultater'
    - Dummydata i vejen
    - Finpudsning af styling
    - Bedre bruger grænseflade. Evt. kombiner med øvre US.
    - Man "kunne filtrer i pris også". Lød ikke til at være nødvendigt.

* User Story: 'Sortering af pris i Søgeresultater'
    - 

* User Story: 'Booking'
    - Mangler forbindelse til server -> Fair, er ifølge US ok. 
    - 

* PO ligeglad med Spikes, da han er PO.

* Opsummering: Grundlæggende ny User Story til optimering af brugergrænsefglade og fejlmeddelelser. 

* Til mandag: Opdatere backlog, reestimerer estimater. Mødes med PO og laver ny planning til næste Sprint.
    - Grund til ikke af finestimerer er, at man ikke vil bruge tid på det. 
    - Men hvis man ved det, uden at det bruger for meget tid, så kan man finjusterer. 
    - Pokerplanning kommunikationsværktøj virker. Bliver brugt i virkelige situationer. 

#### Teknisk Review
* Fordele/ulempe ved at ligger filtrering på henholdsvis backend og frontend.
    - 
* Reservation dilemma: Hvor skal checket af en evt. 'on-going' reservation ligger henne? Man kan ikke komme udenom det. 

* Vi laver backend der integrerer vores og andre API. Tråde implementation?

#### Retroperspektive 
* Søgning/filtrering skal ske på frontend
* Fordelt arbejdet bedre over ugen
* 

### 07/05/18 (mandag, Sprint 2)
#### Tanker inden Sprint Planning møde
* Outsorcing har først deadline torsdag. Evt. mulighed for at arbejde med den over weekenden, da der først er Sprint Review mandag, i næste uge. 
* Stanislav overtager, Log fører rollen, og Alexander overtager SCRUM Master rollen.
* Lidt tvivl om hvordan en userstory om reformatering og tilpasning af afsluttet userstory, skal håndteres med how to demo, og who/what/why userstory beskrivelse


#### Sprint planning møde
* Grov estimater, skal indegå i planlægningen, mht. velocity af et SCRUM team (hvor mange af de grovestimerede point kan vi nå på et sprint)
* Vi får aftalt, at vi får lov til at vælge lidt gruppemæssig om vi vil fortsætte med at afslutte vores web, eller om vi vil igang med at lave app og senere vende tilbage til web.
* God feedback at få ind i rapporten til processen mht. at nogen af userstories ikke kan klares på givne tidspunkt, da det er outsourcet. (presset ud i fremtiden af SCRUM Master)
* PO påpeger manglende startside til app delen af projektet, som teamet har forbiset.
* De userstories som munder ud i allerede afsluttede userstories, kan være lidt "WishyWashy" da der altid dukker nogen ting op i processen, og disse hånderes med nogen acceptence test ud fra det som PO synes der mangler i forrige userstories, som giver lidt mindre sigende userstories, men måske nogen mere konkrete Acceptence tests som beskriver de fejl/ mangler der skal laves om på.

#### Sprint planning reflection
* Vi burde have lavet grov estimatet, i samme format som det endelige estimat da det havde gjort arbejded nemmere med estimering siden hen.
* Vi vil anvende poker-planning igen til at estimere vores endelige estimater, som vi følte fungerede godt forrige gang, og ledte til gode diskussioner.

#### Estimering
* Vi estimerer alle tingene lidt højere denne omgang, da vi sidst ikke helt nåede at få alle design og fejl håndtering på plads
 