package com.desafio.votos.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "VOTE_TYPE", schema = "DESAFIO_VOTOS")
public class VoteType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "vote_description", nullable = false)
    private String voteDescription;
    
    @OneToMany(mappedBy = "voteType")
    private List<Vote> votes = new ArrayList<>();
    
    @ManyToOne
    @JsonIgnoreProperties(value = "voteTypes", allowSetters = true)
    private Agenda agenda;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoteDescription() {
        return voteDescription;
    }

    public VoteType voteDescription(String voteDescription) {
        this.voteDescription = voteDescription;
        return this;
    }

    public void setVoteDescription(String voteDescription) {
        this.voteDescription = voteDescription;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public VoteType votes(List<Vote> votes) {
        this.votes = votes;
        return this;
    }

    public VoteType addVote(Vote vote) {
        this.votes.add(vote);
        vote.setVoteType(this);
        return this;
    }

    public VoteType removeVote(Vote vote) {
        this.votes.remove(vote);
        vote.setVoteType(null);
        return this;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public VoteType agenda(Agenda agenda) {
        this.agenda = agenda;
        return this;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }
}
