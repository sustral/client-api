## /session

* Example Web Request:

    Request Headers:
    
    `Sustral-Client-Type: web`
    
    `Sustral-CSRF: <token>`
    
    Request Cookies:
    
    `sustral_sessiontoken`
        
    Request Body:
    
    `None`
    
    Response Status Code on Success: `200`
    
    Response Headers:
    
    `Sustral-CSRF: <token> (Expires along with the cookies)`
    
    Response Cookies:
    
    `sustral_accesstoken (HTTPOnly, Secure, 15 Mins)`
    
    `sustral_sessiontoken (HTTPOnly, Secure, 4 Days)`
    
    Response Body:
    
    `{
        "error": String, (May contain description for user)
        "data": null (Always null)
     }`
     
* Example Mobile Request:

    Request Headers:
    
    `Sustral-Client-Type: mobile`
    
    `Authorization: Bearer <session token>`
        
    Request Body:
    
    `None`
    
    Response Status Code on Success: `200`
    
    Response Headers:
    
    `Authorization: <token> (15 Mins)`
    
    `Authorization-Session: <token> (4 Days)`
    
    Response Body:
    
    `{
        "error": String, (May contain description for user)
        "data": null (Always null)
     }`
