package ru.folder.pastabox;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.folder.pastabox.api.response.PastaBoxResponse;
import ru.folder.pastabox.entity.PastaBoxEntity;
import ru.folder.pastabox.exception.NotFoundEntityException;
import ru.folder.pastabox.repository.PastaBoxRepository;
import ru.folder.pastabox.service.PastaBoxService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PastaBoxServiceTest {
    @Autowired
    private PastaBoxService pastaBoxService;

    @MockBean
    private PastaBoxRepository repository;

    //Этот тест падает, так как сервис обращается к репозиторию, но в нашем случае он null.
    //Для этого добавлена еще одна проверка when:
    @Test
    public void notExistHash() {
        when(repository.getByHash(anyString())).thenThrow(NotFoundEntityException.class);
        assertThrows(NotFoundEntityException.class, () -> pastaBoxService.getByHash("qwerty"));
    }

    //Не пройдет тест:
//    @Test
//    public void notExistHash1(){
//        assertThrows(ExceptionInInitializerError.class, () -> pastaBoxService.getByHash("qwerty"));
//    }

    //Когда у репозитория запрашивают объект с таким Hash, он должен вернуть этот объект:
    @Test
    public void getExistHash() {
        //Создаю объект, который возвращаю:
        PastaBoxEntity entity = new PastaBoxEntity();
        entity.setHash("1");
        entity.setData("11");
        entity.setPublic(true);

        when(repository.getByHash("1")).thenReturn(entity);

        //возаращает Response, это и буду проверять:
        PastaBoxResponse expected = new PastaBoxResponse("11", true);
        PastaBoxResponse actual = pastaBoxService.getByHash("1");

        assertEquals(expected, actual);
    }

//    //Этот тест провальный так как ожидается data = 11, а по факту 12:
//    @Test
//    public void getExistHash1() {
//        //Создаю объект, который возвращаю:
//        PastaBoxEntity entity = new PastaBoxEntity();
//        entity.setHash("1");
//        entity.setData("11");
//        entity.setPublic(true);
//
//        when(repository.getByHash("1")).thenReturn(entity);
//
//        //возаращает Response, это и буду проверять:
//        PastaBoxResponse expected = new PastaBoxResponse("12", true);
//        PastaBoxResponse actual = pastaBoxService.getByHash("1");
//
//        assertEquals(expected, actual);
//    }

}
