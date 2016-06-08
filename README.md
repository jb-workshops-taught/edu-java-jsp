# Sample Project for Learning jsp and servlet Web Development

This project is a working "stub" application for learners to use to get 
started.  

## To use this stub

1. Fork this project on github.com, so you have your own copy.
Look for the Fork button in the upper right of the web page.
2. In mid-page, or the lower right, find the "SSH" or "HTTP" button
next to a URL, with a "Copy to Clipboard" button next to it.
3. Push "Copy to Clipboard"
4. In a terminal window (mac) or git-bash (windows), `cd` to
the folder where you want your project to appear.
5. Then run git clone (paste) (Notice your username in that address - not mine!) :

     `git clone git@github.com:yourusername/edu-java-jsp.git`

6. Then launch Eclipse.
7. From the File menu, Import, then "Existing project into workspace".
8. Navigate to your edu-java-jsp project folder and click Open.

## So, what's in this project?

`src/main/java` - contains the Java code

`src/main/resources` - contains properties files when needed

`test/main/java` - contains the jetty Start application for local testing, and may contain unit tests.

`src/main/webapp/` - contains html content and jsps, plus the `WEB-INF/web.xml` file

`target/` - contains compiled code (excluded from git)

`pom.xml` - maven configuration file that controls what library dependencies are included

In addition, Eclipse shows a few extra icons like "Maven Dependencies" and "JRE System Library" that are
placeholders for library jars.  You can actually spin these open with the triangle, and see what
jars are included, and open those, and see what packages and classes are provided.  It's not usually 
needed, but if you're curious where a provided class lives, the icons give you a graphical way to
view those.

## What does development workflow look like?

You will create servlets in `src/main/java/your.package.name/` and jsps in `src/main/webapps/*.jsp` (or
optionally in folders within that).  

You will start jetty running, 

    test/main/java/com.codeforanyone.edujavajsp/Start.java (right click, Run as Java Application)

and then go to:

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

The `DataSource` class in `com.codeforanyone.edujavajsp.database` demonstrates (in its main() method) 
how to get a database connection from the pool and close it when you're done.  This same
technique will work from a servlet or from a helper class.


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



# Web Application Development Vocabulary

`Authentication` means identifying yourself to the software, usually with a username and password.  It
ensures that you are who you claim to be.

`Authorization` means the software checks who you are against a list of permissions you're allowed to have,
and ensures you have the permissions to take a certain action.  You must already be authenticated before
the software can determine whether you're authorized to have access to a certain thing.

`Auth` may refer to either or both of those.  Web apps typically require both, and the implementations are
blended, so people shortcut the name to auth.

`Cache` (pronounced cash) means fast temporary storage.  It is both a noun and a verb.  A software product will 
cache a set of data by saving it into a cache in memory or on disk.  

A cache is intentionally temporary, with an expiration date and time after which it's considered to be 
out of date.  The software recognizes that when that time has passed, it should ignore what's in the 
cache and go get a fresh copy instead.  Your web browser performs caching with images, css, 
and other content that is mostly unchanging.  It is less likely to cache something like your 
Facebook feed content which is constantly updating.  Sometimes web servers also have a caching 
layer, where they hold on to content that is unchanging and have it more quickly available for 
browsers.  The headers in an http request and response give hints and requirements to the 
web browser about what is and isn't safe to cache.

`Web server` means software that runs on a computer and answers http requests, giving back static (stays-the-same)
or dynamic (changes depending on data) content to the browser.  It's a software program running on a computer (in 
our case, jetty).  It stays running as long as that computer is up, or until we stop it on purpose.  When it
receives a network request on port 80 (normal http) or port 8080 (if we tell it that), it looks at the URL to
determine what content to respond with.

`Servlet` is a Java class that extends javax.servlet.http.HttpServlet class.  A web server can be configured 
to know that servlets provide content.  Then your `web.xml` file tells the web server exactly which url goes with
exactly which servlet.  The servlet receives data in an HttpServletRequest object and responds through
an HttpServletResponse object.  Usually it responds with html, but sometimes binary image data might be used.

An `http request` is a specially formatted chunk of text (it's just a String) with a header section and a
body section.  During a GET request, the URL will have several sections to it that indicate data about the
request.  During a POST request, the URL is still present, and also the body section has additional (sometimes
large) data.

The parts of a URL (as understood by a servlet) may include: 

`https://docs.oracle.com:80/cd/E19226-01/820-7627/bncby/index?search=foo&page=3`

*  protocol `https`
*  server hostname `docs.oracle.com`
*  port `80`
*  context path `/`
*  servlet - `/cd`
*  path info `/E19226-01/820-7627/bncby/index`
*  query parameters - `?search=foo&page=3`

The exact nuances of context path, servlet, and path info can vary depending on the exact configuration
of that web application.  The context path points to a specific web app if multiple apps are installed
on a web server, but it will be null for the ROOT.war web app installed at the / context path.

An `http response` is data with a header and a body, (no url though!) which indicates what type of data 
content it's returning, and then provides that content.  It might also have some headers for cookies, 
session identifier, caching, and more.  

A `status code` is a particular header on the http response.  It will be `200 OK` if it worked right or
some other code (like `404 Not Found` or such) if the request failed.  See [wikipedia's 
http status codes page](https://en.wikipedia.org/wiki/List_of_HTTP_status_codes) 
for a list with explanations.  You will often see `500 Internal Server Error` when there's some
problem with your servlet code; it's the generic "uh, something broke" message.  Some status codes will
be set automatically in the response.  Optionally, you can set the status code you want to reply with, 
from your servlet, which is mainly useful when there's a specific error you need to indicate directly to the browser
instead of to the user.

`Content-Type` is an http response header, describing what kind of content is being returned to the browser.
Common content types are `text/html`, `text/plain`, `image/png`, `image/jpg`, and `application/octet-stream` (for
raw binary data).  Typically a servlet sets its content type before returning content, so the browser knows how
to interpret the bytes.

To learn more about the http request and response header fields, you can use the [wikipedia page List of HTTP 
header fields](https://en.wikipedia.org/wiki/List_of_HTTP_header_fields) for a full list.  This is a reference chart, 
not a tutorial.  There are tutorials elsewhere on the web that narrow the focus down to just the most common ones.

A `cookie` is a small bit of data (generally text) sent first from the server to the browser in a response,
and then returned back from the browser to the server on every following request to the same server.  It's
typically used for maintaining a login session, and also for retaining user identity across visits such as
for advertising.  Wikipedia has a page on [HTTP Cookie](https://en.wikipedia.org/wiki/HTTP_cookie).  The
Java class for cookies is `java.net.HttpCookie`.

A `session` refers to a few related things: it describes the time period during which a single person,
using a web application, has an identity that lasts from page to page, typically while they shop for a day, 
or use a site for a few hours.  The `session` also refers to the saved file on disk in the web server, where that
identity and their recent activities are maintained; this is more formally called the `session cache`.  But, 
mostly people don't bother saying cache, because it's implied.  "The user's web-browsing session" refers to the
time period and duration.  The "session cache" refers to the saved files on disk.  But people just say "session"
for both. The Java class for the session cache is `javax.servlet.http.HttpSession`.

`Chrome developer tools` and `Firebug for Firefox` are browser-side tools for helping you understand the
http request and response activities happening in your browser, including network transfer time, errors, css layout,
javascript behaviors and errors, and lots of other developer-focused stuff.  You can google for these tools to
learn how to use them to troubleshoot your web pages.  They go well above and beyond "view HTML source".

# Misc Tech Notes

Some people want to run this with Tomcat.  I recommend Tomcat 8 as best matching the
servlet 3.0 API version that this example code is written for.

Tomcat wants Java installed and configured in the JAVA_HOME environment 
variable.  Mac OS X has a weird way of installing Java, so there's a 
trick to finding the latest.  Put the following into your ~/.bash_profile 
and then start a new terminal window:

    export JAVA_HOME=$(/usr/libexec/java_home)

Additionally (regardless of Windows or Mac), Tomcat's manager app GUI requires
that you set up a username and password in a server config file, before you
can use it.  (This is for security.)  See the tomcat docs for manager-gui role
for details.

You have the option in Eclipse to "Run on Server" and can choose an Apache Tomcat 8 server.
This should "just work."

To deploy to Tomcat on another machine, you'll need a war file.  Go to the pom.xml, right 
click, Run As, Maven Build.  You don't have to give a Goal because there's a default
goal of `clean` and `package` already set in the pom.xml.  Just tell it to Run.
Then watch the console window; you should see this at the end:

    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------

Also notice the `target/edu-java-jsp-0.0.1-SNAPSHOT.war` file produced.  You'll 
probably want to give that a simpler name like `edujsp.war` before you deploy to 
tomcat, because whatever you name the file will become the ContextPath in the url (minus `.war`).

