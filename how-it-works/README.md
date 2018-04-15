# *Gazetteer JSON - How it works!*

The lookup gazetteers used in this library are very heterogeneous. Some of them have an API, others a SPARQL endpoint. Moreover, the response is very different in formats (XML, JSON, etc.) and very different structured. This library focuses on harmonising all these gazetteers using [GeoJSON+](https://github.com/linkedgeodesy/geojson-plus).

## Index of Contents

* [ChronOntology](#chronontology)
* [iDAI.gazetteer](#idaigazetteer)
* [GeoNames](#geonames)
* [Getty TGN](#getty-tgn)
* [Pleiades](#pleiades)

## ChronOntology

Query ChronOntology using the API.

### getPlaceById()

**request:**
* **url:** `http://chronontology.dainst.org/data/period/:id`
* **method:** `GET`
* **header:** `Accept: application/json`
* **response:** `JSON`

**used attributes:**
* **feature:** see place gazetteer information + gazetteerrelation `json.resource.{spatiallyPartOfRegion!=0,hasCoreArea!=0,isNamedAfter!=0}`
* **metadata:**
  * **@id**: `http://chronontology.dainst.org/data/period/{json.resource.id}`
  * **periodid**: `json.resource.id`
  * **chronontology**: `json`
  * **names**: `json.resource.names`
  * **when**: `json.resource.hasTimespan`
  * **coverage**: `json.resource.[spatiallyPartOfRegion,hasCoreArea,isNamedAfter]`

## iDAI.gazetteer

Query the iDAI.gazetteer using the API.

### getPlaceById()

**request:**
* **url:** `https://gazetteer.dainst.org/place/:id`
* **method:** `GET`
* **header:** `Accept: application/vnd.geo+json`
* **response:** `JSON`

**used attributes:**
* **prefName:** `json.properties.prefName.{title,language}`
* **altNames:** `json.properties.names[i].{title,language}`
* **geometry:** `json.geometry` [GeoJSON Geometry Object]

### getPlacesByBBox()

**request:**
* **url:** `https://gazetteer.dainst.org/search.json?polygonFilterCoordinates=upperleftLon&polygonFilterCoordinates=upperleftLat&polygonFilterCoordinates=upperrightLon&polygonFilterCoordinates=upperrightLat&polygonFilterCoordinates=lowerrightLon&polygonFilterCoordinates=lowerrightLat&polygonFilterCoordinates=lowerleftLon&polygonFilterCoordinates=lowerleftLat&q=*&fq=types:populated-place`
* **method:** `GET`
* **response:** `JSON`

**used attributes:**
* **prefName:** `json.result[i].prefName.{title,language}`
* **altNames:** `json.result[i].names[i].{title,language}`
* **geometry:** `json.result[i].prefLocation.coordinates[lng, lat]`

### getPlacesByString()

**request:**
* **url:** `https://gazetteer.dainst.org/search.json?q={SearchString}&fq=types:populated-place`
* **method:** `GET`
* **response:** `JSON`

**used attributes:**
* **prefName:** `json.result[i].prefName.{title,language}`
* **altNames:** `json.result[i].names[i].{title,language}`
* **geometry:** `json.result[i].prefLocation.coordinates[lng, lat]`

## GeoNames

Query GeoNames using the API.

### getPlaceById()

**request:**
* **url:** `http://api.geonames.org/get?geonameId=:id&username=chron.ontology`
* **method:** `GET`
* **header:** `Accept: application/xml;charset=UTF-8`
* **response:** `XML`

**used attributes:**
* **prefName:** `geoname.name`
* **altNames:** `geoname.alternateName{text:value,lang:attribute}`
* **geometry:** `geoname.{lat,lng}`

### getPlacesByBBox()

**request:**
* **url:** `http://api.geonames.org/searchJSON?username=chron.ontology&featureClass=A&featureClass=P&style=full&south=upperrightLat&north=lowerleftLat&west=upperrightLon&east=lowerleftLon`
* **method:** `GET`
* **header:** `Accept: application/json;charset=UTF-8`
* **response:** `JSON`

**used attributes:**
* **prefName:** `json.geonames.name`
* **altNames:** `json.geonames.alternateNames[i].{name,lang}`
* **geometry:** `json.geonames.{lat,lng}`

### getPlacesByString()

**request:**
* **url:** `http://api.geonames.org/searchJSON?username=chron.ontology&featureClass=A&featureClass=P&style=full&name={searchString}`
* **method:** `GET`
* **response:** `JSON`

**used attributes:**
* **prefName:** `json.geonames.name`
* **altNames:** `json.geonames.alternateNames[i].{name,lang}`
* **geometry:** `json.geonames.{lat,lng}`

## Getty TGN

Query The Getty TGN using the SPARQL endpoint.

### getPlaceById()

**request:**
* **url:** `http://vocab.getty.edu/sparql.json?query={SPARQL}`
* **method:** `POST`
* **response:** `application/sparql-results+json`

**SPARQL:**

```SQL
PREFIX ontogeo: <http://www.ontotext.com/owlim/geo#>
PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>
PREFIX skos: <http://www.w3.org/2004/02/skos/core#>
PREFIX tgn: <http://vocab.getty.edu/tgn/>
PREFIX foaf: <http://xmlns.com/foaf/0.1/>
PREFIX gvp: <http://vocab.getty.edu/ontology#>
PREFIX skosxl: <http://www.w3.org/2008/05/skos-xl#>
PREFIX dc: <http://purl.org/dc/elements/1.1/>

SELECT DISTINCT ?place ?prefLabel ?altLabel ?lat ?long WHERE {
  ?place skos:inScheme tgn: .
  ?place dc:identifier "id" .
  ?place xl:prefLabel [skosxl:literalForm ?prefLabel] .
  OPTIONAL { ?place xl:altLabel [skosxl:literalForm ?altLabel] }.
  ?place foaf:focus ?p.
  ?p geo:lat ?lat .
  ?p geo:long ?long .
}
```

**used attributes:**
* **prefName:** `json.results.bindings[0].prefLabel.{value,xml:lang}`
* **altNames:** `json.results.bindings[i].altLabel.{value,xml:lang}`
* **geometry:** `json.results.bindings[0].{lat,long}.value`

### getPlacesByBBox()

**request:**
* **url:** `http://vocab.getty.edu/sparql.json?query={SPARQL}`
* **method:** `POST`
* **response:** `application/sparql-results+json`

**SPARQL:**

```SQL
PREFIX ontogeo: <http://www.ontotext.com/owlim/geo#>
PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>
PREFIX skos: <http://www.w3.org/2004/02/skos/core#>
PREFIX tgn: <http://vocab.getty.edu/tgn/>
PREFIX foaf: <http://xmlns.com/foaf/0.1/>
PREFIX gvp: <http://vocab.getty.edu/ontology#>
PREFIX skosxl: <http://www.w3.org/2008/05/skos-xl#>
PREFIX dc: <http://purl.org/dc/elements/1.1/>

SELECT DISTINCT ?place ?prefLabel ?altLabel ?lat ?long ?id WHERE {
  ?place skos:inScheme tgn: .
  ?place dc:identifier ?id .
  ?place foaf:focus [ontogeo:within("lowerleftLat" "lowerleftLon" "upperrightLat" "upperrightLon")].
  ?place xl:prefLabel [skosxl:literalForm ?prefLabel] .
  OPTIONAL { ?place xl:altLabel [skosxl:literalForm ?altLabel] }.
  ?place foaf:focus ?p.
  ?p geo:lat ?lat .
  ?p geo:long ?long .
}
```

**used attributes:**
* **prefName:** `json.results.bindings[index,0].prefLabel.{value,xml:lang}`
* **altNames:** `json.results.bindings[index,i].altLabel.{value,xml:lang}`
* **geometry:** `json.results.bindings[index,0].{lat,long}.value`

### getPlacesByString()

**request:**
* **url:** `http://vocab.getty.edu/sparql.json?query={SPARQL}`
* **method:** `POST`
* **response:** `application/sparql-results+json`

**SPARQL:**

```SQL
PREFIX ontogeo: <http://www.ontotext.com/owlim/geo#>
PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>
PREFIX skos: <http://www.w3.org/2004/02/skos/core#>
PREFIX tgn: <http://vocab.getty.edu/tgn/>
PREFIX foaf: <http://xmlns.com/foaf/0.1/>
PREFIX gvp: <http://vocab.getty.edu/ontology#>
PREFIX skosxl: <http://www.w3.org/2008/05/skos-xl#>
PREFIX dc: <http://purl.org/dc/elements/1.1/>

SELECT DISTINCT ?place ?prefLabel ?altLabel ?lat ?long ?id WHERE {
  ?place skos:inScheme tgn: .
  ?place dc:identifier ?id .
  ?place luc:term 'searchString' .
  ?place xl:prefLabel [skosxl:literalForm ?prefLabel] .
  OPTIONAL { ?place xl:altLabel [skosxl:literalForm ?altLabel] }.
  ?place foaf:focus ?p.
  ?p geo:lat ?lat .
  ?p geo:long ?long .
} ORDER BY ASC(LCASE(STR(?Term)))
```

**used attributes:**
* **prefName:** `json.results.bindings[index,0].prefLabel.{value,xml:lang}`
* **altNames:** `json.results.bindings[index,i].altLabel.{value,xml:lang}`
* **geometry:** `json.results.bindings[index,0].{lat,long}.value`

## Pleiades

Query Pleiades using the [Pelagios Peripleo API](https://github.com/pelagios/peripleo) v1.

### getPlaceById()

**request:**
* **url:** `http://peripleo.pelagios.org/peripleo/places/http:%2F%2Fpleiades.stoa.org%2Fplaces%2F:id`
* **method:** `GET`
* **response:** `JSON`

**used attributes:**
* **prefName:** `json.title`
* **altNames:** `json.names[i]`
* **geometry:** `json.geo_bounds.{min_lon,max_lon,min_lat,max_lat}` calculate a point if `min_lon=max_lon && min_lat=max_lat` or a bounding box

### getPlacesByBBox()

**request:**
* **url:** `http://peripleo.pelagios.org/peripleo/search?bbox=upperleftLon,lowerleftLon,upperrightLat,upperleftLat&types=place&limit=10000`
* **method:** `GET`
* **response:** `JSON`

**used attributes:**
* **prefName:** `json.items[i].title`
* **altNames:** `json.items[i].names[i]`
* **geometry:** `json.items[i].geo_bounds.{min_lon,max_lon,min_lat,max_lat}` calculate a point if `min_lon=max_lon && min_lat=max_lat` or a bounding box

### getPlacesByString()

**request:**
* **url:** `http://peripleo.pelagios.org/peripleo/search?query={searchString}&types=place&limit=250`
* **method:** `GET`
* **response:** `JSON`

**used attributes:**
* **prefName:** `json.items[i].title`
* **altNames:** `json.items[i].names[i]`
* **geometry:** `json.items[i].geo_bounds.{min_lon,max_lon,min_lat,max_lat}` calculate a point if `min_lon=max_lon && min_lat=max_lat` or a bounding box
