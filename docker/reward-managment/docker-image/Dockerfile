FROM  XXXX
RUN mkdir -p /usr/stellar
RUN mkdir /usr/stellar/resources
WORKDIR /usr/stellar
COPY entrypoint.sh /usr/stellar/
COPY reward-managment.jar /usr/stellar/
RUN /usr/sbin/adduser --disabled-password --gecos "" -u 500 test
ENV LOG_LEVEL=INFO
ENV LOG_MAX_FILE_SIZE=25MB
ENV LOG_MAX_HISTORY=5
ENV LOG_TOTAL_SIZE_CAP=500MB
RUN chmod 755 /usr/stellar/entrypoint.sh
ENTRYPOINT ["/usr/stellar/entrypoint.sh"]
