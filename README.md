![logo](/prk_ii_prehrana/WebContent/img/logo.png)
Projekt pri predmetu Praktikum II

## E-R model
![er_model](/er_model/er_model.png)

## Navodila za razvoj/uporabo (testirano na WildFly 12, MySQL Server 5.7.17 in Eclipse EE Oxygen.2):
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
