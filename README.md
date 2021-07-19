# RestaurantService
Crumbs Restaurant Micro-Service

Base URL: http://localhost:8070


1)
URL structure: /restaurants/{restaurantId}

Description:
This endpoint takes in a restaurant ID and returns the restaurant associated with that ID.

Method: GET

Parameters:

Path Variable:
- restaurant_id : String. Not null. ID of restaurant you want to fetch.

Returns:
Restaurant object.

Errors:

IllegalArgumentException: ID is null.

NoSuchElementException: Restaurant with given ID does not exist.


2)
URL structure: /restaurants

Description:
This endpoint returns all restaurant present in our serivce in a pageable format.

Method: GET

Parameters:

Request Parameters:
- page : Integer. Default value : 0.
- sort_by : String. Default Value : id
- order : String. Default value : ascending
- filter: String array. Not required.

Returns:
List of Restaurants.

Errors:

NoSuchElementException: No restaurants currently present in our service.


3)
URL structure: /restaurants/{restaurantId}/menuitems

Description:
This endpoint takes in a restaurant ID and returns all the menu items associated with that restaurant.

Method: GET

Parameters:

Path Variable:
- restaurant_id, String, ID of restaurant you want to fetch

Request Parameters:
- page : Integer. Default value : 0.
- sort_by : String. Default Value : id
- order : String. Default value : ascending
- filter: String array. Not required.

Returns:
List of menu items.

Errors:

IllegalArgumentException: ID is null.

NoSuchElementException: Restaurant with given ID does not exist.


4)
URL structure: /restaurants/menuitems

Description:
This endpoint returns all menu items present in our service.

Method: GET

Parameters:

Request Parameters:
- page : Integer. Default value : 0.
- sort_by : String. Default Value : id
- order : String. Default value : ascending
- filter: String array. Not required.

Returns:
List of menu items.


3)
URL structure: /categories

Description:
This endpoint returns all categories present in our service.

Method: GET

Returns: List of Category objects.


4)
URL structure: /owner/{id}/restaurants

Description:
This endpoint returns all restaurants associated with the given owner_id

Method: GET

Parameters:

Path Variable:
- owner_id : String, ID of owner you wish to get restaurants of.

Returns: List of Restaurants associated with a specific owner.

Errors:

IllegalArgumentException: ID is null.

NoSuchElementException: No owner was found with the given id.

5)
URL structure: /restaurants

Description: Add a restaurant to our service.

Method: POST

Parameters:

Request Body:

AddRestaurantDTO object with fields:
- Long owner_id
- String street
- String city
- String zip
- String state
- String name
- Integer price_rating
- List<String> categories

Returns: Restaurant object created.

Errors:

400 Bad Request:
- Hibernate Exception : AddRestaurantDTO is invalid.
- EntityNotFoundException: No owner with the specified owner_id exists.

409 Conflict:
- DuplicateLocationException : Location already exists in our service. Duplicate not allowed.


6)
URL structure: /restaurants/{id}

Description: Update an owner's and restaurant details.

Method: PUT

Parameters:

Path Variable:

restaurant_id : ID of restaurant you wish to update

Request Body:

UpdateRestaurantDTO object with fields:
- String first name
- String last name
- String email
- String street
- String city
- String zip
- String state
- String name
- Integer price_rating
- List<String> categories
- List<MenuItem> menu

Returns: Updated restaurant object.

Errors:

400 Bad Request:
- Hibernate Exception : UpdateRestaurantDTO is invalid.
- EntityNotFoundException: No restaurant with the specified restaurant_id exists.

409 Conflict:
- DuplicateLocationException : Location already exists in our service. Duplicate not allowed.

7)
URL structure: /owner/restaurant/{id}

Description: Request the deletion of an owner's restaurant. I.e change the status of the restaurant to PENDING_DELETE

Method: DELETE

Parameters:

Path Variable:

restaurant_id : ID of restaurant you wish to request deletion.

Returns: void

Errors:

400 Bad Request:
- EntityNotFoundException: No restaurant with the specified restaurant_id exists.

 
