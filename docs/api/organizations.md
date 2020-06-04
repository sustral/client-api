## /organizations

* Example Web Request:

    Request Headers:
    
    `Sustral-Client-Type: web`
    
    `Sustral-CSRF: <token>`
    
    Request Cookies:
    
    `sustral_accesstoken`
        
    Request Body:
    
    `{
        "ids": String[]
    }`
     
    Response Status Code on Success: `200`
    
    Response Headers:
    
    `None`
    
    Response Body:
    
        {
            "error": String, (May contain description of the error)
            "data": [ (Will be null if an error occured)
                {
                    "id": String,
                    "name": String
                },
                ...
            ]
        }
     
* Example Mobile Request:

    Request Headers:
    
    `Sustral-Client-Type: mobile`
    
    `Authorization: Bearer <access token>`
        
    Request Body:
        
    `{
        "ids": String[]
    }`
    
    Response Status Code on Success: `200`
    
    Response Headers:
    
    `None`
    
    Response Body:
        
        {
            "error": String, (May contain description of the error)
            "data": [ (Will be null if an error occured)
                {
                    "id": String,
                    "name": String
                },
                ...
            ]
        }

## /organizations/create

* Example Web Request:

    Request Headers:
    
    `Sustral-Client-Type: web`
    
    `Sustral-CSRF: <token>`
    
    Request Cookies:
    
    `sustral_accesstoken`
        
    Request Body:
    
    `{
        "name": String (100 Chars Max)
    }`
     
    Response Status Code on Success: `200`
    
    Response Headers:
    
    `None`
    
    Response Body:
            
        {
            "error": String, (May contain description of the error)
            "data": { (Will be null if an error occured)
            
                "id": String,
                "name": String
            }
        }
     
* Example Mobile Request:

    Request Headers:
    
    `Sustral-Client-Type: mobile`
    
    `Authorization: Bearer <access token>`
        
    Request Body:
        
    `{
        "name": String (100 Chars Max)
    }`
    
    Response Status Code on Success: `200`
    
    Response Headers:
    
    `None`
    
    Response Body:
        
        {
            "error": String, (May contain description of the error)
            "data": { (Will be null if an error occured)
            
                "id": String,
                "name": String
            }
        }

## /organizations/edit

* Example Web Request:

    Request Headers:
    
    `Sustral-Client-Type: web`
    
    `Sustral-CSRF: <token>`
    
    Request Cookies:
    
    `sustral_accesstoken`
        
    Request Body:
            
        {
            "id": String,
            "data": {
                "name": String (100 Chars Max)
            }
        }
     
    Response Status Code on Success: `200`
    
    Response Headers:
    
    `None`
    
    Response Body:
            
        {
            "error": String, (May contain description of the error)
            "data": { (Will be null if an error occured)
            
                "id": String,
                "name": String
            }
        }
     
* Example Mobile Request:

    Request Headers:
    
    `Sustral-Client-Type: mobile`
    
    `Authorization: Bearer <access token>`
        
    Request Body:
        
        {
            "id": String,
            "data": {
                "name": String (100 Chars Max)
            }
        }
    
    Response Status Code on Success: `200`
    
    Response Headers:
    
    `None`
    
    Response Body:
        
        {
            "error": String, (May contain description of the error)
            "data": { (Will be null if an error occured)
            
                "id": String,
                "name": String
            }
        }
