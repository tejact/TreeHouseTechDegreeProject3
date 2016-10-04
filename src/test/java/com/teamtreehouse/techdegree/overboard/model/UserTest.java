package com.teamtreehouse.techdegree.overboard.model;

import static org.junit.Assert.assertEquals;

public class UserTest {
    @org.junit.Test
    public void acceptAnswer() throws Exception {

    }

    @org.junit.Test
    public void upVotingQuestionIncreasesReputation() throws Exception {
        Board javaBoard = new Board("java");
        User teja = new User(javaBoard,"Teja");
        User alice = new User(javaBoard,"Alice");

        Question question =  teja.askQuestion("Why is overriding used");
        alice.upVote(question);

        assertEquals(5,teja.getReputation());
    }
}