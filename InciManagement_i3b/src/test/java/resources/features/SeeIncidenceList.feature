# language: en

Characteristic: the user should see the incidences that has created

Scenario: See the incidences that has created
	When the user log in session
	Then click the option 
	Then fill the text fields and clicks "Notify Incidence"
	Then an incidence is created (and redirected to a list view of them)