# Krypt
Krypt is the submission to HackPSU Fall 2017 by Kyle Spencer, Keegan Harris, Nick Brougher, and Rachel Cooper. This was developed completely in under 24 hours. This app won 3rd place out of 64 final submissions. 

## What is it?
Krypt is an Android all written in Java that allows two people with coinbase accounts to exchange Bitcoin just by touching phones together. There is no need to manually send an address or a username. To use the app, you must link your coinbase account, click send or recieve, then touch phones with someone else who has chosen the opposite option. At this point, the person who selected send will be asked to enter an amount of Bitcoin to send and will recieve a two factor authentication code from Coinbase. You enter this information, click send, and the bitcoins are transferred immediately.

## How did you build it?
We interfaced our app with the Coinbase API via an OAuth2 key. When the users were ready to transfer funds, they hold the phones together and an NFC transfer sends a generated recieving address from the phone that clicked recieve to the other. The sending phone then asked Coinbase for a two factor authentication code. When the user clicked send, the two factor authentication code was then used to authenticate the transfer of Bitcoin to the Coinbase API.

## How did you manage this in 24 hours?
Sleep deprivation, absurd amounts of energy drinks, and the determination to win.
We split work into the GUI, the Coinbase interaction, and NFC interaction. I worked on the Coinbase interaction, Keegan worked on NFC interaction, and Nick and Rachel worked on the GUI. After we had completed our parts, I merged the code together and Nick and I debugged and tested until it worked.
The breakdown of the time went like this:
~2 hours trying to get Git collaboration working with Android Studio, before giving up.
~10 hours working on Coinbase and NFC interaction
~6 hours Debugging
~2 hours testing
~4 hours wishing for the sweet relief of death
