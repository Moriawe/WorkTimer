# Data

### CurrentStartTime
Used to make the app remember if the timer was started or not and to keep track of the time it was started

### TimeItem
How I chose to save time in the database
I chosed to limit it as much as I could and calculate the total time outside of the database.

### TimeConverter
Need to make Room understand how to save LocalDateTime objects in the database

### DI
Dependency injection with Dagger/Hilt


# Domain
Using models to display the data in the UI and a mapper to transform from database to UI via use cases.

### UseCase
All communications with the repository goes through the use cases for testing purposes and separation of concern.
I handle errors through the RepositoryResults that sends the error log and snackbar text back to the viewModel.

# Navigation
I used a sealed class for my navigation items and added a function for when I need to send arguments when moving between screens.
The NavGraph holds the snackbar handling as well as all screens and dialogs the user can go to. 
I chosed to plae my dialog here as well since I wanted it to have its own view model. That also ment I needed to send the information about the timeitem via the nav arguments and following the recommendations I only send the INT id and not the whole object.
I decided to reuse the same dialog for both the daily overview and the yearly overview screen. The dialog will check which date the object is created and just change the time. In the future I might add a bigger dialog where you can use a android date and time picker for this.

# Presentation

