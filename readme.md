A small server for posting files to optionally given names / urls. Files will be kept for a short amount of time, then they'll be deleted.

The main idea was to build a way to transfer a file from a computer to a phone through QR codes:
- Computer generates an GUID
- Computer uses the provided client library to post the file with the known GUID as a name
- Computer generates and displays a QR code for the url
- Phone can capture the code and download the file within a few minutes

I've never really used Receiver, because we decided to do it a different way, but I used the opportunity to get some experience with javax.ws
