# Elo Tracker

This repository houses the backend and eventually the web front end for a simple Elo tracking application.
The server is written in [Spring Boot](http://spring.io/projects/spring-boot) [Kotlin](https://kotlinlang.org/), and connects to a MySQL database. It can found in the `server/` directory.

##### THIS HAS BEEN PUT ON HOLD
The web front end should be able to view players and their Elo ratings, as well as past matches. It can be found in the `server/src/main/elo-tracker-web/` directory. It is written in VueJS.

There exists a "kiosk" application, that will take in the serial of an RFID card to be used as the user's login credentials. That codebase can be found in the `kiosk/` directory. It is built using VueJS and Electron.

There will also be a personal iOS application for users to view data and stats, it can be found in the `hockeyEloTracker-ios` directory.

## Set up
A set up document can be found [here](https://github.com/aturingmachine/hockey-elo-tracker/blob/master/docs/dev-support/setup.md)

## Package Structure
The package structure of this application is divided by feature, for example all controller, services, and repositories related to the `User` resource live in the `user` package and are further divided based on function.

## Data Models
The data models for this application can be found [here](https://github.com/aturingmachine/hockey-elo-tracker/blob/master/docs/data-models/data-models.md)

## Contributing
First, check out the [Contributing Guidelines](https://github.com/aturingmachine/hockey-elo-tracker/blob/master/.github/CONTRIBUTING.md).

Then feel free to check out the [GitHub Project](https://github.com/aturingmachine/hockey-elo-tracker/projects/1) to pick up some work
to get started!
