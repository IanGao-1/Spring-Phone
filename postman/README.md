# Postman Files

Import these two files into Postman:

- `Spring-Phone-App-Store.postman_collection.json`
- `Spring-Phone-Local.postman_environment.json`

Recommended run order:

1. `App Browse`
2. `Available Apps`
3. `Purchases`
4. `Suggested Flow`

If you want to verify the duplicate-purchase case, run:

1. `Purchase App 11 For User 1`
2. `Purchase Same App Again For User 1`
