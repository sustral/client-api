# API Guide

Notes:
* Every endpoint uses a POST request
    * This avoids transmitting ids over the url and does not limit the number of ids when requesting multiple objects
    * Every route is authenticated and does not benefit from caching
    * Anyone using this API will have read this documentation
    * All calls to this API wil be by company built clients

## Contents

* [Authentication](#authentication)

* [/sign_in](sign_in.md#sign_in)

* [/sign_up](sign_up.md#sign_up)

* [/sign_out](sign_out.md#sign_out)

* [/session](session.md#session)

* [/reset_password](reset_password.md#reset_password)

* [/reset_password/request](reset_password.md#reset_passwordrequest)

* [/verify_email](verify_email.md#verify_email)

* [/verify_email/request](verify_email.md#verify_emailrequest)

* [/me](me.md#me)

* [/me/edit](me.md#meedit)

* [/users](users.md#users)

* [/organizations](organizations.md#organizations)

* [/organizations/create](organizations.md#organizationscreate)

* [/organizations/edit](organizations.md#organizationsedit)

* [/fields](fields.md#fields)

* [/fields/create](fields.md#fieldscreate)

* [/fields/edit](fields.md#fieldsedit)

* [/scans](scans.md#scans)

* [/files](files.md#files)

* [/objects](objects.md#objects)

* [/users_to_organizations/users](users_to_organizations.md#users_to_organizationsusers)

* [/users_to_organizations/organizations](users_to_organizations.md#users_to_organizationsorganizations)

* [/fields_to_organizations/fields](fields_to_organizations.md#fields_to_organizationsfields)

* [/fields_to_organizations/organizations](fields_to_organizations.md#fields_to_organizationsorganizations)

* [/scans_to_fields/scans](scans_to_fields.md#scans_to_fieldsscans)

* [/files_to_scans/files](files_to_scans.md#files_to_scansfiles)

## Authentication

Every request must use HTTPS.

Every endpoint, barring those that indicate otherwise, requires the "Sustral-Client-Type" to be set to either "mobile"
or "web".

Web clients are sent a JSON Web Token (set as an Http Only, Secure Cookie), a refresh token (set as an Http Only, Secure Cookie),
and a CSRF Token (sent as the "Sustral-CSRF" header). The CSRF token should be stored in local storage and sent in the "Sustral-CSRF"
header with every subsequent request that requires one of the auth tokens.

Mobile clients are sent a JSON Web Token (in the "Authorization" header) and a refresh token (in the "Authorization-Session" 
header). These tokens should be stored and sent as a bearer token in the Authorization header with appropriate requests.
The tokens are used for different endpoints.
