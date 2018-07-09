# java-coaching - Automation
| [Automation](src/main/java/automation "Automated Testing with Java") | [Java Coaching](src/main/java/coaching "Coaching Java Idioms") | [Java Patterns](src/main/java/patterns "Design Patterns in Java") |

## Credentials Handling by tagged Characteristic

### Simple

	final Actor actor = credentials.tagged("@AUTHORISED");

### Platform management by System property

	-Dplatform={dev|sit|pp}

	final Actor actor = credentials.tagged("@AUTHORISED");

### Customised Platform management 

	final Actor actor = credentials.on(platform).tagged(this.tag);

### Data-Source

	{resource-root}/data/Credentials.csv

### Example Credentials.csv

	# tags, username, password, email
	@ADMIN,admin,password,admin@example.com
	@AUTHORISED,alice,password,alice@example.com
	@AUTHORISED,bob,password,bob@example.com
	@UNAUTHORISED,trudy,password,trudy@example.com
