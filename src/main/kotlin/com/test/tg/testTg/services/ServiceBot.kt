package com.test.tg.testTg.services

import com.test.tg.testTg.utils.Constants.TG_BOT_NAME
import com.test.tg.testTg.utils.Constants.TG_TOKEN
import com.test.tg.testTg.web.models.WeatherRes
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

@Service
class ServiceBot : TelegramLongPollingBot(TG_TOKEN), Contract {

    private val botName: String = TG_BOT_NAME

    override fun getBotUsername(): String = botName

    private val repository = Repository(this)

    override fun onUpdateReceived(update: Update?) {
        if(update == null || !update.hasMessage())
            return

        when (update.message.text) {
            "/start" -> startMessage(update.message.chatId)
            "Москва", "Вашингтон" ->  getWeatherFake(update.message.chatId, update.message.text)
            else -> repository.getGeo(update.message.chatId, update.message.text)
        }
    }

    fun startMessage(chatId: Long){
        val responseMessage = SendMessage(chatId.toString(), "Выберите свой город")
        responseMessage.enableMarkdown(true)
        responseMessage.replyMarkup = getReplyMarkup(
            listOf(
                listOf("Москва", "Вашингтон"),
                listOf("Астана")
            )
        )
        execute(responseMessage)
    }

    private fun getReplyMarkup(allButtons: List<List<String>>): ReplyKeyboardMarkup {
        val markup = ReplyKeyboardMarkup()
        markup.keyboard = allButtons.map { rowButtons ->
            val row = KeyboardRow()
            rowButtons.forEach { rowButton -> row.add(rowButton) }
            row
        }
        return markup
    }

    private fun getWeatherFake(chatId: Long, city: String){
        val text = when(city){
            "Москва" -> "В Москве всегда говно"
            else -> "В Вашингтоне переменный шпиль"
        }
        val responseMessage = SendMessage(chatId.toString(), text)
        execute(responseMessage)
    }

    override fun onError(chatId: Long, error: String) {
        val responseMessage = SendMessage(chatId.toString(), error)
        execute(responseMessage)
        startMessage(chatId)
    }

    override fun onSuccessWeather(data: WeatherRes, chatId: Long) {
        val text = "В точке ${data.name} сейчас ${data.weather[0].description}.\n" +
                   "На улице ${data.main.temp}°C, ощущается как ${data.main.feelsLike}°С."

        val responseMessage = SendMessage(chatId.toString(), text)
        execute(responseMessage)
        startMessage(chatId)
    }

    override fun startLoader(chatId: Long) {
        val animation = SendAnimation(chatId.toString(), InputFile("https://media.giphy.com/media/3AMRa6DRUhMli/giphy.gif"))
        execute(animation)
    }
}