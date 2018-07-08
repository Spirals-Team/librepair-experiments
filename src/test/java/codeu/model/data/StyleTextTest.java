package codeu.model.data;

import org.junit.Assert;
import org.junit.Test;

public class StyleTextTest {

  @Test
  public void IndividualTags() {
    String message = "Change messages to [b]bold[/b] [i]italics[/i] and [u]underlined[/u] text";

    String result = StyleText.style(message);

    String expectedOutput =
        "Change messages to <b>bold</b> <i>italics</i> and <u>underlined</u> text";
    Assert.assertEquals(expectedOutput, result);
  }

  // multiple of the same tags?
  @Test
  public void MultipleBoldTags() {
    String message = "[b]bold[/b][b]bold[/b][b]bold[/b]";

    String actual = StyleText.style(message);

    String expected = "<b>bold</b><b>bold</b><b>bold</b>";
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void MultipleItalicsTags() {
    String message = "[i]italics[/i][i]text[/i]";

    String actual = StyleText.style(message);

    String expected = "<i>italics</i><i>text</i>";
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void MultipleUnderlineTags() {
    String message = "[u]underline[/u][u]text[/u]";

    String actual = StyleText.style(message);

    String expected = "<u>underline</u><u>text</u>";
    Assert.assertEquals(expected, actual);
  }

  // Tags inside of tags?
  @Test
  public void TagsInsideTagsOne() {
    String message = "[b]style [i]text [u]tags[/u] inside[/i] another[/b]";

    String actual = StyleText.style(message);

    String expected = "<b>style <i>text <u>tags</u> inside</i> another</b>";
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void TagsInsideTagsTwo() {
    String message = "[b]style [i]text [/i]tags[u] inside[/u] another[/b]";

    String actual = StyleText.style(message);

    String expected = "<b>style <i>text </i>tags<u> inside</u> another</b>";
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void TagsInsideTagsThree() {
    String message = "start [b]style [i]text [/i]tags[u] inside[/u] another[/b] end";

    String actual = StyleText.style(message);

    String expected = "start <b>style <i>text </i>tags<u> inside</u> another</b> end";
    Assert.assertEquals(expected, actual);
  }

  // Close tags before open tags?
  @Test
  public void ClosedBeforeOpenOne() {
    String message = "closed tags[/b] before open tags [b]";

    String actual = StyleText.style(message);

    String expected = "closed tags before open tags ";
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void ClosedBeforeOpenTwo() {
    String message = "closed tags[/b] before[/b] open tags[b] twice[b]";

    String actual = StyleText.style(message);

    String expected = "closed tags before open tags twice";
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void ClosedBeforeOpenThree() {
    String message = "closed tags[/b] before[/b] open tags[b] twice";

    String actual = StyleText.style(message);

    String expected = "closed tags before open tags twice";
    Assert.assertEquals(expected, actual);
  }

  // A bunch of open tags at the end of the string?
  @Test
  public void OpenTagsAtEnd() {
    String message = "open tags at the end[u][i][b]";

    String actual = StyleText.style(message);

    String expected = "open tags at the end";
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void OpenTagsAtEndTwo() {
    String message = "open tags at the end[b][b][b]";

    String actual = StyleText.style(message);

    String expected = "open tags at the end";
    Assert.assertEquals(expected, actual);
  }

  // Tags of different types that aren't properly nested?
  @Test
  public void NestedTags() {
    String message = "tags[/b] of different [u]types [b]that are[/i] not nested[i][/u] properly";

    String actual = StyleText.style(message);

    String expected = "tags of different <u>types that are not nested</u> properly";
    Assert.assertEquals(expected, actual);
  }

  // Tags with capital letters?
  @Test
  public void CapitalLetterTags() {
    String message = "tags with [U]capital letters[/U]";

    String actual = StyleText.style(message);

    String expected = "tags with [U]capital letters[/U]";
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void CapitalLetterTagsTwo() {
    String message = "tags with [b]capital letters[/B]";

    String actual = StyleText.style(message);

    String expected = "tags with capital letters[/B]";
    Assert.assertEquals(expected, actual);
  }

  // Invalid tags?
  @Test
  public void InvalidTags() {
    String message = "invalid tags [a]wont work [d]and print[/a] as it[/d] is";

    String actual = StyleText.style(message);

    String expected = "invalid tags [a]wont work [d]and print[/a] as it[/d] is";
    Assert.assertEquals(expected, actual);
  }
}
