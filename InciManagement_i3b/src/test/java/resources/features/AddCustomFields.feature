# language: en

Characteristic: the user could add custom fields 

Scenario: Add custom fields
	When the user log in session
	Then see a page to create incidences
	Then fill the form of a field
	Then click "Add field"
	Then the field is created and showed in a table