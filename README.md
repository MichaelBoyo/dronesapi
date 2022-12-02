## Drones API

---
### Build

- unzip the file into your computer or clone from gitlab by running 'git clone https://gitlab.com/MichaelBoyo/dronesapi.git'
- locate the  folder in your terminal
- ensure you have maven and java 18 sdk installed
- run 'mvn package' in your terminal or command line to build the project 
---

### Run
- run  'java -jar target/drones-0.0.1-SNAPSHOT.jar' in your terminal or command line to start the application
- the application will be started on http://localhost:8080

### Test
- visit http://localhost:8080 and you should get "Welcome to Drones Api"
- to get data on all drones visit http://localhost:8080/all_drones
- make a POST request to http://localhost:8080/api/v1/drone/register to add a new drone. required request body 
{ "model": "",
  "serialNumber": "" }
- make a PUT request to http://localhost:8080/api/v1/drone/load to load the drone with medication items. 
required request body
  { "droneId": 1,
  "name": "test_name",
  "weight" : 420.0,
  "imageUrl": "test_url",
  "code" :"test_code" }
- make a GET request to http://localhost:8080/api/v1/drone/loaded_medications to get loaded medication items.
  required request body
  { "droneId": 1}
- make a GET request to http://localhost:8080/api/v1/drone/available_drones to get available drones for loading.
  no request body is required
- make a GET request to http://localhost:8080/api/v1/drone/battery_level to get battery level of a drone.
  required request body
  { "droneId": 1}
# dronesapi
