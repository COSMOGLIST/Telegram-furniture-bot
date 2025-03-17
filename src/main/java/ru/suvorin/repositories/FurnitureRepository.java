package ru.suvorin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.suvorin.models.Furniture;

import java.util.List;

@Repository
public interface FurnitureRepository extends JpaRepository<Furniture, Long> {
    public List<Furniture> findAllByStyleAndPurposeAndMoneyIsLessThan(String style, String purpose, Long money);
}
