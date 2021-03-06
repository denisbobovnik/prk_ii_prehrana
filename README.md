# ![logo](/prk_ii_prehrana/WebContent/img/logo.png)
Projekt pri predmetu Praktikum II

## E-R model
![er_model](/er_model/er_model.png)

## Navodila za preizkus strani na lastnem računalniku (povzetek iz vseh spodaj)

- Predzahteve za delujočo aplikacijo so: JDK (testirano na 10.0.1), WildFly (testirano na 12.0.0) in MySQL (testirano na 8.0.11.0)
1. Navodila za JDK in WildFly vzpostavitev: https://tomylab.wordpress.com/2016/05/13/how-to-install-wildfly-on-windows/ (obiskano nazadnje 12. 6. 2018)
2. MySQL Installer (na uradni strani pridobimo) in namestimo kar Full Package (načeloma bi bil dovolj le Server; če želimo prihraniti na prostoru) (POMEMBNO: ko vas vpraša za ustvarjanje admin uporabnika MySQL serverja, nastavite tip hashanja gesla na "Standard")
3. Poženemo MySQL Server

- Deployment:
1. Ustavimo WildFly strežnik (poženemo cmd in navigiramo (cd) do mape \bin ter poženemo ukaz):
```bash
jboss-cli.bat --connect command=:shutdown
```
2. Prenesite si datoteke, ki so v sledečem imeniku (prenos): https://mega.nz/#!KA83RYzJ!uSAp7pcEXAaNaSZA9yaG3tO6oaoWWAVP3vBwAHgNiuY
3. Ob ugasnjenem serverju WildFly obstoječe datoteke nadomestimo z pravkar prenesenimi (prepišemo oz. pobrišemo/shranimo in prilepimo nove)
4. Poženemo WildFly strežnik (poženemo cmd in navigiramo (cd) do mape \bin ter poženemo ukaz):
```bash
standalone.bat
```
5. V MySQL ustvarimo podatkovno bazo "prk_ii_prehrana"
```bash
CREATE SCHEMA `prk_ii_prehrana` DEFAULT CHARACTER SET utf8 COLLATE utf8_slovenian_ci ;
```
6. Nato s pomočjo GUI na administrativni konzoli WF strežnika ustvarimo novi Non XA datasource s pomočjo zaznanega MySQL driverja in nastavimo name na "prk_ii_prehrana", JNDI na "java:jboss/datasources/prk_ii_prehrana" in povezavo na "jdbc:mysql://localhost:3306/prk_ii_prehrana". Prav tako vnesemo prijavne podatke za naš MySQL strežnik.

- Nazadnje še s pomočjo CMD dodamo po enega uporabnika spletne strani za posamezno vlogo (en strokovnjak, en posameznik). Navigiramo do imenika našega WF strežnika in gremo v imenik bin (po do tega kopiramo):

- V cmd prilepimo in potrdimo:
```bash
cd <kopirana_pot>
```

- Napišemo in potrdimo:
```bash
add-user
```

- Sledimo navodilom in ustvarimo aplikacijskega uporabnika z uporabniškim imenom, geslom in vlogo (ime vloge: STROKOVNJAK; POSAMEZNIK). Ostale nastavitve nastavimo na "no" (EJB calls, ipd.).

- Tukaj je seznam uporabnikov, ki so nujni za izvedbo opcijske točke 8 (uvoz pripravljenih podatkov):
```bash
Username: strokovnjak@eprehrana.si, Geslo: eprehrana, Vloga: STROKOVNJAK
Username: posameznik@eprehrana.si,  Geslo: eprehrana, Vloga: POSAMEZNIK
Username: posameznik2@eprehrana.si, Geslo: eprehrana, Vloga: POSAMEZNIK
```
7. Pojdite na administrativno konzolo vašega izvajalnega okolja WF (in se vanj prijavite uporabnikom, ki je tipa "Management user" - pri uporabi naših datotek iz imenikov za prenos je ta uporabniško ime: user, geslo: user):
```bash
http://localhost:9990/console/App.html#home
```

- Kliknite na "Deployments"
- Kliknite na "Add"
- Izberite možnost "Upload a new deployment"
- Izberite preneseno datoteko "prk_ii_prehrana.war" iz imenika deploy (na GitHub-u)
- Kliknite Finish
- Aplikacija je tako dostopna na URL naslovu:
```bash
http://localhost:8080/prk_ii_prehrana/faces/index.xhtml
```
8. (opcijsko) Uvoz pripravljenih podatkov:
- Prenesite si datoteko ![prk_ii_prehrana.sql](/podatki_za_uvoz/prk_ii_prehrana.sql)
- V koliko so v vaši, že kreirani schemi v MySQL že ustvarjene tabele, le-te predhodhno drop-ajte.
- V MySQL Workbench pod razdelkom MANAGEMENT izberite orodje Data Import/Restore:

![1.png](/podatki_za_uvoz/1.png)

- Izberite prvo opcijo Import orodja "Import from Dump Project Folder" in izberite imenik, kjer je datoteka prenesena. Nato pa le odkljukajte schemo "prk_ii_prehrana" in kliknite na gumb "Start Import".

![2.png](/podatki_za_uvoz/2.png)

- Sedaj so vneseni testni podatki. 
- Opomba: pri programih je vnesen po 1 obrok na dan; kar ne pomeni, da ne podpira več obrokov; le ni še vnesenega dovolj velikega nabora receptov, da bi lahko naredili tako količino različnih programov.
- Opomba: uvoz smo omogočili na ta način, ker imamo v PB shranjene slike kot LONGBLOB, kar ni mogoče uvoziti v obliki navadnih INSERT stavkov.

Dodatno (po Blockchain implementaciji) - uvoz blockchain podatkov iz datoteke "blockchain.data"
- Zaustavite WF strežnik
- Datoteko skopiramo v mapo /bin našega izvajalnega okolja WF:
```bash
...\wildfly-12.0.0.Final\bin\blockchain.data
```
- Nato strežnik poženite in izvedite full publish (oz restartajte) (poženemo cmd in navigiramo (cd) do mape \bin ter poženemo ukaz):
```bash
jboss-cli.bat --connect command=:reload
```

## Navodila za deploy:
- Pojdite na administrativno konzolo vašega izvajalnega okolja WF (in se vanj prijavite uporabnikom, ki je tipa "Management user" - pri uporabi naših datotek iz imenikov za prenos je ta uporabniško ime: user, geslo: user):
```bash
http://localhost:9990/console/App.html#home
```

- Kliknite na "Deployments"
- Kliknite na "Add"
- Izberite možnost "Upload a new deployment"
- Izberite preneseno datoteko "prk_ii_prehrana.war" iz imenika deploy (na GitHub-u)
- Kliknite Finish
- Aplikacija je tako dostopna na URL naslovu:
```bash
http://localhost:8080/prk_ii_prehrana/faces/index.xhtml
```

- Dodatno bo potrebno ustvariti tudi JMS Queue, zato si prvo prenesite datoteke, ki so v sledečem imeniku (prenos): https://mega.nz/#!KA83RYzJ!uSAp7pcEXAaNaSZA9yaG3tO6oaoWWAVP3vBwAHgNiuY
- Ugasnemo strežnik in prepišemo njegove datoteke s prenesenimi (ta korak zna pobrisati obstoječe uporabnike - na novo vnesemo in če nas vpraša za update, damo da (a možnost) -  ali pa MySQL datasource - glej navodila spodaj).
- Strežnik ponovno zaženemo.
- Projektni email: eprehrana@gmail.com, geslo: ePrehrana2018

## Navodila za razvoj/uporabo:
- Potrebno si je kreirati datasource v Wildfly aplikacijskem strežniku. Ugasnemo strežnik in prepišemo datoteke v njegovem imenu z temi, ki so v sledečem imeniku (prenos): https://mega.nz/#!KZMg1DCS!iNFLC8482Xl1U_pn8hj54rDGCMV9ZphFveeV5pOzOBs
- Najprej v MySQL ustvarimo podatkovno bazo "prk_ii_prehrana"
```bash
CREATE SCHEMA `prk_ii_prehrana` DEFAULT CHARACTER SET utf8 COLLATE utf8_slovenian_ci ;
```

- Nato s pomočjo GUI na administrativni konzoli WF strežnika ustvarimo novi Non XA datasource s pomočjo zaznanega MySQL driverja in nastavimo name na "prk_ii_prehrana", JNDI na "java:jboss/datasources/prk_ii_prehrana" in povezavo na "jdbc:mysql://localhost:3306/prk_ii_prehrana". Prav tako vnesemo prijavne podatke za naš MySQL strežnik.

- Nazadnje še s pomočjo CMD dodamo po enega uporabnika spletne strani za posamezno vlogo (en strokovnjak, en posameznik). Navigiramo do imenika našega WF strežnika in gremo v imenik bin (po do tega kopiramo):

- V cmd prilepimo in potrdimo:
```bash
cd <kopirana_pot>
```

- Napišemo in potrdimo:
```bash
add-user
```

- Sledimo navodilom in ustvarimo aplikacijskega uporabnika z uporabniškim imenom, geslom in vlogo (ime vloge: STROKOVNJAK; POSAMEZNIK). Ostale nastavitve nastavimo na "no" (EJB calls, ipd.).

## Uvoz pripravljenih podatkov:
Ker je pripravljena podatkovna struktura vezana na točno določenega uporabnika (ne le vloga, temveč tudi username), je tukaj seznam uporabnikov, ki so prijavljeni v strežnik WF na naših razvojnih okoljih:
```bash
Username: strokovnjak@eprehrana.si, Geslo: eprehrana, Vloga: STROKOVNJAK
Username: posameznik@eprehrana.si,  Geslo: eprehrana, Vloga: POSAMEZNIK
Username: posameznik2@eprehrana.si, Geslo: eprehrana, Vloga: POSAMEZNIK
```

- Prenesite si datoteko ![prk_ii_prehrana.sql](/podatki_za_uvoz/prk_ii_prehrana.sql)
- V koliko so v vaši, že kreirani schemi v MySQL že ustvarjene tabele, le-te predhodhno drop-ajte.
- V MySQL Workbench pod razdelkom MANAGEMENT izberite orodje Data Import/Restore:

![1.png](/podatki_za_uvoz/1.png)

- Izberite prvo opcijo Import orodja "Import from Dump Project Folder" in izberite imenik, kjer je datoteka prenesena. Nato pa le odkljukajte schemo "prk_ii_prehrana" in kliknite na gumb "Start Import".

![2.png](/podatki_za_uvoz/2.png)

- Sedaj so vneseni testni podatki. 
- Opomba: pri programih je vnesen po 1 obrok na dan; kar ne pomeni, da ne podpira več obrokov; le ni še vnesenega dovolj velikega nabora receptov, da bi lahko naredili tako količino različnih programov.
- Opomba: uvoz smo omogočili na ta način, ker imamo v PB shranjene slike kot LONGBLOB, kar ni mogoče uvoziti v obliki navadnih INSERT stavkov.

Dodatno (po Blockchain implementaciji) - uvoz blockchain podatkov iz datoteke "blockchain.data"
- Zaustavite WF strežnik
- Datoteko skopiramo v mapo /bin našega izvajalnega okolja WF:
```bash
...\wildfly-12.0.0.Final\bin\blockchain.data
```
- Nato strežnik poženite in izvedite full publish.

## Uporaba rest storitve (za skupini "Corporate Wellness" in "Fitnes"):
Da pridete do podatkov o naših receptih (oblike JSON), si prenesite našo aplikacijo v eclipse (po navodilih za deploy/razvoj - ni potrebno imeti uporabnikov kreiranih, ostalo pa po navodilih za namestitev; prav tako prenesite in uvozite pripravljene podatke po navodilih), nato pa pojdite na url: 
```bash
http://localhost:8080/prk_ii_prehrana/rest/eprehrana/recepti
```
Tukaj so podatki o vseh receptih, strukturirani na način:
- ime, 
- opis,
- kategorija, 
- število kalorij (skupno), 
- količina sladkorja (g) (skupno), 
- sestavine-mnogo (količina, enota_kolicine, ime, število kalorij, količina sladkorja),
- alergeni-mnogo (ime).

Povratna povezava do našega projekta bi bila predvidoma na url:
```bash
http://localhost:8080/prk_ii_prehrana/faces/seznam-receptov.xhtml
```

## Zahteve:
- testirano na WildFly 12, MySQL Server 5.7.17 in Eclipse EE Oxygen.2
