# sjbit

Generated with the [_Knot.x Extension Maven Archetype_](https://github.com/Knotx/knotx-extension-archetype).

Check out the [Knot.x Wiki](https://github.com/Cognifide/knotx/wiki/Knot) for more information 
about the Knot concept and APIs used here.


To run the extension:

1. [Download the Knot.x fat jar](https://oss.sonatype.org/content/groups/public/io/knotx/knotx-standalone/1.1.2/knotx-standalone-1.1.2.fat.jar). 
2. Copy it to the `apps` folder relative to this `README.md` file.
3. Build the extension using `mvn clean install`
4. Copy the fat jar from the `target` directory into the `apps` directory
5. Execute the `run.sh` bash script.


The project follows the following logical structure:

```
├── app (move executable jars here)
|
├── src
│   ├── main
│   |   ├── java
|   |   |     ├── com.flipkart
|   |   |            ├── knot (custom knot)
|   |   |
│   |   ├── resources (Additional Knot.x configuration files)
|   |
│   ├── test
│       ├── java (java test classes)
│       ├── resources (test resources)
|
├── knotx-standalone.json (Knot.x configuration)
├── knotx-standalone.logback.xml (Logging configuration)
├── run.sh (startup script)
├── pom.xml (Project Object Model for the extension)
├── README.md (this file)
```


When you run `run.sh` you will see output similar to the following:
```
2017-08-06 21:55:19 [vert.x-eventloop-thread-1] INFO  io.knotx.server.KnotxServerVerticle - Knot.x HTTP Server started. Listening on port 8092
2017-08-06 21:55:19 [vert.x-eventloop-thread-0] INFO  i.k.launcher.KnotxStarterVerticle - Knot.x STARTED

                Deployed 40d9256e-0603-4337-91e2-4a7cdb4bbe77 [knotx:io.knotx.FilesystemRepositoryConnector]
                Deployed c1b9ccd5-2f7d-41d6-919a-4c738090c698 [knotx:io.knottest.knot.example.ExampleKnot]
                Deployed a6c55387-48e5-489c-8fca-226ec223c882 [knotx:io.knotx.FragmentAssembler]
                Deployed 77488430-236a-4795-91ec-226e22b810e4 [knotx:io.knotx.ServiceKnot]
                Deployed 607f7646-6127-422a-b3be-9f321b4a538f [knotx:io.knotx.ActionKnot]
                Deployed 068306bf-0cf8-42b8-bf8f-75f5d0079c9e [knotx:io.knotx.FragmentSplitter]
                Deployed baa88eba-91cc-42de-a923-baf408fc5a21 [knotx:io.knotx.HandlebarsKnot]
                Deployed 42ed4089-b32c-45f1-91d8-4c3f9d7c235c [knotx:io.knotx.KnotxServer]
```
Then you can verify the application:
```
$ curl http://localhost:8092/content/local/template.html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Knot example</title>
</head>
<body>
<h1>Knot example</h1>
<!-- start compiled snippet -->
    <p>In service adapter you can now consume a secret key from a client request!</p>
  <!-- end compiled snippet -->
</body>
</html>
```
In the log file you should find:
```
2017-08-06 21:55:28 [vert.x-eventloop-thread-0] TRACE i.k.knot.example.ExampleKnotProxy - This request is processed by me!
```


To deploy the extension to a remote location:

1. Specify the `serverId` property value in the `pom.xml` file, for example `my-server`
2. Specify the `deploymentUrl` property value in the `pom.xml` file, for example `localhost:2222/content/knotx/extensions`
This is the directory that the .jar file with your extension will be uploaded to
3. Configure your server in your Maven `settings.xml` file with the id from step 1, for example:

```
<server>
    <id>my-server</id>
    <username>user</username>
    <password>password</password>
</server>
```

4. Run `mvn clean install wagon:upload-single`