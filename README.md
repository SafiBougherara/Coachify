# **Coachify** 🏋️‍♂️

## 📌 **Description**
Coachify est une application JavaFX conçue pour les coachs sportifs et les salles de sport. Elle permet de **gérer efficacement les clients et leurs exercices**, tout en offrant une interface intuitive et un stockage sécurisé des données.

## 🚀 **Fonctionnalités**
✅ Interface ergonomique pour la gestion des clients et des exercices  
✅ Ajout, modification et suppression des séances d'entraînement  
✅ Intégration avec une base de données MySQL pour un stockage **persistant**  
✅ Traitement des données JSON avec **Jackson**  
✅ Tests automatisés avec **JUnit**

## 🛠 **Technologies utilisées**
- **Java 21** 🏗 (Langage principal)
- **JavaFX** 🎨 (Interface utilisateur)
- **MySQL** 🗄 (Gestion de base de données)
- **Jackson** 📜 (Traitement JSON)
- **JUnit** 🧪 (Tests unitaires)
  Ajoute cette section **Dépendances** à ton README :

## Dependencies

Coachify utilise plusieurs bibliothèques pour assurer son bon fonctionnement. Assurez-vous que votre projet inclut les dépendances suivantes dans le fichier `pom.xml` :

### JavaFX (Interface Graphique)
```xml
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-controls</artifactId>
    <version>21</version>
</dependency>
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-fxml</artifactId>
    <version>21</version>
</dependency>
```

### MySQL Connector (Gestion de la base de données)
```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
```

### Jackson (Traitement JSON)
```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.18.2</version>
</dependency>
```

### BCrypt (Chiffrement des mots de passe)
```xml
<dependency>
    <groupId>org.mindrot</groupId>
    <artifactId>jbcrypt</artifactId>
    <version>0.4</version>
</dependency>
```

### JUnit (Tests unitaires)
```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.9.2</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-engine</artifactId>
    <version>5.9.2</version>
    <scope>test</scope>
</dependency>
```

Ces dépendances garantissent que toutes les fonctionnalités de l'application fonctionnent correctement.
## 📥 **Installation**
1️⃣ **Cloner le dépôt**
```bash
git clone https://github.com/SafiBougherara/Coachify.git
cd Coachify
```
2️⃣ **Exécuter le projet**
```bash
mvn clean javafx:run
```

## 👥 **Contribuer**
Les contributions sont **les bienvenues** ! 🛠
1. **Fork** le projet
2. **Créer une branche** dédiée :
   ```bash
   git checkout -b feature-nouvelle-fonctionnalite
   ```  
3. **Effectuer les modifications** et **committer** :
   ```bash
   git commit -m "Ajout d'une nouvelle fonctionnalité"
   ```  
4. **Pousser la branche** et ouvrir une **Pull Request**

📩 Pour toute suggestion, n’hésite pas à ouvrir une issue !

SCREENSHOTS/ 

![{46EF5564-C942-43A3-9AAB-93CE04670855}](https://github.com/user-attachments/assets/e3fb520e-b201-41b0-996f-e6b824884476)

![image](https://github.com/user-attachments/assets/98db2824-bf5a-4dbc-8804-60ddced17ca4)

![image](https://github.com/user-attachments/assets/6ba88736-c7cb-4475-97b7-5ba40517dbba)

![image](https://github.com/user-attachments/assets/607518d9-ac4f-4206-9293-601bd577f1e8)

![image](https://github.com/user-attachments/assets/b540abfb-a123-44c2-acef-d23f27d1be1e)
