# Welcome!
Hi there! Welcome to my CPSC 210 term project - the GroceryBuddy app!

This is a project I made in my 3rd year at UBC as part of the CPSC 210, Software Construction. This project was meant to
test our ability to apply core concepts such as data abstraction and demonstrate an understanding of how to analyze
program structure.

This project was separate into 4 components:
- Phase 1: Making a console app
- Phase 2: Using JSon to store and load program data
- Phase 3: Designing a GUI
- Phase 4: Implementing an event tracking log & reflection

Something I'm particularly proud of is that a bonus was given to students who were able to implement a means of playing
audio, which I was able to complete! This project plays audio from the Pokemon Emerald video game (specifically, the 
home screen music and button press sounds).

I found this experience super rewarding - here's the original ReadMe that I wrote as part of the project and I hope 
you enjoy exploring this project!

Sincerely,
Nathan

_____________________________________________________

# Grocery Buddy (Nathan Chang Personal Project, CPSC 210)

### What is this?
*Have you every struggled to remember what you need to purchase the second you arrived at a grocery store, resulting in
multiple inefficient end-to-end walks across the aisles?* 

Look no further than **Grocery Buddy**, an app that can generate a
grocery list with all the ingredients you need to meal prep for week!

This application caters to students and people living on their own for the first time and are just grasping independent
skills, or people who might struggle keeping mental lists to track numerous tasks in a stimulating environment.

This project is of interest to me because I started grocery shopping and meal prepping on my own in secondary school,
which was a struggle in terms of executive function. After moving to residence at UBC, a lot of my peers observed that
there is difficulty being able to balance learning independent living skills, while dealing with academic commitments.
I believe this app could help alleviate some of these external stressors faced by students and people living away from 
home for the first time.

### User Stories:
- As a user, I want to be able to add a dish and its ingredients to my GroceryList.
- As a user, I want to be able to calculate the cost of all the items on my GroceryList.
- As a user, I want to be able to verify that the dishes on my GroceryList contain enough servings to have food for the 
week.
- As a user, I want to be able to add a new dish, its number of servings, and ingredients to a list of possible dishes 
to include on my GroceryList.
- As a user, I want to be able to save a GroceryList and dish.
- As a user, I want to be able to load a previously saved GroceryLists and dishes.
- As a user, I want to be able to see all dishes and ingredients have been added to GroceryList
### Phase 4: Task 2
Here is a sample event log with the following actions taken: load grocery list, create grocery list, 
add/remove dish, and save.

Successfully loaded Grocery List!
Saved to ./data/myFile.json
Fri Dec 01 14:51:20 PST 2023
Loaded GroceryList from json.
Fri Dec 01 14:51:20 PST 2023
Created GroceryList Nathan's list.
Fri Dec 01 14:51:20 PST 2023
Added dish Spaghetti Marinara.
Fri Dec 01 14:51:20 PST 2023
Added dish Tomato Egg.
Fri Dec 01 14:51:20 PST 2023
Added dish Mapo Tofu.
Fri Dec 01 14:51:20 PST 2023
Added dish Mapo Tofu.
Fri Dec 01 14:51:20 PST 2023
Added dish Tomato Egg.
Fri Dec 01 14:51:25 PST 2023
Remove dish Tomato Egg.
Fri Dec 01 14:51:31 PST 2023
Saved current GroceryList to file.

### Phase 4: Task 3
If I had more time, here are some things I might change:
- Refactor the AddDishWindow to separate the user database from the construction of the window itself. This class 
currently contains 2 separable functions, thus going against the Single Responsibility Principle.
- Refactor the data file to include separate files for backgrounds/visuals and audio. This makes it easier to find
specific files for the PlaySound method.
- Standardization: some of the classes vary in how they are structured. For example, in the more complex classes 
(GUI, EditGroceryListWindow, AddDishWindow) I separated all changes to the properties of the J-components prior to
adding them to the respective JPanel. In contrast, in simpler classes (CreateGroceryList, RemoveDishWindow) I
implemented a PlaceComponents method to act on all the components and place them on the JPanel. I personally found
the separation more useful when debugging the more complex windows but recognize that the added helper methods have 
lower value when the class is simpler.
- Include return keys on all windows to return to the home screen. It makes the app more user-friendly and makes
testing easier (you don't have to restart if you make a typo or click the wrong button).