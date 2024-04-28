package com.nvelich.newsnba.service;

import com.nvelich.newsnba.exceptions.YourFriendly500Exception;
import com.nvelich.newsnba.models.News;
import com.nvelich.newsnba.models.Player;
import com.nvelich.newsnba.repositories.NewsRepository;
import com.nvelich.newsnba.cache.PlayerCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    private static final Logger LOG = LoggerFactory.getLogger(NewsService.class);

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private PlayerCache playerCache;

    public News saveNews(News news) {
        // Получаем связанного игрока из новости
        Player player = news.getPlayer();
        if (player != null) {
            String playerName = player.getName();
            if (playerCache.getPlayer(playerName) == null) {
                // Если игрок отсутствует в кэше, добавляем его
                playerCache.addPlayer(playerName, player);
            }
        }

        News savedNews = newsRepository.save(news);
        LOG.info("Сохранена новость: {}", savedNews.getTitle());
        LOG.debug("Детали сохраненной новости: {}", savedNews);

        return savedNews;
    }

    public List<News> getAllNews() {
        LOG.info("Запрос всех новостей");
        return newsRepository.findAll();
    }

    public News getNewsById(Long id) {
        LOG.info("Запрос новости по ID: {}", id);
        News newsByID = newsRepository.findById(id).orElse(null);
        if (newsByID != null) {
            return newsByID;
        } else {
            LOG.info("Ошибка сервера 500");
            throw new YourFriendly500Exception("Ошибка сервера!!!",500);
        }
    }

    public void deleteNewsById(Long id) {
        LOG.info("Удаление новости по ID: {}", id);
        newsRepository.deleteById(id);
    }
}