## /fields_to_organizations/fields

* Example Web Request:

    Request Headers:
    
    `Sustral-Client-Type: web`
    
    `Sustral-CSRF: <token>`
    
    Request Cookies:
    
    `sustral_accesstoken`
        
    Request Body:
    
        {
            "id": String,       (ID of the organization)
            "offset": Integer,  (Number received so far)
            "limit": Integer    (Number desired)
        }
     
    Response Status Code on Success: `200`
    
    Response Headers:
    
    `None`
    
    Response Body:
    
        {
            "error": String, (May contain description of the error)
            "data": String[] (Will be null if an error occured)
        }
     
* Example Mobile Request:

    Request Headers:
    
    `Sustral-Client-Type: mobile`
    
    `Authorization: Bearer <access token>`
        
    Request Body:
        
        {
            "id": String,       (ID of the organization)
            "offset": Integer,  (Number received so far)
            "limit": Integer    (Number desired)
        }
    
    Response Status Code on Success: `200`
    
    Response Headers:
    
    `None`
    
    Response Body:
        
        {
            "error": String, (May contain description of the error)
            "data": String[] (Will be null if an error occured)
        }

## /fields_to_organizations/organizations

* Example Web Request:

    Request Headers:
    
    `Sustral-Client-Type: web`
    
    `Sustral-CSRF: <token>`
    
    Request Cookies:
    
    `sustral_accesstoken`
        
    Request Body:
    
        {
            "id": String,       (ID of the field)
            "offset": Integer,  (Number received so far)
            "limit": Integer    (Number desired)
        }
     
    Response Status Code on Success: `200`
    
    Response Headers:
    
    `None`
    
    Response Body:
    
        {
            "error": String, (May contain description of the error)
            "data": String[] (Will be null if an error occured)
        }
     
* Example Mobile Request:

    Request Headers:
    
    `Sustral-Client-Type: mobile`
    
    `Authorization: Bearer <access token>`
        
    Request Body:
        
        {
            "id": String,       (ID of the field)
            "offset": Integer,  (Number received so far)
            "limit": Integer    (Number desired)
        }
    
    Response Status Code on Success: `200`
    
    Response Headers:
    
    `None`
    
    Response Body:
        
        {
            "error": String, (May contain description of the error)
            "data": String[] (Will be null if an error occured)
        }
