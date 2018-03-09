package ru.otus_jee_matveev_anton.repository.model;

import javax.persistence.*;

@Entity
@Table(name = "positions", schema = "public", catalog = "otus_jee")
public class Position {
    private int id;
    private String title;

    public Position(){}

    public Position(String title) {
        this.title = title;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position that = (Position) o;

        if (id != that.id) return false;
        return title != null ? title.equals(that.title) : that.title == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
