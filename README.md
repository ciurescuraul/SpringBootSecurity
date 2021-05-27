# Spring Boot Security

### #03 Authorities and Roles
   In Spring Security, granted authorities and roles are a form of expressing a privilege/permission for an authenticated user.

### #04 HTTP Basic Authentication
   In the context of HTTP, basic authentication is the process for browser to request a username and password when making a request in order to authentify the user

### #05 Form Based Authentication
   It is the process of authenticating a user by presenting a custom HTML page that will collect credentials and by directing the authentication responsibility to the web application that collects the form data.

### #06 Token Based Authentication using JWT https://jwt.io
   JSON Web Tokens (JWT) is a compact and safe way to transmit data between two parties. The information cand be trusted because it is digitally signed.

#### JWT Structure
JSON Web Tokens consist of three parts separated by dots(.)

      1. Header
      2. Payload
      3. Signature
Example: hhhhh.pppppppp.ssssssss

### #07 SSL & HTTPS
   HTTPS is a combination of HTTP plus SSL security layer on top of it. HTTPS is just HTTP that delivers data securely between endpoints.

      1. Self-Signed (Created by you) -> Good for development
      2. Signed by Trusted Authority (Comodo, Symantec, DigiCert, etc)

   SSL is mandatory for any web application, regardless of the chosen security config
   
### #15 Enable HTTPS/SSL in Spring Boot
#### For Spring Boot apps running with Tomcat embedded server

1. Certificate
    * Self Signed

    _keytool -genkey -alias bootsecurity -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore bootsecurity.p12 -validity 3650_
    
    * Buy
    
2. Modify app.properties

    - server.port=8443
    - server.ssl.enabled=true
    - server.ssl.key-store: src/main/resources/bootsecurity.p12
    - server.ssl.key-store-password: bootsecurity
    - server.ssl.keyStoreType: PKCS12
    - server.ssl.keyAlias: bootsecurity


3. Add @Bean for ServletWebServerFactory

        @Bean
             public ServletWebServerFactory servletContainer() {
                 // Enable SSL Trafic
                 TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
                     @Override
                     protected void postProcessContext(Context context) {
                         SecurityConstraint securityConstraint = new SecurityConstraint();
                         securityConstraint.setUserConstraint("CONFIDENTIAL");
                         SecurityCollection collection = new SecurityCollection();
                         collection.addPattern("/*");
                         securityConstraint.addCollection(collection);
                         context.addConstraint(securityConstraint);
                     }
                 };
         
                 // Add HTTP to HTTPS redirect
                 tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());
         
                 return tomcat;
             }
         
             /*
             We need to redirect from HTTP to HTTPS. Without SSL, this application used
             port 8082. With SSL it will use port 8443. So, any request for 8082 needs to be
             redirected to HTTPS on 8443.
              */
             private Connector httpToHttpsRedirectConnector() {
                 Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
                 connector.setScheme("http");
                 connector.setPort(8082);
                 connector.setSecure(false);
                 connector.setRedirectPort(8443);
                 return connector;
             }

## #16 Database Authentication - Overview

1. Create user entity to store user information
2. Store the User in our database
3. Link our User entity with th built in classes in Spring Security
    - Link User with UserDetails interface
    - Link UserRepository with UserDetailsService interface
4. Integrate Database Auth in our configuration

## #17 Database Authentication - User Entity

## #18 Database Authentication - User Repository

## #19 Database Authentication - Implement User Details Service
- Use Decorator Design Pattern

## #20 Database Authentication - Configure Database Provider

## #21 Form Based Auth - Create a Custom Login Form

## #22 Form Based Auth - Integrate Spring Security With Custom Login Form

## #23 Implement Logout Action - Forms Authentication

## #24 Implement Remember Me - Forms Authentication

## #25 Customize Form Control Names - Form Authentication

## #26 Spring Security Thymeleaf Integration
## Views are security aware
### Show / Hide content if user is authenticated or not
### Show / Hide content if user has a role or a permission
### Display user details such as name or permissions
____
##  Use Thymeleaf with Spring Security
    1. Add POM dependency
    <dependency>
        <groupId>org.thymeleaf.extras</groupId>
        <artifactId>thymeleaf-extras-springsecurity5</artifactId>
    </dependency>

    2. Use specific Thymeleaf security attributes


## #27 JWT (JSON Web Tokens) Authentication - When Should You Use JWT Security ?
1. When you want to centralize authorization and authentification part of your application.
2. You want to deliver one ore more Web Applications.
3. You may also want to expose parts of your bussiness logic or parts of your application to third party customers.
4. When you want to create a Mobile App.

- CORS Enabled
- Cross Origin Resource Sharing

## #28 JWT (JSON Web Tokens) - Sample Application & JWT Dependencies

## #29 JWT (JSON Web Tokens) - Implement JWT Authentication

## #30 JWT (JSON Web Tokens) - Implement JWT Authorization

## #31 JWT (JSON Web Tokens) - Spring Security JWT Configuration

    