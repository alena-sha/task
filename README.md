Technologies:Spring Boot, Java 8, JUnit 4, HSQLDB (in memory database).

Classes are distributed in packages: model,repo,controller.

Model package contains 2 classes: Voting (represents a theme for voting, ex.: "Do you like Java?"), VotingOption(represents an option for voting, ex.: "Yes" or "No", this class also contains number of votes).

Repo package contains classes to work with DB. VotingRepository, VotingOptionRepository are interfaces which extend CrudRepository (perform simple CRUD operations), RepositoryConfiguration - configuration for Spring Data, VotingLoader - creates some initial data in database after starting the app (adds two voting themes with options).

Controller package contains REST controller, which gets and retuns data in JSON format.

Also, I wrote JUnit tests to test each REST method of the controller.
