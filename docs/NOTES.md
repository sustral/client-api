# Notes

* The data models and services were intentionally denormalized with the goal of soon breaking each into its own microservice.
  Inheritance in JPA models for the sake of eliminating duplicated getters and setters is not appropriate. This is doubly true when
  any of these entities could be altered at any time.
