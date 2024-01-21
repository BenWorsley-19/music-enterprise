# Music Enterprise

The code here is structured and written so to provide an example of an enterprise-like codebase to showcase OO design 
and styles of tests for those who have not had much experience working in such codebases. I have not used a framework
here or ORM to keep the codebase simple and easy to understand.

## Code problem statement
You work for a very popular music streaming service. Your product manager wants to implement a "Quick Listens" 
feature to your service and so we need a system which gives us a list of 20 tracks that are less than 2 minutes 
as well as their price and their genre.
The PM also wants the total price of those tracks. Apply a 20 percent discount to tracks in the Rock genre.
We will also want to be able to display the total runtime of the tracks in the list in minutes and seconds 
such as 12:24.

The code here delivers this requirement though interacting with a MySQL database.

## Prerequisites

- java 17+
- gradle 7.2+
