# Client SOAP Android avec KSOAP2

Cette application Android permet de gérer des comptes bancaires (courant et épargne) en se connectant à un service SOAP distant.

## Fonctionnalités

- Afficher la liste des comptes
- Ajouter un nouveau compte (courant ou épargne)
- Supprimer un compte existant
- Interface utilisateur moderne avec Material Design

## Prérequis

- Android Studio (version récente recommandée)
- SDK Android 21 ou supérieur
- Accès à un service SOAP fonctionnel (URL configurée dans la classe `Service.kt`)

## Configuration

1. Cloner le dépôt
2. Ouvrir le projet dans Android Studio
3. Vérifier que l'URL du service SOAP est correctement configurée dans `Service.kt`
4. Compiler et exécuter l'application sur un émulateur ou un appareil physique

## Structure du projet

- `MainActivity.kt` - Activité principale gérant l'interface utilisateur
- `adapter/CompteAdapter.kt` - Adaptateur pour l'affichage des comptes dans un RecyclerView
- `beans/Compte.kt` - Modèle de données pour les comptes
- `ws/Service.kt` - Classe de service pour les appels SOAP

## Bibliothèques utilisées

- [KSOAP2](https://github.com/simpligility/ksoap2-android) - Pour les appels SOAP
- [Material Components](https://material.io/components) - Pour l'interface utilisateur
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - Pour la gestion asynchrone

