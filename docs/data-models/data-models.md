# Data Models

## Users

### User Domain Model
| Field Name | Data Type | Notes                   |
|------------|-----------|-------------------------|
| id         | long      | auto generated          |
| rfid       | string    | rfid card serial number |
| name       | string    |the name of the user     |
| elo        | int      | defaults to 1200         |
| wins       | int      |total number of wins      |
| losses     | int      |total number of losses    |

### User DTO
| Field Name | Data Type | Notes                   |
|------------|-----------|-------------------------|
| id         | long      | auto generated          |
| name       | string    |                         |
| elo        | int      | defaults to 1200        |
| wins       | int      |                         |
| losses     | int      |                         |

### User Login Submission
> Sent from app on login

| Field Name | Data Type | Notes                   |
|------------|-----------|-------------------------|
| rfid       | string    | rfid card serial number |
| name       | string    |                         |

## Matches

### Match Domain Model
| Field Name     | Data Type | Notes                    |
|----------------|-----------|--------------------------|
| id             | long      | auto generated           |
| playerOneId    | long      | auto generated db id of player|
| playerTwoId    | long      | auto generated db id of player|
| playerOneScore | int      | the final score of the player |
| playerTwoScore | int      |the final score of the player |
| winner         | long      | id of the winning player |

### Match Creation Request
> Send this when starting a new match

| Field Name     | Data Type | Notes                    |
|----------------|-----------|--------------------------|
| playerOneId    | long      |                          |
| playerTwoId    | long      |                          |

### Match Update Request
> Send matchid as a path variable

| Field Name     | Data Type | Notes                    |
|----------------|-----------|--------------------------|
| playerOneScore | long      |                          |
| playerTwoScore | long      |                          |

## Elo History

### Elo History Data Model
| Field Name | Data Type | Notes                    |
|------------|-----------|--------------------------|
| id         | long      |                          |
| userId     | long      |                          |
| elo        | int       | Elo post-change           |
| date       | Date      | Date elo change occurred |
