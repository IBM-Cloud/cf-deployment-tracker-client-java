# Overview

This is a Java client that can track and report details of a demo/tutorial that has been deployed to Cloud Foundry.

# Privacy Notice
Sample web applications that include this tracking library may be configured to track deployments to [IBM Bluemix](https://www.bluemix.net/) and other Cloud Foundry platforms. The following information is sent to a [Deployment Tracker](https://github.com/IBM-Bluemix/cf-deployment-tracker-service) service on each deployment by default:
* Application Name (`application_name`)
* Space ID (`space_id`)
* Application Version (`application_version`)
* Application URIs (`application_uris`)

This data is collected from the `VCAP_APPLICATION` environment variable in IBM Bluemix and other Cloud Foundry platforms. This data is used by IBM to track metrics around deployments of sample applications to IBM Bluemix to measure the usefulness of our examples, so that we can continuously improve the content we offer to you. 

The following additional information will be sent in addition if the following are defined as described in the usage notes below:
* Source code download/issue URL (`repository_url`)
* Source code version (`code_version`)

# To Use

To take advantage if the tracking functionality in a web application:

1. Download the Cloud Foundry Java application tracker client code from https://github.com/IBM-Bluemix/cf-deployment-tracker-client-java
2. Build the utlity JAR file (cf-java-app-tracker-client-{version}.jar) by running the provided ant script
3. Copy the generated utility JAR (target/cf-java-app-tracker-client_{version}.jar) to your web application's WebContent/WEB-INF/lib directory
4. Create a `package.json` file in the web applications' WebContent/META-INF directory. Replace the application specific information in the example below with your application's information.

  ````
   {
     "name": "talent-manager",
     "version": "1.0.0",
     "repository": {
        "type": "git",
        "url": "https://github.com/IBM-Bluemix/talent-manager.git"
     }
   }
  ````
5. Package the web application
6. Deploy the web application

To view basic information about the tracker client and the most recent tracking request direct your browser to `<APPLICATION_URL>/comibmbluemix/CFAppTracker`, replacing `<APPLICATION_URL>` with the web application URL.

# Example app
No example application is currently available.


## Disabling  Deployment Tracking
To disable deployment tracking remove cf-java-app-tracker-client-{version}.jar from the web application's WebContent/WEB-INF/lib directory and redeploy the application.