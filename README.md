# TransformersSaga

Fictional Toy Transformers and their Battle. 
Goal is to test swagger generated APIs and swagger generated spring based server code stub
## APIs

APIs defined and documented in swagger is available <br/>
<b><a>
https://app.swaggerhub.com/apis/TransformerSaga/Transformers/1.0.1
</b></a>
<br/>
json and yaml open api definations are in api folder and html documentation is in doc folder

## Build Requirements
<li> Java 1.8 (JDK)
<li> Maven 3.0

## Run Requirements
Java 1.8 (JRE)

## Build Instructions:
<li> Import the transformers_server folder as Maven project in your IDE(Eclipse)
<li> Update Maven project and Build Maven project with goals "clean package"
<li> run at OS command prompt, the <b>swagger-spring-1.0.0.jar</b> file in the target directory.
<br/> Say on a Linux machine you have jar file in the folder api-server
<br/>root@server:/api-server# <b> nohup java -jar -Dserver.port=80 swagger-spring-1.0.0.jar &</b><br/>
<li> Alternatively you may run <b>Swagger2SpringBoot.java</b> in your IDE as java application.

## Example Data
<li>Now, assuming server is running on same machine at port 80, in a browser you may get the initial example data: <br/>
http://localhost/sy_kamran/Transformers/1.0.0/transformers/all
<br/>
http://localhost/sy_kamran/Transformers/1.0.0/transformers/list/Autobot
<br/>
http://localhost/sy_kamran/Transformers/1.0.0/transformers/list/Decepticon
<br/> or <br/>
http://localhost/sy_kamran/Transformers/1.0.0/transformers/list/a <br/>
http://localhost/sy_kamran/Transformers/1.0.0/transformers/list/d <br/>





 