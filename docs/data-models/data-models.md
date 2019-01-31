# Data Models

## Users ##

### Users Record 
| Field Name | Data Type | Notes                   |
|------------|-----------|-------------------------|
| id         | long      | auto generated          |
| rfid       | string    | rfid card serial number |
| name       | string    | the name of the user    |
| email      | string    | the email of the user   |

### User Stats Record
| Field Name | Data Type | Notes                   |
|------------|-----------|-------------------------|
| id         | long      | auto generated          |
| userId     | long      | the type of game (enum) |
| gameType   | GameType  | the type of game (enum) |
| elo        | int       | defaults to 1200        |
| wins       | int       | total number of wins    |
| losses     | int       | total number of losses  |

### User
| Field Name | Data Type      | Notes                   |
|------------|----------------|-------------------------|
| id         | long           | auto generated          |
| name       | string         | the name of the user    |
| email      | string         | the email of the user   |
| stats      | UserStatsDTO[] | array of user stats     |

### User Stats
| Field Name | Data Type | Notes                   |
|------------|-----------|-------------------------|
| id         | long      | auto generated          |
| gameType   | GameType  | the type of game (enum) |
| elo        | int       | defaults to 1200        |
| wins       | int       | the total wins          |
| losses     | int       | the total losses        |

### User Login Request
> Sent from app on login

| Field Name | Data Type | Notes                   |
|------------|-----------|-------------------------|
| name       | string    |                         |
| rfid       | string    | rfid card serial number |

## Matches ###

### Match Record
| Field Name     | Data Type | Notes                         |
|----------------|-----------|-------------------------------|
| id             | long      | auto generated                |
| gameType       | GameType  | the type of game (enum)       |
| playerOneId    | long      | id of player                  |
| playerTwoId    | long      | id of player                  |
| playerOneScore | int       | the final score of the player |
| playerTwoScore | int       | the final score of the player |
| winner         | long      | id of the winning player      |

### Match
| Field Name     | Data Type | Notes                   |
|----------------|-----------|-------------------------|
| id             | long      | auto generated          |
| gameType       | GameType  | the type of game (enum) |
| playerOneId    | long      | id of player 1          |
| playerTwoId    | long      | id of player 2          |
| playerOneScore | int       | final score of player 1 |
| playerTwoScore | int       | final score of player 2 |
| winner         | long      | winning player id       |

### Match Summary
| Field Name     | Data Type | Notes                          |
|----------------|-------------------|------------------------|
| gameName       | long      		 | Game Type Name         |
| playerOne      | UserMatchStatsDTO | player one match stats |
| playerTwo      | UserMatchStatsDTO | player one match stats |
| playerOneScore | Int               | match player 1 score   |
| playerTwoScore | Int               | match player 2 score   |
| winnerName     | String            | winner of match name   |

### Match Creation Request
> Send this when starting a new match

| Field Name     | Data Type | Notes                    |
|----------------|-----------|--------------------------|
| playerOneId    | long      |                          |
| playerTwoId    | long      |                          |
| gameType       | GameType  |                          |

### Score Update Request
> Send matchid as a path variable

| Field Name     | Data Type | Notes                    |
|----------------|-----------|--------------------------|
| playerOneScore | long      |                          |
| playerTwoScore | long      |                          |

### User Match Stats
 Field Name      | Data Type | Notes                    |
|----------------|-----------|--------------------------|
| name           | long      |                          |
| elo            | long      |                          |
| wins           | long      |                          |
| losses         | long      |                          |

## Elo History ##

### Elo History Record
| Field Name | Data Type | Notes                    |
|------------|-----------|--------------------------|
| id         | long      |                          |
| userId     | long      |                          |
| gameType   | GameType  | the type of game (enum)  |
| elo        | int       | Elo post-change          |
| date       | Date      | Date elo change occurred |

### Elo History
| Field Name | Data Type | Notes                    |
|------------|-----------|--------------------------|
| id         | long      |                          |
| userId     | long      |                          |
| gameType   | GameType  | the type of game (enum)  |
| elo        | int       | Elo post-change          |
| date       | Date      | Date elo change occurred |
