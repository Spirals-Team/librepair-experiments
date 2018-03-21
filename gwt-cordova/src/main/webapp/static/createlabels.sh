# @since Apr 13, 2015 11:56:11 AM (creation date)
# @author Peter Withers <p.withers@psych.ru.nl>

#rm Resources/label_*.*

labelCounter=1;
makeLabel() {
    convert -gravity center -size 128x128 -background black -fill white -font /Library/Fonts/Arial\ Black.ttf -pointsize 80 label:"$1" "label_on_$labelCounter.png"
    convert -gravity center -size 128x128 -background white -fill black -font /Library/Fonts/Arial\ Black.ttf -pointsize 80 label:"$1" "label_off_$labelCounter.png"
    convert -gravity center -size 128x128 -background green -fill white -font /Library/Fonts/Arial\ Black.ttf -pointsize 80 label:"$1" "label_green_$labelCounter.png"
    convert -gravity center -size 128x128 -background blue -fill white -font /Library/Fonts/Arial\ Black.ttf -pointsize 80 label:"$1" "label_blue_$labelCounter.png"
    convert -gravity center -size 128x128 -background blue -fill white -font /Library/Fonts/Arial\ Black.ttf -pointsize 80 label:"$1" "$labelCounter.png"
    convert -gravity center -background blue -fill white -font /Library/Fonts/Arial\ Black.ttf -pointsize 80 label:"$1" "$1.jpg"
    say -v Alex "$1" -o $1.aiff
((labelCounter++))
}

#for x in {A..Z}
#do
#    makeLabel "$x";
#done
#for x in {0..9}
#do
#    makeLabel "$x";
#done

for x in "termites" "scorpions" "centipedes"
do  
    for y in "Rocket" "Festival" "Thai" "ประเพณีบุญบั้งไฟ" "Lao" "ບຸນບັ້ງໄຟ"
    do
        for i in 0 1 2 3 4 5 6
        do
            fileNameOut=$x'_'$y'_'$i
            echo $fileNameOut
            convert -gravity center -background blue -fill white -font /Library/Fonts/Tahoma.ttf -pointsize 80 label:"$x\n$y\n$i" "$fileNameOut.jpg"
            say -v Alex "$x $i" -o $fileNameOut.aiff
            ffmpeg -n -i "$fileNameOut.aiff" -acodec libmp3lame "$fileNameOut.mp3"
            ffmpeg -n -i "$fileNameOut.aiff" -acodec libvorbis "$fileNameOut.ogg"
        done
    done
done

#makeLabel "one";
#makeLabel "two";
#makeLabel "three";
#makeLabel "four";
#makeLabel "five";
#makeLabel "six";
#makeLabel "sim_rabbit";
#makeLabel "sim_cat";
#makeLabel "sim_muffin";
#makeLabel "sim_you";
#makeLabel "mid_rabbit";
#makeLabel "mid_cat";
#makeLabel "mid_muffin";
#makeLabel "mid_you";
#makeLabel "diff_rabbit";
#makeLabel "diff_cat";
#makeLabel "diff_muffin";
#makeLabel "diff_you";
#makeLabel "noise_rabbit";
#makeLabel "noise_cat";
#makeLabel "noise_muffin";
#makeLabel "noise_you";

#makeLabel "\@";
#makeLabel "$";
#makeLabel "%";
#makeLabel "&";
#makeLabel "#";
#makeLabel "<";
#makeLabel "-";
#makeLabel "!";
#makeLabel "?";
#makeLabel ">";
#makeLabel " ";
#makeLabel " ";