Run with Docker

docker run -e DATABASE_URL="jdbc:postgresql://<ip>:<port>/<database>" \
           -e DATABASE_USERNAME="<username>" \
           -e DATABASE_PASSWORD="<password>" \
           -e USERS_URL="http://<ip>:<port>/auth/confirm"
           -p 8081:8081 \
           bernardosousa03/orders:latest