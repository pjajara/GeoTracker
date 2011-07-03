

Softwares Required
----------------------------------
1. JDK 1.6 or higher
2. Jboss 6.0.0
3. Mysql 5.1 or higher
4. Apache Ant 1.8.2
5. Junit 4.8.x

Configuration Steps
------------------------------------

1. Set JAVA_HOME, JBOSS_HOME and ANT_HOME environment variables pointing to the directory where they are installed.

2. Set path to bin directory located inside ant installation folder.

3. Extract the project folder.


4. Go to Project directory:   project directory\lib

	download & Paste the jar file for junit.


5. Go to this directory:  \jboss-6.0.0.GA\server\default\conf and open standardjbosscmp-jdbc.xml file in notepad.

	Make sure the <datasoruce> & <datasource-mapping> elements have following values:-

	<datasource>java:/DefaultDS</datasource>
	<datasource-mapping>Hypersonic SQL</datasource-mapping> 

6. Create a queue "VtaQueue" in Jboss directory/server/default/deploy/jms/hornetq-jms.xml file by pasting following code before </configuration> tag:
	
	<queue name="VtaQueue">
	
	<entry name="/queue/VtaQueue" />

	</queue>
  

7. Start JBOSS Server

8. Open command promt and execute below mentioned commands:
   
   :> go till project directory
   :> ant clean
   :> ant build 	 	
   :> ant deploy
   :> ant loadroute
   :> ant loaddata
   :> ant client (KML file generated into the KML folder of project)	
 

