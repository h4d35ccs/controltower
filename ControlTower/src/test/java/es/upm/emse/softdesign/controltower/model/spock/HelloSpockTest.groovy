package es.upm.emse.softdesign.controltower.model.spock

class HelloSpockTest {
	def "length of Spock's and his friends' names"() {
		expect:
		name.size() == length
	
		where:
		name     | length
		"Spock"  | 5
		"Kirk"   | 4
		"Scotty" | 6
	  }
}
