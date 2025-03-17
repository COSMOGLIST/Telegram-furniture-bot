package ru.suvorin.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Furniture")
public class Furniture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long money;
    @Column
    private String category;
    @Column
    private String style;
    @Column
    private String purpose;
    @Column
    private String description;
    @Column
    private Long width;
    @Column
    private Long length;
    @Column
    private Long height;
    @Column
    private String material;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "furniture")
    private List<Request> requests;

    public void addRequest(Request request) {
        requests.add(request);
    }

    public void removeRequest(Request request) {
        this.requests.removeIf(element -> Objects.equals(element.getChatId(), request.getChatId()));
    }
}
