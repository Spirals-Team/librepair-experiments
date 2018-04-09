@echo off
docker run --name recruiter_db -d -p 27017:27017 recruiter_mongo
PAUSE
@echo on