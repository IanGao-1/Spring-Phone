# Spring-Phone

Spring Boot REST backend for a smartphone app store hackathon project.

## Features

- Browse all apps
- Search apps by name
- Filter apps by category
- Show apps still available for a given user
- Purchase an app
- List apps already purchased by a user

## Tech Stack

- Spring Boot 3
- Spring Web
- Spring Data JPA
- H2 file database

## Project Structure

- `controller`: REST API layer
- `service`: business logic layer
- `repository`: database access layer
- `entity`: JPA entities
- `dto`: response/request payloads

## API Examples

Browse all apps:

```http
GET /api/apps
```

Browse apps by name:

```http
GET /api/apps?name=fit
```

Browse apps by category:

```http
GET /api/apps?category=Productivity
```

Browse apps available to a user:

```http
GET /api/users/1/apps/available
GET /api/users/1/apps/available?category=Games
GET /api/users/1/apps/available?name=map
```

Purchase an app:

```http
POST /api/users/1/purchases
Content-Type: application/json

{
  "appId": 11
}
```

List purchased apps:

```http
GET /api/users/1/purchases
```

## Database Notes

- Database URL: `jdbc:h2:file:./data/appstoredb`
- Data persists between runs because H2 is configured in file mode.
- Spring loads `schema.sql` and `data.sql` automatically on startup.

## Assumptions

- An app remains in the store globally after purchase.
- "No longer show up on the list of available apps" means it should not appear in that specific user's available-app list.
- A user cannot purchase the same app twice.
