# Test Samples

These are useful sample outputs to compare with Postman responses while testing edge cases.

## Successful Purchase

Request:

```http
POST /api/users/1/purchases
Content-Type: application/json

{
  "appId": 11
}
```

Expected response shape:

```json
{
  "purchaseId": 102,
  "appId": 11,
  "appName": "TravelMap",
  "category": "Travel",
  "price": 2.19,
  "purchaseTime": "2026-03-27T10:45:00"
}
```

## Duplicate Purchase Error

Request:

```http
POST /api/users/1/purchases
Content-Type: application/json

{
  "appId": 11
}
```

Expected response shape:

```json
{
  "error": "User 1 already purchased app 11"
}
```

## Missing User Error

Request:

```http
GET /api/users/99/purchases
```

Expected response shape:

```json
{
  "error": "User not found: 99"
}
```

## Missing App Error

Request:

```http
POST /api/users/1/purchases
Content-Type: application/json

{
  "appId": 999
}
```

Expected response shape:

```json
{
  "error": "App not found: 999"
}
```

## Invalid Request Body Example

Request:

```http
POST /api/users/1/purchases
Content-Type: application/json

{}
```

Possible response shape if your service rejects null app ids:

```json
{
  "error": "App id must not be null"
}
```

## Malformed JSON Example

Request:

```http
POST /api/users/1/purchases
Content-Type: application/json

{
  "appId":
}
```

Expected HTTP status:

```text
400 Bad Request
```
