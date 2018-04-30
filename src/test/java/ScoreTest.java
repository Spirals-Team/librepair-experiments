import model.Score;
import org.junit.Test;
import org.junit.Assert;

public class ScoreTest {
    @Test
    public void addCommentInScoreTest(){
        String comment= "Buen auto";
        Score score= new Score();
        score.setComment(comment);
        Assert.assertEquals("Buen auto", score.getComment());
    }
    @Test
    public void modifiedComentInScoreTest(){
        String comment= "Buen auto";
        String newComment="Genial Auto";
        Score score= new Score(22,comment);
        Assert.assertEquals(score.getComment(),comment);
        score.setComment(newComment);
        Assert.assertEquals(score.getComment(),newComment);
    }
    @Test
    public void valueInScoreTest(){
        Score score= new Score(22,"");
        Assert.assertTrue(score.getValue().equals(22));
    }

}
