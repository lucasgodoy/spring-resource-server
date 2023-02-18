# spring-resource-server
Implementation of a oauth2 resource server using Spring 3.

Application runs on port 5000.

Example configuration in Spring `application.yaml` file:

```yaml
security:
  introspectionUri: http://identity-provider.com/introspection
  issuerUri: http://identity-provider.com
```

- `introspectionUri`: specifies the public uri provided by the identity provider used to perform tasks related to 
opaque tokens. Don't add this field to the properties file if the resource server must not support opaque tokens.
- `issuerUri`specifies the public uri provided by the identity provider used to perform tasks related to tokens JWT. 
Don't add this field to the properties file if the resource server must not support JWT tokens. 
