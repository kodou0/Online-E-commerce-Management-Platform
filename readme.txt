This is an e-commerce management for online
Download the Project
Download the front-end project mall-webfront from GitHub, extract the compressed file and place it in the root directory of local Disk D.
Open the Project with IDEA
Launch IntelliJ IDEA 2022.1.4, click File - Open, select the path D:\mall-webfront to import the project.
Project Directory Structure
plaintext
mall-webfront
├── public           # Folder for static resources
├── src              # Core source code of the project
├── .env.development # Development environment configuration
├── .env.production  # Production environment configuration
└── package.json    # Project dependency configuration file

Install Vue Syntax Support Plugin
On the top menu bar of IDEA: File → Settings → Plugins, switch to the Marketplace tab.
Search for the keyword Vue.js, click to install the official Vue plugin.
Restart IDEA after installation. This plugin provides syntax hints, component jump navigation and code auto-completion for Vue files.
2.2 Install Front-End Dependencies (CMD Terminal Operations)
Open Windows Terminal: Press the shortcut Win + R, input cmd and press Enter to launch Command Prompt.
Switch to the project root directory and run the following commands:
shell
# Switch to Disk D
d:
# Enter the front-end project folder
cd mall-webfront

Run the installation commands one by one to install core dependencies required by the project:
shell
# 1. Install vue-router for Vue3
npm install vue-router@next

# 2. Install axios for network requests
npm install axios --save

# 3. Install Element Plus UI component library
npm install element-plus --save

# 4. Install icon library matching Element Plus
npm install @element-plus/icons-vue

III. Back-End Project Preparation (mall)
Back-end project path: D:\25-26-2\Web Application Development Practice\Lab Projects\mall
Import the SpringBoot back-end project mall via IDEA. The core startup class is com.Lc.MallApplication.java.
The project integrates Mybatis-Plus, permission management, and complete database mapping files (mapper.xml) for commodity, order and user modules.
IV. Steps to Run Front-End and Back-End Projects
4.1 Startup Order: Start Back-End First, Then Front-End
Start the back-end mall project
Open MallApplication.java in IDEA.
Right-click and run the main method. Wait for the SpringBoot service to finish loading, then the back-end interface service is ready.
Start the front-end mall-webfront project
Keep the terminal directory as D:\mall-webfront in CMD.
Execute the startup command:
shell
npm run serve

Access the front-end page
After successful startup, the terminal will print access URLs. Open the link in a browser to enter the background management system:
plaintext
Local: http://localhost:8080/
Network: http://localhost:8080/

V. System Test Accounts (For Login)
表格
Account	Password	Role Permission
admin	123456	Super Administrator
charles1	1234	Administrator
test	1234	Ordinary User
