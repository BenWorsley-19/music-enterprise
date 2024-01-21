# Music Enterprise

The code here is structured and written so to provide an example of an enterprise-like codebase to showcase OO design 
and styles of tests for those who have not had much experience working in such codebases. I have not used a framework
here or ORM to keep the codebase simple and easy to understand.
I have provided some thoughts in the code base in the form of comments/docs with "NOTE:" next to them.

## Code problem statement
You work for a very popular music streaming service. Your product manager wants to implement a "Quick Listens" 
feature to your service and so we need a system which gives us a list of 20 tracks that are less than 2 minutes 
as well as their genre.
The PM also wants the total price of those tracks. Apply a 20 percent discount to tracks in the Rock genre.
We will also want to be able to display the total runtime of the tracks in the list in minutes and seconds 
such as 12:24.

The code here delivers this requirement though interacting with a MySQL database.

## Prerequisites and set up

- java 17+
- gradle 7.2+
- Set up the database following these instructions: https://github.com/lerocha/chinook-database?tab=readme-ov-file
  (It is on my TODO list to make this easier with docker)
- Set up the following environment variables:
    - MUSIC_ENTERPRISE_DB_URL - e.g. jdbc:mysql://localhost:3306/chinook
    - MUSIC_ENTERPRISE_DB_USERNAME
    - MUSIC_ENTERPRISE_DB_PASSWORD
