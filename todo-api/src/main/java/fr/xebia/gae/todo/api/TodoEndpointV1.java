package fr.xebia.gae.todo.api;


import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import fr.xebia.gae.todo.model.Todo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

@Api(
        name = "todo",
        version = "v1"
)
public class TodoEndpointV1 {

    private static Logger logger = Logger.getLogger(TodoEndpointV1.class.getName());

    private static Map<String, Todo> todos = new HashMap<>();

    static {
        Todo todo = new Todo("1", "test 1", false);
        todos.put(todo.getId(), todo);
        todo = new Todo("2", "test 2", true);
        todos.put(todo.getId(), todo);
    }

    @ApiMethod(name = "list", httpMethod =  ApiMethod.HttpMethod.GET)
    public Collection<Todo> getTodos() {

        return todos.values();
    }

    @ApiMethod(name = "create", httpMethod =  ApiMethod.HttpMethod.POST)
    public Todo create(Todo todo) {
        logger.info("creating todo : " + todo.toString());

        todo.setId(UUID.randomUUID().toString());

        todos.put(todo.getId(), todo);

        logger.info(todos.values().toString());

        return todo;
    }

    @ApiMethod(name = "update", httpMethod =  ApiMethod.HttpMethod.PUT)
    public Todo update(Todo todo) {
        logger.info("updating todo with id " + todo.getId());

        if (todo.getId() == null) {
            logger.severe("update suppose todo already has an id !!");
            // TODO we should throw an error
        } else {
            todos.put(todo.getId(), todo);
        }

        return todo;
    }

    @ApiMethod(name = "remove", httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(Todo todo) {
        logger.info("removing todo : " + todo.toString());

        if (todo.getId() == null) {
            logger.severe("cannot remove a todo with no id");
            // TODO we should throw an error
        }

        todos.remove(todo.getId());
    }
}
