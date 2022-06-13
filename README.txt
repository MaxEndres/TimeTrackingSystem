Änderungen in der Klasse DatabaseService:
  - Neue Methoden:
    - createUser: creating a new userEntity
    - listAllUsers: listing all available users in an ObservableList
    - listAllRequests: listing all current requestEntity in an ObservableList
    - createTimestamp: create a certain timestamp when a userEntity presses start/pause/stop button
    - createRequest: create a requestEntity when a userEntity wants to change a timestamp they created
    - acceptRequest: admin can accept a requestEntity which then updates the desired timestamp
    - denyRequest: admin can deny a requestEntity which simply deletes it from the database
    - getWorkedHours: userEntity wants to see how much hours they worked in a certain month (month is a number between 1 and 12)
    
    
  --> die Methoden wurden noch nicht alle getestet. Insbesondere getWorkedHours ist wahrscheinlich noch nicht final, könnte aber so oder so ähnlich funktionieren.
  
  In der Datenbank befinden sich nun außerdem ein paar erste Dummy Daten zum Testen.
