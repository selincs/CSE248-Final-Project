# CSE248Project
Program that assigns instructors to classes with data parsed from Excel sheets. Displays each instructor's personal availabilities and allows all assignments to be made via GUI, adjusting schedules and courses as assignments are made.

File parses the .xlsx file in Java via Apache POI, a third party library. After skipping the initial row to find the start of the data entries, the program delimits each new instructor via the "—" key in Cell 0, skipping the rest of that particular row to the data. The program uses a importInstructors utility package to populate each Instructor object utilizing a String array to hold data, then inputting said data via a Switch case switched via a rowCounter. Once all rows have been read, the last Instructor is manually added to the list. 

With JavaFX, a user may use the MenuBar to select their .xlsx file from the Project_GUI.java file found in the application package and automatically run the above code to import all data of all instructors. Then, they may search via Instructor ID to display all current Data on the selected instructor in the GUI via GridPanes. The first GridPane displays the relevant info in text format, while the second GridPane displays a graphic array populated with colored buttons that change between green(available) and red(unavailable)depending on whether an instructor is available during a certain period of a specific day. The TextArea for searching via name currently does not have a function.

Courses objects and Section objects have been added, along with respective sets to contain each object. CourseSet is a static object that contains all courses of college, each course containing a section set with sections created for each individual instances of a course with appropriate data included during the semester. Introduced new organization of packages, alongside appropriate converters to ensure data is stored properly. More converters to come, and CSVtoXLSX converter is not yet in use. Course and section data is currently processed via CourseDemo.java.

#https://www.markdownguide.org/cheat-sheet/
