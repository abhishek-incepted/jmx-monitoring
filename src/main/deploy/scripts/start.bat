echo 'starting JMX SERVER'

java -XX:MaxPermSize=512m -XX:-HeapDumpOnOutOfMemoryError -XX:-PrintGCDetails -XX:+PrintGCTimeStamps -Xms1024m -Xmx4096m -Dindices.jmx.server.host=localhost -Dindices.jmx.server.port=50421 -Dapplication.properties=jmx.properties -cp lib\* JMXMonitoringStartup

echo 'started JMX SERVER'