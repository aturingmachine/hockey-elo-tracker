# Local Setup

This document should help you get set up with a local environment. Our stack is as follows:

- Spring Boot Kotlin
- MySQL
- VueJS (probably)

## Getting started

First clone the repo:

`git clone git@github.com:aturingmachine/hockey-elo-tracker.git elo-tracker`

The repo contains two codebases:

1. `server/` - The Kotlin REST API
2. `app/` - The iOS (maybe) app


Open the `elo-tracker/server` directory in IntelliJ (if thats what you use) to start importing the dependencies, or go about that how you normally would.

### Database

The application is expecting a MySQL database to be backing it, specifically tested with version `8.0.11`.

> Once installed your version can be checked by running `select version();` in MySQL.

On MacOS simply run `brew install mysql`. This will install MySQL as a brew service.

Once installed run `brew services start mysql`, this will start a local run of MySQL.

Then log in as root using `mysql -u root`, there should be no password set on the initial root account.

Now we can make a new user on the DB for this application:

`CREATE USER '<some username>'@'localhost' IDENTIFIED BY '<some password>';`

**Remember these values**

Now we will create the database: `create database elotracker`

Now grant privileges on that database to the new user:

`grant all privileges on elotracker.* to '<that same username>'@'localhost';`

We can now exit MySQL: `exit`

### Application.properties and ENV Vars

The `application.properties` contains the username and password for the MySQL connection, however they should be set as environment variables. This can be done my adding them to your `bash_profile`, `.bashrc`, or `zshrc` depending on your setup. These variables are:

- `ELO_TRACKER_DB_USERNAME`
- `ELO_TRACKER_DB_PASSWORD`

Set them like so:

`export ELO_TRACKER_DB_USERNAME=<that username from earlier>`

`export ELO_TRACKER_DB_PASSWORD=<that password from earlier>`

Set them to whatever you set them to when making the user. Then source your file and restart Intellij.

The application should now be able to communicate with the local database.

## TODO

- Add section for Web Application

- Add section for iOS Application