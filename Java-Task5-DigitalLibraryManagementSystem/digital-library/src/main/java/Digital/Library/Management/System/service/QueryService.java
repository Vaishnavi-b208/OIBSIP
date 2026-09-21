package Digital.Library.Management.System.service;

import Digital.Library.Management.System.entity.Query;
import Digital.Library.Management.System.entity.User;
import Digital.Library.Management.System.repository.QueryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QueryService {

    private final QueryRepository queryRepository;

    public QueryService(QueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    public Query submitQuery(Query query) {

        if (query.getCreatedAt() == null) {
            query.setCreatedAt(LocalDateTime.now());
        }

        if (query.getStatus() == null || query.getStatus().isBlank()) {
            query.setStatus("PENDING");
        }

        return queryRepository.save(query);
    }

    public List<Query> getAllQueries() {
        return queryRepository.findAll();
    }

    public List<Query> getQueriesByUser(User user) {
        return queryRepository.findByUser(user);
    }

    public List<Query> getPendingQueries() {
        return queryRepository.findByStatus("PENDING");
    }

    public Query updateStatus(Long queryId, String status) {

        Query query = queryRepository.findById(queryId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Query not found."));

        query.setStatus(status);

        return queryRepository.save(query);
    }
}