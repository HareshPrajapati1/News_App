## NewsApp

NewsApp is a modern Android application built using Kotlin that follows the MVVM (Model-View-ViewModel) architecture.
It allows users to browse breaking news, save their favorite articles, search for specific news, and manage saved articles with undo functionality.
This project leverages powerful libraries and tools for a seamless user experience.

## Features

• Home Page: Displays a curated list of breaking news articles.

• Saved Page: Shows a list of articles that the user has saved for later.

• Breaking News Page: View full details of a selected news article.

• Search Page: Search for news articles based on keywords.

• Swipe to remove news articles from the saved list.Undo functionality allows users to restore accidentally removed articles for a few seconds.

## Tech Stack

• Programming Language: Kotlin

• Architecture: MVVM (Model-View-ViewModel)

• Database: Room for local data storage

• Networking: Retrofit for API calls

• Navigation: Jetpack Navigation Component for managing in-app navigation

• Concurrency: Kotlin Coroutines for background tasks

## Application Screenshots

<div align="center">
  <!-- First row -->
  <img src="https://github.com/user-attachments/assets/93ac09ff-26b1-4daf-a1e7-66f2856d2a49" alt="Home Page" width="30%" />
  <img src="https://github.com/user-attachments/assets/ce297ecf-3b16-4acd-a4ed-ecae479192bc" alt="Breaking News Page" width="30%" />
  <img src="https://github.com/user-attachments/assets/3cbbdbff-e659-41d8-b4b9-0cfee2c7c6c2" alt="Saved News Page" width="30%" />
  
  <!-- Second row -->
  <img src="https://github.com/user-attachments/assets/2cbb8157-4ee2-4fb6-8ab8-83e6d9f00ec9" alt="Saved News Delete Page" width="45%" style="margin-top: 10px;" />
  <img src="https://github.com/user-attachments/assets/fbb75436-9ffd-4d63-b142-c48584165ab8" alt="Search Page" width="45%" style="margin-top: 10px;" />
</div>



## How It Works

• Home Page: Fetches breaking news articles using Retrofit and displays them in a RecyclerView.

• Saved Page: Articles can be saved and stored locally using Room.

• Breaking News Page: Displays the full content of a selected news article.

• Search Page: Allows users to search for specific news articles. Swiping an article removes it from the saved list, with an option to undo the action for a short period.
Libraries Used

• Room: Local database for storing saved articles.

• Retrofit: Networking library for making API calls.

• Jetpack Navigation: Simplifies app navigation and ensures a smooth user experience.

• Kotlin Coroutines: Enables asynchronous programming.

## Libraries Used

• Room: Local database for storing saved articles.

• Retrofit: Networking library for making API calls.

• Jetpack Navigation: Simplifies app navigation and ensures a smooth user experience.

• Kotlin Coroutines: Enables asynchronous programming.


