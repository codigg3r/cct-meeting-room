# Meeting Room Spring Boot REST API
##### Java based meeting room scheduler Rest API implementation.
----
## API reference
- GET /getrooms
  * `Require Auth`
- GET /reservations
  * `Require Auth`
- GET /avaiblerooms/{from}/{to}
  * `Require Auth`
- POST /createroom
  * `Require Auth`
  * `Content-Type: application/json`
  * `BODY-> {"name": "roomName","adress": "roomAdress"}`
- POST /createreserve
  * `Require Auth`
  * `Content-Type: application/json`
  * `BODY-> {"roomid": 10,"createdby": "testUser", "fromdate": 20190917, "todate": 20190918, "title": "Interview with sukru polat"}`
- POST /cancelreserve
  * `Require Auth`
  * `Content-Type: application/json`
  * `BODY-> {"id": 22,roomid": 10,"createdby": "testUser", "fromdate": 20190917, "todate": 20190918, "title": "Interview with sukru polat"}`
- POST /authenticate
  * `{"username":"testUser","password":"testPass"}`
  * `Content-Type: application/json`
- POST /register
  * `{"username":"testUser","password":"testPass"}`
  * `Content-Type: application/json`
---
This project secured with JWT authentication. I have used [tutorial-1](https://www.javainuse.com/spring/boot-jwt) and [tutorial-2](https://www.javainuse.com/spring/boot-jwt-mysql) to this achive secure layer.Big Thanks to javainuse.

#### powered by codigger.
----
