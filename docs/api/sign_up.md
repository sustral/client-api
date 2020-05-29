## /sign_up

* Example Web Request:

    Request Headers:
    
    `Sustral-Client-Type: web`
        
    Request Body:
    
    `{
        "name": "<name>", (100 Chars Max)
        "email": "<email>", (Standard Email, 320 Chars Max)
        "password": "<password>" (8-256 Chars, LowerCase, UpperCase, Digit)
     }`
    
    Response Status Code on Success: `200`
    
    Response Headers:
    
    `Sustral-CSRF: <token> (Expires along with the cookies)`
    
    Response Cookies:
    
    `sustral_accesstoken (HTTPOnly, Secure, 15 Mins)`
    
    `sustral_sessiontoken (HTTPOnly, Secure, 4 Days)`
    
    Response Body:
    
    `{
        "error": null, (May contain description for user)
        "data": null (Always null)
     }`
     
* Example Mobile Request:

    Request Headers:
    
    `Sustral-Client-Type: mobile`
        
    Request Body:
    
    `{
        "name": "<name>", (100 Chars Max)
        "email": "<email>", (Standard Email, 320 Chars Max)
        "password": "<password>" (8-256 Chars, LowerCase, UpperCase, Digit)
     }`
    
    Response Status Code on Success: `200`
    
    Response Headers:
    
    `Authorization: <token> (15 Mins)`
    
    `Authorization-Session: <token> (4 Days)`
    
    Response Body:
    
    `{
        "error": null, (May contain description for user)
        "data": null (Always null)
     }`
