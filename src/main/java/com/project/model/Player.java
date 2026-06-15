package com.project.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Players")
public class Player {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "firstPlayer")
    private List<Match> matchesByFirstPlayer;

    @OneToMany(mappedBy = "secondPlayer")
    private List<Match> matchesBySecondPlayer;

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, List<Match> matchesByFirstPlayer, List<Match> matchesBySecondPlayer) {
        this.name = name;
        this.matchesByFirstPlayer = matchesByFirstPlayer;
        this.matchesBySecondPlayer = matchesBySecondPlayer;
    }
}
