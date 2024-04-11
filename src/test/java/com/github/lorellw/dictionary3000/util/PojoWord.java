package com.github.lorellw.dictionary3000.util;

import com.github.lorellw.dictionary3000.enums.Status;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class PojoWord {
    private final String en;
    private final String ru;
    private final Status status;

    public static Status stringToStatus(String status) {
        return switch (status) {
            case "vaadin:check" -> Status.STUDIED;
            case "vaadin:clock" -> Status.ONSTUDY;
            case "vaadin:book" -> Status.NEW;
            default -> Status.BROKEN;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PojoWord pojoWord = (PojoWord) o;
        return ru.equals(pojoWord.ru) && en.equals(pojoWord.en) && status.equals(pojoWord.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ru, en, status);
    }
}
