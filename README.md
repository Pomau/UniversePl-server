# UniversePl-server
Backend for UniversePl
POST http://localhost:8080/api/token/get
Cookie: session=123
Content-Type: application/json

{"id": "ff8081817de2e73c017de2e7a3bc0000"}


###
POST http://localhost:8080/api/token/create
Cookie: session=12321
Content-Type: application/json

{"token": "VeryLongToken", "chat": {"id": "ff8081817de2e73c017de2e7a3bc0000"}}


###

POST http://localhost:8080/api/message/get
Cookie: session=123
Content-Type: application/json

{"id": "ff8081817de2e73c017de2e7a3bc0000"}


###
POST http://localhost:8080/api/message/create
Cookie: session=123
Content-Type: application/json

{ "text": "test2", "chat": {"id": "ff8081817de2e73c017de2e7a3bc0000"}}


###
POST http://localhost:8080/api/chat/public_key
Cookie: session=12321
Content-Type: application/json

{"key":"test3", "chat":"ff8081817de2e73c017de2e7a3bc0000"}


###
POST http://localhost:8080/api/chat/create
Cookie: session=12321
Content-Type: application/json

{
  "users" :  [{"id":"ff8081817dde1968017dde197cc70000"}]
}

###
POST http://localhost:8080/api/chat/get
Cookie: session=12321
Content-Type: application/json

{}

###
POST http://localhost:8080/api/user/session
Content-Type: application/json

{"tokenAccess":"sad", "publicKey":"1"}



###
POST http://localhost:8080/api/user/create
Content-Type: application/json

{"username":"pom", "tokenAccess":"sad", "publicKey":"1"}

###
POST http://localhost:8080/api/user/get
Cookie: session=12321
Content-Type: application/json

{ "username" :  "Po"}
