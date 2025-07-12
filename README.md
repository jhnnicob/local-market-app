🛒 Local Market App
An Android application for managing and displaying products in a local market using Jetpack Compose, MVVM architecture, Firebase Firestore, and Hilt for dependency injection.

📱 Features
🔍 Realtime product listing from Firestore

➕ Add new product via a form

✅ Checkbox for stock availability

♻️ Live updates using addSnapshotListener

🗃 MVVM architecture with StateFlow

✏️ Jetpack Compose UI with Hilt integration

🧱 Tech Stack
Category	Libraries / Tools
UI	Jetpack Compose
Architecture	MVVM, StateFlow
Dependency DI	Hilt
Database	Firebase Firestore
Navigation	Jetpack Navigation Compose
Language	Kotlin

🧰 Setup Instructions
Clone the repo

git clone https://github.com/yourusername/local-market-app.git
cd local-market-app
Open with Android Studio

Use Android Studio Hedgehog or above

Sync Gradle

Firebase Setup

Add your google-services.json to app/

Make sure Firestore rules allow read/write (during development):

service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if true;
    }
  }
}
Run the App

Connect a device or emulator

Click ▶️ Run

📸 Screenshots
Home Screen	Add Product Screen

📂 Project Structure
kotlin
Copy
Edit
.
├── data/
│   └── repository/
│       └── ProductRepositoryImpl.kt
├── domain/
│   └── model/Product.kt
│   └── repository/ProductRepository.kt
├── presentation/
│   └── screen/
│       ├── HomeScreen.kt
│       ├── ProductAddScreen.kt
│       └── HomeViewModel.kt
├── di/
│   └── AppModule.kt
├── MainActivity.kt
└── AppNavHost.kt
