# UniversePl-server
Backend for UniversePl

Почти ко всем запросам вы должны передавать куки сессии для индификации пользователя
Для получение куков вы должны пройти по запросу /api/user/session

Получение токена для расшифровки сообщений конкретного чата.
Передается ID чата

POST http://localhost:8080/api/token/get
Cookie: session=123
Content-Type: application/json

{"id": "ff8081817de2e73c017de2e7a3bc0000"}


###

Cоздание токена для расшифровки сообщений конкретного чата.
Передается ID чата и сам токен

POST http://localhost:8080/api/token/create
Cookie: session=12321
Content-Type: application/json

{"token": "VeryLongToken", "chat": {"id": "ff8081817de2e73c017de2e7a3bc0000"}}


###

Получение зашифрованных сообщений конкретного чата.
Передается ID чата

POST http://localhost:8080/api/message/get
Cookie: session=123
Content-Type: application/json

{"id": "ff8081817de2e73c017de2e7a3bc0000"}


###

Cоздание зашифрованого сообщения сообщений конкретного чата.
Передается ID чата и зашифрованый текст сообщения

POST http://localhost:8080/api/message/create
Cookie: session=123
Content-Type: application/json

{ "text": "test2", "chat": {"id": "ff8081817de2e73c017de2e7a3bc0000"}}


###

Отправка публичного ключа для создания токена другому пользователю.
Передается ID чата и ключ

POST http://localhost:8080/api/chat/public_key
Cookie: session=12321
Content-Type: application/json

{"key":"test3", "chat":"ff8081817de2e73c017de2e7a3bc0000"}


###

Создание чата
Передается ID пользователя

POST http://localhost:8080/api/chat/create
Cookie: session=12321
Content-Type: application/json

{
  "users" :  [{"id":"ff8081817dde1968017dde197cc70000"}]
}

###

Получение всех чатов пользователя

POST http://localhost:8080/api/chat/get
Cookie: session=12321
Content-Type: application/json

{}

###

Получение куки сессии для индификации пользователя
Передается приватный зашифрованный токен и публичный код пользователя, созданный во время регистрации
Токен = Sha3(Публичный ключ + Cоль + Приватный ключ)

POST http://localhost:8080/api/user/session
Content-Type: application/json

{"tokenAccess":"sad", "publicKey":"1"}



###

Cоздание пользователя
Передается никнейм, приватный зашифрованный токен и публичный код пользователя, созданный во время регистрации
Токен = Sha3(Публичный ключ + Cоль + Приватный ключ)


POST http://localhost:8080/api/user/create
Content-Type: application/json

{"username":"pom", "tokenAccess":"sad", "publicKey":"1"}

###

Получение списка пользователей по нику
Передается часть никнейма 

POST http://localhost:8080/api/user/get
Cookie: session=12321
Content-Type: application/json

{ "username" :  "Po"}
