# Instructions to check the task 3

## Pre-requisites

This task uses a mocked standalone authorization server that will be mounted in a docker container.

**Docker**

Latest version [![GitHub release (latest SemVer including pre-releases)](https://img.shields.io/github/v/release/navikt/mock-oauth2-server?color=green&include_prereleases&label=GitHub%20Package%20Registry&logo=Docker)](https://github.com/navikt/mock-oauth2-server/packages/)

```bash
docker pull ghcr.io/navikt/mock-oauth2-server:$MOCK_OAUTH2_SERVER_VERSION
```

In my case `$MOCK_OAUTH2_SERVER_VERSION = 0.3.3`

# Running the app

**Docker container**

```bash
docker run -p 8080:8080 $IMAGE_NAME
```

**Run the spring boot app**

To run the app just press start in the IDE. The application will use the port 8081 since the Authorization server is using the port 8080.

At the start the DB will be filled with a Message entity:

```json
{
  "id": 1,
  "message": "Hello Spring Data",
  "author": "Antonio"
}
```

# Testing the task 3

| Endpoints        | Description                                                                |
| ---------------- | -------------------------------------------------------------------------- |
| Get `/`          | Allows you access to a Hello world message without Authorization           |
| Get `/messages`  | You will get the messages stored if you has an authenticated Authorization |
| Post `/messages` | You will create a message if you has the `message:write` Authority         |

# How to get the Access Token

In our Authorization server, there is a page that acts like a Oauth2 client that allow us to get the token.

1. Go to http://localhost:8080/default/debugger, a form will appear, we can let all values by default but the `scope`. In this field set the value `openid message:write` .
2. Click on the `Get a Token`.
3. In the Server Sign-in form, put any value in both fields.
4. You will get the Token Request and the Token Response. From the Token response, copy the JWT `access_token` value and put it as the Authorization Bearer in the requests.

**Checking the JWT content**

1. Go to https://jwt.io/ and paste the token.
2. You will see in the right how the `aud` claim in the payload has the scope `message:write`.

**Spring Authorization configuration**

By default Spring Security will look for the authorities in the `scope` or `scp` attribute. Our mocked Authorization server allow us to do this but it requires a more complex configuration, so instead to do that, the flexibility of Spring will allow us to look for in the `aud` claim field.

That is done with the `JwtAuthenticationConverter`. https://docs.spring.io/spring-security/site/docs/current/reference/html5/#oauth2resourceserver-jwt-authorization-extraction

```java
@Bean
public JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    grantedAuthoritiesConverter.setAuthoritiesClaimName("aud");

    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
    return jwtAuthenticationConverter;
}
```

## Performing the requests

**Get /**

```bash
curl --location --request GET 'http://localhost:8081/'
```

**Get /messages**

```bash
curl --location --request GET 'http://localhost:8081/messages' \
--header 'Authorization: Bearer eyJraWQiOiJkZWZhdWx0IiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJhIiwiYXVkIjoibWVzc2FnZTpyZWFkIiwiYWNyIjoiYSIsIm5iZiI6MTYyMzIzMTA4NSwiYXpwIjoiZGVidWdnZXIiLCJpc3MiOiJodHRwOlwvXC9sb2NhbGhvc3Q6ODA4MFwvZGVmYXVsdCIsImV4cCI6MTYyMzIzNDY4NSwiaWF0IjoxNjIzMjMxMDg1LCJub25jZSI6IjU2NzgiLCJqdGkiOiJjYjlmYjRkMi04YTc4LTRjYWItOWYwYi05YzM2MzYyYmQzNmMiLCJ0aWQiOiJkZWZhdWx0In0.Orq9w3s-iCk3r5Z5hdrdlVvM12F4of03KZ0V8wq0xnB7729umpfpBv6s-bLKjVVKJ0z8xg6t-Wxds3r15Jpav1h2bG4lH0Uq1G7mdnuae6sqgFYxhLYQOFEBIzAoSliazIk8Ms0TXa4n5UwUoviTW6phUoZ3fZ22G6w8u2-RofBhRV14tjvazlXOXYz3r8F6VbIBm718qDI60oNFox5W3Uq8187QAe6pPnWybk4LFSmRZ4fbMg-2PN1tnXpE7S3MczxjlUnjaDU_0brfCyFzc-DPHB_H9Rf0aLyCf0gkwB6x1NYyZzXI_yLohPKIksgxYecsjhG5_S-yulejOoRwxw'
```

**POST /messages**

```bash
curl --location --request POST 'http://localhost:8081/messages' \
--header 'Authorization: Bearer eyJraWQiOiJkZWZhdWx0IiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJhIiwiYXVkIjoibWVzc2FnZTpyZWFkIiwiYWNyIjoiYSIsIm5iZiI6MTYyMzIzMTA4NSwiYXpwIjoiZGVidWdnZXIiLCJpc3MiOiJodHRwOlwvXC9sb2NhbGhvc3Q6ODA4MFwvZGVmYXVsdCIsImV4cCI6MTYyMzIzNDY4NSwiaWF0IjoxNjIzMjMxMDg1LCJub25jZSI6IjU2NzgiLCJqdGkiOiJjYjlmYjRkMi04YTc4LTRjYWItOWYwYi05YzM2MzYyYmQzNmMiLCJ0aWQiOiJkZWZhdWx0In0.Orq9w3s-iCk3r5Z5hdrdlVvM12F4of03KZ0V8wq0xnB7729umpfpBv6s-bLKjVVKJ0z8xg6t-Wxds3r15Jpav1h2bG4lH0Uq1G7mdnuae6sqgFYxhLYQOFEBIzAoSliazIk8Ms0TXa4n5UwUoviTW6phUoZ3fZ22G6w8u2-RofBhRV14tjvazlXOXYz3r8F6VbIBm718qDI60oNFox5W3Uq8187QAe6pPnWybk4LFSmRZ4fbMg-2PN1tnXpE7S3MczxjlUnjaDU_0brfCyFzc-DPHB_H9Rf0aLyCf0gkwB6x1NYyZzXI_yLohPKIksgxYecsjhG5_S-yulejOoRwxw' \
--header 'Content-Type: application/json' \
--data-raw '{
    "message": "I got it!",
    "author": "Antonio"
}'
```

If you get another token without the `message:write` scope, you will recevide a `403 Forbidden` status code, which means that you does not have the required authorites to perform the request.
