package net.gonina.telega;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.ResourceBundle;

public class Main {
    private static final ResourceBundle properties = ResourceBundle.getBundle("config");

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            String proxyHost = properties.getString("telegram.bot.proxy.host");
            Integer proxyPort = Integer.valueOf(properties.getString("telegram.bot.proxy.port"));
            String username = properties.getString("telegram.bot.username");
            String token = properties.getString("telegram.bot.token");
            HttpHost httpHost = new HttpHost(proxyHost, proxyPort);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setProxy(httpHost)
                    .setAuthenticationEnabled(false)
                    .build();
            DefaultBotOptions options = ApiContext.getInstance(DefaultBotOptions.class);
            options.setRequestConfig(requestConfig);
            options.setProxyHost(proxyHost);
            options.setProxyPort(proxyPort);
            telegramBotsApi.registerBot(new Bot(options, username, token));
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}
