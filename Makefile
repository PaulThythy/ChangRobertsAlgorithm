# Compiler le projet
compile:
	mvn clean install
	mvn dependency:copy-dependencies

# Lancer l'application
run:
	java -cp target/classes:target/dependency/* perso.test.App

# Par défaut, exécute 'compile' puis 'run'
default: compile run

