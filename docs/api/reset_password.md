## /reset_password

* Example Request (No Client Distinction):

    Request Headers:
    
    `None`
        
    Request Body:
    
        {
            "token": String,
            "password": String
        }
    
    Response Status Code on Success: `200`
    
    Response Headers:
    
    `None`
    
    Response Body:
    
        {
            "error": String, (May contain description for user)
            "data": null (Always null)
        }

## /reset_password/request

* Example Request (No Client Distinction):

    Request Headers:
    
    `None`
        
    Request Body:
    
        {
            "email": String
        }
    
    Response Status Code on Success: `200`
    
    Response Header:
    
    `None`
    
    Response Body:
    
        {
            "error": String, (May contain description for user)
            "data": null (Always null)
        }
