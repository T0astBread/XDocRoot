# XDocRoot
Useful tool for changing the document root of your XAMPP installation.

## Changing the Document Root:
1. Navigate to the new document root folder using the file chooser in the main window
2. Press "Open" on the file chooser. The document root is now set BUT the changes are not written to the Apache config file yet.
3. Press the "SET" button or hit enter. This will modify XAMPP's config files and save the changes.

## Changing the Apache Config File Path:
You might want to change the path of the apache config file (for example if you are on a different operating system than Windows or if you're not using the XAMPP default installation path). This is how you do it:
1. Click the "Settings" button in the top right corner of the main window. A settings dialog will pop up.
2. Click "Select" right next to the text field for the configuration file
3. Select the httpd.conf file of your XAMPP installation

## Running the Project in your IDE:
To set the project up in your IDE, perform the following steps:
1. Download the sources
2. Download everything in [this Dropbox folder](https://www.dropbox.com/sh/aekhdhbk5267irm/AAA62UGHI1exBu8TkUBf2vc7a?dl=0)
3. Open the project in your IDE
4. Add the downloaded JARs to the classpath
5. It should be working now!
