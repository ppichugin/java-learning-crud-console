package kz.pichugin.service;

import kz.pichugin.model.Writer;
import kz.pichugin.repository.WriterRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WriterServiceTest {
    public static final long TEST_WRITER_ID = 10000L;

    @Mock
    WriterRepository writerRepoMock;

    @InjectMocks
    WriterService writerService = new WriterService(writerRepoMock);
    ArgumentCaptor<Writer> writerCaptor;
    ArgumentCaptor<Long> idCaptor;
    Writer testWriter;
    List<Writer> testWritersList;

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    void setUp() {
        Mockito.reset(writerRepoMock);
        writerCaptor = ArgumentCaptor.forClass(Writer.class);
        idCaptor = ArgumentCaptor.forClass(Long.class);
        testWriter = new Writer(TEST_WRITER_ID, "testFirstName", "testLastName");
        testWritersList = Arrays.asList(
                new Writer(TEST_WRITER_ID + 1, "firstName1", "lastName1"),
                new Writer(TEST_WRITER_ID + 2, "firstName2", "lastName2"),
                new Writer(TEST_WRITER_ID + 3, "firstName3", "lastName3"));
    }

    @Test
    void save() {
        Mockito.when(writerRepoMock.save(testWriter)).thenReturn(testWriter);
        Writer writer = writerService.save(testWriter);
        Mockito.verify(writerRepoMock).save(writerCaptor.capture());
        assertEquals(testWriter, writerCaptor.getValue());
        assertEquals(TEST_WRITER_ID, writer.getId());
    }

    @Test
    void update() {
        Mockito.when(writerRepoMock.save(testWriter)).thenReturn(testWriter);
        Writer writer = writerService.update(testWriter);
        Mockito.verify(writerRepoMock).update(writerCaptor.capture());
        assertEquals(testWriter, writerCaptor.getValue());
        assertEquals(TEST_WRITER_ID, writerCaptor.getValue().getId());
    }

    @Test
    void getById() {
        Mockito.when(writerRepoMock.getById(TEST_WRITER_ID)).thenReturn(testWriter);
        Writer currentWriter = writerService.getById(TEST_WRITER_ID);
        Mockito.verify(writerRepoMock).getById(idCaptor.capture());
        assertEquals(testWriter, currentWriter);
    }

    @Test
    void deleteById() {
        writerService.deleteById(TEST_WRITER_ID);
        Mockito.verify(writerRepoMock).deleteById(idCaptor.capture());
        assertEquals(TEST_WRITER_ID, idCaptor.getValue());
    }

    @Test
    void getAll() {
        Mockito.when(writerRepoMock.getAll()).thenReturn(testWritersList);
        List<Writer> currentList = writerService.getAll();
        Mockito.verify(writerRepoMock).getAll();
        assertEquals(testWritersList, currentList);
    }
}
