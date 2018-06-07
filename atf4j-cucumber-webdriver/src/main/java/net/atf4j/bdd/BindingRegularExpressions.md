Use Regular Expression libs with caution, 
many RegExp designed with the purpose to verify the input, 
in this case that not always appropriate,
Scenarios will often need to apply invalid input,

Practical Examples

Plurals     names?		matches name or names
			an?     	matches a or an
Quoted      '[^']*' 	matches "something" in single quotes
            "[^"]*"		matches "something" in double quotes
Bracket
Wildcard    .*      	matches anything 0-n times
            .+			matches anything 1-n times
digit       [0-9]		matches a single digit
number      [0-9]+		matches series of digits until none digit
            d+      	matches series of digits
word        [a-z]*		matches word until none letter
            d*			matches a series of digits (or nothing)
email		[^@+@\S]
url			http://[]
