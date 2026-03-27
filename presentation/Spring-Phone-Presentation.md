# Spring Phone Presentation

Cute, clear, and story-driven presentation draft for a Spring Boot smartphone app store backend.

---

## Slide 1. Cover

### Title
**Spring Phone**

### Subtitle
**A Personalized App Store Backend Built with Spring Boot**

### Cute Tagline
**Browse it. Love it. Buy it. Keep it.**

### Visual Idea
- Soft phone mockup
- Tiny app icons floating around
- Friendly pastel palette: mint, sky blue, soft coral, pale yellow

### On-Slide Text
- Team name
- Hackathon project
- Spring Boot + MySQL

---

## Slide 2. Product Vision

### Title
**What Problem Are We Solving?**

### On-Slide Bullets
- Users need a simple way to explore apps
- They should be able to buy apps easily
- They should also see what they already own
- The store should feel personal to each user

### Highlight Box
**Our idea:** after a user buys an app, that app should disappear from their own available-app list.

### Visual Idea
- A cute journey line with 4 icons:
  `Browse -> Filter -> Purchase -> My Apps`

---

## Slide 3. User Story

### Title
**Meet Alice**

### On-Slide Story
**Alice opens Spring Phone and wants to find a useful app.**

1. She browses all apps
2. She filters by category or name
3. She buys an app she likes
4. She sees it in her purchased list
5. The app no longer appears in her available list

### Key Message
**This user journey is the heart of our product and the guide for our backend design.**

### Visual Idea
- Small cartoon phone screen for each step
- Use arrows and soft rounded boxes

---

## Slide 4. Architecture Overview

### Title
**Three-Layer Architecture**

### On-Slide Bullets
- **Controller Layer** handles HTTP requests and responses
- **Service Layer** contains business rules and logic
- **Repository Layer** communicates with the database
- **MySQL** stores apps, users, and purchases

### Diagram
```text
User / Postman
      |
      v
Controller Layer
(AppController, UserPurchaseController)
      |
      v
Service Layer
(AppService, PurchaseService)
      |
      v
Repository Layer
(AppRepository, UserRepository, PurchaseRepository)
      |
      v
MySQL Database
```

### Key Message
**We used a classic three-layer structure, but we organized the experience around the user's journey.**

---

## Slide 5. Domain Model

### Title
**Our Core Data Model**

### On-Slide Bullets
- **App** stores app information
- **User** stores phone user information
- **Purchase** connects a user to an app

### Entity Summary
- `App`: id, name, category, price, description
- `User`: id, username, email
- `Purchase`: id, user, app, purchaseTime

### Relationship Diagram
```text
User 1 --- * Purchase * --- 1 App
```

### Key Message
**Purchase is the key entity because it records ownership and drives personalized app availability.**

---

## Slide 6. Product Logic Highlight

### Title
**What Makes Our Store Personal?**

### On-Slide Formula
**Available Apps for One User**

```text
All Apps - Purchased Apps = Available Apps
```

### On-Slide Bullets
- An app does not disappear from the whole store
- It only disappears for the user who already bought it
- This creates a personalized store view for every user

### Cute Callout
**Same store, different experience for each user.**

---

## Slide 7. Request Flow

### Title
**Example Flow: Alice Buys TravelMap**

### On-Slide Steps
1. Alice sends `POST /api/users/1/purchases`
2. Controller receives the request
3. Service checks the user and app
4. Service validates duplicate purchase rules
5. Repository saves the purchase
6. Database stores the new purchase record
7. TravelMap appears in Alice's purchased list
8. TravelMap disappears from Alice's available list

### Key Message
**Each layer has one clear responsibility, which keeps the code readable and maintainable.**

---

## Slide 8. REST API Design

### Title
**Our Main API Endpoints**

### On-Slide Table

| Endpoint | Purpose |
|---|---|
| `GET /api/apps` | Browse all apps |
| `GET /api/apps?name=...&category=...` | Search and filter apps |
| `GET /api/users/{userId}/apps/available` | Show apps available to one user |
| `POST /api/users/{userId}/purchases` | Purchase an app |
| `GET /api/users/{userId}/purchases` | List purchased apps |

### Key Message
**Our API design directly follows the user journey.**

---

## Slide 9. Persistence and Testing

### Title
**How We Make It Reliable**

### On-Slide Bullets
- We use **MySQL** for persistent storage
- Data remains available after restarting the app
- We created **dummy data** for realistic testing
- We tested both happy paths and error cases

### Tested Scenarios
- browse apps
- filter by name and category
- successful purchase
- duplicate purchase
- missing user
- missing app
- empty result cases

### Cute Callout
**A good app store should be dependable, not dramatic.**

---

## Slide 10. Future Ideas

### Title
**If We Had More Time...**

### Product Ideas
- ratings and reviews
- app recommendations
- user login and authentication
- app detail pages
- admin dashboard

### Technical Ideas
- local cache for repeated purchase checks
- pagination and sorting
- global exception handling
- integration tests
- payment status tracking

### Closing Line
**Our project is small, but it is designed to grow.**

---

## Slide 11. Closing

### Title
**Thank You**

### Final Message
**We designed Spring Phone around a user journey, and we implemented it with a clean three-layer Spring architecture.**

### Closing Line
**From discovery to ownership, every step is simple, personal, and structured.**

### Visual Idea
- Smiling phone
- Purchased app card with a tiny check mark

