
## Table of Contents

* [About the Project](#about-the-project)
  * [Built With](#built-with)
  * [Modules](#Football-app-modules)
  * [Installition](#installation)

# about-the-project
It is an *English Premier League* sample app which will let you follow its daily matches, you also can add the fixture to your favorites so you can show them only.

# screenshots
<p align="center">
  <img src="https://github.com/sohafawzy/FootballAppTask/blob/master/Screenshot_1636691573.png" width="250">
  <img src="https://github.com/sohafawzy/FootballAppTask/blob/master/Screenshot_1636691577.png" width="250">
</p>

# #Football-app-modules:
1. **domain**: It has the models and bussiness rules.
2. **data**: Local & remote datasources and repositories.
3. **app**: presentations layer

# built-with
* Kotlin
* Clean Archticture
* MVVM archticture Pattern
* Hilt
* Coroutines
* Room
* Retrofit
* Unit Testing using Junit, MockWebServer and Robolectric.

## Note
Unit testing applied on data layer only.

## Installation
First you should get your own [Api-key](https://www.football-data.org/client/register)
After that you have to create your own ```apikey.properties```  file then add you key as that  ```API_KEY=“YOUR_API_KEY” ```
