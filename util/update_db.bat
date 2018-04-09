@echo off
docker cp create_db.js recruiter_db:/data/create_db.js
docker exec -it recruiter_db mongo /data/create_db.js
pause
@echo on