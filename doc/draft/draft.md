# Draft

## More documents
* FAQ (still in progress)

#### Complex example
Imagine the two fictional classes ```Person``` and ```Address```:

```Java
public class Person {
	enum Feature {
		HAIR_COLOR, SKIN_COLOR, TATTOO_TEXT, WRISTWATCH_BRAND
	}

	private final String name;
	private final int age;
	private final double moneyInPocket;
	private final Address address;

	private final Map<Feature, String> features;
	private final Set<String> leisureActivities;

	...
	// Constructor
	...
	// Getters
	...
}
```
```Java
public class Address {
    private final String streetName;
    private final int houseNumber;
    private final int zipCode;
    private final String city;
    private final String country;

	...
	// Constructor
	...
	// Getters
	...
}
```