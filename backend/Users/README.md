Run with Docker

docker run -e DATABASE_URL="jdbc:postgresql://<ip>:<port>/<database>" \
           -e DATABASE_USERNAME="<username>" \
           -e DATABASE_PASSWORD="<password>" \
           -p 8080:8080 \
           bernardosousa03/users:latest