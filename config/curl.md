### Test AdminRestController (application deployed in application context `votingforrestaurant`).
> For windows use `Git Bash`

#### get All Users
`curl -s http://localhost:8080/rest/admin/users --user admin@yandex.ru:admin`

#### get Users 100001
`curl -s http://localhost:8080/rest/admin/users/100001 --user admin@yandex.ru:admin`

#### create User
`curl -s -X POST -d '{"name": "New2","email":"newEmail@yandex.ru","password": "passwordNew","roles": ["ROLE_USER"]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/users --user admin@yandex.ru:admin`

#### delete User 100000
`curl -s -X DELETE http://localhost:8080/rest/admin/users/100000 --user admin@yandex.ru:admin`

#### update User 100001
`curl -s -X PUT -d '{"name": "UserUpdated","email":"newEmail@yandex.ru","password": "passwordNew","roles": ["ROLE_USER"]}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/users/100001 --user admin@yandex.ru:admin`

### Test ProfileRestController

#### get Profile
`curl -s http://localhost:8080/rest/profile --user user@yandex.ru:password`

#### delete Profile
`curl -s -X DELETE http://localhost:8080/rest/profile --user user@yandex.ru:password`

#### update Profile
`curl -s -X PUT -d '{"name": "UserUpdated","email":"a@ya.ru","password": "passwordNew","roles": ["ROLE_USER"]}' -H 'Content-Type: application/json' http://localhost:8080/rest/profile --user user@yandex.ru:password`

### Test AdminRestaurantRestController

#### get Restaurant 100002
`curl -s http://localhost:8080/rest/admin/restaurants/100002 --user admin@yandex.ru:admin`

#### get All Restaurants
`curl -s http://localhost:8080/rest/admin/restaurants --user admin@yandex.ru:admin`

#### create Restaurant
`curl -s -X POST -d '{"name": "New123"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/restaurants --user admin@yandex.ru:admin`

#### update Restaurant 100002
`curl -s -X PUT -d '{"name": "New777"}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/restaurants/100002 --user admin@yandex.ru:admin`

#### delete Restaurant 100002
`curl -s -X DELETE http://localhost:8080/rest/admin/restaurants/100002 --user admin@yandex.ru:admin`

### Test ProfileRestaurantRestController

#### get Restaurant 100002
`curl -s http://localhost:8080/rest/profile/restaurants/100002 --user user@yandex.ru:password`

#### get All Restaurants
`curl -s http://localhost:8080/rest/profile/restaurants --user user@yandex.ru:password`

### Test AdminMenuRestController

#### get Menu 100007
`curl -s http://localhost:8080/rest/admin/menus/100007?restId=100002 --user admin@yandex.ru:admin`

#### get All Menus for Restaurant 100002
`curl -s http://localhost:8080/rest/admin/menus?restId=100002 --user admin@yandex.ru:admin`

#### delete Menu 100007
`curl -s -X DELETE http://localhost:8080/rest/admin/menus/100007?restId=100002 --user admin@yandex.ru:admin`

#### get todays Menu with meals for Restaurant 100002
`curl -s http://localhost:8080/rest/admin/menus/todays?restId=100002 --user admin@yandex.ru:admin`

#### create Menu
`curl -s -X POST -d '{"addDate": "2015-06-01T10:00"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/menus?restId=100002 --user admin@yandex.ru:admin`

#### update Menu 100007
`curl -s -X PUT -d '{"addDate": "2017-06-01T10:00"}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/menus/100007?restId=100002 --user admin@yandex.ru:admin`

### Test ProfileMenuRestController

#### get Menu 100007
`curl -s http://localhost:8080/rest/profile/menus/100007?restId=100002 --user user@yandex.ru:password`

#### get All Menus for Restaurant 100002
`curl -s http://localhost:8080/rest/profile/menus?restId=100002 --user user@yandex.ru:password`

#### get todays Menu with meals for Restaurant 100002
`curl -s http://localhost:8080/rest/profile/menus/todays?restId=100002 --user user@yandex.ru:password`

### Test AdminMealRestController

#### get Meal 100014
`curl -s http://localhost:8080/rest/admin/meals/100014?menuId=100007 --user admin@yandex.ru:admin`

#### get All Meals for Menu 100007
`curl -s http://localhost:8080/rest/admin/meals?menuId=100007 --user admin@yandex.ru:admin`

#### create Meal
`curl -s -X POST -d '{"name": "New123","price":"123"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/meals?menuId=100007 --user admin@yandex.ru:admin`

#### update Meal 100014
`curl -s -X PUT -d '{"name": "New777","price":"777"}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/meals/100014?menuId=100007 --user admin@yandex.ru:admin`

#### delete Meal 100014
`curl -s -X DELETE http://localhost:8080/rest/admin/meals/100014?menuId=100007 --user admin@yandex.ru:admin`

### Test ProfileMealRestController

#### get Meal 100014
`curl -s http://localhost:8080/rest/profile/meals/100014?menuId=100007 --user user@yandex.ru:password`

#### get All Meals for Menu 100007
`curl -s http://localhost:8080/rest/profile/meals?menuId=100007 --user user@yandex.ru:password`

### Test AdminVoteRestController

#### get Vote 100021
`curl -s http://localhost:8080/rest/admin/votes/100021 --user admin@yandex.ru:admin`

#### get All Votes for all
`curl -s http://localhost:8080/rest/admin/votes --user admin@yandex.ru:admin`

#### create Vote for Restaurant 100002 and ADMIN
`curl -s -X POST -d '{"user":{"id":100001,"name": "Admin","email":"admin@yandex.ru","password": "password",
"registered":"2018-04-10T19:07:31.749+0000","roles": ["ROLE_ADMIN"],"votes": null},"date": "2018-04-10","restaurant": 
{"id": 100002,"name": "Russia","menus": null,"votes": null}}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/votes?restId=100002 --user admin@yandex.ru:admin`

#### update Vote 100021
`curl -s -X PUT -d '{"user":{"id":100001,"name": "Admin","email":"admin@yandex.ru","password": "password",
                    "registered":"2018-04-10T19:07:31.749+0000","roles": ["ROLE_ADMIN"],"votes": null},"date": "2017-04-10","restaurant": 
                    {"id": 100003,"name": "Ukraine","menus": null,"votes": null}}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/votes/100021?restId=100003 --user admin@yandex.ru:admin`

#### update Vote 100021 - Exception
`curl -s -X PUT -d '{"user":{"id":100001,"name": "Admin","email":"admin@yandex.ru","password": "password",
                    "registered":"2018-04-10T19:07:31.749+0000","roles": ["ROLE_ADMIN"],"votes": null},"date": "2018-04-10","restaurant": 
                    {"id": 100003,"name": "Ukraine","menus": null,"votes": null}}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/votes/100021?restId=100003 --user admin@yandex.ru:admin`

#### delete Vote 100021
`curl -s -X DELETE http://localhost:8080/rest/admin/votes/100021 --user admin@yandex.ru:admin`

### Test ProfileVoteRestController

#### create Vote for Restaurant 100002 and USER
`curl -s -X POST -d '{"user":{"id":100000,"name": "User","email":"user@yandex.ru","password": "password",
"registered":"2018-04-10T19:07:31.749+0000","roles": ["ROLE_USER"],"votes": null},"date": "2018-04-10","restaurant": 
{"id": 100002,"name": "Russia","menus": null,"votes": null}}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/votes?restId=100002 --user admin@yandex.ru:admin`
