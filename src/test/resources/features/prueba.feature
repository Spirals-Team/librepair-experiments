Feature: modificar una incidencia
 
  Scenario: operario modifica el estado de una de sus incidencias asignadas
    Given el operario con identificador "99999999A"
    	And la primera de las incidencias de su lista de incidencias asignadas
    		
    When el operario cambia el estado de la incidencia por "Cerrada"
    And la incidencia se actualiza en la base de datos
    And la incidencia se recupera de nuevo de la base de datos
    
    Then la incidencia tiene el estado "Cerrada"