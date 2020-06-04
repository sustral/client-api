## /scans

A scan's "updated" attribute is the time at which the scan was last updated 
in the form of milliseconds since 01/01/1970 00:00:00 GMT

A scan's "status" attribute reflects its progress toward completion. This "status" can take on the values:

    "PENDING_COLLECTION",
    "COLLECTION",
    "PENDING_ANALYSIS",
    "ANALYSIS",
    "PENDING_COMPLETE", and
    "COMPLETE"

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
                    "updated": Long,
                    "status": String,
                    "coordinates": String (Well Known Text)
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
                    "updated": Long,
                    "status": String,
                    "coordinates": String (Well Known Text)
                },
                ...
            ]
        }
