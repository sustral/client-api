## /objects

* Example Web Request:

    Request Headers:
    
    `Sustral-Client-Type: web`
    
    `Sustral-CSRF: <token>`
    
    Request Cookies:
    
    `sustral_accesstoken`
        
    Request Body:
    
    `{
        "id": String
    }`
     
    Response Status Code on Success: `200`
    
    Response Headers:
    
    `None`
    
    Response Body:
    
    `JPEG Image`
     
* Example Mobile Request:

    Request Headers:
    
    `Sustral-Client-Type: mobile`
    
    `Authorization: Bearer <access token>`
        
    Request Body:
        
    `{
        "id": String
    }`
    
    Response Status Code on Success: `200`
    
    Response Headers:
    
    `None`
    
    Response Body:
        
    `JPEG Image`
