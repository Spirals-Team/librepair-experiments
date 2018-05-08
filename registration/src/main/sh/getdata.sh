#curl -u user:password -i -X POST -H "Content-Type: application/json; charset=utf-8" -T participants.json http://localhost:8080/frinex/participants

wget -O screenviews.json "http://ems12.mpi.nl:8080/registration-0.1.26-pretesting/screenviews?size=2000"
wget -O participants.json "http://ems12.mpi.nl:8080/registration-0.1.26-pretesting/participants?size=2000"
wget -O timestamps0.json "http://ems12.mpi.nl:8080/registration-0.1.26-pretesting/timestamps?page=0&size=2000"
wget -O timestamps1.json "http://ems12.mpi.nl:8080/registration-0.1.26-pretesting/timestamps?page=1&size=2000"
wget -O timestamps2.json "http://ems12.mpi.nl:8080/registration-0.1.26-pretesting/timestamps?page=2&size=2000"
wget -O timestamps3.json "http://ems12.mpi.nl:8080/registration-0.1.26-pretesting/timestamps?page=3&size=2000"
wget -O timestamps4.json "http://ems12.mpi.nl:8080/registration-0.1.26-pretesting/timestamps?page=4&size=2000"
wget -O timestamps5.json "http://ems12.mpi.nl:8080/registration-0.1.26-pretesting/timestamps?page=5&size=2000"
wget -O tagevents0.json "http://ems12.mpi.nl:8080/registration-0.1.26-pretesting/tagevents?page=0&size=2000"
wget -O tagevents1.json "http://ems12.mpi.nl:8080/registration-0.1.26-pretesting/tagevents?page=1&size=2000"
wget -O tagevents2.json "http://ems12.mpi.nl:8080/registration-0.1.26-pretesting/tagevents?page=2&size=2000"
wget -O tagevents3.json "http://ems12.mpi.nl:8080/registration-0.1.26-pretesting/tagevents?page=3&size=2000"
wget -O tagevents4.json "http://ems12.mpi.nl:8080/registration-0.1.26-pretesting/tagevents?page=4&size=2000"
wget -O tagevents5.json "http://ems12.mpi.nl:8080/registration-0.1.26-pretesting/tagevents?page=5&size=2000"





