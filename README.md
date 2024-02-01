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
