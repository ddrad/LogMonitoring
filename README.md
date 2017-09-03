<h3>Description:</h3>

This simple application contains three separated modules:
<ul>
<li>Parent</li>
<li>LogMonitorService</li>
<li>WebbApp</li>
</ul>
This application read log file and displays result on web page. Log file is read all times.
<h3>Configuration:</h3>
 You can define interval of time in milliseconds, for it you have to use file configuration:
webapp\src\main\resources\application.properties
Also you can define file path using the same file. 
For display result I used websocket. For process of read I used Spring Framework components such as spring-web, spring-mvc, pring-batch and et al.

<h3>Installation:</h3> 
For run this application using the following steps:
1. Download or checkout application from GitHub
2. Before you have to build Parent module using Maven. Go to Parent module and call command: 
<code>mvn clean install</code>
3. Then you have to go in root directory and call command again:
<code>mvn clean install</code>
4. Move log-monitoring-app.war to {tomcat home directory}/webapp directory. I used Apache Tomcat 8.5.8. 
5. Start your tomcat server
6. Open http://localhost:8080/log-monitoring-app/ui/index.html
7. Click Connect for connect with websocket and you can look at result
