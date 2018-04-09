db = db.getSiblingDB('recruiter');

db.user.drop();
db.jobposition.drop();

// https://bcrypt-generator.com/
// password - 123456
db.user.insert({
    _id: "user",
    password: "$2a$10$aS/lF2c/9JWPUjDHfJ/zTed1ihGBgfX/7xnGTOM5/lW59X4FHalSi",
    firstname: "user",
    lastname: "user",
    email: "user@user.com",
    enabled: true,
    lastPasswordResetDate: new Date(2018, 2, 23),
    authorities: ["ROLE_USER"]
});
// password - 123456
db.user.insert({
    _id: "moderator",
    password: "$2a$10$aS/lF2c/9JWPUjDHfJ/zTed1ihGBgfX/7xnGTOM5/lW59X4FHalSi",
    firstname: "mod",
    lastname: "mod",
    email: "mod@mod.com",
    enabled: true,
    lastPasswordResetDate: new Date(2018, 2, 23),
    authorities: ["ROLE_MODERATOR"]
});

// Stanowiska
db.jobposition.insert({
	_id: "java developer",
	active: true
});
db.jobposition.insert({
	_id: "c++ developer",
	active: false
});