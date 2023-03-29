# Show Booking System (backend)

This is the backend (API) for a simple show booking system built using Spring Boot.

It works together with the frontend which was built using React.js. See https://github.com/ewansou/JPMShowBookingUI

To see a live demo, please visit https://jpmshowbooking-ui.herokuapp.com/

Tech Stack Overview
- Spring Boot
- Lombok
- Gson
- Jackson
- Junit
- JPA (Hibernate)
- MySQL (database)

This application serves two groups of users, Admin and Buyer.

Admin
- Setup new show
- View details of all shows
- View details of a show by show number
- Retrieve all shows seatsings
- View show seatings of a show by show number
- View show seatings by show number and seat status

Buyer
- Retrieve available seats by show number
- Book seats
- Cancel tickets

Constraints / Assumption
- Maximum number of rows is set at default 26 (A to Z) and max number of seats per row is set at 10. These two values can be configured via the application.yml
- Seat number is of format <alphabet><digit>. For example, A12
- Only one booking per phone is allowed per show
- Buyer who have booked a ticket is allowed to cancel within the show's cancellation window. Past the cancellation window, buyer will not be able to cancel the show anymore
- Buyer access their menu via (https://jpmshowbooking-ui.herokuapp.com/buyer). Admin access their menu via (https://jpmshowbooking-ui.herokuapp.com/admin). Assumption: Buyer is not aware of how to access the Admin's menu
- The main landing page URL (https://jpmshowbooking-ui.herokuapp.com/) is only made known to the Admin.

