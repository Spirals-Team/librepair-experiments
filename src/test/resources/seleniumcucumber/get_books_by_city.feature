Feature: Get Books By City
  Return list of books

  Scenario Outline: Enter Valid City
    Given The city is '<city>'
    When Enter '<city>' and choose '<database>'
    Then I should get '<page>'

Examples:
    | city     | page            | database |
    | Anderson | success         | mongodb  |
    | Anderson | success         | stub     |

