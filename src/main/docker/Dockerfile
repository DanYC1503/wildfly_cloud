# Base image
FROM quay.io/wildfly/wildfly:30.0.1.Final-jdk17

# Environment variables
ENV WILDFLY_USER=user
ENV WILDFLY_PASS=userPassword 
ENV DS_NAME=Cloud_SQL 
ENV DS_USER=postgres 
ENV DS_PASS=dan 
ENV DS_URI=jdbc:postgresql://10.128.0.4:5432/Docker_SQL
ENV DEPLOYMENT_DIR=$JBOSS_HOME/standalone/deployments


# Set JBOSS_CLI environment variable
ENV JBOSS_CLI=$JBOSS_HOME/bin/jboss-cli.sh

# Add WildFly administrator
RUN $JBOSS_HOME/bin/add-user.sh -u $WILDFLY_USER -p $WILDFLY_PASS --silent



# Configure Wildfly server and install necessary extensions
RUN echo "Starting WildFly server" && \
	  bash -c '$JBOSS_HOME/bin/standalone.sh -c standalone.xml -Djboss.as.management.blocking.timeout=1000 &' && \ 
      bash -c '$JBOSS_HOME/bin/standalone.sh -c standalone.xml &' && \
      bash -c 'until `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null | grep -q running`; do echo `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null`; sleep 1; done' && \
      curl --location --output /tmp/postgresql-42.7.3.jar --url https://jdbc.postgresql.org/download/postgresql-42.7.3.jar && \
      $JBOSS_CLI --connect --command="/extension=org.wildfly.extension.microprofile.openapi-smallrye:add" && \
      $JBOSS_CLI --connect --command="/subsystem=microprofile-openapi-smallrye:add" && \
      $JBOSS_CLI --connect --command="/subsystem=undertow/server=default-server/ajp-listener=ajp-listener:add(socket-binding=ajp, scheme=https, enabled=true)" && \
      $JBOSS_CLI --connect --command="/subsystem=undertow:write-attribute(name=statistics-enabled,value=true)" && \
      $JBOSS_CLI --connect --command="module add --name=org.postgres --resources=/tmp/postgresql-42.7.3.jar --dependencies=javax.api,javax.transaction.api" && \
      $JBOSS_CLI --connect --command="/subsystem=datasources/jdbc-driver=postgres:add(driver-name=postgres,driver-module-name=org.postgres,driver-class-name=org.postgresql.Driver)" && \
      $JBOSS_CLI --connect --command="data-source add \
        --name=${DS_NAME} \
        --jndi-name=java:jboss/datasources/${DS_NAME} \
        --user-name=${DS_USER} \
        --password=${DS_PASS} \
        --driver-name=postgres \
        --connection-url=${DS_URI} \
        --min-pool-size=10 \
        --max-pool-size=20 \
        --blocking-timeout-wait-millis=5000 \
        --statistics-enabled=true \
        --enabled=true" && \
      $JBOSS_CLI --connect --command=":shutdown" && \
      rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history/ $JBOSS_HOME/standalone/log/* && \
      rm -f /tmp/*.jar

COPY maven/Cloud_project.war ${JBOSS_HOME}/standalone/deployments/

EXPOSE 8080
EXPOSE 8009
EXPOSE 9990
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-c", "standalone.xml", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
