# Battleships-Bluetooth

Created in Android Studio for devices with Lollipop and newer

This programme isn't finished but allows to play with another player via bluetooth and with computer. 
Some methods have their name in polish some in english because this was a group project. But I tried to comment everything so you can easily read this code. 

This game isn't stable for now because of some issues.

Things to do:
- modify Config class - It does what it was designed to in most cases but there are few cases when this class doesn't work correctly (it doesn't store some of the coordinates for destroyers and battleships. It skips one spot.)
- modify an algorithm for Computer in BattleActivity. We can play with computer but when computer has a hit it won't look for other spots for this ship to make it sunken. It choose another random spot on board.
- modify Bluetooth. For now we can only play with paired devices.
- modify BattleMultiActivity. There are some problems with checking if the ship we hit is already sunken or not. 
