package com.example.db.quizQuestion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.db.EntityLoader;
import com.example.entities.Answer;
import com.example.entities.QuizQuestion;

public class QuizQuestionLoader extends EntityLoader<QuizQuestion> {

    @Override
    public QuizQuestion loadById(int id) {
        try {

            PreparedStatement st = conn.prepareStatement("SELECT question, ca.answer_id AS correct_answer_id, answer FROM Questions AS q INNER JOIN CorrectAnswers AS ca ON q.id = ca.question_id INNER JOIN Answers AS a ON a.id = ca.answer_id WHERE ca.question_id = ?");
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            if (!rs.next()) {
                return null;
            }

            String question = rs.getString("question");
            Integer correctAnswerId = rs.getInt("correct_answer_id");


            st = conn.prepareStatement("SELECT id, answer FROM Answers WHERE question_id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();

            ArrayList<Answer> answers = new ArrayList<>();

            while (rs.next()) {
                Integer answerId = rs.getInt("id");
                String text = rs.getString("answer");
                
                answers.add(new Answer(answerId, text));
            }

            
            return new QuizQuestion(id, question, answers, correctAnswerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<QuizQuestion> loadAll() {
        try {
            ArrayList<QuizQuestion> quizQuestions = new ArrayList<>();
            HashMap<Integer, QuizQuestion> questionsMap = new HashMap<>();

            PreparedStatement st = conn.prepareStatement("SELECT ca.question_id, question, ca.answer_id AS correct_answer_id, answer FROM Questions AS q INNER JOIN CorrectAnswers AS ca ON q.id = ca.question_id INNER JOIN Answers AS a ON a.id = ca.answer_id");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Integer id = rs.getInt("question_id");
                String question = rs.getString("question");
                Integer correctAnswerId = rs.getInt("correct_answer_id");

                questionsMap.put(
                    id,
                    new QuizQuestion(id, question, new ArrayList<>(), correctAnswerId)
                );
            }

            st = conn.prepareStatement("SELECT id, question_id, answer FROM Answers");
            rs = st.executeQuery();

            ArrayList<Answer> answers = new ArrayList<>();

            while (rs.next()) {
                Integer answerId = rs.getInt("id");
                Integer questionId = rs.getInt("question_id");
                String text = rs.getString("answer");
                
                answers.add(new Answer(answerId, questionId, text));
            }

            for (Answer answer : answers) {
                QuizQuestion q = questionsMap.get(answer.getQuestionId());

                q.getAnswers().add(answer);
            }

            questionsMap.forEach((id, question) -> {
                quizQuestions.add(question);
            });

            return quizQuestions;
        
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

}
