# NewDiplwm

-----------------AppDataBase class---------------------

This class is an abstract and extends the RoomDatabase because in this way we define our db
The annotation Database create the tables we provide in the curly brackets in our case the tables 
are the following ({User.class, Game.class, GameEvent.class, Statistic.class}). In adition if we want to grow up the db the we hae to 
change the version
The type converters are used to "create" sqlite datatypes.
In order to guarantee that there is no way for conflicts we create a sinchonized singeton. We also use the "on Create" callback to create
the game instances when the user signup.

-----------------AudioPersonPick activity-------------

The AudioPersonPick Activity is one of the app games. In this game you have to choose the right desciption of the displayed objects.
For listening the description you have to tap the play button which is displayed at the bottom of the screen. We support the game in landscape and portrait mode. While playing or listening the audio you are able to do any screen configuration for example if the game detect screen orientation then the audio keep playing and the new layout replace the old one. The "OnsaveInstanse State method" is required for saving variables the time that activity detect any configuration. In this way when the "Oncreate" method is called we assign the variables which we saved. So the activity continues running normally. There is also the "createRound" method where we define the number of rounds and which mode we will display. we create three modes "easy", "medium" and "advance" so there are three display functions. 

The activity also implements the "OnClickListener" in order to handle the clicks using one "onClick" method. The "nextRound" function handles the rounds, the timer between the rounds and the messages.

The "showTutorialPopUp" display the Turorial video with  the equivalent resource calling the Tutorial class.

The "shopPopUp" functionraise the allertDialog that inform user about his progress.

The "countPoints" function is responsible for counting the points and display the points of every single round 

-----------------BucketGame activity-------------

All games have the the same template. In this game there are some diferences as the player have to drag and drop the items. Every object implements a "custom TouchListener". The TouchListener detects the motion so we create a shadow of the objech which is selected from the user. From the other side the "buckets" objects have to implement a "custom DragListener" to interact with the objects we mention before. The DragListener has the following states
 "ACTION_DRAG_STARTED:" this state inform us that an object picked from the User.

"ACTION_DRAG_ENTERED" this state is activated when the object which is dragged interact with the specific area.

"ACTION_DRAG_EXITED" this state is activated when the object which is dragged left the specific area.

"ACTION_DROP" this state is activated when the object is dropped in the specific area

"ACTION_DRAG_ENDED" this state is activated when the motion is completed


-----------------Charts activity-------------

The graphic charts in our app are created with the powerfull library "implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'"
The library is on github and gives you the chance to create easily graphic charts. We retrieve the data to display from our database  and create a "List<PieEntry>" then we pass the List<PieEntry> in a "PieDataSet" to edit the graphics. In the end we created the PieChart.


-----------------DialogMsg  class-------------

The DialogMsg class handle the allert dialog when the game is over. This class extends the "DialogFragment" but in our case a new .xml file is created which overrides the standard layout. In "onDismiss" method an intent will transfer the user in the gameActivity


  
-----------------Game class-------------

This class is a model. The annotation "Entity" is required in order to be a table in the db. The annotation @PrimaryKey(autoGenerate = true) is used to autogenerate the id of each game


-----------------GameAdapter class-------------

The GameAdapter Class is required for the recycler view. We pass the data we need then we count the data and "onCreateViewHolder" create a Holder for each item. The "onBindViewHolder" binds the position with the holder. It was necessary to create a clickInterface beacuse we wanted each item to be clickable


-----------------GameDao Interface-------------

This interface hold the queries which are related with the games


-----------------GameEvent class-------------

This class is a model. It holds information about the relation between user and  game for every round. That is why we use the annotation "ForeignKye"


-----------------GameEventViewModel  class-------------

This class send data to the Asynk

-----------------GameHelper  class-------------

The GameHelper class is a helper class. This class holds some global information which are common everywhere in the app for example the  games instances, a map of greek - english game name and the csv writer class.

-----------------GameList Activity-------------

This Activity is the main Activity of the app. It holds the menu and the games. This activity also implements SharedPreferences.OnSharedPreferenceChangeListener, NavigationView.OnNavigationItemSelectedListener. The first one implementation is to save user's preferences and the second one is the menu item listener.
In this activity we ask for permission to read and write in storage. In addition we handle user's game choise "gameAdapter.setOnClickListener" with this statement. We also override "onBackPressed" to display an alert dialog that exits the app.
The " setupPreferences" function holds the user's preferences and we can retrieve them every single time that the user join our app.
The "onSharedPreferenceChanged" save and display instantly if the user make some changes in his preefrences.
The "onNavigationItemSelected" function handles the user's option in the menu.
There is also a class ExportDatabaseCSVTask which extends Asynctask and create a .csv file with the user's stats.


-----------------GameViewModel class-------------

This class extends the "AndroidViewModel" and it holds a single game or a list of games it depends on the function which is called. 


-----------------Login Activity-------------

This activity is the first activity the user face up when join the app. First of all we check if his preference is to stay logged in , if so an intent will transfer him in the GameList otherwise he has to fill his username and if it exists in the db will successfully log in.

-----------------MemoryMatrix Activity-------------

MemoryMatrix activity is a game. As we mention before all games have the same template so we will not analyze all of the functions again. We will analyse the specialities. In this game there is a great difference in the layout between the difficulties. So it was necessary to create separate fragments for each difficulty. So in "checkMode" function we check the choosen difficulty and load the appropriate fragment. We load fragment using "fragment manager" then we create a "fragment transaction" and we provide the tag of the fragment in order to handle it later. The fragments communicate with the activity via an interface which the activity implements called "OnDataPass". It is an interface which pass data from fragment to the activity in order to handle the rounds and the points. There is also a "checkIfEnds" method which check if the game is over.

The fragments we mention before are  "MemoryMatrixAdv" and "MemoryMatriEz"







