# ![logo](/prk_ii_prehrana/WebContent/img/logo.png)
Projekt pri predmetu Praktikum II

## E-R model
![er_model](/er_model/er_model.png)

## Navodila za razvoj/uporabo:
- Potrebno si je kreirati datasource v Wildfly aplikacijskem strežniku. Ugasnemo strežnik in prepišemo datoteke v njegovem imenu z temi, ki so v sledečem imeniku (prenos): https://mega.nz/#!KZMg1DCS!iNFLC8482Xl1U_pn8hj54rDGCMV9ZphFveeV5pOzOBs
- Najprej v MySQL ustvarimo podatkovno bazo "prk_ii_prehrana"
```bash
CREATE SCHEMA `prk_ii_prehrana` DEFAULT CHARACTER SET utf8 COLLATE utf8_slovenian_ci ;
```

- Nato s pomočjo GUI na administrativni konzoli WF strežnika ustvarimo novi Non XA datasource s pomočjo zaznanega MySQL driverja in nastavimo name na "prk_ii_prehrana", JNDI na "java:jboss/datasources/prk_ii_prehrana" in povezavo na "jdbc:mysql://localhost:3306/prk_ii_prehrana"

- Nazadnje še s pomočjo CMD dodamo po enega uporabnika spletne strani za posamezno vlogo (en strokovnjak, en posameznik). Navigiramo do imenika našega WF strežnika in gremo v imenik bin (po do tega kopiramo):

V cmd prilepimo in potrdimo:
```bash
cd <kopirana_pot>
```

Napišemo in potrdimo:
```bash
add-user
```

Sledimo navodilom in ustvarimo aplikacijskega uporabnika z uporabniškim imenom, geslom in vlogo (ime vloge: STROKOVNJAK; POSAMEZNIK). Ostale nastavitve nastavimo na "no" (EJB calls, ipd.).

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

## Zahteve:
- testirano na WildFly 12, MySQL Server 5.7.17 in Eclipse EE Oxygen.2
