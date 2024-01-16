package io.project.SpringTgBot.service;

import io.project.SpringTgBot.QuestionsRepo;
import io.project.SpringTgBot.config.BotConfig;
import io.project.SpringTgBot.model.Question;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Random;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final QuestionsRepo questionsRepo;
    final BotConfig config;

    public TelegramBot(BotConfig config, QuestionsRepo questionsRepo) {
        this.config = config;
        this.questionsRepo = questionsRepo;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId(); // посмотреть что еще может метод update

            switch (messageText) {
                case "/start":
                    SendMessage message1 = new SendMessage();
                    SendMessage message2 = new SendMessage();
                    SendMessage message3 = new SendMessage();
                    SendMessage message4 = new SendMessage();
                    SendMessage message5 = new SendMessage();
                    SendMessage message6 = new SendMessage();
                    Question randomQuestion = randomId(); // Получение случайного вопроса
                    message1.setChatId(String.valueOf(chatId));
                    message2.setChatId(String.valueOf(chatId));
                    message3.setChatId(String.valueOf(chatId));
                    message4.setChatId(String.valueOf(chatId));
                    message5.setChatId(String.valueOf(chatId));
                    message6.setChatId(String.valueOf(chatId));
                    message1.setText(randomQuestion.getQuestionName()); // Использование поля questionName
                    message2.setText(randomQuestion.getFirstAnswer());
                    message3.setText(randomQuestion.getSecondAnswer());
                    message4.setText(randomQuestion.getThirdAnswer());
                    message5.setText(randomQuestion.getForthAnswer());
                    message6.setText(randomQuestion.getRightAnswer());
                    if (!messageText.equals(randomQuestion.getRightAnswer())){
                        try {
                            execute(message6);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    try {
                        execute(message1);
                        execute(message2);
                        execute(message3);
                        execute(message4);
                        execute(message5);
                    } catch (TelegramApiException e) {
                        e.printStackTrace(); // Обработка ошибок, если не удается отправить сообщение
                    }
                    break;

                default:
                    sendMessage(chatId, "Извините, раздел находится в стадии разработки");
            }
        }

    }

    private void startCommandReceived(long chatId, String name) {
        String answer = "Привет, " + name + ", этот бот предназначен для " +
                "прохождения теста А1 по промышленной безопасности. ";

        sendMessage(chatId, answer);

    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private Question randomId() {
        List<Question> randomQuestion = questionsRepo.findAll();
        if (randomQuestion.isEmpty()) {
            // Если список вопросов пуст, вернуть null или сделать другое необходимое действие
            return null;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(randomQuestion.size());
        return randomQuestion.get(randomIndex);
    }


}
