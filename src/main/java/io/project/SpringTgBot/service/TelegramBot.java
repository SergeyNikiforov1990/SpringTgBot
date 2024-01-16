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
                    SendMessage message = new SendMessage();
                    //startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    message.setText(" " + randomId().getQuestionName());

                    try {
                        execute(message);
                    } catch (TelegramApiException e) {

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
        message.setText(" " + randomId().getQuestionName());

        try {
            execute(message);
        } catch (TelegramApiException e) {

        }
    }
    private Question randomId() {
        List<Question> randomQuestion = questionsRepo.findAll();
        Random random = new Random();
        int randomIndex = random.nextInt(randomQuestion.size());
        return randomQuestion.get(randomIndex);
    }


}
