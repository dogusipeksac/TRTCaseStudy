# 🎬 TRT Case Study - Movie App (Jetpack Compose + Clean Architecture)

This project is a **Movie Listing and Detail Android Application** developed as part of a case study for TRT. It showcases modern Android development practices using **Jetpack Compose**, **Clean Architecture**, **offline support**, and **embedded video playback**.

---

## 🛠 Tech Stack

| Layer        | Technology                                   |
|--------------|----------------------------------------------|
| UI           | Jetpack Compose, Material Design             |
| Architecture | MVVM, Clean Architecture                     |
| Async        | Kotlin Coroutines, StateFlow, Flow           |
| Networking   | Retrofit, OkHttp                             |
| Local Cache  | Room Database                                |
| DI           | Hilt (Dependency Injection)                  |
| Media        | YouTube WebView Embed                        |
| Utilities    | Accompanist (System UI), Coil (Image Loader) |

---

## 📱 Features

### 🏠 Home Screen
- Fetches and displays movies from [The Movie DB API](https://www.themoviedb.org/)
- Lazy list with loading indicator and error handling
- Reactive UI state with `UiState` sealed class
- Retry mechanism on failure

### 🎞 Detail Screen
- Displays movie details: title, overview, poster
- YouTube trailer embedded with fallback to poster if no video is found
- Responsive UI that gracefully handles empty/null data
- Cached locally for offline access

### 🌐 Connectivity Aware
- Observes internet connection in real-time
- Automatically retries failed network requests when back online
- Global `ErrorPopup` to show retry dialogs

---

## 🧱 Architecture Overview

Presentation (Jetpack Compose, ViewModel)
↑
Domain (UseCase, Model)
↑
Data (Repository, Retrofit API, Room DB)


- Follows SOLID principles and separation of concerns
- Highly testable and scalable structure
- Reactive data flow using `Flow` and `StateFlow`

---

## 📷 Screenshots

| Home Screen| 
![Preview]![home](https://github.com/user-attachments/assets/06bf63d4-9a9e-4a8f-8388-6fec962f9bee) 
| Detail Screen|
![Preview]![detail](https://github.com/user-attachments/assets/e50eb7f9-7c7a-4a89-a429-acbdd6abd88f)
| Favorite Screen| 
![Preview]![favorite](https://github.com/user-attachments/assets/9694818a-2396-4811-a4c8-cdde8f0cdbea)
|Detail Screen (Offline)|
![Preview]![offline_detail](https://github.com/user-attachments/assets/5357337e-7787-40b5-bd17-3f039b64a471)

---

## 🚀 How to Run

1. Clone the repository:

```bash
git clone https://github.com/your-username/trt-case-compose.git
cd trt-case-compose

2.Open in Android Studio (Giraffe or newer)

3.Normally, the TMDB API key is added to local.properties, but in this project, it is placed in gradle.properties and accessed via BuildConfig to keep it out of version control and avoid exposure on GitHub.

TMDB_API_KEY=your_api_key

4.Run the app on emulator or device

🧪 Testability
-ViewModels and UseCases are unit test-friendly

-Network errors and edge cases handled via UiState

-Offline caching tested by disabling connectivity

🙋‍♂️ Author
Doğuş İpeksaç
Senior Android Developer
