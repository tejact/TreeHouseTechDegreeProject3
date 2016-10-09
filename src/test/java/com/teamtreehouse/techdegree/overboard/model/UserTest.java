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
        // Arrange: Given that teja and alice are users on board
        // and "teja" asked a question

        //Act: When alice  upvotes teja's question
        alice.upVote(question);

        //Assert
        assertEquals(5,teja.getReputation());
    }

    @Test
    public void upVotingAnswerIncreasesReputation() throws Exception {
        //Arrange: Given that teja and alice are users on board
        // and "teja" asked a question
        //alice answer's teja's question
        Answer answer = alice.answerQuestion(question, "Child class can extend parent..");

        //Act: When teja upvotes alice's answer
        teja.upVote(answer);

        //Assert
        assertEquals(10,alice.getReputation());
    }

    @Test
    public void acceptingAnswerIncreasesReputation() throws Exception {
        //Arrange: Given that teja and alice are users on board
        // and "teja" asked a question
        //alice answer's teja's question
        Answer answer = alice.answerQuestion(question, "Child class can extend parent..");

        //Act: teja accept's alice's answers
        teja.acceptAnswer(answer);

        //Assert
        assertEquals(15,alice.getReputation());
    }

    @Test(expected = com.teamtreehouse.techdegree.overboard.exc.VotingException.class)
    public void upvotingOwnQuestionNotAllowed() throws Exception{
        teja.upVote(question);
    }

    @Test(expected = com.teamtreehouse.techdegree.overboard.exc.VotingException.class)
    public void upvotingOwnAnswerNotAllowed() throws Exception{
        //Arrange: Given that teja and alice are users on board
        // and "teja" asked a question
        //alice answer's teja's question
        Answer answer = alice.answerQuestion(question, "Child class can extend parent..");

        //Act : Alice upvote's her own answer
        alice.upVote(answer);
    }


    @Test
    public void originalQuestionerIsOnlyAllowedToAnswer() {
        //Arrange: Given that teja and alice are users on board
        // and "teja" asked a question
        //alice answer's teja's question
        Answer answer = alice.answerQuestion(question, "Child class can extend parent..");
        //Expect the exception
        throwns.expect(com.teamtreehouse.techdegree.overboard.exc.AnswerAcceptanceException.class);
        throwns.expectMessage("Only "+question.getAuthor().getName()+" can accept this answer as it is their question");

        //Act: Alice accepts her own asnwer
        alice.acceptAnswer(answer);
    }

    @Test
    public void downvotingAnswerReducesReputation() {
        //Arrange: Given that teja and alice are users on board
        // and "teja" asked a question
        //alice answer's teja's question
        Answer answer = alice.answerQuestion(question, "Child class can extend parent..");

        //Act : Teja accepts the answer and downvotes it.
        teja.acceptAnswer(answer);
        teja.downVote(answer);

        //Assert
        assertEquals(14,alice.getReputation());
    }

}