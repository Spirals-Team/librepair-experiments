# language: en

Characteristic: the user should create incidences 

Scenario: Create incidences
	When the user log in session
	Then see a page to create incidences
	Then fill the text fields and clicks "Notify Incidence"
	Then an incidence is created (and redirected to a list view of them)