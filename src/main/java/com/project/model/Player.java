package com.project.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "Players")
public class Player {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "firstPlayer")
    private List<Match> matchesByFirstPlayer;

    @OneToMany(mappedBy = "secondPlayer")
    private List<Match> matchesBySecondPlayer;

    public Player() {
    }

    public Player(String name, List<Match> matchesByFirstPlayer, List<Match> matchesBySecondPlayer) {
        this.name = name;
        this.matchesByFirstPlayer = matchesByFirstPlayer;
        this.matchesBySecondPlayer = matchesBySecondPlayer;
    }

    public List<Match> getAllMatches(List<Match> matchesByFirstPlayer, List<Match> matchesBySecondPlayer) {
        return Stream.concat(matchesByFirstPlayer.stream(), matchesBySecondPlayer.stream())
                .collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Match> getMatchesByFirstPlayer() {
        return matchesByFirstPlayer;
    }

    public void setMatchesByFirstPlayer(List<Match> matchesByFirstPlayer) {
        this.matchesByFirstPlayer = matchesByFirstPlayer;
    }

    public List<Match> getMatchesBySecondPlayer() {
        return matchesBySecondPlayer;
    }

    public void setMatchesBySecondPlayer(List<Match> matchesBySecondPlayer) {
        this.matchesBySecondPlayer = matchesBySecondPlayer;
    }
}
