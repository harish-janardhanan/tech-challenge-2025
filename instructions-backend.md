# Project context

    I am designing technical challenge for a cohort of programing students. As the part of this challenge a partly complete The challenge primarily focuses on primarily the below things

    - Candidate will be asked to setup the repository in their local machine 
    - The project will contain a full stack app
    - The project will contain failing test cases 
    - The project will be structured in a way where a candidate can progress logically by completing assigned task
    - Initial task will be easy and as the candidate progress, the tasks will be tougher.
    - completing each task will earn candidate points and the weightage will be based on the complexity of the task solved.

    # Task for the candidates
    - First and formost task will be to set up the project in candidates workspaces
    - There will also be a Business/ Functional  Document proivded explaining the functionality of the application
    - Once the project is setup, the task will be for the candidates to fix the failing test cases
    - Once the test cases are fixed, the next task for the candidates will be to implement a missing functionality in the code, which is identifiable from the code itself. Expectation is that the candidates write test cases for the new functionality added 
    - When the missing functionality is implemented, Candidates will be informed that there is a bug in the code. There will be a bug report raised to the candidates. Expectation is that the candidate identifies the bug, documents the root cause for presentation after. 
    - Next step will be an additional functionality which is complex in nature , ie create a new field in the db as part of trade capture and implement front to back from ui to controller to database. here, in the project there will be an entity class which is pribary function is to make the fields extendable. the extra points will be given to candidates if they extend using the additional info table. If the candidate extends the trade table, the task is still a success without bonus points 
    - Next 2 steps are strech activities 
    Containerise the application and run it in local kubernatis 
    Deploy the app into an azure cloud and run it (Infrastructure will be provided )



# Back end Framework
- Use spring boot to create a backend REST web service 
- Maven as the build tool
- Hibernate & JPA for repository connections 
- JUnit5 for unit testing 
- cucumber framework for end to end integrated testing
- use H2 in memory database as a back end database 
- While exiting the app, save down the entires to an access db file or any file based db . While starting up the app, load the data from the file based db 
- docker for image creation 
- Spring docs open API for documentation using swagger

# Data model 

# Trade Model
- Trade model: Every trade has 2 legs. every trade has a counterparty, book, trade date, start date, maturity date , execution date, UTI code, Additional fields, trader name, Trade inputter name, last touch timestamp. 

# Trade Table
id - pk
trade id - long
version - integer
book - fk to book table - long
counterparty - fk to counterparty table -long 
trader name - long - fk for user table
trade inputter name - long fk for user table 
Trade type - long - fk to trade types table
trade sub type - long - fk to trade sub type tab;e 
Trade status - long - fk to trade status table
uti code - varchar
trade date - date
trade start date - date
trade maturity date -date 
trade execution date - date
additional fields - long - fk for additional fields table 
last toucch timestamp - datetime - millisecond precision 


# Trade Leg 
Trade table has one is to many association with trade leg table. In practice a trade can have only 2 legs. 

# Trade leg table 
leg id - long 
tradeid (foreign key from trade table) - long
notional - big decimal
currency - long - fk to the currency tabe
legRateType (Fixed/Floating) - long fk to leg type field,
rate - double 
index (only applicable for floating legs) - long fk to index table 
holiday calendar (foreign key to holiday calendar table) - long fk to index table 
calculation period schedule - long fk to schedule table
payment business day convention (foreign key to bdc table) - long fk to  bdc table
fixing business day convention (fK to  bdc) - long fk to bdc table 
pay/recieve flag (fk for pay/rec leg) - long - fk to pay/rec table 

A trade leg can have many cashflows associated with the leg


# Cashflows table

A Trade leg can have many cashflows. A cashflow table has the below fields

Id - long - PK
leg ID  long - FK for leg
value - big decimal
value date - date
pay/rec - long FK for pay/rec table
payment type - long -fk for payment type table 
Rate - double 
payment business day convention - long - fk to payment bdc table 

# Counterparty table 

Counterparty table holds all the counterparty information. 

Id - long - pk
Counterparty name - varchar
counterparty address - varchar
counterparty phone number - varchar
counterparty internal code - long 
counterparty entity (entity to wwhich the counterparty is paperd with) - long FK to entities table
created date - date
last modified date  - date
active - boolean

# Books table

A trade from the user side is logically aggregated in a heirarchy. The lowest granular node of the heirarchy is the Book. 

Id - long - pk
Book name - varchar
Cost center - long - fk to cost center table
active - boolean
version - int - version of the book
Entity - long - fk to the entity table 

# Cost center table

 A cost center is one level above the book

 Id - long - pk
 Cost center name - varchar 
 entity - long - fk to the entity table
 Subdesk - long - fk to subdesk table

 # Sub desk table 

 A sub desk is one level above cost center. 

 Id - long - pk
 Subdesk name - varchar 
 entity - long - fk to entity table
 desk - long - fk to desk table

 # Desk Table

 A desk is a level above Subdesk

 id - long - pk
 desk name - varchar
 entity - long - fk to entity table
 

# User table 

A user table contains the user information

id - long - pk
First Name - varchar 
Last Name - varchar
login id - varcahr
password - varchar 
active - boolean
user type - long - fk to user type table
version
last modified timestamp

# User Profile  table 

id - long - pk
User type - varchar 
    values: TRADER, SALES, MIDDLE OFFICE, SUPPORT

# User previleges table
    Based on the user profile, each user has different previleges

TODO - how to implement previleges 

A TRADER can create new trade, terminate trade, cancel trade 
A Sales user can only book and amend a trade, cannot cancel or terminate a trade
A Middle office user can only amend and view a trade 
A support user can only view a trade 

# Trade type table 

id - long - pk
trade type - varchar
values for trade type : SWAP

# Trade sub type table 

id - long - pk
trade subtype - varchar
values for subtupe: Interest Rate Swap, Cross Currency Swap, Credit Default Swap

# Trade status table

id - long - pk
trade status - varchar
values for trade status: NEW, AMENDED, TERMINATED, CANCELLED 

# Currency table
id - long pk
currency - varchar

# leg type table 
id - long - pk
type - varchar
values for type : Fixed, Floating 

# index table 
id- long - pk
index  - varchar
values for index : //Todo get from isda indices available

# Holiday calendar table

id - long - pk
holiday calendar - varchar

//todo values from standard regional calendars

# Schedules table 

id - long - pk
schedule - varchar ;

1M, 2M, 3M ... upto 12M 

# Payment business day convention table
id - long - pk
bdc - varchar 
// todo get standard bdc 

# PayRec Table
id - long - pk
pay rec - varchar
values for payrec - Pay, Recieve


Database rules
- All primary keys are generated by sequence 
- 
- All entitites to have a last modified timestamp
- All entities to have an active field , created date time and deactivated datetime
- creation of a new record creates a new entry in the database with the creation datetime as now and deactivated date time as 0000000
- updation of an entry in the database makes the current active entry inactive, deactivated datetime as now and creates a new record
- For trade entity , trade id field stays unique for all the versions of the trade. 


# REST Api specifications

Use RSQL JPA specification to impement controllers 

# Implement, Controller, Service and Repository classes as per below 

Trade entity 
- get  
by trade id, book, counterparty, trade date or and a combination of these 4. use rsql jpa if it simplifies the implementation. Also get a list of all trades
- post 
to create new trade, amend an existing trade, cancel and existing trade and terminate an existing trade

Creating a trade
- Creating a trade creates trade, trade leg, & cashflows.  For other related entities, the name / value should always exist in  the corresponding entity table. For example.. a new trade which comes in with a counterparty name as ABC should have an entry in the counterparty table with the name ABC. 

Amending an existing trade
- Amending a trade will persist the new information coming from the payload. db creates a new entry and deactivates the old one. Sets the trade state to AMENDED

Terminating an existing trade
 - TErminating an existing trade will set the trade status to TERMINATED. 

 Cancelling an existing trade
 - cacelling an existing trade will set the trade status to cancelled. 

 Cashflow generation logic:
  One cashflow entry needs to be generated as per the schedule  and maturity date of the leg. Example for a leg with 1M as the schedule and a maturity date of 1 year from now, 12 cashflow entries need to be generated for every month. the value date should be calculated in the monthly schedule. Another example of a leg with 3 M as the schedyke and a maturity date of 1 year from now, 4 cashflows should be generated with value date of every 3 minths . Another example of a leg with 12 M as a schedule and a maturity date of 4 years from now , 4 cashflow needs to be generated with a value date of every 12 months 

  for a leg with leg type as fixed leg, the value of the cashflow should be calculated as rate /100 * notional value. For a floating leg the value of the cashflow should be 0 

  Other business validations
   - A trade can only be created with users, books and counterparties that has at least one active entry in the table at the point of booking. 
   - Maturity date of the trade cannot be before the start date or the trade date
   - start date cannot be before the trade date
   - trade date can be in the past 
   - if a trade leg maturity should always be equal 
   - if a maturity date is less than the minimum selected schedule validation error has to be thrown. 
   - A user can only action on a trade if he has the correct previleges. 


 All other entities - create get by id, and value . Post new entity , for other entities, only a user with support previlege can create or modify. all other users can read. 

# Deployments and setup
 - Set the project up to run locally as well as on a docker image
 

 # Front end Setup

  # Framework
  - Front end for the setup will be written on React 19
  - node will be used as the package manager
  - pnpm used for building, deploying and running locally
  - vite as the development server
  - vittest as the unit testing framework
  - playwright as the end to end testing framework
  - mobx as the state management store
  - tailwind css for styling 
  - AG grid react for data representation 

# Web UI Pages
 - Sign in / sign up page
 - Profile page
 - Trader / Sales view 
 - Middle office view 
 - Support / Admin view

# Common Components
 # Header
  - Masthead model header with drop down navigation on the top.
  - company logo on the left top corner followed by the application name
  - Search bar on top right 
  - Mast head dropdown buttons have Down sign at the end 
# Buttons
  - Primary green color
  - secondary red color
  - boxed 
  - rounded 
  - with shadow
  - on hover pops up

# Snackbar type notifications
  - Pops up and stays fore a default of 5 seconds
  - Green background for OK, Red for Error and Amber for warning 
# Overlay popup
  - pop up sliding down from the top of the screen. w, h fit to contents 


# Sign in / Sign up page
 - User avatar in the center, login id and password field down. 
 - Green button for Signing in justified and alligned center
 - Sign up button. On clicking sign up button, overlay pops up asking first name, last name, login id, password and the profile type , which is a drop down list from the user types table in the backend . 
 - The overlay has a sign up button which sends a post request to create the user entity. Based on success or failure a snackbar notification pops up 
 - Clicking the sign in, authenticates the user against the database and post successful authentication redirects to the trade page for all users, except for support users. For support users redirection to support page

# Trade home page
- A Traders home page has the Masthead drop down button New Trade and Amend trade
- Clicking on new trade loads a Trade modal. #
 # Trade modal
  Trade modal will have a center justified container. The left half of the container will have the modal field names, derived from the trade and trade leg table. all the trade table fields will be shown from the top. and the values on the right columns. When the leg level field starts, the value fields will be split into two, each representing each leg of the trade. for all the elements with the foreign key- a default value will be loaded, and the selection will be a drop down from the available values in the database. 
  This will then have a button to book the trade. on click the action will send the post request to the backend, backend converts the modal to entity and based on the response, a snackbar notification will be shown. if success, the snack bar notification will have the trade id in it 

  On the Amend trade page, there will be a central text box which takes the id of the trade. on enter or clicking a search button, the trade will be loaded fromt he back end and represented in the trade modal. 

  There will be an expandable button in the trade modal for cashflows. Clicking on it for a new trade will generate a cashflow section, and for the existing trade it will show each legs cashflow in different tabs loaded from the database. 

  # Blotter screen
  The blottor screen will render a grid showign the trade table in a tabular form. it will have all the trades booked by the user on that day. Potentially use react query to fetch the latest every minute. 

# Main instructions
Based on the description, I want copilot to create
- 2 modules front end and back end inside the project
- backend module with maven configuration and front end with node /pnpm/vite/react
- Create read me file in the respective modules explaining how to setup the project locally
- Create a comprehensive design documentation and functionality documentation which will help both co hort and the assesors understand the setup and functionality of the application
- Create a check list of the tasks for the candidates to strike of when complete
- create an assesment form for the assesors to assign points when they review the final submission. 
- create the database back end code and front end code for both the projects
- include unit, functional and end to end tests using the right frameworks