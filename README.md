# MySDA interless app

This is MySDA app similarly to MySDA streaming app, this is rather meant to store the programs on the device and play off that

## Setup
To setup the app, you need to to upload programs into it. to do this

- Copy a CSV file containing details of your programs to: `/sdcard/.mysdainterless/programs/programs.csv`
- Then copy your video files named after program codes into: `/sdcard/.mysdainterless/programs/`
- The app will store the program details and delete the CSV file

## Done

- [x] Main screen
- [x] Favorite screen
- [x] Search screen
- [x] List programs
- [x] Importing programs data from CSV
- [x] Testing on mobile device
- [x] Handle program duplication, probably ORMlite isn't good here
- [x] Cache programs
- [x] Create a better player for program screen
- [x] Favorite program

## Pending

- [ ] encrypt programs
- [ ] support control remote navigation on all screens
- [ ] viewer should be able to resume a started on program when he/she returns to it
- [ ] Improve layouts
- [ ] Test on TV (add millions of programs)
- [ ] Figure out how to navigate millions of programs without slowing down the app
- [ ] programs backend categorisation on disk and ui listing/ordering
- [ ] Work on programs category filtering on favorites page
- [ ] Replace list view with well improved layout fragments
- [ ] Remove all unused code
- [ ] Apply included formatter onto the code and do final refactoring
- [ ] Document process of importing programs and managment, this app is just a container,
it should be versioned separately from the included programs
- [ ] Demo app