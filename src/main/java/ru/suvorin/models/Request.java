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
@Table(name = "Requests")
public class Request {
    @Id
    private Long chatId;
    @Column
    private Long width;
    @Column
    private Long length;
    @Column
    private Long height;
    @Column
    private String style;
    @Column
    private String purpose;
    @Column
    private Long money;
    @Column
    private String hotelki;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "request_furniture",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "furniture_id")
    )
    private List<Furniture> furniture;

    public void addFurniture(Furniture furniture) {
        this.furniture.add(furniture);
    }
    public void removeFurniture(Furniture furniture) {
        this.furniture.removeIf(element -> Objects.equals(element.getId(), furniture.getId()));
    }
}
