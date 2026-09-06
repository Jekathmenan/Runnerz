package com.example.demo.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcClientRunRepository {
    private List<Run> runs = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(JdbcClientRunRepository.class);
    private final JdbcClient jdbcClient;

    public JdbcClientRunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Run> findAll() {
        return jdbcClient.sql("select * from run").query(Run.class).list();
    }

    public Optional<Run> findByID (Integer id) {
        return jdbcClient.sql("SELECT * FROM run WHERE id = :id")
                .param("id", id)
                .query(Run.class)
                .optional();
    }

    @Transactional
    public void create(Run run) {
        var inserted = jdbcClient.sql("INSERT INTO Run (title, started_on, completed_on, miles, location) VALUES (?, ?, ?, ?, ?)")
                .params(List.of( run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString()))
                .update();

        Assert.state(inserted == 1, "Failed to create run " + run.title());
    }

    @Transactional
    public void update(Run run, Integer id) {
        var updated = jdbcClient.sql("UPDATE Run SET title = ?, started_on = ?, completed_on = ?, miles = ?, location = ? WHERE id = ?")
                .params(List.of(run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString(), id))
                .update();

        Assert.state(updated == 1, "Failed to update run " + id);
    }

    @Transactional
    public void delete(Integer id) {
        var updated = jdbcClient.sql("DELETE FROM Run WHERE id = :id")
                .param("id", id)
                .update();

        Assert.state(updated == 1, "Failed to update run " + id);
    }

    public int count() {
        return jdbcClient.sql("SELECT COUNT(*) FROM Run").query(Integer.class).single();
    }

    public void saveAll(List<Run> runs) {
        runs.forEach(this::create);
    }

    public List<Run> findByLocation (Location location) {
        return jdbcClient.sql("SELECT * FROM Run Where Location = :location")
                .param("location", location.toString())
                .query(Run.class)
                .list();
    }
}
