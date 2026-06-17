package com.project.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Matches")
@Check(constraints = "FIRST_PLAYER <> SECOND_PLAYER AND (WINNER = FIRST_PLAYER OR WINNER = SECOND_PLAYER)")
public class Match {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FIRST_PLAYER", referencedColumnName = "id", nullable = false)
    private Player firstPlayer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SECOND_PLAYER", referencedColumnName = "id", nullable = false)
    private Player secondPlayer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WINNER", referencedColumnName = "id", nullable = false)
    private Player winner;

    public Match(Player firstPlayer, Player secondPlayer, Player winner) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.winner = winner;
    }
}
