# Opis

**Airsoft IAM** je mikro-servis zaduzen za autorizaciju i 
perzistenciju korisnika.

# Pitanja i odgovori
1. Kako pokrenuti aplikaciju?
    1. Instalirajte `docker` ukoliko ga vec nemate.
        1. MacOS 🍐
            1. `sudo hdiutil attach Docker.dmg`
            2. `sudo /Volumes/Docker/Docker.app/Contents/MacOS/install`
            3. `sudo hdiutil detach /Volumes/Docker`
        2. Linux (pomocu `apt-get`) 🦃
            1. `sudo apt-get remove docker docker-engine docker.io containerd runc`
        3. Windows 🪲
            1. Preuzmite [odavde](https://desktop.docker.com/win/main/amd64/Docker%20Desktop%20Installer.exe) i pokrenite
    2. Pokrenuti naredbu preko konzole/terminala `docker-compose up --build`
