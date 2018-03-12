package pw.spn.crawler.livelib;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import pw.spn.crawler.livelib.entity.Book;
import pw.spn.crawler.livelib.entity.BookStatus;

public class LivelibCrawlerTest {
    private LivelibCrawler testSubject = new LivelibCrawler();

    @Test
    public void getWish_validUserName_wishBooksReturned() {
        // given
        String userName = "SergeyPoluyan";

        // when
        List<Book> result = testSubject.getWish(userName);

        // then
        assertThat(result.size(), is(1));
        Book book = result.get(0);
        assertThat(book.getId(), is("1001589042"));
        assertThat(book.getName(), is("Финальный аккорд"));
        assertThat(book.getAuthor(), is("Кевин Алан Милн"));
        assertThat(book.getYear(), is(2016));
        assertThat(book.getIsbn(), is("978-5-699-90669-7"));
        assertThat(book.getDate(), is("сентябрь 2016 г."));
        assertThat(book.getAvgMark(), is(not(0)));
        assertThat(book.getRating(), is(not(0)));
        assertThat(book.getPriority(), is(1));
        assertThat(book.getStatus(), is(BookStatus.WISH_TO_READ));
        assertThat(book.getPublisher(), containsString(("Эксмо")));
        assertThat(book.getPicUrl(), is("https://i.livelib.ru/boocover/1001589042/100/537d/boocover.jpg"));
        assertThat(book.getLargePicUrl(), is("https://i.livelib.ru/boocover/1001589042/200/537d/boocover.jpg"));
        assertThat(book.getNumberOfReaders(), is(not(0)));
    }

    @Test
    public void getReading_validUserName_readingBooksReturned() {
        // given
        String userName = "SergeyPoluyan";

        // when
        List<Book> result = testSubject.getReading(userName);

        // then
        assertThat(result.size(), is(1));
        Book book = result.get(0);
        assertThat(book.getId(), is("1001458881"));
        assertThat(book.getName(), is("На службе зла"));
        assertThat(book.getAuthor(), is("Роберт Гэлбрейт"));
        assertThat(book.getYear(), is(2016));
        assertThat(book.getIsbn(), is("978-5-389-11220-9"));
        assertThat(book.getDate(), is("сентябрь 2016 г."));
        assertThat(book.getAvgMark(), is(not(0)));
        assertThat(book.getRating(), is(not(0)));
        assertThat(book.getPriority(), is(2));
        assertThat(book.getStatus(), is(BookStatus.READING));
        assertThat(book.getPublisher(), containsString(("Иностранка")));
        assertThat(book.getPicUrl(), is("https://j.livelib.ru/boocover/1001458881/100/7731/boocover.jpg"));
        assertThat(book.getLargePicUrl(), is("https://j.livelib.ru/boocover/1001458881/200/7731/boocover.jpg"));
        assertThat(book.getNumberOfQuotes(), is(not(0)));
        assertThat(book.getNumberOfReaders(), is(not(0)));
        assertThat(book.getNumberOfReviews(), is(not(0)));
        assertThat(book.getNumberOfSelections(), is(not(0)));
    }

    @Test
    public void getUnread_validUserName_unreadBooksReturned() {
        // given
        String userName = "SergeyPoluyan";

        // when
        List<Book> result = testSubject.getUnread(userName);

        // then
        assertThat(result.size(), is(1));
        Book book = result.get(0);
        assertThat(book.getId(), is("1001532545"));
        assertThat(book.getName(), is("Клеймо"));
        assertThat(book.getAuthor(), is("Сесилия Ахерн"));
        assertThat(book.getYear(), is(2016));
        assertThat(book.getIsbn(), is("978-5-389-09533-5"));
        assertThat(book.getDate(), is("сентябрь 2016 г."));
        assertThat(book.getAvgMark(), is(not(0)));
        assertThat(book.getRating(), is(not(0)));
        assertThat(book.getPriority(), is(1));
        assertThat(book.getStatus(), is(BookStatus.UNREAD));
        assertThat(book.getPublisher(), containsString("Иностранка"));
        assertThat(book.getPicUrl(), is("https://i.livelib.ru/boocover/1001532545/100/5c72/boocover.jpg"));
        assertThat(book.getLargePicUrl(), is("https://i.livelib.ru/boocover/1001532545/200/5c72/boocover.jpg"));
        assertThat(book.getNumberOfQuotes(), is(not(0)));
        assertThat(book.getNumberOfReaders(), is(not(0)));
        assertThat(book.getNumberOfReviews(), is(not(0)));
        assertThat(book.getNumberOfSelections(), is(not(0)));
        assertThat(book.getNumberOfStories(), is(not(0)));
    }

    @Test
    public void getRead_validUserName_readBooksReturned() {
        // given
        String userName = "SergeyPoluyan";

        // when
        List<Book> result = testSubject.getRead(userName);

        // then
        assertThat(result.size(), is(1));
        Book book = result.get(0);
        assertThat(book.getId(), is("1001483077"));
        assertThat(book.getName(), is("Круглосуточный книжный мистера Пенумбры"));
        assertThat(book.getAuthor(), is("Робин Слоун"));
        assertThat(book.getYear(), is(2016));
        assertThat(book.getIsbn(), is("978-5-9907254-2-3"));
        assertThat(book.getDate(), is("сентябрь 2016 г."));
        assertThat(book.getAvgMark(), is(not(0)));
        assertThat(book.getRating(), is(not(0)));
        assertThat(book.getPriority(), is(0));
        assertThat(book.getStatus(), is(BookStatus.ALREADY_READ));
        assertThat(book.getPublisher(), containsString("Livebook"));
        assertThat(book.getPicUrl(), is("https://j.livelib.ru/boocover/1001483077/100/ebf5/boocover.jpg"));
        assertThat(book.getLargePicUrl(), is("https://j.livelib.ru/boocover/1001483077/200/ebf5/boocover.jpg"));
        assertThat(book.getNumberOfQuotes(), is(not(0)));
        assertThat(book.getNumberOfReaders(), is(not(0)));
        assertThat(book.getNumberOfReviews(), is(not(0)));
        assertThat(book.getNumberOfSelections(), is(not(0)));
        assertThat(book.getNumberOfStories(), is(not(0)));
    }

    @Test
    public void searchForBook_validData_booksReturned() {
        // given
        String query = "Java in";

        // when
        List<Book> result = testSubject.searchForBook(query);

        // then
        assertThat(result, is(not(empty())));
    }

    @Test
    public void getByStatus_statusWish_wishBooksReturned() {
        // given
        String userName = "SergeyPoluyan";
        BookStatus status = BookStatus.WISH_TO_READ;

        // when
        List<Book> result = testSubject.getByStatus(userName, status);

        // then
        assertThat(result.size(), is(1));
        Book book = result.get(0);
        assertThat(book.getId(), is("1001589042"));
        assertThat(book.getName(), is("Финальный аккорд"));
        assertThat(book.getAuthor(), is("Кевин Алан Милн"));
        assertThat(book.getYear(), is(2016));
        assertThat(book.getIsbn(), is("978-5-699-90669-7"));
        assertThat(book.getDate(), is("сентябрь 2016 г."));
        assertThat(book.getAvgMark(), is(not(0)));
        assertThat(book.getRating(), is(not(0)));
        assertThat(book.getPriority(), is(1));
        assertThat(book.getStatus(), is(BookStatus.WISH_TO_READ));
        assertThat(book.getPublisher(), containsString(("Эксмо")));
        assertThat(book.getPicUrl(), is("https://i.livelib.ru/boocover/1001589042/100/537d/boocover.jpg"));
        assertThat(book.getLargePicUrl(), is("https://i.livelib.ru/boocover/1001589042/200/537d/boocover.jpg"));
        assertThat(book.getNumberOfReaders(), is(not(0)));
    }

    @Test
    public void getByStatus_statusReading_readingBooksReturned() {
        // given
        String userName = "SergeyPoluyan";
        BookStatus status = BookStatus.READING;

        // when
        List<Book> result = testSubject.getByStatus(userName, status);

        // then
        assertThat(result.size(), is(1));
        Book book = result.get(0);
        assertThat(book.getId(), is("1001458881"));
        assertThat(book.getName(), is("На службе зла"));
        assertThat(book.getAuthor(), is("Роберт Гэлбрейт"));
        assertThat(book.getYear(), is(2016));
        assertThat(book.getIsbn(), is("978-5-389-11220-9"));
        assertThat(book.getDate(), is("сентябрь 2016 г."));
        assertThat(book.getAvgMark(), is(not(0)));
        assertThat(book.getRating(), is(not(0)));
        assertThat(book.getPriority(), is(2));
        assertThat(book.getStatus(), is(BookStatus.READING));
        assertThat(book.getPublisher(), containsString(("Иностранка")));
        assertThat(book.getPicUrl(), is("https://j.livelib.ru/boocover/1001458881/100/7731/boocover.jpg"));
        assertThat(book.getLargePicUrl(), is("https://j.livelib.ru/boocover/1001458881/200/7731/boocover.jpg"));
        assertThat(book.getNumberOfQuotes(), is(not(0)));
        assertThat(book.getNumberOfReaders(), is(not(0)));
        assertThat(book.getNumberOfReviews(), is(not(0)));
        assertThat(book.getNumberOfSelections(), is(not(0)));
    }

    @Test
    public void getByStatus_statusUnread_unreadBooksReturned() {
        // given
        String userName = "SergeyPoluyan";
        BookStatus status = BookStatus.UNREAD;

        // when
        List<Book> result = testSubject.getByStatus(userName, status);

        // then
        assertThat(result.size(), is(1));
        Book book = result.get(0);
        assertThat(book.getId(), is("1001532545"));
        assertThat(book.getName(), is("Клеймо"));
        assertThat(book.getAuthor(), is("Сесилия Ахерн"));
        assertThat(book.getYear(), is(2016));
        assertThat(book.getIsbn(), is("978-5-389-09533-5"));
        assertThat(book.getDate(), is("сентябрь 2016 г."));
        assertThat(book.getAvgMark(), is(not(0)));
        assertThat(book.getRating(), is(not(0)));
        assertThat(book.getPriority(), is(1));
        assertThat(book.getStatus(), is(BookStatus.UNREAD));
        assertThat(book.getPublisher(), containsString("Иностранка"));
        assertThat(book.getPicUrl(), is("https://i.livelib.ru/boocover/1001532545/100/5c72/boocover.jpg"));
        assertThat(book.getLargePicUrl(), is("https://i.livelib.ru/boocover/1001532545/200/5c72/boocover.jpg"));
        assertThat(book.getNumberOfQuotes(), is(not(0)));
        assertThat(book.getNumberOfReaders(), is(not(0)));
        assertThat(book.getNumberOfReviews(), is(not(0)));
        assertThat(book.getNumberOfSelections(), is(not(0)));
        assertThat(book.getNumberOfStories(), is(not(0)));
    }

    @Test
    public void getByStatus_statusRead_readBooksReturned() {
        // given
        String userName = "SergeyPoluyan";
        BookStatus status = BookStatus.ALREADY_READ;

        // when
        List<Book> result = testSubject.getByStatus(userName, status);

        // then
        assertThat(result.size(), is(1));
        Book book = result.get(0);
        assertThat(book.getId(), is("1001483077"));
        assertThat(book.getName(), is("Круглосуточный книжный мистера Пенумбры"));
        assertThat(book.getAuthor(), is("Робин Слоун"));
        assertThat(book.getYear(), is(2016));
        assertThat(book.getIsbn(), is("978-5-9907254-2-3"));
        assertThat(book.getDate(), is("сентябрь 2016 г."));
        assertThat(book.getAvgMark(), is(not(0)));
        assertThat(book.getRating(), is(not(0)));
        assertThat(book.getPriority(), is(0));
        assertThat(book.getStatus(), is(BookStatus.ALREADY_READ));
        assertThat(book.getPublisher(), containsString("Livebook"));
        assertThat(book.getPicUrl(), is("https://j.livelib.ru/boocover/1001483077/100/ebf5/boocover.jpg"));
        assertThat(book.getLargePicUrl(), is("https://j.livelib.ru/boocover/1001483077/200/ebf5/boocover.jpg"));
        assertThat(book.getNumberOfQuotes(), is(not(0)));
        assertThat(book.getNumberOfReaders(), is(not(0)));
        assertThat(book.getNumberOfReviews(), is(not(0)));
        assertThat(book.getNumberOfSelections(), is(not(0)));
        assertThat(book.getNumberOfStories(), is(not(0)));
    }

    @Test
    public void getByStatus_invalidStatus_emptyListReturned() {
        // given
        // given
        String userName = "SergeyPoluyan";
        BookStatus status = BookStatus.UNKNOWN;

        // when
        List<Book> result = testSubject.getByStatus(userName, status);

        // then
        assertThat(result, is(empty()));
    }
}
