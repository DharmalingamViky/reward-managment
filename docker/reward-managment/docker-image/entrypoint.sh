#!/bin/sh
if [ "$JDWP" == true ]; then
    java -agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=n -Djava.security.egd=file:/dev/./urandom -Xms${Xms_SIZE} -Xmx${Xmx_SIZE} -Dlog.level.service=${LOG_LEVEL_SERVICE} -Dlog.max.file.size=${LOG_MAX_FILE_SIZE} -Dlog.max.history=${LOG_MAX_HISTORY} -Dlog.total.size.cap=${LOG_TOTAL_SIZE_CAP} -jar /usr/stellar/reward-managment.jar
else
    java -XX:+UseG1GC -Xms${Xms_SIZE} -Xmx${Xmx_SIZE} -Dlog.level.service=${LOG_LEVEL_SERVICE} -Dlog.max.file.size=${LOG_MAX_FILE_SIZE} -Dlog.max.history=${LOG_MAX_HISTORY} -Dlog.total.size.cap=${LOG_TOTAL_SIZE_CAP} -jar /usr/stellar/reward-managment.jar
fi