# Photo shooting service

This repository represents a photo shooting service. The goal behind it is to allow full transition of Order through different stages.

### Assumptions

* When the order is created outside of business hours, the order state is UNSCHEDULED
* It is possible to run the order scheduling on a PENDING order. Depending on the date and time being sent, it will move to UNSCHEDULED or PENDING
* Only order in PENDING state can be assigned to a photographer.
* You can not assign one order multiple times. After it is assigned it can't be assigned again.
* Processing of the ZIP file is not a requirement.
* It is allowed to mark order as not verified after it was marked as verified before.
* It is allowed to cancel the completed order.

### Documentation

The basic Swagger documentation is available on the `/swagger-ui/` URL.

### Possible improvements

* Adding caching for all GET calls (list of all orders, get order details)
* Implement pagination for GET orders and GET photographers calls
* Improve test code coverage with all scenarios for order transitions
* Improve validation
* Improve responses for bad requests with appropriate response texts
* Make a new route for importing Photographers in bulk
* Improve Swagger API documentation
