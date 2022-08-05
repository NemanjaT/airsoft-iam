# Description

**Airsoft IAM** is a microservice in charge of authenticating 
and storing user data.

# Questions and answers
1. How do I run the app?
   1. Install `docker` if you don't already have it.
      1. MacOS 🍐
         1. `sudo hdiutil attach Docker.dmg`
         2. `sudo /Volumes/Docker/Docker.app/Contents/MacOS/install`
         3. `sudo hdiutil detach /Volumes/Docker`
      2. Linux (via `apt-get`) 🦃
         1. `sudo apt-get remove docker docker-engine docker.io containerd runc`
      3. Windows 🪲
         1. Download from [here](https://desktop.docker.com/win/main/amd64/Docker%20Desktop%20Installer.exe) and execute
   2. Run `docker-compose up`
