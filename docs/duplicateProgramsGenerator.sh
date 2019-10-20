#!/usr/bin/env bash

totalToGenerate=$1
csvFileToAppendTo=$2
code=$3
name=$4
description=$5
duration=$6
participants=$7
transcript=$8
favourited=$9
category=${10}

if [ -z "$totalToGenerate" ] || [ -z "$csvFileToAppendTo" ] || [ -z "$code" ] || [ -z "$name" ] || [ -z "$description" ] || [ -z "$duration" ] || [ -z "$participants" ] || [ -z "$transcript" ] || [ -z "$favourited" ] || [ -z "$category" ]; then
  echo -e "Usage: ./duplicateProgramsGenerator.sh totalNumberOfProgramsToGenerate csvFileToAppendTo programCode programName programDescription programDuration programParticipants programTranscript programFavourited programCategory"
  echo -e "Example: ./duplicateProgramsGenerator.sh 500 programs.csv YTP190002 '02 - YOU ARE 3ABN.mp4' 'A slide show tribute to the supporters and viewers of 3ABN.' 1:01 'supporters and viewers' 'https://www.youtube.com/watch?v=hL1B0mArSgM' FALSE FAMILY_ISSUES_AND_INTERCITY"
  exit 1
fi

if [ ! -f "$name" ]; then
  echo "Ensure program named '$name' exists in :$PWD"
  exit 1
fi

if [ ! -f "$csvFileToAppendTo" ]; then
  echo "Ensure program named '$csvFileToAppendTo' exists in :$PWD"
  exit 1
fi

rm -r programs;mkdir programs
cp "$csvFileToAppendTo" programs/"$csvFileToAppendTo"
  
for ((i=1; i<=totalToGenerate; i++))
do
  cp "$name" programs/"$i"_"$name"
  printf "\n$i"_"$code","$i"_"$name","$i"_"$description","$duration","$i"_"$participants","$i"_"$transcript","$favourited","$category" >> programs/"$csvFileToAppendTo"
done