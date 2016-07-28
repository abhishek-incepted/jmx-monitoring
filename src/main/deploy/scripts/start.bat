echo 'starting JMX SERVER'

java -XX:MaxPermSize=512m -XX:-HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/var/log/jmx-monitoring -XX:-PrintGCDetails -XX:+PrintGCTimeStamps -Xms1024m -Xmx4096m -Dmonitoring.jmx.server.host=localhost -Dmonitoring.jmx.server.port=50421 -Dapplication.properties=jmx.properties -cp lib\* com.ndyatech.jmx.monitoring.rest.services.JMXMonitoringStartup

echo 'started JMX SERVER'
