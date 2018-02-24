# Genre Clawer

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)  [![](https://jitpack.io/v/maxwu/Genre-Clawer.svg)](https://jitpack.io/#maxwu/Genre-Clawer) [![Analytics](https://ga-beacon.appspot.com/UA-89976940-3/genre-clawer)](https://github.com/maxwu/genre-clawer)

[![Build Status](https://travis-ci.org/maxwu/Genre-Clawer.svg?branch=master)](https://travis-ci.org/maxwu/Genre-Clawer/branch/master) [![codecov](https://codecov.io/gh/maxwu/Genre-Clawer/branch/master/graph/badge.svg)](https://codecov.io/gh/maxwu/Genre-Clawer) [![CircleCI](https://circleci.com/gh/maxwu/Genre-Clawer/tree/master.svg?style=shield)](https://circleci.com/gh/maxwu/Genre-Clawer/tree/master) 

[![Build Status](https://travis-ci.org/maxwu/Genre-Clawer.svg?branch=dev)](https://travis-ci.org/maxwu/Genre-Clawer) [![codecov](https://codecov.io/gh/maxwu/Genre-Clawer/branch/dev/graph/badge.svg)](https://codecov.io/gh/maxwu/Genre-Clawer/branch/dev) [![CircleCI](https://circleci.com/gh/maxwu/Genre-Clawer/tree/dev.svg?style=shield)](https://circleci.com/gh/maxwu/Genre-Clawer/tree/dev)

A clawer to fetch song's genre information from internet. 
The released Jar file could also execute on command line to generate billboard top 100 song list upon CLI options, fetch genres and dump them into YAML format to console.

In background, HtmlUnit or Selenide(a concise wrapper of Selenium) will be invoked to fetch genres and match the patterns on results.
By default it will choose HtmlUnit as a headless client to invoke silently. However, to keep it open, Selenide option is available. 
If Selenide is chosen, Chrome browser is now the only option in released codes since WebDriver handling is overridden with WebDriverManager to make a better control. 

## User Guide

### Maven 

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
 </repositories>
```

```xml
<dependency>
    <groupId>com.github.maxwu</groupId>
    <artifactId>Genre-Clawer</artifactId>
    <version>v1.3</version>
</dependency>
```

With above dependencies with maven, here is a quick sample to query genre list with song information.

```Java
import me.maxwu.genre.htmlUnit.HtmlUnitBase;

public class GenreCli {
    public static void main(String[] args) {
        System.out.println(
            "Song: That's What I Like, Genre:"
                + new HtmlUnitBase()
                .getSongGenres("That's What I Like", "Bruno Mars")
        );
    }
}
```

```
# Execution Logs
Song: That's What I Like, Genre:[Pop]
```
Another sample to use HtmlUnit client in both billboard top100 song list and genre fetching. 

```Java
import me.maxwu.genre.htmlUnit.HtmlUnitBase;

public class CliApp {
    public static void main(String[] args){
        new HtmlUnitBase()
            .getBillboardTop100Map(4)
            .values()
            .forEach(s -> System.out.println(
                "Song: " + s.get("song") + ", Genre:"
                    + new HtmlUnitBase()
                        .getSongGenres(s.get("song").toString(), s.get("artist").toString()) )
            );
    }
}
```

```
#Execution logs
Song: Shape Of You, Genre:[Pop]
Song: That's What I Like, Genre:[Pop]
Song: Bad And Boujee, Genre:[Hip-hop, rap]
Song: I Don't Wanna Live Forever (Fifty Shades Darker), Genre:[Pop]
```

Since in most situation people will not utilize multiple clients unless in testing, app package is in progress of an update to support a simplified application and efficient improvement.
These are on list for v1.4 as next release, which is open for recommendations.


### Java Application Cli

Usage: 

>java -jar ./target/Genre-Clawer-1.3.jar [-h] | [-n ${size}] [-c ${client}] [-s ${song} | [-a ${artist]]
>
>"-h": Show help message and exit.
>
>"-n": Size of song list fetched from BillBoard Top100 music. This option is int type and defaults to 10.
>
>"-c": Case insensitive client type from {"HtmlUnit", "Selenide" and Jsoup (in progress)}. 
Option is case insensitive string of client name.
>   HtmlUnit is the default client type.
>   Using Selenide requests Chrome Browser configured up with Selenium and WebDriverManager.
>
>"-s": Song name in string to query genres for a specific music piece.
>   Song name is recommended to wrapped with quotation marks.
>
>"-a": Artist name in string to support the above query with song name.
>   Artist name is recommended to wrapped with quotation marks.
   
#### Example 1: Fetch billboard top 5 songs genres
By default, HtmlUnit headless browser is used in this example.
```
# To fetch billboard top5 songs genres:
>>java -jar ./target/Genre-Clawer-1.3.jar -n5
Got total 5 songs
1:
  song: Shape Of You
  artist: Ed Sheeran
  genres: [Pop]
2:
  song: Bad And Boujee
  artist: Migos Featuring Lil Uzi Vert
  genres: [Hip-hop, rap]
3:
  song: That's What I Like
  artist: Bruno Mars
  genres: [Pop]
4:
  song: I Don't Wanna Live Forever (Fifty Shades Darker)
  artist: Zayn / Taylor Swift
  genres: [Pop]
5:
  song: Love On The Brain
  artist: Rihanna
  genres: [Contemporary R&B, Pop]

Success rate = 100.00%
```

#### Example 2: Fetch genres for song name "Love On The Brain" and artist name "Rihanna"
Obviously, this example will utilize default HtmlUnit headless browser.

[![asciicast](https://asciinema.org/a/b0n59hqlnjjqte4es7e9wg96f.png)](https://asciinema.org/a/b0n59hqlnjjqte4es7e9wg96f)

## Targets

Download top 100 billboard tracks information and search genre information with google.

-[X] PageObject to fetch top 100 billboard list;

-[X] Query google about the song's genre;
  
  - [X] Resolved Exception handling in Page Object with Selenide.
  
  - [X] Improve the success rate (now it is 83% after introduced multiple keywords to query with google)

-[X] From v1.2, introduced JSoup and HtmlUnit as convenient search strategies.

  - [ ] JSoup HttpClient, due to the lack of JavaScript engine, this strategy will move to search on Wikipedia instead.
  - [X] HtmlUnit query strategy.
  - [ ] Refactor the query to strategy factory.

The genre list for top 100 billboard songs is accessible on [Github Link](https://github.com/maxwu/selenide-genre-clawer/blob/master/BB_top100_genres.yaml)

In general, __92%__ of songs in list have easily patterned Google genre query results.
(With multiple google query options, the success rate increased from 47.96% to 83%)
(With 2nd pattern of genre presentation style on google page, success rate increased from 83% to 92%)

The sample of first 10 songs genre query results. YAML structure is mapped with [$rank : {song: $str, artist: $str, genres: $list } ].

```yaml
1:
  song: Shape Of You
  artist: Ed Sheeran
  genres: [Pop]
2:
  song: Bad And Boujee
  artist: Migos Featuring Lil Uzi Vert
  genres: [Hip-hop, rap]
3:
  song: That's What I Like
  artist: Bruno Mars
  genres: [Pop]
4:
  song: I Don't Wanna Live Forever (Fifty Shades Darker)
  artist: Zayn / Taylor Swift
  genres: [Pop]
5:
  song: Love On The Brain
  artist: Rihanna
  genres: [Contemporary R&B, Pop]
6:
  song: Tunnel Vision
  artist: Kodak Black
  genres: [Pop]
7:
  song: Paris
  artist: The Chainsmokers
  genres: [Dance, electronic]
8:
  song: Bounce Back
  artist: Big Sean
  genres: [Hip-hop, rap]
9:
  song: Rockabye
  artist: Clean Bandit Featuring Sean Paul & Anne-Marie
  genres: [Dance, electronic, Pop]
10:
  song: Closer
  artist: The Chainsmokers Featuring Halsey
  genres: [Dance, electronic]
```

## Backgrounds

This project is inspired by a post found in super market notice wall. 
It described an individual Java program to generate playlist based on a specified genre list mapped to time-sequence.
The rough analysis divided this problem to two phases, genre tagging and automatic recommendation/randomization algorithm. Current Genre-Clawer is the tool to solve first sub-problem.

This selenide maven project is developed as a fast prototype to verify an idea to fetch genre tags via Google search engine.
At that time, I have worked with selenium for some time and thought it shall be concise and brief to move to selenide, which offers most of the features we duplicated in many projects.
Therefore, it is also a fast-learning code to practice with selenide. 

After that, I added HtmlUnit option to avoid popped up browser windows since it is a headless client. Therefore, this program could be regarded as a cli wrapper of customized google search of song's genres.
It has the function to try a series of keywords with preconfigured patterns to recognize genre results.

The third option of Jsoup is still under work. Since Jsoup does not execute the front-end javascripts, currently the idea is to expand Jsoup to query Wikipedia instead. For registered songs, the URL has a pattern composited with song and artist name.
With the preconfigured pattern, Jsoup could extract genres from static pages on Wikipedia.

The hours on this task is a good journey. I learned that genres could be tagged to both tracks and artists. They are not orthogonal and a common genre list is defined in ID3 information standard originally for MP3 format.
WinAmp has expanded the genre list from 80 to 126. Reference link is [Link](http://id3.org/id3v2.3.0#Appendix_A_-_Genre_List_from_ID3v1)

## Notes 

__Exception Handling__

As recommended by Andrei on Selenide.org, the PageObject is expected to interact with method not Web Elements with test logic. 
In cases of sophisticated logic or Web Claw scenarios, the exceptions are handled with PageObject or abstraction on top of it.

Selenide has wrapped the Element Extraction operations with Instance Proxy. Within the proxy.invoke(), the logic of invokeAndRetry() will pack exceptions up to a Throwable type with more concise information.

- The error type to capture is __Throwable__, or, more specifically __UIAssertionError__. 

- The error shall be captured when properties are __first accessed__ with the WebElement or ElementCollection, not the association time as defined in getters by PageObject with Selectors.

    ```java
    try{
        genres = Arrays.asList($("#rso div._uX div._XWk").getText().split("/"));
    }catch (ElementNotFound e){
        logger.info("Mark genre to NA due to error:\n" + e.toString());
        genres = new ArrayList<>(Arrays.asList("NA"));
    }
    ```

Most recent version is v1.4a1 and System Property "SelenideTest=True" will open the selenide/selenium test with Maven/JUnit.
    
## TODO

- Try to introduce multi-thread to run the query faster. 

Selenide relies on Selenium to support multiple browsers if we pay attention on the static methods. 
The selenide development community is open to support multiple browser support but which is not released. 
In general, the way is to create multiple WebDrivers and tell out which driver to speak to while calling the proxies. 

- Add JUnit tests for v1.3 cli functions.

- Create Jenkins task to [http://jenkins.maxwu.me](http://jenkins.maxwu.me) and package this project with Pipeline.
  [![Build Status](http://jenkins.maxwu.me/buildStatus/icon?job=Genre-Clawer)](http://jenkins.maxwu.me/job/Genre-Clawer/)
