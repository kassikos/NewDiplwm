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
  
  






