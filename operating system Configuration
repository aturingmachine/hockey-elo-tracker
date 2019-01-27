Software Configuration:
A Debian based Linux distribution known as Raspbian OS will be installed onto a Raspberry Pi to collect data over a serial interface and send collected data to a processing server via wifi network connectivity. 

Minimum requirements for the Raspbian OS:
The basic install provides only a terminal interface, in order to display an app in a GUI on the screen a X11 desktop environment running a minimal user interface called Open Box will be used.  The application portion of the kiosk will run a Chromium browser in “kiosk” mode.

“Kiosk” mode of chromium will lock the user environment to a specific website (our electron app) allowing interaction via touch screen and possibly a hard wired USB Keyboard.

The OS will need to be configured to automatically launch the web browser after a reboot and require no user interaction, making the whole system run like a simple appliance serving just the ELO app

* Install Raspbian light to a SD card
* Sign in with (default user is ‘pi’ , and the password is ‘raspberry’) then run:
sudo raspi-config
* Change the password
    - User: pi    
    - Password: ELOg0@l
* Configure network automatically to join a wifi network
* Enable SSH access (for remote adjustments and maintenance)
* Enable boot to desktop without logging in
* Adjust GPU memory if needed
* Finish and exit 
* Update the system package manager run:
sudo apt-get update
* Upgrade the system run 
sudo apt-get upgrade
* Install minimal desktop environment and user interface and Chromium and tools to remove un needed UI elements
sudo apt-get install xserver-xorg-video-all xserver-xorg-input-all xserver-xorg-core xinit x11-xserver-utils
sudo apt-get install chromium-browser
sudo apt-get install unclutter
* Configure auto start of the browser in a kiosk mode
sudo nano /etc/xdg/openbox/autostart
* Clear data (if any) and replace with:
# Disable any form of screen saver / screen blanking / power management
xset s off
xset s noblank
xset -dpms

# Allow quitting the X server with CTRL-ATL-Backspace
setxkbmap -option terminate:ctrl_alt_bksp

# Start Chromium in kiosk mode
sed -i 's/"exited_cleanly":false/"exited_cleanly":true/' ~/.config/chromium/'Local State'
sed -i 's/"exited_cleanly":false/"exited_cleanly":true/; s/"exit_type":"[^"]\+"/"exit_type":"Normal"/' ~/.config/chromium/Default/Preferences
chromium-browser --disable-infobars --kiosk 'http://your-url-here'
