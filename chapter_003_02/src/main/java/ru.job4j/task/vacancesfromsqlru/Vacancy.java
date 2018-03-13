package ru.job4j.task.vacancesfromsqlru;

class Vacancy {

    String name;
    String vacancyurl;
    String pageurl;

    public Vacancy(String name, String vacancyurl, String pageurl) {
        this.name = name;
        this.vacancyurl = vacancyurl;
        this.pageurl = pageurl;
    }

    public Vacancy() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vacancy vacancy = (Vacancy) o;
        if (name != null ? !name.equals(vacancy.name) : vacancy.name != null) {
            return false;
        }
        if (vacancyurl != null ? !vacancyurl.equals(vacancy.vacancyurl) : vacancy.vacancyurl != null) {
            return false;
        }
        return pageurl != null ? pageurl.equals(vacancy.pageurl) : vacancy.pageurl == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (vacancyurl != null ? vacancyurl.hashCode() : 0);
        result = 31 * result + (pageurl != null ? pageurl.hashCode() : 0);
        return result;
    }
}
