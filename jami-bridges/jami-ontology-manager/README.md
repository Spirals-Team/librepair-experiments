JAMI extension of the psi-tools OntologyManager

Example
```java
InputStream ontology = ...
MIOntologyManager om = new MIOntologyManager(ontology);
String ontologyIdentifier = "MI";
OntologyAccessTemplate<MIOntologyTermI> accessMI = om.getOntologyAccess(ontologyIdentifier);
```