# Overview

This is a Java client that can track and report details of a demo/tutorial that has been deployed to Cloud Foundry.

# To Use

## Reference the library in your project

To take advantage if the tracking functionality in a web application:

### With Maven

  ```xml
  <dependency>
    <groupId>com.ibm.websphere.appserver.api</groupId>
    <artifactId>com.ibm.websphere.appserver.api.json</artifactId>
    <version>1.0.11</version>
  </dependency>
  <dependency>
    <groupId>com.ibm.bluemix.deploymenttracker</groupId>
    <artifactId>cf-java-app-tracker-client</artifactId>
    <version>0.1.0</version>
  </dependency>
  ```

### Or Download/Build it yourself

1. Obtain the tracker client library
  * From the [Maven Central Repository](http://search.maven.org/#search|ga|1|g%3A%22com.ibm.bluemix.deploymenttracker%22%20AND%20a%3A%22cf-java-app-tracker-client%22) (groupId **com.ibm.bluemix.deploymenttracker**, artifactId **cf-java-app-tracker-client**) *or*
 * Download the source code from https://github.com/IBM-Bluemix/cf-deployment-tracker-client-java and build the JAR using the default target/goal of the provided ant or pom script.
2. Copy client library `cf-java-app-tracker-client-{version}.jar` to your web application's `WebContent/WEB-INF/lib` directory.

## Provide information about your application

1. Create a `package.json` file in the web applications' `WebContent/META-INF` directory. Replace the application specific information in the example below with your application's information.

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

## Add a Privacy Notice

Add the following to your application README.md:

```
# Privacy Notice
Sample web applications that include this tracking library may be configured to track
deployments to [IBM Bluemix](https://www.bluemix.net/) and other Cloud Foundry platforms.
The following information is sent to a [Deployment Tracker](https://github.com/IBM-Bluemix/cf-deployment-tracker-service)
service on each deployment by default:
* Application Name (`application_name`)
* Space ID (`space_id`)
* Application Version (`application_version`)
* Application URIs (`application_uris`)

This data is collected from the `VCAP_APPLICATION` environment variable in IBM Bluemix 
and other Cloud Foundry platforms. This data is used by IBM to track metrics around 
deployments of sample applications to IBM Bluemix to measure the usefulness of our examples,
so that we can continuously improve the content we offer to you. 

The following additional information will be sent in addition if the following are defined
as described in the usage notes below:
* Source code download/issue URL (`url`)
* Source code version (`version`)

# Disabling  Deployment Tracking

To disable deployment tracking remove cf-java-app-tracker-client-{version}.jar
from the web application's `WebContent/WEB-INF/lib` directory or from the pom.xml
and redeploy the application.
```

**Note:** All applications that use the deployment tracker must have a Privacy Notice.

## Package and deploy your web application

To view basic information about the tracker client and the most recent
tracking request direct your browser to `<APPLICATION_URL>/comibmbluemix/CFAppTracker`,
replacing `<APPLICATION_URL>` with the web application URL.

# Example app

[This project](https://github.com/IBM-Bluemix/local-liberty-tutorial) uses the deployment tracker library.
You can look at [its pom.xml](https://github.com/IBM-Bluemix/local-liberty-tutorial/blob/master/pom.xml),
[its package.json](https://github.com/IBM-Bluemix/local-liberty-tutorial/blob/master/src/main/webapp/META-INF/package.json)
and [its privacy notice](https://github.com/IBM-Bluemix/local-liberty-tutorial#privacy-notice).

# License

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
