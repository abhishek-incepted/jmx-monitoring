echo 'starting JMX SERVER'

nohup /etc/alternatives/java_sdk/bin/java -XX:MaxPermSize=512m -XX:-HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/var/log/jmx-monitoring -XX:-PrintGCDetails -XX:+PrintGCTimeStamps -Xms1024m -Xmx2048m -Dmonitoring.jmx.server.host=localhost -Dmonitoring.jmx.server.port=50420 -Dapplication.properties=jmx.properties -cp lib/jmx-monitoring.jar:lib/* com.ndyatech.jmx.monitoring.rest.services.JMXMonitoringStartup > jmx_monitoring.log &

echo 'started JMX SERVER'
