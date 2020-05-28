# API Guide

Notes:
* Every endpoint uses a POST request
    * This avoids transmitting ids over the url and does not limit the number of ids when requesting multiple objects
    * Every route is authenticated and does not benefit from caching
    * Anyone using this API will have read this documentation
    * All calls to this API wil be by company built clients

## Contents

* [Authentication](#authentication)

* [/sign_in](#sign_in)

* [/sign_up](#sign_up)

* [/sign_out](#sign_out)

* [/session](#session)

* [/reset_password](#reset_password)

* [/reset_password/request](#reset_passwordrequest)

* [/verify_email](#verify_email)

* [/verify_email/request](#verify_emailrequest)

* [/me](#me)

* [/me/edit](#meedit)

* [/users](#users)

* [/organizations](#organizations)

* [/organizations/edit](#organizationsedit)

* [/fields](#fields)

* [/fields/edit](#fieldsedit)

* [/scans](#scans)

* [/files](#files)

* [/objects](#objects)

* [/users_to_organizations](#users_to_organizations)

* [/fields_to_organizations](#fields_to_organizations)

* [/scans_to_fields](#scans_to_fields)

* [/files_to_scans](#files_to_scans)

## Authentication

Every request must use HTTPS.

Every endpoint, barring those that indicate otherwise, requires the "Sustral-Client-Type" to be set to either "mobile"
or "web".

Web clients are sent a JSON Web Token (set as an Http Only, Secure Cookie), a refresh token (set as an Http Only, Secure Cookie),
and a CSRF Token (sent as the "Sustral-CSRF" header). The CSRF token should be stored in local storage and sent in the "Sustral-CSRF"
header with every request that requires one of the auth tokens.

Mobile clients are sent a JSON Web Token (in the "Authorization" header) and a refresh token (in the "Authorization-Session" 
header). These tokens should be stored in something like the keychain and sent in the Authorization header with each 
request. Each token is used for different endpoints.

## /sign_in

* Example Web Request:

    Request Header:
    
    `Sustral-Client-Type: web`
        
    Request Body:
    
    `{
        "email": "user@example.com",
        "password": "UserPassword1"
     }`
    
    Response Code on Success: `200`
    
    Response Header:
    
    `Sustral-CSRF: <token>`
    
    Response Cookies (HTTPOnly):
    
    `sustral_accesstoken`
    
    `sustral_sessiontoken`
    
    Response Body (Irrelevant):
    
    `{
        "error": null,
        "data": null
     }`
     
* Example Mobile Request:

    Request Header:
    
    `Sustral-Client-Type: mobile`
        
    Request Body:
    
    `{
        "email": "user@example.com",
        "password": "UserPassword1"
     }`
    
    Response Code on Success: `200`
    
    Response Header:
    
    `Authorization: <token>`
    
    `Authorization-Session: <token>`
    
    Response Body (Irrelevant):
    
    `{
        "error": null,
        "data": null
     }`

## /sign_up

* Example Web Request:

    Request Header:
    
    `Sustral-Client-Type: web`
        
    Request Body:
    
    `{
        "name": "User Name",
        "email": "user@example.com",
        "password": "UserPassword1"
     }`
    
    Response Code on Success: `200`
    
    Response Header:
    
    `Sustral-CSRF: <token>`
    
    Response Cookies (HTTPOnly):
    
    `sustral_accesstoken`
    
    `sustral_sessiontoken`
    
    Response Body (Irrelevant):
    
    `{
        "error": null,
        "data": null
     }`
     
* Example Mobile Request:

    Request Header:
    
    `Sustral-Client-Type: mobile`
        
    Request Body:
    
    `{
        "name": "User Name",
        "email": "user@example.com",
        "password": "UserPassword1"
     }`
    
    Response Code on Success: `200`
    
    Response Header:
    
    `Authorization: <token>`
    
    `Authorization-Session: <token>`
    
    Response Body (Irrelevant):
    
    `{
        "error": null,
        "data": null
     }`

## /sign_out

## /session

## /reset_password

## /reset_password/request

## /verify_email

## /verify_email/request

## /me

## /me/edit

## /users

## /organizations

## /organizations/edit

## /fields

## /fields/edit

## /scans

## /files

## /objects

## /users_to_organizations

## /fields_to_organizations

## /scans_to_fields

## /files_to_scans
