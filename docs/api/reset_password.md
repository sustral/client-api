## /reset_password

* Example Request (No Client Distinction):

    Request Headers:
    
    `None`
        
    Request Body:
    
    `{
        "token": "<token>",
        "password": "<password>"
     }`
    
    Response Status Code on Success: `200`
    
    Response Headers:
    
    `None`
    
    Response Body:
    
    `{
        "error": null, (May contain description for user)
        "data": null (Always null)
     }`

## /reset_password/request

* Example Request (No Client Distinction):

    Request Headers:
    
    `None`
        
    Request Body:
    
    `{
        "email": "<email>"
     }`
    
    Response Status Code on Success: `200`
    
    Response Header:
    
    `None`
    
    Response Body:
    
    `{
        "error": null, (May contain description for user)
        "data": null (Always null)
     }`
