package AstonHomeWork.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "name")
    @NotEmpty(message = "Поле name не должно быть пустое!")
    private String name;

    @Column(name = "email")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",message = "Неверный формат mail!")
    @NotEmpty(message = "Поле email не должно быть пустое!")
    private String email;

    @Min(value = 1,message = "Возраст должен быть положительным!")
    @Column(name = "age")
    private int age;

    @Column(name = "create_at",updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime create;

    public User(String name, String email, int age, LocalDateTime create) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.create = create;
    }

    @PrePersist
    public void onCreate(){
        create = LocalDateTime.now();
    }
}
