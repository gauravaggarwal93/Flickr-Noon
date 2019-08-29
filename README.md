# Flickr Sample
A Project to show implementation of Flickr API with endless feed, written in kotlin using MVP Structure.

### This project uses:
* **RxJava 2** - For Reactive Programming
* **Dagger 2** - For Dependency Injection
* **Retrofit 2** - As Type Safe HTTP client
* **Room** - For persistence storage(abstraction of SQLite)

### The app has following packages:
1. **base:** It contains base classes
2. **data:** It contains all the data accessing and manipulating components like data models, sources, repositories
    * model
      * local
      * remote
    * source
      * db
      * network
      * prefs
      * repository
         * local
         * remote
3. **di:** Dependency providing classes using Dagger2.
    * component
    * module
4. **ui:** View classes along with their corresponding Presenters.
    * custom
    * adapters
    * main
5. **service:** Services for the application.
6. **utils:** Utility classes.
    * fonts 
    * rx 

