# Secure REST API

To test this sample application you must:

1. Open an API platform (like Postman)
2. Authenticate (POST method)
    - Endpoint: **localhost:8080/user**
    - User: **elpepe**
    - Password: **pass1234**
3. Get the **JWT** token
4. Place the token in the **Authentication** header of the request you wish to make.
5. You can now access the endpoints (GET method)
    - **localhost:8080/players**
    - **localhost:8080/teams**