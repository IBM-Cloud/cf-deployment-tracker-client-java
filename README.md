# Overview

This is a Java class that can track and report details of a demo/tutorial that has been deployed to Cloud Foundry

# To Use

1. Download the Cloud Foundry Java application tracker client code from https://github.com/IBM-Bluemix/cf-deployment-tracker-client-java
2. Build the utlity JAR file (cf-java-app-tracker-client_{version}.jar) by running the provided ant script
3. Copy the generated utility JAR (target/cf-java-app-tracker-client_{version}.jar) to your existing WebContent/WEB-INF/lib directory
4. Add the following lines in your existing WebContent/WEB-INF/web.xml
  <servlet>
    <servlet-name>CFJavaTrackerClient</servlet-name>
  	<servlet-class>com.ibm.sample.CFJavaTrackerServlet</servlet-class>
    <load-on-startup>2</load-on-startup>
  </servlet>
5. Re-build, package and deploy your web application.

# Example app
No example application is currently available.

# Privacy Notice

Sample web applications that include this servlet may be configured to track deployments to [IBM Bluemix](https://www.bluemix.net/) and other Cloud Foundry platforms. The following information is sent to a [Deployment Tracker](https://github.com/IBM-Bluemix/cf-deployment-tracker-service) service on each deployment:
* Application Name (`application_name`)
* Space ID (`space_id`)
* Application Version (`application_version`)
* Application URIs (`application_uris`)

This data is collected from the `VCAP_APPLICATION` environment variable in IBM Bluemix and other Cloud Foundry platforms. This data is used by IBM to track metrics around deployments of sample applications to IBM Bluemix to measure the usefulness of our examples, so that we can continuously improve the content we offer to you. Only deployments of sample applications that include code to ping the Deployment Tracker service will be tracked.

## Disabling or customizing Deployment Tracking
To disable deployment tracking remove the CFJavaTrackerClient servlet entry from your web.xml.
