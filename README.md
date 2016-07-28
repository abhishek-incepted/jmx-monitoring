# jmx-monitoring

This project is an effort to monitor java applications and show memory, gc, thread charts along with thread-stack and, push monitoring-events to clients(over HTTP) connected to it. Internally, this application makes only a single connection to live-java-application and, pushes the monitoring details to all clients connected to it using SSE mechanism. Although, JMX tools are available within java sdk to monitor java applications. This application tries to fill the gap where using traditional JMX tool, each client would connect to live-java-application by making dedicated thread in its jvm, which would burden the jvm in case multiple clients connect to it using traditional JMX tool.

# Snapshots
// TODO

# How to run
1. Build the project
2. Unzip jmx-monitoring-dist.zip which should now be under target\dist. (Copy the zip to any path you like)
3. Edit -Dmonitoring.jmx.server.host and, -Dmonitoring.jmx.server.port in start.sh/start.bat placed under \target\dist\jmx-monitoring. (The host is the place where the java-application which you want to monitor is running)
4. Run script start.sh or, .bat file per environment.
5. Once your script is up and running - open http://localhost:8016

#Known issues:
- Currently, hardcoded to run on port 8016.
- Reconnection issues seen sometimes.
- Data(pushed to client) not being cleared as of now.
- Time-lines to show better coverage
- Naming of x/y axis
