# PGR208-Androidprogrammering
Eksamensbesvarelse i PGR208 Androidprogrammering ved Høyskolen Kristiania V20

Dette er en eksamensbesvarelse i faget Android Programmering ved Høyskolen Kristiania våren 2020.
Oppgaven var å lage en liste, koblet til NoForeignerLand sitt API, som listet steder og viste informasjon og location for hvert sted.
Oppgaven ble sensurert til en A.

Obs! 
API-endepunktet til NoForeignLand har endret seg siden eksamen ble gjennomført og appen fungerer dermed ikke lengre. 
API-nøklen til Google maps er deaktivert og derfor fungerer ikke maps-delen av appen. 

I tillegg til appen, skrev vi en kort beskrivelse av valg og beskrivelse av appen.

## Report 

My approach has been to overall try to adhere to the MVVM architecture as specified in the Google
Guide to app architecture. I've implemented ViewModels holding the data (defined in data-model
classes) for the UI. The data in the ViewModels are wrapped with the LiveData data holder class, so
that updating the UI only happens when the fragment is in an active lifecycle state. The repositories
are responsible for fetching data from either the API endpoints or the implemented Room database.
I'm a little uncertain whether it was a good idea to call on the database-repository in the apirepository, as that would mean that they are dependent on each other. The same goes for calling on
the API-repository to fetch data from the API and add it to the database directly from the
SplashActivity, because it means that the SplashActivity currently is directly reliant on the APIrepository. My reasoning for keeping it this way, is that the call is not relevant to any UI-component.
API calls and caching

#### API fetching and caching

I decided on caching the id and name of all locations from the first API endpoint in a
location_database when the SplashActivity shows on opening the app. When the user presses a
location-name in the list of locations, the second API endpoint is called to get the image URL,
comment(description), longitude and latitude. If a URL is defined, I've used the Picasso library to call
the URL and load the photo into the ImageView in the LocationDetailsFragment. Currently this
implementation is a bit slow, it might have been better to make the API calls for details while the
location is visible in the recyclerview, so that on clicking it would only be necessary to do a network
call for the photo URL. However, this would've led to many unnecessary network calls, so I decided
against it. When clicking the location icon in the list, it does a similar call to the second API endpoint
and retrieves the LAT and LON values for the location, that gets loaded in the LocationMapActivity.
Libraries


#### Libraries
I've used a lot of the libraries demonstrated in class.

- For the SQLite database implementation, I have used the Room persistence library. The room
library is built to work with LiveData.
- I've used the Retrofit2 library for interacting with the NoForeignLand API. I've implemented
async fetching and parsing by using the .enqueue method of the retrofit Call objects.
- For parsing the responses from the NoForeignLand API I've used the GSON
deserialization/serialization library, that converts JSON strings to Java objects.
- For loading photos from a photo-URL into an ImageView I've used the Picasso library.