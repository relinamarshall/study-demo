#!/bin/bash

script=/nasdisk/ipub/script
delDir=/nasdisk/ipub
log=$script/autorm.log
list=$script/list.txt

if [ ! -f "$list" ]; then
  echo $script' not found list.txt file' >> $log
  exit 2
fi

#for dir in $(cat "$list"); do
for dir in "$delDir"/*; do
  if [ -d "$dir" ] && [ -w "$dir" ]; then
    for indir in "$dir"/* ; do
      if [ -d "$indir" ] && [ -w "$indir" ]; then
        time=$(date "+%Y-%m-%d %H:%M:%S")
        find "$indir" -maxdepth 1 -type d -mmin +30 ! -path "$indir" -exec rm -rf \;
        echo "${time} Delete TimeDir Success ${indir}" >> "$log"
      fi
    done
    time=$(date "+%Y-%m-%d %H:%M:%S")
    find "$dir" -maxdepth 1 -type d -mtime +0 ! -path "$dir" -exec rm -rf \;
    echo "${time} Delete DateDir Success ${dir}" >> "$log"
  fi
done
