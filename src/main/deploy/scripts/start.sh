echo 'starting JMX SERVER'

nohup /etc/alternatives/java_sdk/bin/java -XX:MaxPermSize=512m -XX:-HeapDumpOnOutOfMemoryError -XX:-PrintGCDetails -XX:+PrintGCTimeStamps -Xms1024m -Xmx2048m -Dindices.jmx.server.host=localhost -Dindices.jmx.server.port=50420 -Dapplication.properties=jmx.properties -cp lib/jmx-monitoring.jar:lib/* JMXMonitoringStartup > analytics_log.out &

echo 'started JMX SERVER'