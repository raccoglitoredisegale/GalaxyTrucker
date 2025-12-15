## Group Compotent
 - Paolo Mornato
 - Morini Matteo
 - Leonardo Miotto
 - Raul Olla


## Functionalities
 - Complete Rules
 - Only TUI
 - Multiple lobbies functionalities

## 📝 How to Use

This section contains all instructions about how to run this project.

**From Intellij IDEA (Maven required)**

1. Open Terminal on project directory (`C:\[dir]\[project_folder]\`)
2. Run `mvn clean`
3. Run `mvn package`
4. Go to directory `C:\[dir]\[project_folder]\deliverables\JAR`
5. Launch Server: `java -jar Server.jar` (Server port = 54321 and ip from ipconfig on CMD of the server)
6. Launch CLI: `java -jar Client.jar` (ip and port will ask after running the program)

If you change the code, you'll have to repeat all the steps over again.

**From Repo clone**

1. Download Server, CLI jar files from `/deliverables/final/jar` folder
2. Open Windows PowerShell on project directory
3. Type `[Console]::OutputEncoding = [System.Text.Encoding]::UTF8`
4. Type `chcp 65001` 
5. You must run line `4)` and `5)` only for Client
6. Launch Server: `java -jar Server.jar` (Server port = 54321 and ip from ipconfig on CMD of the server)
7. Launch CLI: `java -jar Client.jar` (ip and port will ask after running the program)

## Test coverage
 - 78% on model 
