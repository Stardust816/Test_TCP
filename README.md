# Airline-Reservation-Plattform

- Thomas Molterer (ic21b501@technikum-wien.at)
- Stephan Weidinger (ic21b064@technikum-wien.at)

## Must Have Features (Genügend - 4)

Eingabe der Sitzplätze über ein Terminal. 

Server/Client Application zur Durchführung von Reservierungen.

Der Server soll zum Speichern der Daten fungieren. Client zum Reservieren und Darstellen der Daten.

Eingabe der Buchung über CLI des Clients. Anzeige der gebuchten Sitzplätze über GUI.

“Abspeichern” der belegten Sitzplätze in einem Dokument zum späteren überprüfen.			

## Should Have Features (Befriedigend - 3)

Ticket Confirmation Message für die Passagiere.

Flugauswahl also -> “betrifft Flug XY von Wien nach …”

					
## Nice to Have Features (Gut - 2)

Anzeigen des “fertigen” Tickets mit allen zuvor eingegebenen Daten.

Priorisierung von Vielfliegern, Vorteilscard Besitzern etc.
						
## Overkill (Sehr Gut - 1)

Sitzplatzreservierung für unterschiedliche Flugzeugtypen einer Flotte.

Sitzpläne so “real” wie möglich in der GUI realisieren.

Applikation für Airline Mitarbeiter zum Darstellen der gesamten Sitzplätze in einer GUI.

# Program Description
The project includes 3 GUI Interfaces to control and interact with a server instance on port 1286, 1287, 1288.
The idea is to make a small booking system for airplanes, which stores the data in files. The booking is done with a GUI, which loads the stored flight information and makes it possible to book new persons to a flight or delete them.
A second GUI is for the user, who wants to check his flight information. With the combination of the flight number and the Passport number, the server will send the requested information.
The last GUI starts and stops the server instances.
Before you work with the GUIs, which are responsible for interacting with the server, you need to start the “ServerHandler.java” to start the server instances in different threads. After this, the other GUIs can communicate with them.
The data is stored in different .txt files which are included in the project directory.
The location is coded in the program itself. Before the server can start, they need to be adopted.

# Get it Started
Bevor you can start the server, you need do adopt the path for the files in the Server1.java.
The paths are hard coded and need to be adopted to your directory.
If this is done, you need to start the Project in the right order:<br>
1) Start ServerHandler
 
2) Start the Server 1-3 

	 1) HalloApplication => Booking System; 
	 2) TicketRequest => User interface to get Flight Information; 
	 3) => ServerCLI => direct interface to the Server
	 
3) <br>
	1) Start HalloApplication <br>
  	2) Start TicketRequest <br>
  	3) Start ServerCLI

Now the system is running.

To stop everything you can close the GUI interfaces for interaction with the server
The last step ist to stop the server's with the stop button or to close the ServerHandler Application.
