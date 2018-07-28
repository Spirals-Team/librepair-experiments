package pw.spn.crawler.livelib.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import pw.spn.crawler.livelib.entity.deserializer.StringToBookStatusDeserializer;
import pw.spn.crawler.livelib.entity.deserializer.StringToFloatDeserializer;
import pw.spn.crawler.livelib.entity.deserializer.StringToIntDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    private String id;

    private String name;

    private String author;

    @JsonDeserialize(using = StringToIntDeserializer.class)
    private int year;

    private String isbn;

    private String date;

    @JsonDeserialize(using = StringToFloatDeserializer.class)
    @JsonProperty("avg_mark")
    private float avgMark;

    @JsonDeserialize(using = StringToFloatDeserializer.class)
    private float rating;

    @JsonDeserialize(using = StringToIntDeserializer.class)
    private int priority;

    @JsonDeserialize(using = StringToBookStatusDeserializer.class)
    private BookStatus status;

    private String publisher;

    @JsonProperty("pic_url")
    private String picUrl;

    @JsonProperty("large_pic_url")
    private String largePicUrl;

    @JsonDeserialize(using = StringToIntDeserializer.class)
    @JsonProperty("count_readers_num")
    private int numberOfReaders;

    @JsonDeserialize(using = StringToIntDeserializer.class)
    @JsonProperty("count_reviews")
    private int numberOfReviews;

    @JsonDeserialize(using = StringToIntDeserializer.class)
    @JsonProperty("count_quotes")
    private int numberOfQuotes;

    @JsonDeserialize(using = StringToIntDeserializer.class)
    @JsonProperty("count_stories")
    private int numberOfStories;

    @JsonDeserialize(using = StringToIntDeserializer.class)
    @JsonProperty("count_selections")
    private int numberOfSelections;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getAvgMark() {
        return avgMark;
    }

    public void setAvgMark(float avgMark) {
        this.avgMark = avgMark;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getLargePicUrl() {
        return largePicUrl;
    }

    public void setLargePicUrl(String largePicUrl) {
        this.largePicUrl = largePicUrl;
    }

    public int getNumberOfReaders() {
        return numberOfReaders;
    }

    public void setNumberOfReaders(int numberOfReaders) {
        this.numberOfReaders = numberOfReaders;
    }

    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(int numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

    public int getNumberOfQuotes() {
        return numberOfQuotes;
    }

    public void setNumberOfQuotes(int numberOfQuotes) {
        this.numberOfQuotes = numberOfQuotes;
    }

    public int getNumberOfStories() {
        return numberOfStories;
    }

    public void setNumberOfStories(int numberOfStories) {
        this.numberOfStories = numberOfStories;
    }

    public int getNumberOfSelections() {
        return numberOfSelections;
    }

    public void setNumberOfSelections(int numberOfSelections) {
        this.numberOfSelections = numberOfSelections;
    }
}
