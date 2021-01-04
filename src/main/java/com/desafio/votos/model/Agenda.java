package com.desafio.votos.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "AGENDA", schema = "DESAFIO_VOTOS")
public class Agenda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "agenda_name", nullable = false)
    private String agendaName;

    @NotNull
    @Column(name = "agenda_description", nullable = false)
    private String agendaDescription;

    @OneToMany(mappedBy = "agenda")
    private List<VotingSession> votingSessions = new ArrayList<>();

    @OneToMany(mappedBy = "agenda")
    @JsonIgnore
    private List<Vote> votes = new ArrayList<>();
    
    @OneToMany(mappedBy = "agenda")
    private List<VoteType> voteTypes = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgendaName() {
        return agendaName;
    }

    public Agenda agendaName(String agendaName) {
        this.agendaName = agendaName;
        return this;
    }

    public void setAgendaName(String agendaName) {
        this.agendaName = agendaName;
    }

    public String getAgendaDescription() {
        return agendaDescription;
    }

    public Agenda agendaDescription(String agendaDescription) {
        this.agendaDescription = agendaDescription;
        return this;
    }

    public void setAgendaDescription(String agendaDescription) {
        this.agendaDescription = agendaDescription;
    }

    public List<VotingSession> getVotingSessions() {
        return votingSessions;
    }

    public Agenda votingSessions(List<VotingSession> votingSessions) {
        this.votingSessions = votingSessions;
        return this;
    }

    public Agenda addVotingSession(VotingSession votingSession) {
        this.votingSessions.add(votingSession);
        votingSession.setAgenda(this);
        return this;
    }

    public Agenda removeVotingSession(VotingSession votingSession) {
        this.votingSessions.remove(votingSession);
        votingSession.setAgenda(null);
        return this;
    }

    public void setVotingSessions(List<VotingSession> votingSessions) {
        this.votingSessions = votingSessions;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public Agenda votes(List<Vote> votes) {
        this.votes = votes;
        return this;
    }

    public Agenda addVote(Vote vote) {
        this.votes.add(vote);
        vote.setAgenda(this);
        return this;
    }

    public Agenda removeVote(Vote vote) {
        this.votes.remove(vote);
        vote.setAgenda(null);
        return this;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public List<VoteType> getVoteTypes() {
        return voteTypes;
    }

    public Agenda voteTypes(List<VoteType> voteTypes) {
        this.voteTypes = voteTypes;
        return this;
    }

    public Agenda addVoteType(VoteType voteType) {
        this.voteTypes.add(voteType);
        voteType.setAgenda(this);
        return this;
    }

    public Agenda removeVoteType(VoteType voteType) {
        this.voteTypes.remove(voteType);
        voteType.setAgenda(null);
        return this;
    }

    public void setVoteTypes(List<VoteType> voteTypes) {
        this.voteTypes = voteTypes;
    }
}
