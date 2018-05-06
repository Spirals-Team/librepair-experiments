Feature:AddField

 Characteristic: the user could add custom fields 

 Scenario: Add custom fields
	When the user logs in session
	Then fill the form of a field
	Then click Add field
	Then see the info