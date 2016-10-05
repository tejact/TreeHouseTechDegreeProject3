package com.teamtreehouse.techdegree.overboard.model;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class UserTest {

    Board javaBoard;
    User teja;
    User alice;
    Question question;

    @Rule
    public ExpectedException throwns = ExpectedException.none();

    @Before
    public void setUp() {
        javaBoard = new Board("java");
        teja = new User(javaBoard,"Teja");
        alice = new User(javaBoard,"Alice");
        question =  teja.askQuestion("Why is overriding used");
    }



    @Test
    public void upVotingQuestionIncreasesReputation() throws Exception {
        alice.upVote(question);

        assertEquals(5,teja.getReputation());
    }

    @Test
    public void upVotingAnswerIncreasesReputation() throws Exception {
        Answer answer = alice.answerQuestion(question, "Child class can extend parent..");

        teja.upVote(answer);

        assertEquals(10,alice.getReputation());
    }

    @Test
    public void acceptingAnswerIncreasesReputation() throws Exception {
        Answer answer = alice.answerQuestion(question, "Child class can extend parent..");

        teja.acceptAnswer(answer);

        assertEquals(15,alice.getReputation());
    }

    @Test(expected = com.teamtreehouse.techdegree.overboard.exc.VotingException.class)
    public void upvotingOwnQuestionNotAllowed() throws Exception{
        teja.upVote(question);
    }

    @Test(expected = com.teamtreehouse.techdegree.overboard.exc.VotingException.class)
    public void upvotingOwnAnswerNotAllowed() throws Exception{
        Answer answer = alice.answerQuestion(question, "Child class can extend parent..");

        alice.upVote(answer);
    }


    @Test
    public void originalQuestionerIsOnlyAllowedToAnswer() {
        Answer answer = alice.answerQuestion(question, "Child class can extend parent..");

        throwns.expect(com.teamtreehouse.techdegree.overboard.exc.AnswerAcceptanceException.class);
        throwns.expectMessage("Only "+question.getAuthor().getName()+" can accept this answer as it is their question");

        alice.acceptAnswer(answer);
    }

    @Test
    public void downvotingAnswerReducesReputation() {
        Answer answer = alice.answerQuestion(question, "Child class can extend parent..");

        teja.acceptAnswer(answer);
        teja.downVote(answer);

        assertEquals(14,alice.getReputation());
    }

}