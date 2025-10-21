# Dashboard Search API - Testing Guide

## Endpoint
```
GET /v1/api/dashboard/search?search={searchTerm}
```

## Example Requests

### Using cURL
```bash
# Search for "john"
curl -X GET "http://localhost:8080/v1/api/dashboard/search?search=john"

# Search for "rock"
curl -X GET "http://localhost:8080/v1/api/dashboard/search?search=rock"

# Search for "love"
curl -X GET "http://localhost:8080/v1/api/dashboard/search?search=love"
```

### Using Postman
1. Method: `GET`
2. URL: `http://localhost:8080/v1/api/dashboard/search`
3. Query Params:
   - Key: `search`
   - Value: `{your search term}`

## Response Structure
```json
{
  "success": true,
  "message": "Search completed successfully",
  "data": {
    "userInfo": [
      {
        "id": 1,
        "fullname": "John Doe",
        "username": "johndoe",
        "email": "john@example.com",
        "password": "hashed_password"
      }
    ],
    "groupInfo": [
      {
        "id": 1,
        "name": "Rock Lovers",
        "description": "Group for rock music fans"
      }
    ],
    "songInfo": [
      {
        "id": 1,
        "title": "Bohemian Rhapsody",
        "artist": "Queen",
        "album": "A Night at the Opera",
        "year": 1975,
        "durationsec": 354
      }
    ]
  }
}
```

## Search Behavior
- **Case-insensitive** search
- **Partial matching** using LIKE with wildcards (`%search%`)
- Searches across:
  - **Users**: `username` OR `fullname`
  - **Groups**: `name`
  - **Songs**: `title`

## Example Use Cases

### 1. Search for a user
```
GET /v1/api/dashboard/search?search=john
```
Returns users with "john" in username or fullname

### 2. Search for a group
```
GET /v1/api/dashboard/search?search=rock
```
Returns groups with "rock" in the name, plus any users/songs matching "rock"

### 3. Search for a song
```
GET /v1/api/dashboard/search?search=bohemian
```
Returns songs with "bohemian" in the title, plus any users/groups matching

### 4. Empty search
```
GET /v1/api/dashboard/search?search=
```
Returns empty lists for all three categories

## Implementation Details

### Repository Methods
- `UserRepository.searchByUsernameOrFullname(String search)`
- `GroupRepository.searchByName(String search)`
- `SongMasterRepository.searchByTitle(String search)`

### Service Layer
- `DashboardServiceImpl.search(String searchTerm)`
  - Calls all three repository methods
  - Aggregates results into `SearchResultDTO`

### Controller
- `DashboardController.search(@RequestParam String search)`
  - Wraps response in `ApiResponse<SearchResultDTO>`
  - Returns HTTP 200 OK with search results
