## /me

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
    
        {
            "error": String, (May contain description of the error)
            "data": { (Will be null if an error occured)
                "id": String,
                "email": String,
                "name": String,
                "emailVerified": Boolean
            }
        }
     
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
    
        {
            "error": String, (May contain description of the error)
            "data": { (Will be null if an error occured)
                "id": String,
                "email": String,
                "name": String,
                "emailVerified": Boolean
            }
        }

## /me/edit

* Example Web Request:

    Request Headers:
    
    `Sustral-Client-Type: web`
    
    `Sustral-CSRF: <token>`
    
    Request Cookies:
    
    `sustral_accesstoken`
        
    Request Body:
    
        {
            "name": String,
            "email": String,
            "password": String
        }
     
    Response Status Code on Success: `200` or `207 (Partial Success)`
    
    Response Headers:
    
    `None`
    
    Response Body:
    
        {
            "error": String, (May contain description of the error)
            "data": { (Will be null if an error occured)
                "id": String,
                "email": String,
                "name": String,
                "emailVerified": Boolean
            }
        }
     
* Example Mobile Request:

    Request Headers:
    
    `Sustral-Client-Type: mobile`
    
    `Authorization: Bearer <access token>`
        
    Request Body:
    
        {
            "name": String,
            "email": String,
            "password": String
        }
    
    Response Status Code on Success: `200` or `207 (Partial Success)`
    
    Response Headers:
    
    `None`
    
    Response Body:
    
        {
            "error": String, (May contain description of the error)
            "data": { (Will be null if an error occured)
                "id": String,
                "email": String,
                "name": String,
                "emailVerified": Boolean
            }
        }
