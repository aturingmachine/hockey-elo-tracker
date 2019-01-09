# Data Models

## Users

### User Domain Model
| Field Name | Data Type | Notes                   |
|------------|-----------|-------------------------|
| id         | long      | auto generated          |
| rfid       | string    | rfid card serial number |
| name       | string    |                         |
| elo        | int      | defaults to 1200        |
| wins       | int      |                         |
| losses     | int      |                         |

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

### Match Data Model
| Field Name     | Data Type | Notes                    |
|----------------|-----------|--------------------------|
| id             | long      | auto generated           |
| playerOneId    | long      |                          |
| playerTwoId    | long      |                          |
| playerOneScore | int      |                          |
| playerTwoScore | int      |                          |
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
| userId     | long      |                          |
| elo        | int       | Elo pre-change           |
| date       | Date      | Date elo change occurred |