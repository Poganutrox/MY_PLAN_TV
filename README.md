# JavaFX Video Management Application

## Description

This JavaFX application is designed to manage a collection of videos. The application allows users to view, filter, add, update, and delete videos from a centralized server.

![image](https://github.com/Poganutrox/MY_PLAN_TV/assets/63597815/12cd8d48-f831-47d9-99ed-4895ae66bfbd)

## Features

- **Initial Load**: Upon startup, the application connects to the server and lists all available videos in the list view on the left side of the window.
- **Filtering**: At the top of the window, there are combo boxes for each filter criteria (by type, by platform, by category, and by rating). Each combo box includes an additional "Show all" option to display all videos according to the selected criteria. Only one filter criteria can be applied at a time. When a filter is selected, the other combo boxes are deselected, and the list view is updated. If no videos match the selected criteria, an alert is shown indicating "No videos found."
- **Video Information Display**: Selecting a video from the list view displays its information in the form at the center of the window.
- **Form Buttons**:
  - **Save**: Adds a new video to the collection by posting the form data to the server, provided no fields are empty.
  - **Update**: Updates the selected video's information on the server with the form data, provided no fields are empty.
  - **Delete**: Deletes the selected video from the server after confirmation through an alert message.
- **Auto-update**: The list view is automatically updated after any action (save, update, delete). Any applied filter criteria are cleared, and all videos are displayed.

## Requirements

- Java Development Kit (JDK)
- JavaFX SDK
- Internet connection to connect to the server
