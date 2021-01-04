package com.desafio.votos.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "VOTE", schema = "DESAFIO_VOTOS")
public class Vote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "voting_date", nullable = false)
    private LocalDateTime votingDate;

    @OneToOne
    @JoinColumn(unique = true)
    private Associate associate;
    
    @ManyToOne
    @JsonIgnoreProperties(value = "votes", allowSetters = true)
    private Agenda agenda;
    
    @ManyToOne
    @JsonIgnoreProperties(value = "votes", allowSetters = true)
    private VoteType voteType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getVotingDate() {
        return votingDate;
    }

    public Vote votingDate(LocalDateTime votingDate) {
        this.votingDate = votingDate;
        return this;
    }

    public void setVotingDate(LocalDateTime votingDate) {
        this.votingDate = votingDate;
    }

    public Associate getAssociate() {
        return associate;
    }

    public Vote associate(Associate associate) {
        this.associate = associate;
        return this;
    }

    public void setAssociate(Associate associate) {
        this.associate = associate;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public Vote agenda(Agenda agenda) {
        this.agenda = agenda;
        return this;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public Vote voteType(VoteType voteType) {
        this.voteType = voteType;
        return this;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }
}
