#!/bin/bash

# @since Jun 23, 2016 11:36:37 AM (creation date)
# @author Peter Withers <peter.withers@mpi.nl>

# This script converts for use in HTML5 and Android any WAV or MPEG files found 
# in or beneath the directory that this file resides in, eg static/<experiment name>/.
# Once the resulting file are created this script need not be run again, unless 
# new files are provided. The build process will exclude files such as MPEG and WAV 
# so it is acceptable for the original files to also be stored in the static/<experiment name>/ directory.

cd "$(dirname "$0")"

for filePath in */*.mpeg ; do 
    echo $filePath
    ffmpeg -n -i "$filePath" -b 1500k -vf "scale=640:-1" -vcodec libtheora -acodec libvorbis -ab 160000 -g 30 "${filePath%.*}".ogg # perhaps this should be ogv
    ffmpeg -n -i "$filePath" -c:v libx264  -b:v 500k -vf "scale=640:-1" -r 25 -profile:v baseline -level 3.0 -c:a aac -strict -2 -ar 44100 -ac 1 -b:a 64k "${filePath%.*}".mp4
done

for filePath in */*.wav ; do 
    echo $filePath
    ffmpeg -n -i "$filePath" -acodec libmp3lame "${filePath%.*}".mp3 
    ffmpeg -n -i "$filePath" -acodec libvorbis "${filePath%.*}".ogg 
done
