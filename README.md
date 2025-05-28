# Infolibertaire Android Application

<p align="center">
  <img src="info.png" alt="Infolibertaire Logo" width="300"/>
</p>

![Build Status](https://img.shields.io/github/actions/workflow/status/votre-utilisateur/infolibertaire-android/android.yml?branch=main)
![License](https://img.shields.io/github/license/votre-utilisateur/infolibertaire-android)
![Android](https://img.shields.io/badge/platform-Android-green)

## Description
L'application **Infolibertaire** est une application Android dédiée à la diffusion d'informations et de ressources liées à la liberté et à l'autonomie. Elle permet aux utilisateurs d'accéder à des articles, des événements et des ressources utiles directement depuis leur appareil mobile.

## Fonctionnalités principales
- 🌐 Chargement des articles depuis [infolibertaire.net](https://infolibertaire.net)
- ⭐ Gestion des favoris (ajout, suppression, liste)
- 🔄 Actualisation des pages
- 📤 Partage des articles

## Prérequis
- **Android Studio** (version recommandée : Arctic Fox ou plus récent)
- **JDK 17**
- Un appareil Android ou un émulateur configuré

## Installation
1. Clonez le dépôt :
   ```bash
   git clone https://github.com/votre-utilisateur/infolibertaire-android.git
   ```
2. Ouvrez le projet dans Android Studio.
3. Synchronisez le projet avec Gradle en cliquant sur "Sync Now".
4. Exécutez l'application sur un appareil ou un émulateur.

## Structure du projet
- `app/src/main/java/net/infolibertaire/MainActivity.kt` : Classe principale gérant l'interface utilisateur.
- `app/src/main/java/net/infolibertaire/data/` : Gestion des données (Room Database).
- `app/src/main/res/layout/` : Fichiers de mise en page XML.

## Contribuer
Les contributions sont les bienvenues ! Veuillez suivre les étapes ci-dessous :
1. Forkez le projet
2. Créez une branche pour votre fonctionnalité :
   ```bash
   git checkout -b feature/ma-fonctionnalite
   ```
3. Faites vos modifications et committez-les :
   ```bash
   git commit -m "Ajout de ma fonctionnalité"
   ```
4. Poussez vos modifications :
   ```bash
   git push origin feature/ma-fonctionnalite
   ```
5. Ouvrez une Pull Request

## Licence
Ce projet est sous licence MIT. Consultez le fichier [LICENSE](LICENSE) pour plus d'informations.

## Captures d'écran
Ajoutez ici des captures d'écran de l'application pour montrer son interface et ses fonctionnalités.

---

Développé avec ❤️ par l'équipe Infolibertaire.
