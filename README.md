* Cavabunga is an open source calendar server with support of RESTful API.
* Developed with Java Spring Framework (Spring boot)  
* Cavabunga's purpose is retrieve calendar information from many resources and delivering calendar information to many different platforms

* For delivering and retriving calendar information from platforms which other then rest api, Cavabunga uses (will be) cavabunga-[PROTOCOL] applications. For example: cavabunga-caldav -> cavabunga caldav proxy which is understands CalDav protocol (https://tools.ietf.org/html/rfc4791). When participants requires calendar format(iCal) via CalDav protocol. "cavabunga-caldav" communicates "cavabunga" as storage service and collects iCal information.

* Storage entity class structure is an implementation of iCal data format (https://tools.ietf.org/html/rfc5545)


# Installing

* mvn clean install

# API example

http://localhost:8080/participant/ [GET] => getting all participants 
```
{
	"status": 0,
	"message": null,
	"data": [
		{
			"@type": "User",
			"id": 1,
			"userName": "testuser",
			"creationDate": 1520700000000,
			"components": []
		},
		{
			"@type": "Group",
			"id": 2,
			"userName": "testgroup",
			"creationDate": 1520700000000,
			"components": []
		}
	]
}
```

https://localhost:8080/participant/testuser/calendar [GET] => getting participant "testuser"'s calendars
```
{
	"status": 0,
	"message": null,
	"data": [
		{
			"@type": "Calendar",
			"id": 1,
			"components": [
				{
					"@type": "Event",
					"id": 2,
					"components": [
						{
							"@type": "Alarm",
							"id": 3,
							"components": [],
							"properties": [],
							"creationDate": 1520790033000
						}
					],
					"properties": [
						{
							"@type": "Attach",
							"id": 1,
							"name": null,
							"value": "A FILE",
							"parameters": [
								{
									"@type": "Encoding",
									"id": 1,
									"name": null,
									"value": "UTF-8"
								}
							]
						},
						{
							"@type": "Dtstamp",
							"id": 2,
							"name": null,
							"value": "160620018092822 UTC+3",
							"parameters": []
						}
					],
					"creationDate": 1520790033000
				}
			],
			"properties": [
				{
					"@type": "Uid",
					"id": 3,
					"name": null,
					"value": "23734BC-AD123DEF-CC-D123",
					"parameters": []
				},
				{
					"@type": "Calscale",
					"id": 4,
					"name": null,
					"value": "GREGORIAN",
					"parameters": []
				}
			],
			"creationDate": 1520790033000
		}
	]
  ```


