package com.example.db.quizQuestion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.db.Repository;
import com.example.entities.Answer;
import com.example.entities.QuizQuestion;

public class QuizQuestionRepository extends Repository<QuizQuestion> {
    
    @Override
    public Integer add(QuizQuestion entity) {
        try {
            PreparedStatement st = conn.prepareStatement(
                "INSERT INTO Questions(question) VALUES(?)",
                PreparedStatement.RETURN_GENERATED_KEYS
            );
            st.setString(1, entity.getQuestion());

            st.executeUpdate();

            ResultSet rs = st.getGeneratedKeys();
            if (!rs.next()) {
                return null;
            }

            Integer questionId = rs.getInt(1);

            insertMultipleAnswers(conn, questionId, entity.getAnswers());

            st = conn.prepareStatement("INSERT INTO CorrectAnswers(question_id, answer_id) VALUES(?, ?)");

            st.setInt(1, questionId);
            st.setInt(2, entity.getRightAnswerId());
    
            rs.close();
            st.close();

            return questionId;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement st = conn.prepareStatement("DELETE FROM Questions WHERE id = ?");
            st.setInt(1, id);
            
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(QuizQuestion entity) {
        try {
            PreparedStatement st = conn.prepareStatement("UPDATE Questions SET question = ? WHERE id = ?");
            st.setString(1, entity.getQuestion());
            st.setInt(2, entity.getId());
            st.executeUpdate();
            st.close();

            for (Answer answer : entity.getAnswers()) {
                st = conn.prepareStatement("UPDATE Answers SET answer = ? WHERE id = ?");
                st.setString(1, answer.getText());
                st.setInt(2, answer.getId());
                st.executeUpdate();
                st.close();
            }
    
            // st = conn.prepareStatement("DELETE FROM Answers WHERE question_id = ?");
            // st.setInt(1, entity.getId());
            // st.executeUpdate();
            // st.close();
    
            // insertMultipleAnswers(conn, entity.getId(), entity.getAnswers());
    
            st = conn.prepareStatement("UPDATE CorrectAnswers SET question_id = ?, answer_id = ? WHERE question_id = ?");
            st.setInt(1, entity.getId());
            st.setInt(2, entity.getRightAnswerId());
            st.setInt(3, entity.getId());
            st.executeUpdate();
            st.close();
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMultiple(ArrayList<QuizQuestion> entities) {
        try {
            conn.setAutoCommit(false);

            PreparedStatement updateQuestionSt = conn.prepareStatement("UPDATE Questions SET question = ? WHERE id = ?");
            PreparedStatement deleteAnswersSt = conn.prepareStatement("DELETE FROM Answers WHERE question_id = ?");
            PreparedStatement insertAnswerSt = conn.prepareStatement("INSERT INTO Answers(question_id, answer) VALUES (?, ?)");
            PreparedStatement updateCorrectAnswerSt = conn.prepareStatement("INSERT OR REPLACE INTO CorrectAnswers(question_id, answer_id) VALUES(?, ?)");
    
            for (QuizQuestion entity : entities) {
                updateQuestionSt.setString(1, entity.getQuestion());
                updateQuestionSt.setInt(2, entity.getId());
                updateQuestionSt.addBatch();

                deleteAnswersSt.setInt(1, entity.getId());
                deleteAnswersSt.addBatch();

                for (Answer answer : entity.getAnswers()) {
                    insertAnswerSt.setInt(1, entity.getId());
                    insertAnswerSt.setString(2, answer.getText());
                    insertAnswerSt.addBatch();
                }

                updateCorrectAnswerSt.setInt(1, entity.getId());
                updateCorrectAnswerSt.setInt(2, entity.getRightAnswerId());
                updateCorrectAnswerSt.addBatch();
            }

            updateQuestionSt.executeBatch();
            deleteAnswersSt.executeBatch();
            insertAnswerSt.executeBatch();
            updateCorrectAnswerSt.executeBatch();

            conn.commit();

            updateQuestionSt.close();
            deleteAnswersSt.close();
            insertAnswerSt.close();
            updateCorrectAnswerSt.close();
    
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertMultipleAnswers(Connection conn, int questionId, ArrayList<Answer> answers) {
        try {
            StringBuilder sql = new StringBuilder("INSERT INTO Answers(question_id, answer) VALUES ");
            for (int i = 0; i < answers.size(); i++) {
                sql.append("(?, ?)");
                if (i < answers.size() - 1) {
                    sql.append(", ");
                }
            }
    
            PreparedStatement st = conn.prepareStatement(sql.toString());
    
            int parameterIndex = 1;
            for (Answer answer : answers) {
                st.setInt(parameterIndex++, questionId);
                st.setString(parameterIndex++, answer.getText());
            }
    
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
