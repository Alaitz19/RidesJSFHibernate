# Proiektua: Bidaia Kudeaketa Sistema

## Deskribapena
Proiektu hau Java erabiliz garatutako bidaia kudeatzeko sistema bat da,JSF eta Hibernate erabiliz. Sistema honek erabiltzaileek bidaien informazioa kontsultatzeko, bidaia berriak erregistratzeko eta saioa hasteko aukera ematen du. Gainera, sistema honek saioetan sartzen diren erabiltzaileen datuak gordetzeko aukera ere badu. Proiektuan bost funtzionalitate nagusi daude:

1. **Saioa hasteko aukera** 
2. **Erregistroa egitea** 
3. **Bidaien kontsulta orokorra** 
4. **Bidaien kontsulta hiriaren arabera** 
5. **Saioen sarrerak gordetzea** (Logingertaera)

## Teknologiak
- **Java**: Programazio hizkuntza nagusia.
- **JSF (JavaServer Faces)**: Web aplikazioen interfazea sortzeko framework-a.
- **Hibernate**: Datu basearekin interakzionatzeko ORM (Object-Relational Mapping) teknologia.
- **MySQL**: Datu basea kudeatzeko sistema kudeatzailea.

## Instalazioa

### 1. Beharrezkoak
Proiektua martxan jartzeko, honako hauek instalatuta eduki behar dituzu:
- **Java JDK 8 edo goiagoa**: Proiektua Java 8 edo berriroarekin garatua izan da.
- **Apache Maven**: Proiektuaren menedaketan oinarritutako biltegirako tresna.
- **MySQL edo beste datu base bat**: Datu basea kudeatzeko.

### 2. Datu basearen konfigurazioa
1. `hibernate.cfg.xml` fitxategian zure MySQL datu basearen konfigurazioa sartu:
```xml
<hibernate-configuration>
   <session-factory>
      <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>
      <property name="hibernate.hbm2ddl.auto">update</property>
      <property name="hibernate.connection.url">jdbc:mysql://localhost:Portua/datu-basearen izena</property>
      <property name="hibernate.connection.username">user</property>
      <property name="hibernate.connection.password">password</property>
   </session-factory>
</hibernate-configuration>
```
## Egilea
Alaitz Shan Ye



