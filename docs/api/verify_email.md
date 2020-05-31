## /verify_email

* Example Request (No Client Distinction):

    Request Headers:
    
    `None`
        
    Request Body:
    
    `{
        "token": String
     }`
    
    Response Status Code on Success: `200`
    
    Response Headers:
    
    `None`
    
    Response Body:
    
    `{
        "error": String, (May contain description for user)
        "data": null (Always null)
     }`

## /verify_email/request

* Example Web Request:

    Request Headers:
    
    `Sustral-Client-Type: web`
    
    `Sustral-CSRF: <token>`
    
    Request Cookies:
    
    `sustral_accesstoken`
        
    Request Body:
    
    `None`
    
    Response Status Code on Success: `200`
    
    Response Headers:
    
    `None`
    
    Response Body:
    
    `{
        "error": String, (May contain description for user)
        "data": null (Always null)
     }`
     
* Example Mobile Request:

    Request Headers:
    
    `Sustral-Client-Type: mobile`
    
    `Authorization: Bearer <access token>`
        
    Request Body:
    
    `None`
    
    Response Status Code on Success: `200`
    
    Response Headers:
    
    `None`
    
    Response Body:
    
    `{
        "error": String, (May contain description for user)
        "data": null (Always null)
     }`
