package com.nvelich.newsnba;

import com.nvelich.newsnba.exceptions.YourFriendly500Exception;
import com.nvelich.newsnba.models.News;
import com.nvelich.newsnba.models.Player;
import com.nvelich.newsnba.repositories.NewsRepository;
import com.nvelich.newsnba.cache.PlayerCache;
import com.nvelich.newsnba.service.NewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private PlayerCache playerCache;

    @InjectMocks
    private NewsService newsService;

    private News news;

    @BeforeEach
    void setUp() {
        news = new News();
        news.setId(1L);
        news.setTitle("Title");
        news.setText("Content");
        Player player = new Player();
        player.setId(1L);
        player.setName("LeBron James");
        news.setPlayer(player);
    }

    @Test
    void testSaveNews() {
        when(newsRepository.save(any(News.class))).thenReturn(news);

        News savedNews = newsService.saveNews(news);

        assertNotNull(savedNews);
        assertEquals(news.getId(), savedNews.getId());
        assertEquals(news.getTitle(), savedNews.getTitle());
        assertEquals(news.getText(), savedNews.getText());
        assertEquals(news.getPlayer(), savedNews.getPlayer());
    }

    @Test
    void testGetAllNews() {
        List<News> newsList = new ArrayList<>();
        newsList.add(news);

        when(newsRepository.findAll()).thenReturn(newsList);

        List<News> returnedNews = newsService.getAllNews();

        assertNotNull(returnedNews);
        assertEquals(1, returnedNews.size());
        assertEquals(news, returnedNews.get(0));
    }

    @Test
    void testGetNewsById_NewsExists() {
        when(newsRepository.findById(anyLong())).thenReturn(Optional.of(news));

        News returnedNews = newsService.getNewsById(1L);

        assertNotNull(returnedNews);
        assertEquals(news, returnedNews);
    }

    @Test
    void testGetNewsById_NewsNotExists() {
        when(newsRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(YourFriendly500Exception.class, () -> newsService.getNewsById(1L));
    }

//    @Test
//    void testDeleteNewsById() {
//        doNothing().when(newsRepository).deleteById(anyLong());
//
//        newsService.deleteNewsById(1L);
//
//        verify(newsRepository, times(1)).deleteById(anyLong());
//    }
}
