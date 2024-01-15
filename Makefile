compile:
	mvn compile
	mvn package

run:
	mvn exec:java -Dexec.mainClass="akka.App"

default:
	compile
	run
	