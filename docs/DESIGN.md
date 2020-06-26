# Design

### Package Descriptions

   - config:
       - handles configuration specific to the parent package
       - data package has its own config
       
   - controllers:
       - handles authorization
       - marshals resources from the service layer into
       the form required by callers
       
   - data: 
       - provides access to the database
    
   - dataservices:
       - CRUD layer
       - maintains data consistency
    
   - filters:
       - handles authentication based on the client type
       - sets universal headers
       
   - miscservices:
       - significant logic that does not relate to data
       
   - utils:
       - useful bits of code

### Data Flow

    filters -> controllers -> dataservices -> data
                           -> miscservices

## Notes

* The data models and services were intentionally denormalized with the goal of soon breaking each into its own microservice.
  Inheritance in JPA models for the sake of eliminating duplicated getters and setters is not appropriate. This is doubly true when
  any of these entities could be altered at any time.
