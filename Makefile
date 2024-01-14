compile:
	mvn compile

run:
	mvn exec:java -Dexec.mainClass="akka.App"

default:
	compile
	run
	