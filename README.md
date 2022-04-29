# CLI COVID Tracker

CLI COVID Tracker is a CLI application written in Java that displays information regarding COVID-19, this application also utilizes RMI registry to simulate as distributed system.

## Installation 

This application is developed using Intellij IDE, with Maven dependencies.

It is recommended to launch the application using Intellij.


First you will need to start rmiregistry before running the application.

To run the rmiregistry navigates to project directory, open Command Console:
```
start rmiregistry
```
Once rmiregistry  is running you can start the application.

Note: The TrackerServer needs to be running first before TrackerClient.

## Usage
Once the client is launched it will display welcome message and will be prompted to enter a command.
```
Welcome to the COVID tracker app, enter /help for help, enter /quit to exit
Enter:
```

Display help

```
Enter:/help
/total | obtains up-to-date COVID information
/continent <continent-name> | obtains all country information for specified continent
/country <country-name> | obtains information for specified country
/world <sort-type> (e.g. cases, todayCases, deaths, recovered, active) | obtains information for the world, sorted by specified criteria
/world json | obtains information for the world, displayed in JSON format
/quit | exits the program
```

Display total
```
Enter:/total
Total cases: 252101412
Today cases: 534350.0
Total deaths: 5087653.0
Active: 18855354
```
