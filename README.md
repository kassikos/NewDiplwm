# NewDiplwm

~~~~~~~~~~~AppDataBase class~~~~~~~~~~~~
This class is an abstract and extends the RoomDatabase because in this way we define our db
The annotation Database create the tables we provide in the curly brackets in our case the tables 
are the following ({User.class, Game.class, GameEvent.class, Statistic.class}). In adition if we want to grow up the db the we hae to 
change the version
The type converters are used to "create" sqlite datatypes.
In order to guarantee that there is no way for conflicts we create a sinchonized singeton. We also use the "on Create" callback to create
the game instances when the user signup.


