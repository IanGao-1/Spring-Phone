INSERT INTO mobile_app (id, name, category, price, description) VALUES
(1, 'ChatWave', 'Social', 0.99, 'A lightweight messaging app for quick chats with friends and family.'),
(2, 'FitTrack Pro', 'Health', 3.99, 'Tracks steps, workouts, calories, and daily fitness goals.'),
(3, 'NoteNest', 'Productivity', 1.99, 'Capture notes, to-do lists, and study reminders in one place.'),
(4, 'Pixel Studio', 'Photography', 2.99, 'Apply creative filters and edit photos directly on your phone.'),
(5, 'Budget Buddy', 'Finance', 4.99, 'Manage expenses, monthly budgets, and savings targets.'),
(6, 'Sky Journey', 'Games', 2.49, 'A relaxing adventure game with puzzles and exploration.'),
(7, 'TuneBox', 'Music', 1.49, 'Discover playlists and organize your personal music library.'),
(8, 'Recipe Rush', 'Food', 0.79, 'Browse quick recipes and save your favorite meal ideas.'),
(9, 'LinguaSprint', 'Education', 5.99, 'Practice vocabulary and speaking drills for new languages.'),
(10, 'WeatherNow', 'Utilities', 0.00, 'Check real-time weather forecasts and severe weather alerts.'),
(11, 'TravelMap', 'Travel', 2.19, 'Plan trips, pin destinations, and store travel notes.'),
(12, 'Focus Timer', 'Productivity', 0.99, 'Use pomodoro sessions to stay focused while studying or working.'),
(13, 'CodeSnap', 'Education', 3.49, 'Solve short coding challenges and review programming basics.'),
(14, 'HomeChef', 'Food', 1.29, 'Plan weekly meals and organize shopping lists for home cooking.'),
(15, 'PocketPay', 'Finance', 2.79, 'Track bills, subscriptions, and payment reminders.'),
(16, 'ZenSpace', 'Health', 2.59, 'Follow breathing exercises and guided meditation sessions.'),
(17, 'GoalGrid', 'Productivity', 1.89, 'Set personal goals and visualize progress with simple boards.'),
(18, 'StreetBall', 'Games', 4.49, 'A fast arcade basketball game with online scoreboards.'),
(19, 'PhotoVault', 'Utilities', 1.99, 'Secure important images behind a private gallery lock.'),
(20, 'RoadScout', 'Travel', 3.19, 'Find nearby attractions, routes, and local travel tips.'),
(21, 'BeatForge', 'Music', 2.99, 'Create simple beats and loops from your phone.'),
(22, 'StudySprint', 'Education', 1.59, 'Build flashcards and timed quiz sessions for exam prep.'),
(23, 'PetCare Pal', 'Lifestyle', 0.89, 'Manage feeding schedules, vet visits, and pet reminders.'),
(24, 'StyleBoard', 'Lifestyle', 1.19, 'Save outfit ideas and build personal fashion mood boards.')
ON DUPLICATE KEY UPDATE
    name = VALUES(name),
    category = VALUES(category),
    price = VALUES(price),
    description = VALUES(description);

INSERT INTO store_user (id, username, email) VALUES
(1, 'alice', 'alice@example.com'),
(2, 'bob', 'bob@example.com'),
(3, 'charlie', 'charlie@example.com'),
(4, 'diana', 'diana@example.com'),
(5, 'eric', 'eric@example.com'),
(6, 'fiona', 'fiona@example.com'),
(7, 'george', 'george@example.com'),
(8, 'helen', 'helen@example.com')
ON DUPLICATE KEY UPDATE
    username = VALUES(username),
    email = VALUES(email);

INSERT INTO purchase (id, user_id, app_id, purchase_time) VALUES
(1, 1, 2, '2026-03-20 09:15:00'),
(2, 1, 3, '2026-03-20 09:20:00'),
(3, 1, 10, '2026-03-21 18:05:00'),
(4, 1, 17, '2026-03-24 20:10:00'),
(5, 2, 1, '2026-03-22 10:00:00'),
(6, 2, 6, '2026-03-22 10:12:00'),
(7, 2, 8, '2026-03-23 11:40:00'),
(8, 2, 18, '2026-03-25 13:05:00'),
(9, 3, 5, '2026-03-24 14:10:00'),
(10, 3, 9, '2026-03-24 14:25:00'),
(11, 3, 13, '2026-03-26 09:35:00'),
(12, 4, 4, '2026-03-25 16:30:00'),
(13, 4, 7, '2026-03-25 16:45:00'),
(14, 4, 12, '2026-03-26 08:05:00'),
(15, 5, 11, '2026-03-23 07:55:00'),
(16, 5, 20, '2026-03-23 08:10:00'),
(17, 5, 23, '2026-03-26 21:00:00'),
(18, 6, 14, '2026-03-21 19:20:00'),
(19, 6, 16, '2026-03-22 06:45:00'),
(20, 6, 24, '2026-03-24 22:10:00'),
(21, 7, 15, '2026-03-20 12:00:00'),
(22, 7, 19, '2026-03-25 17:40:00'),
(23, 8, 21, '2026-03-22 23:55:00'),
(24, 8, 22, '2026-03-26 10:50:00')
ON DUPLICATE KEY UPDATE
    user_id = VALUES(user_id),
    app_id = VALUES(app_id),
    purchase_time = VALUES(purchase_time);
