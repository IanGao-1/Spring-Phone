# Spring Phone Speaker Notes

## Slide 1. Cover

Hello everyone, today we are presenting Spring Phone, a personalized app store backend built with Spring Boot. Our goal was to build a simple but structured REST backend that supports browsing apps, purchasing apps, and viewing purchased apps.

## Slide 2. Product Vision

We started from the product side, not only the technical side. We wanted a small app store experience where users can explore apps, buy them, and keep track of what they already own. The most important product idea is that after a user buys an app, that app should no longer appear in that user's available list.

## Slide 3. User Story

To explain our project, we use one simple user story. Alice opens Spring Phone, browses apps, filters them by category or name, buys one app, and then sees that app in her purchased list. At the same time, that app disappears from her own available-app list. This story guided both our product design and our backend design.

## Slide 4. Architecture Overview

Technically, we implemented the project using a classic three-layer architecture. The controller layer handles HTTP requests, the service layer contains our business logic, and the repository layer talks to the database. This separation keeps the project organized and makes each responsibility very clear.

## Slide 5. Domain Model

Our system is built around three main entities: App, User, and Purchase. App stores the app information, User stores user information, and Purchase connects a user with an app. Purchase is especially important, because it records ownership and helps us decide what should still appear in the user's available list.

## Slide 6. Product Logic Highlight

The key business rule in our project is personalized availability. An app does not disappear from the whole store after one purchase. Instead, it only disappears for the user who has already bought it. So in our system, available apps are calculated as all apps minus the apps already purchased by that user.

## Slide 7. Request Flow

Here we show one complete request flow. Alice sends a purchase request. The controller receives it, the service checks whether the user exists and whether the app exists, then it validates whether the app has already been purchased. If everything is valid, the repository saves the purchase in the database. After that, the app appears in the purchased list and disappears from the available-app list for that user.

## Slide 8. REST API Design

Our API design follows the user journey very closely. We have endpoints for browsing all apps, filtering apps, checking available apps for a user, purchasing an app, and listing purchased apps. This helped us keep the API intuitive and directly connected to the user experience.

## Slide 9. Persistence and Testing

We use MySQL as our persistent storage, so the data remains available between application runs. We also prepared dummy data to simulate realistic app store behavior. For testing, we covered both normal and error scenarios, such as successful purchases, duplicate purchases, missing users, missing apps, and empty results.

## Slide 10. Future Ideas

If we had more time, we would like to expand both the product and the engineering side. On the product side, we could add reviews, recommendations, and authentication. On the technical side, we could add caching for repeated purchase checks, pagination, stronger exception handling, and more integration testing.

## Slide 11. Closing

To conclude, Spring Phone is a small but complete backend project. It is designed around a user journey and implemented with a clean three-layer architecture. Our goal was to make the system simple, personal, and easy to extend. Thank you.

