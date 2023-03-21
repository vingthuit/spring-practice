package net.thumbtack.buscompany.endpoints;

import lombok.AllArgsConstructor;
import net.thumbtack.buscompany.daoImpl.ClearDaoImpl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/debug/clear")
public class ClearBaseEndpoint {
    private final ClearDaoImpl clearDao;

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void clearDatabase() {
        clearDao.deleteAll();
    }
}
