package ru.esplit.first_security_app.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IdPersonDTO {
    private long id;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        IdPersonDTO other = (IdPersonDTO) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "IdPersonDTO [id=" + id + "]";
    }
}
