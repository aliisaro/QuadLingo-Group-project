<h2> Why have interfaces and then separately implement them?
Why not just have the class implement the interface directly? </h2>

**Explanation:** 
By CoPilot

These interfaces and their implementations are necessary if you want to maintain a clean architecture and 
follow good software design principles. Each interface represents a contract for interacting with 
a specific part of your database (users, progress, and quizzes). 
The implementations (UserDaoImpl, ProgressDaoImpl, QuizDaoImpl) provide the specific logic for 
interacting with the database.

This separation of concerns makes your code more modular, easier to maintain, and more flexible. 
If you need to change how you interact with the database (for example, if you switch to a different database system), 
you only need to update the implementations, not the interfaces or the parts of your code that use these interfaces.  

However, whether you need all of these interfaces and implementations depends on the specific requirements of your project. 
If you find that some interfaces or implementations are not being used, or if their functionality is duplicated elsewhere, 
it may be a good idea to refactor your code to remove unnecessary components.