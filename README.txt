Änderungen in der Klasse DatabaseService:
  - Neue Methoden:
    - createUser: creating a new user
    - listAllUsers: listing all available users in an ObservableList
    - listAllRequests: listing all current request in an ObservableList
    - createTimestamp: create a certain timestamp when a user presses start/pause/stop button
    - createRequest: create a request when a user wants to change a timestamp they created
    - acceptRequest: admin can accept a request which then updates the desired timestamp
    - denyRequest: admin can deny a request which simply deletes it from the database
    - getWorkedHours: user wants to see how much hours they worked in a certain month (month is a number between 1 and 12)
    
    
  --> die Methoden wurden noch nicht alle getestet. Insbesondere getWorkedHours ist wahrscheinlich noch nicht final, könnte aber so oder so ähnlich funktionieren.
  
  In der Datenbank befinden sich nun außerdem ein paar erste Dummy Daten zum Testen.
