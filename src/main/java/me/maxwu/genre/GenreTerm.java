package me.maxwu.genre;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by maxwu on 3/15/17.
 */
public class GenreTerm {
    // The list was from http://id3.org/id3v2.3.0#Appendix_A_-_Genre_List_from_ID3v1
    // The Java compiler will optimize static string to avoid extra concatenation.
    private static String _genreText = "" +
            "0. Blues\n" +
            "  1. Classic Rock\n" +
            "  2. Country\n" +
            "  3. Dance\n" +
            "  4. Disco\n" +
            "  5. Funk\n" +
            "  6. Grunge\n" +
            "  7. Hip-Hop\n" +
            "  8. Jazz\n" +
            "  9. Metal\n" +
            " 10. New Age\n" +
            " 11. Oldies\n" +
            " 12. Other\n" +
            " 13. Pop\n" +
            " 14. R&B\n" +
            " 15. Rap\n" +
            " 16. Reggae\n" +
            " 17. Rock\n" +
            " 18. Techno\n" +
            " 19. Industrial\n" +
            " 20. Alternative\n" +
            " 21. Ska\n" +
            " 22. Death Metal\n" +
            " 23. Pranks\n" +
            " 24. Soundtrack\n" +
            " 25. Euro-Techno\n" +
            " 26. Ambient\n" +
            " 27. Trip-Hop\n" +
            " 28. Vocal\n" +
            " 29. Jazz+Funk\n" +
            " 30. Fusion\n" +
            " 31. Trance\n" +
            " 32. Classical\n" +
            " 33. Instrumental\n" +
            " 34. Acid\n" +
            " 35. House\n" +
            " 36. Game\n" +
            " 37. Sound Clip\n" +
            " 38. Gospel\n" +
            " 39. Noise\n" +
            " 40. AlternRock\n" +
            " 41. Bass\n" +
            " 42. Soul\n" +
            " 43. Punk\n" +
            " 44. Space\n" +
            " 45. Meditative\n" +
            " 46. Instrumental Pop\n" +
            " 47. Instrumental Rock\n" +
            " 48. Ethnic\n" +
            " 49. Gothic\n" +
            " 50. Darkwave\n" +
            " 51. Techno-Industrial\n" +
            " 52. Electronic\n" +
            " 53. Pop-Folk\n" +
            " 54. Eurodance\n" +
            " 55. Dream\n" +
            " 56. Southern Rock\n" +
            " 57. Comedy\n" +
            " 58. Cult\n" +
            " 59. Gangsta\n" +
            " 60. Top 40\n" +
            " 61. Christian Rap\n" +
            " 62. Pop/Funk\n" +
            " 63. Jungle\n" +
            " 64. Native American\n" +
            " 65. Cabaret\n" +
            " 66. New Wave\n" +
            " 67. Psychadelic\n" +
            " 68. Rave\n" +
            " 69. Showtunes\n" +
            " 70. Trailer\n" +
            " 71. Lo-Fi\n" +
            " 72. Tribal\n" +
            " 73. Acid Punk\n" +
            " 74. Acid Jazz\n" +
            " 75. Polka\n" +
            " 76. Retro\n" +
            " 77. Musical\n" +
            " 78. Rock & Roll\n" +
            " 79. Hard Rock\n" +
            " 80. Folk\n" +
            " 81. Folk-Rock\n" +
            " 82. National Folk\n" +
            " 83. Swing\n" +
            " 84. Fast Fusion\n" +
            " 85. Bebob\n" +
            " 86. Latin\n" +
            " 87. Revival\n" +
            " 88. Celtic\n" +
            " 89. Bluegrass\n" +
            " 90. Avantgarde\n" +
            " 91. Gothic Rock\n" +
            " 92. Progressive Rock\n" +
            " 93. Psychedelic Rock\n" +
            " 94. Symphonic Rock\n" +
            " 95. Slow Rock\n" +
            " 96. Big Band\n" +
            " 97. Chorus\n" +
            " 98. Easy Listening\n" +
            " 99. Acoustic\n" +
            "100. Humour\n" +
            "101. Speech\n" +
            "102. Chanson\n" +
            "103. Opera\n" +
            "104. Chamber Music\n" +
            "105. Sonata\n" +
            "106. Symphony\n" +
            "107. Booty Bass\n" +
            "108. Primus\n" +
            "109. Porn Groove\n" +
            "110. Satire\n" +
            "111. Slow Jam\n" +
            "112. Club\n" +
            "113. Tango\n" +
            "114. Samba\n" +
            "115. Folklore\n" +
            "116. Ballad\n" +
            "117. Power Ballad\n" +
            "118. Rhythmic Soul\n" +
            "119. Freestyle\n" +
            "120. Duet\n" +
            "121. Punk Rock\n" +
            "122. Drum Solo\n" +
            "123. A capella\n" +
            "124. Euro-House\n" +
            "125. Dance Hall";

    @Contract(value = " -> !null")
    public static List<String> getDefaultGenreList(){
        return new ArrayList<>(Arrays.asList("NA"));
    }

    @Contract(value = "null -> false")
    public static boolean isDefaultGenreList(List<String> genres){
        return ((genres!=null) && (!genres.isEmpty())&&(genres.get(0).equals("NA"))&&(genres.size()==1));
    }

    // set to volatile to avoid optimized execution order when reading shall always be after the writing JDK 1.5+
    private volatile static List<String> genreList = null;

    // DCL (Double Checked Locking) is not necessary here.
    // The practice is just for fun and reserved for future multi-threading consideration.
    public static List<String> getGenreList(){
        if (genreList == null){
            synchronized (GenreTerm.class){
               if (genreList == null){
                   genreList = new ArrayList<String>(128);
                   Arrays.stream(_genreText.split("\\n")).forEach(ln -> {
                       String[] words =  ln.split("\\.\\s*", 2);
                       genreList.add(words[1].trim());
                   });
               }
            }
        }

        return genreList;
    }

    public static void main(String[] args){
        List<String> genres = GenreTerm.getGenreList();
        IntStream.range(0, genres.size())
                .forEach(idx -> System.out.printf("%3d %s\n", idx, genres.get(idx)));
    }
}
