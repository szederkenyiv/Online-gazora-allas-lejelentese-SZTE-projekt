Teszt felhasználónév: elek01
Teszt jelszó: elek01

Firebase autentikáció: 
  - Regisztráció:
                  -  java/com/example/gas_meter/Activitys/RegistrationActivity.java
  - Bejelentkezés:
                  -  java/com/example/gas_meter/Activitys/LoginActivity.java

Adatmodell definiálása:
  - java/com/example/gas_meter/Model/GasMeterModel.java
  - java/com/example/gas_meter/Model/UserModel.java

Legalább 3 különböző activity használata:
  - java/com/example/gas_meter/Activitys/MainActivity.java
  - java/com/example/gas_meter/Activitys/RegistrationActivity.java
  - java/com/example/gas_meter/Activitys/LoginActivity.java 

Beviteli mezők beviteli típusa megfelelő:
  - pl. res/layout/activity_registration.xml  EditText id:password inputType-nál keresd.

ConstraintLayout és még egy másik layout típus használata:
  - ConstraintLayout:
                      - res/layout/activity_registration.xml
  - CardView:
              - res/layout/gas_item.xml

Reszponzív: 
  - res/layout-land

Legalább 2 különböző animáció használata:
- java/com/example/gas_meter/Activitys/LoginActivity.java -> onCreate() metódusában a RotateAnimation
- java/com/example/gas_meter/Activitys/MainActivity.java -> onCreate() metódusában az AlphaAnimation

Intentek használata: navigáció meg van valósítva az activityk között:
- java/com/example/gas_meter/Activitys/LoginActivity.java
      -> onCreate() metódusában a regisztráció gombra nyomva átirányít a RegistrationActivity-re
      -> login() metódusában a sikeres autentikáció után átirányít a MainActivity-re

Legalább egy Lifecycle Hook használata a teljes projektben:
- java/com/example/gas_meter/Activitys/MainActivity.java -> pl. onStart() vagy onStop() az Adapter elindításáért és leállításáért felel

CRUD műveletek mindegyike megvalósult:
- Create:
          - java/com/example/gas_meter/Activitys/RegistrationActivity.java
- Read:
          - java/com/example/gas_meter/Activitys/MainActivity.java
- Update:
          - java/com/example/gas_meter/Fragments/UpdateGasMeterFragment.java
- Delete:
          - java/com/example/gas_meter/Fragments/DeleteGasMeterFragment.java
