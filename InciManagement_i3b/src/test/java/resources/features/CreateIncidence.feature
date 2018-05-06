Feature:Create Incidence

Characteristic: the user should create incidences 

Scenario: Create incidences
	When the user completes the log in
	Then fill the text fields and clicks Notify Incidence
	Then an incidence is created (and redirected to a list view of them)