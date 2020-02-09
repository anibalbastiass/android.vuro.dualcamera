# Vuro Dual Camera App
> Challenge app for Android Developer/Architect technical test for apply in Vuro Tech, New York City, USA.

This project is a technical test for apply Android Enginner job. 

Requirements:
1. Develop an APK under Android Studio, Android version 9.0. Don't mind  the UI.
2. When the APP is launched, it will continuously record video from both forward and backward cameras. The recorded frames can be saved into memory or on local storage temporarily.
3. Every 3 mins, it generates two video files with all the frame information for the past 3 mins. One file is for forward camera, and the other for backward camera.
4. Delete the frames which have been saved from either the memory or local storage

## Steps

- Frontend (Android):
  - From a personal project with a solid, scalable and clean architecture for Android apps (CLEAN + MVVM), begins complete features for this project.
  
- Dynamic feature:
 - Dual camera feature is a external lib as dynamic feature whenever contains into app, but is a independent
 and decouple module.
 - Research list of compatible devices with this feature. Only Snapdragon processors compatible:
 
   - For devices with Snapdragon processor, it is possible to do the dual interaction.
   - In contrast, devices with Exynos processor (Samsung vendor), not support dual cameras (dual-ISP capability)
   
So, this app is a private product (No available in Google Play Store) and run with specific devices as
Nokia 7.1, Google Pixel, Google Nexus.

This project contains the following milestones
 
- Build a scalable and stable architecture for Android App: MVVM + CLEAN Architecture
- Apply Android Architecture Components: Navigation, View Model, Live Data, Databinding
- Use Android X dependencies
- Keep clean code and use minimal dependencies

- Navigation:
This app contains a single activity with a Navigation Controller with some Fragments: `MainFragment`

- ViewModel:
This component is useful for share and save data throw Live Data

- Continuous Integration
With Github Actions feature (Top tab), this repository is processing throw CI.

## Screenshots

![Main](https://raw.githubusercontent.com/anibalbastiass/android.vuro.dualcamera/feature/upload_video_dummy_demo/art/device-2020-02-09-112146.png) ![DualCamera](https://raw.githubusercontent.com/anibalbastiass/android.vuro.dualcamera/feature/upload_video_dummy_demo/art/device-2020-02-09-112350.png)
