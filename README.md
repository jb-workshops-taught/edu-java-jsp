# Sample Project for Learning jsp and servlet Web Development

This project is a working "stub" application for learners to use to get 
started.  

## To use this stub

1. Fork this project on github.com, so you have your own copy.
Look for the Fork button in the upper right of the web page.
2. In mid-page, or the lower right, find the "SSH" or "HTTP" button
next to a URL, with a "Copy to Clipboard" button next to it.
3. Push Copy the Clipboard
4. In a terminal window (mac) or git-bash (windows), `cd` to
the folder where you want your project to appear.
5. Then run (Notice your username in that address - not mine!) :

     git clone git@github.com:yourusername/edu-java-jsp.git

6. Then launch Eclipse.
7. From the File menu, Import, then "Existing project into workspace".
8. Navigate to your edu-java-jsp project folder and click Open.

## So, what's in this project?

`src/main/java` - contains the Java code

`src/main/resources` - contains properties files when needed

`src/test/java` - contains the jetty Start application for local testing, and may contain unit tests.

`src/main/webapp/` - contains html content and jsps, plus the WEB-INF/web.xml file

`target/` - contains compiled code (excluded from git)

`pom.xml` - maven configuration file that controls what library dependencies are included

In addition, Eclipse shows a few extra icons like "Maven Dependencies" and "JRE System Library" that are
placeholders for library jars.  You can actually spin these open with the triangle, and see what
jars are included, and open those, and see what packages and classes are provided.  It's not usually 
needed, but if you're curious where a provided class lives, the icons give you a graphical way to
view those.

## What does development workflow look like?

You will create servlets in `src/main/java/your.package.name/` and jsps in `src/main/webapps/*.jsp` (or
optionally in folders within that).  You will start jetty running, and go to:

     http://localhost:8080/

in a web browser of your choosing.  As you modify servlets and jsps, jetty will automatically
pick up most changes, if you just reload the web page.  If it fails to pick up a change, you
can stop and restart jetty to see your change.  (Do make sure your code is compiling first; if
it's not recompiled, then jetty won't pick up the change.)

As you add new servlet urls, you may need to edit `web.xml` to match pretty urls with the
servlet classes that support them.

You may also wish to add Java classes that aren't servlets, but which instead help with 
database access, or represent data entities, or whatever.  You can create any packages
and classes you want within `src/main/java/` and they will be included in the web app.

You may also want to write unit tests; those go in `test/main/java/` instead.  Anything 
in `test/` is not available from jetty (except Start.java that runs jetty itself).  The
test folder is meant for unit tests that are only needed by the developer, not in a
running web app.

Periodically, as you finish a feature, you will want to save your progress:

     git add --all .
     git status
     git commit -m "Added feature blah blah blah" 
     git push

## How do I use a database with my app?

One of the maven dependencies is the mysql connector jar.  This is a jdbc driver that
lets your application talk to a MySQL database. (JDBC = Java Data Base Connectivity)

Additionally, there is a properties file listing out the driver class name, the database url,
and a database schema name, username, and password, for the database.  Java code
reads this properties file and uses it to configure the driver to talk to the right database.

Creating a database connection turns out to be a somewhat slow process (a few seconds is slow
in code time) so instead of creating a new connection each time you need it, there is a pool
of several connections that are pre-created and held available by the application.  You
can "borrow" a connection from the pool, use it, and put it back, much faster than it can
create new connections; using a pool speeds up your app significantly.  So, one of 
the maven dependencies is a connection-pooling library, and a helper is provided to 
make it easy for your code to get a connection from the pool.

## How do I rename this project to something like web-pet-store?

You've already done a fork and clone.  You have a copy of the code.  But the project name
isn't to your liking, and you'd like to separate from my git naming entirely.  How do you
do it?

Commit your existing work first.  The project name change will be its own step in git for simplicity.

     git add --all .
     git commit -m "Finished blah blah feature"
     git push

Then there are a few steps.

### 1. Eclipse Project Changes for Project Rename

First, we'll Close the project in Eclipse (right click the project, choose Close Project).  
Eclipse would just get confused during this renaming process anyway.

Then, use a text editor to rename the project in the `.project` and the `pom.xml` files.
The name appears twice or more in each file.

Then go to the project directory using file system tools, and rename the directory to 
your new project name (no spaces but dashes are okay).

Now in Eclipse, use File, Import, Existing project into workspace, and choose your newly
renamed directory.  If the project comes up with no errors, you're in good shape;
otherwise, check the Markers or Problems tab and troubleshoot.  You probably missed a
name somewhere.

Next, look for any Java package names that contain the old name, and use the right-click, Refactor, Rename
tool to choose a new name for those.  You might also need to modify `web.xml` to match if you
renamed any of the packages that servlets are in.

### 2. Git Changes for Project Rename

Got it working?  Commit the changes, but don't push yet.

     git add --all .
     git commit -m "Changed project name"

Okay, now we need to repoint git to a new repo.

Go to github.com and create a New Repository, and give it the name to match your new
project directory name.  Skip the creation of a README or a license file.  Then in your
local terminal or git-bash, from inside of your existing project, you will set a new
origin.

Remember to modify the command below to set `yourusername` and `your-new-project-name` to
something appropriate.

     git remote add origin git@github.com:yourusername/your-new-project-name.git
     git push -u origin master

The first line changes which remote git repository your project points to.  The second
pushes your latest changes plus all the history to the new repo.  

Go to your web browser, and refresh the github page, and you should be able to see the code now.

At this point, you should be done.  You have your new project name, and you've abandoned
the prior git repo with the old project name.  Both git and eclipse know about the new
name, and you have a fresh git repo that has no relationship to the original fork except
for some similar code.

As a bonus, you now know all the locations where your project name appears in the project code!



