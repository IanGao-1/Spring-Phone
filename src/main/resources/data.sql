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
(12, 'Focus Timer', 'Productivity', 0.99, 'Use pomodoro sessions to stay focused while studying or working.')
ON DUPLICATE KEY UPDATE
    name = VALUES(name),
    category = VALUES(category),
    price = VALUES(price),
    description = VALUES(description);

INSERT INTO store_user (id, username, email) VALUES
(1, 'alice', 'alice@example.com'),
(2, 'bob', 'bob@example.com'),
(3, 'charlie', 'charlie@example.com'),
(4, 'diana', 'diana@example.com')
ON DUPLICATE KEY UPDATE
    username = VALUES(username),
    email = VALUES(email);

INSERT INTO purchase (id, user_id, app_id, purchase_time) VALUES
(1, 1, 2, '2026-03-20 09:15:00'),
(2, 1, 3, '2026-03-20 09:20:00'),
(3, 1, 10, '2026-03-21 18:05:00'),
(4, 2, 1, '2026-03-22 10:00:00'),
(5, 2, 6, '2026-03-22 10:12:00'),
(6, 2, 8, '2026-03-23 11:40:00'),
(7, 3, 5, '2026-03-24 14:10:00'),
(8, 3, 9, '2026-03-24 14:25:00'),
(9, 4, 4, '2026-03-25 16:30:00'),
(10, 4, 7, '2026-03-25 16:45:00'),
(11, 4, 12, '2026-03-26 08:05:00')
ON DUPLICATE KEY UPDATE
    user_id = VALUES(user_id),
    app_id = VALUES(app_id),
    purchase_time = VALUES(purchase_time);
