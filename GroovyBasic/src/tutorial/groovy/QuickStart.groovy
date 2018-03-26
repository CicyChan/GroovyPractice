
/*
 * Lesson 1.1 Print Hello World
 */
println "hello Groovy"


/*
 *lesson 1.2. GROOVY DOCUMENT
 */

/**
 * 
 * @author i007102
 * Class Description
 */
class Person{
	/**  the name of the person*/
	String name

	/**
	 * 
	 * @param otherPerson
	 * @return a Greeting Message
	 */
	String greet(String otherPerson){
		return "Hello ${otherPerson}"
	}
}

def person = new Person()
println person.greet("Groovy Developers")


/**
 *  Lesson 1.3 Shebang line
* #!/usr/local/bin/groovy
* println "hello from the shebang line"
*/


/*
 *  lesson 1.3  Quote Identifier
 */

def map = [:]
def firstname = "Homer"
map."Simpson-${firstname}" = "Homer Simpson"  					// with exclamation mark
map."an identifier with a space and double quotes" = "ALLOWED"  // with space mark
map.'with-dash-signs-and-single-quotes' = "ALLOWED" 			//with dash mark

assert map."an identifier with a space and double quotes" == "ALLOWED"
assert map.'with-dash-signs-and-single-quotes' == "ALLOWED"
assert map.'Simpson-Homer' == "Homer Simpson"

println map."an identifier with a space and double quotes"
println map.'with-dash-signs-and-single-quotes'
println map."Simpson-${firstname}" 
println map."Simpson-Homer"

/*
 * 1. 4Strings
 * 
 */

def strippedFirstNewline = '''\
line one
line two
line three
'''

assert !strippedFirstNewline.startsWith('\n')

// String interpolation
def name = 'Guillaume' // a plain string
def greeting = "Hello ${name}"
assert greeting.toString() == 'Hello Guillaume'

//arithmetic expressions
def sum = "The sum of 2 and 3 equals ${2 + 3}"
assert sum.toString() == 'The sum of 2 and 3 equals 5'

//In addition to ${} placeholders, we can also use a lone $ sign prefixing a dotted expression:
def peple = [name: 'Guillaume', age: 36]
assert "$peple.name is $peple.age years old" == 'Guillaume is 36 years old'

//interpolating closure expressions
def sParameterLessClosure = "1 + 2 == ${-> 3}"
assert sParameterLessClosure == '1 + 2 == 3'

def sOneParamClosure = "1 + 2 == ${ w -> w << 3}"
assert sOneParamClosure == '1 + 2 == 3'

//interoperability with java
String takeString(String message) {
	assert message instanceof String
	return message
}

def message = "The message is ${'hello'}"
assert message instanceof GString

def result = takeString(message)
assert result instanceof String
assert result == 'The message is hello'

//

