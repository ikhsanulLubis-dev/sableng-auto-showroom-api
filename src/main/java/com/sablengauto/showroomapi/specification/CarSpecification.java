package com.sablengauto.showroomapi.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.sablengauto.showroomapi.entity.Car;
import com.sablengauto.showroomapi.enums.CarStatus;

import jakarta.persistence.criteria.Predicate;

public class CarSpecification {

    public static Specification<Car> filterCars(
            String brand,
            CarStatus status,
            Integer year,
            Double minPrice,
            Double maxPrice,
            Integer startYear,
            Integer endYear) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (brand != null &&
                    !brand.isEmpty()) {

                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(
                                        root.get("brand")),
                                "%" + brand.toLowerCase() + "%"));

            }

            if (status != null) {

                predicates.add(
                        criteriaBuilder.equal(
                                root.get("status"),
                                status));

            }

            if (year != null) {

                predicates.add(
                        criteriaBuilder.equal(
                                root.get("year"),
                                year));

            }

            if (minPrice != null) {

                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get("price"),
                                minPrice));

            }

            if (maxPrice != null) {

                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get("price"),
                                maxPrice));

            }

            if (startYear != null) {

                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get("year"),
                                startYear));

            }

            if (endYear != null) {

                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get("year"),
                                endYear));

            }

            return criteriaBuilder.and(
                    predicates.toArray(
                            new Predicate[0]));

        };
    }
}
