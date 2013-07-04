Spoken Tutorial Android Application
===================================

A event management implementation using slide-drawer menu, which allows users to navigate between views
in spoken tutorial app. Most commonly the menu is revealed by clicking the 'up' button in the action bar.


Features
--------

 * Workshop menu fetches the list of upcoming spoken tutorial events from 
   `this <http://www.spoken-tutorial.org/>`_ and display in the layout
 * The same list will be stored in the database which can be very helpful during offline use
 * Saves the contact person details in the database
 * Displays the state wise persons details by clicking on the INDIA map.
 * Videos download and play will be available.


Usage
-----

This library is very simple to use. It requires no extension of custom classes,
it's simply added to an activity by calling one of the `MenuDrawer#attach(...)`
methods.

For more examples on how to use this library, check out the sample app.


License
-------

Copyright 2012 IIT Bombay

Licensed under the GNU GPL, version-3, 29 June 2007
http://www.gnu.org/licenses/gpl-3.0.txt
