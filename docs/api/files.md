## /files

A file's "type" can take on the values:

    "RGB_RAW", and
    "RGB_ORTHOMOSAIC"

* Example Web Request:

    Request Headers:
    
    `Sustral-Client-Type: web`
    
    `Sustral-CSRF: <token>`
    
    Request Cookies:
    
    `sustral_accesstoken`
        
    Request Body:
    
        {
            "ids": String[]
        }
     
    Response Status Code on Success: `200`
    
    Response Headers:
    
    `None`
    
    Response Body:
    
        {
            "error": String, (May contain description of the error)
            "data": [ (Will be null if an error occured)
                {
                    "id": String,
                    "type": String
                },
                ...
            ]
        }
     
* Example Mobile Request:

    Request Headers:
    
    `Sustral-Client-Type: mobile`
    
    `Authorization: Bearer <access token>`
        
    Request Body:
        
        {
            "ids": String[]
        }
    
    Response Status Code on Success: `200`
    
    Response Headers:
    
    `None`
    
    Response Body:
        
        {
            "error": String, (May contain description of the error)
            "data": [ (Will be null if an error occured)
                {
                    "id": String,
                    "type": String
                },
                ...
            ]
        }
