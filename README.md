# SpringBootSecurity
# #15 Enable HTTPS/SSL in Spring Boot
**For Spring Boot apps running with Tomcat embedded server**

1. Certificate <br>
    a. Self Signed <br>
    keytool -genkey -alias bootsecurity -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore bootsecurity.p12 -validity 3650
    
    b. Buy
    
2. Modify app.properties

    server.port=8443
    server.ssl.enabled=true
    server.ssl.key-store: src/main/resources/bootsecurity.p12
    server.ssl.key-store-password: bootsecurity
    server.ssl.keyStoreType: PKCS12
    server.ssl.keyAlias: bootsecurity


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



    