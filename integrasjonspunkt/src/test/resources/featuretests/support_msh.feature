Feature: Integrasjonspunkt skal kunne støtte å sende meldinger til eksisterende MSH løsning

  Background: Dersom adresseregisteret mangler sertifikat til mottakende organisasjon så
  er det ønskelig at integrasjonspunktet videresender forespørsler til eksisterende MSH
  slik at integrasjonspunkt ikke fremstår som en dårligere løsning enn eksisterende

  Scenario Outline: Sjekke om mottaker kan motta meldinger
    Given virksomhet <adresseregister> i Adresseregisteret
    And virksomhet <msh> i MSH sitt adresseregister
    When vi spør integrasjonspunktet om virksomhet kan motta meldinger
    Then skal vi få <svar> om at de kan motta meldinger
    Examples:
      | adresseregister | msh         | svar  |
      | finnes          | finnes      | sann  |
      | finnes ikke     | finnes      | sann  |
      | finnes ikke     | finnes ikke | usann |

  Scenario: Vi sender en melding til MSH
    Given virksomhet finnes ikke i Adresseregisteret
    And virksomhet finnes i MSH sitt adresseregister
    When integrasjonspunktet mottar en melding
    Then skal melding sendes til MSH