-- 1. List all apps ordered by name
SELECT id, name, category, price, description
FROM mobile_app
ORDER BY name;

-- 2. Search apps by partial name
SELECT id, name, category, price, description
FROM mobile_app
WHERE LOWER(name) LIKE LOWER('%fit%')
ORDER BY name;

-- 3. Filter apps by category
SELECT id, name, category, price, description
FROM mobile_app
WHERE category = 'Productivity'
ORDER BY name;

-- 4. Search by both name and category
SELECT id, name, category, price, description
FROM mobile_app
WHERE LOWER(name) LIKE LOWER('%note%')
  AND category = 'Productivity'
ORDER BY name;

-- 5. Apps available for a specific user
-- Example: user 1
SELECT a.id, a.name, a.category, a.price, a.description
FROM mobile_app a
WHERE NOT EXISTS (
    SELECT 1
    FROM purchase p
    WHERE p.user_id = 1
      AND p.app_id = a.id
)
ORDER BY a.name;

-- 6. Available apps for a user by category
SELECT a.id, a.name, a.category, a.price, a.description
FROM mobile_app a
WHERE a.category = 'Games'
  AND NOT EXISTS (
      SELECT 1
      FROM purchase p
      WHERE p.user_id = 1
        AND p.app_id = a.id
  )
ORDER BY a.name;

-- 7. Available apps for a user by name
SELECT a.id, a.name, a.category, a.price, a.description
FROM mobile_app a
WHERE LOWER(a.name) LIKE LOWER('%map%')
  AND NOT EXISTS (
      SELECT 1
      FROM purchase p
      WHERE p.user_id = 1
        AND p.app_id = a.id
  )
ORDER BY a.name;

-- 8. Purchase an app for a user
-- Example: user 1 buys app 11
INSERT INTO purchase (user_id, app_id, purchase_time)
SELECT 1, 11, NOW()
WHERE NOT EXISTS (
    SELECT 1
    FROM purchase
    WHERE user_id = 1
      AND app_id = 11
);

-- 9. List purchased apps for a specific user
SELECT p.id AS purchase_id,
       a.id AS app_id,
       a.name,
       a.category,
       a.price,
       p.purchase_time
FROM purchase p
JOIN mobile_app a ON a.id = p.app_id
WHERE p.user_id = 1
ORDER BY p.purchase_time DESC;

-- 10. Show how many apps each user purchased
SELECT u.id,
       u.username,
       COUNT(p.id) AS purchased_count
FROM store_user u
LEFT JOIN purchase p ON p.user_id = u.id
GROUP BY u.id, u.username
ORDER BY purchased_count DESC, u.username;

-- 11. Most popular apps
SELECT a.id,
       a.name,
       a.category,
       COUNT(p.id) AS purchase_count
FROM mobile_app a
LEFT JOIN purchase p ON p.app_id = a.id
GROUP BY a.id, a.name, a.category
ORDER BY purchase_count DESC, a.name;

-- 12. Revenue by category
SELECT a.category,
       ROUND(SUM(a.price), 2) AS total_revenue
FROM purchase p
JOIN mobile_app a ON a.id = p.app_id
GROUP BY a.category
ORDER BY total_revenue DESC;
