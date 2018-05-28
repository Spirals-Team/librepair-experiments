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

for filePath in */*/*.wav */*.wav *.wav ; do 
    echo $filePath
    fileName=$(basename $filePath)
    echo $fileName
    ffmpeg -n -i "$filePath" -acodec libmp3lame "${fileName%.*}".mp3 
    ffmpeg -n -i "$filePath" -acodec libvorbis "${fileName%.*}".ogg 
done

for filePath in */*/*.jpg */*/*.jpeg */*/*.jpg */*/*.png ; do 
# by changing the suffix of the original, it will not be included in the final app, which is needed to keep the app small
        echo $filePath
        fileName=$(basename $filePath)
        fileName=${fileName//_dis_/_}
        fileName=${fileName//_N_/_}
        fileName=${fileName//_A_/_}
        fileName=${fileName//_B_/_}
        echo "${fileName%.*}".jpg
        mv -n $filePath $filePath-original
        convert $filePath-original -resize 700x700\> "${fileName%.*}".jpg
done

for filePath in T_*.mp3 T_*.ogg ; do 
        echo $filePath
        fileName=$(basename $filePath)
        fileName=${fileName//_dis./.}
        fileName=${fileName//_N./.}
        fileName=${fileName//_A./.}
        fileName=${fileName//_B./.}
#        echo "${fileName%.*}".jpg
        mv -n $filePath $fileName
#        convert $filePath-original -resize 700x700\> "${fileName%.*}".jpg
done

for filePath in *middle* ; do 
        echo $filePath
        fileName=$(basename $filePath)
        fileName=${fileName//_middle/_centre}
        echo $fileName
        mv -n $filePath $fileName
done

for filePath in P*.jpg ; do 
        echo $filePath
        fileName=${filePath//.jpg/_centre.jpg}
        echo $fileName
        #mv -n $filePath $fileName
done