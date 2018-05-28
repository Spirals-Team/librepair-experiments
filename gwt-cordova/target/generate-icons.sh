if [ ! -f icon.png ]; then
convert -gravity center -size 512x512 -background "#FFFFFF" -fill "#D5D8D8" -bordercolor "#4A7777" -border 10x10 -font /Library/Fonts/Arial\ Black.ttf -pointsize 80 label:"ld-screensize\nFrinex\nFieldKit" icon.png
fi
