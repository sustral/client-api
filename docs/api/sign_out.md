## /sign_out

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
    
    `Irrelevant`
    
    Response Cookies:
    
    `Set Null Cookies`
    
    Response Body:
    
    `Irrelevant`
     
* Example Mobile Request:

    Request Headers:
    
    `Sustral-Client-Type: mobile`
    
    `Authorization: Bearer <session token>`
        
    Request Body:
    
    `None`
    
    Response Status Code on Success: `200`
    
    Response Headers:
    
    `Irrelevant`
    
    Response Body:
    
    `Irrelevant`
