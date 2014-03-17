package fr.xebia.gae.todo.api;


import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.appengine.api.datastore.ReadPolicy;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import fr.xebia.gae.todo.api.model.Todo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Api(
        name = "todo",
        version = "v2",
        scopes = {Constants.EMAIL_SCOPE},
        clientIds = {Constants.WEB_LOCALHOST_CLIENT_ID, Constants.WEB_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID}
)
public class TodoEndpointV2 {

    private static Logger logger = Logger.getLogger(TodoEndpointV2.class.getName());

    static {
        ObjectifyService.register(Todo.class);
    }

    @ApiMethod(name = "currentuser", httpMethod =  ApiMethod.HttpMethod.GET)
    public User getCurrentUser(User user) {

        logger.info("user:" + user);
        if (user != null) {
            return user;
        }

        return null;
    }

    @ApiMethod(name = "list", httpMethod =  ApiMethod.HttpMethod.GET)
    public Collection<Todo> getTodos(User user) {
        logger.info("user:" + user);

        List<Todo> todos = new ArrayList<>();

        if (user != null) {
//            List<Todo> todosTmp = null;
//            todosTmp = ofy().cache(false).consistency(ReadPolicy.Consistency.STRONG).load().type(Todo.class).filter("userId", new Long(user.getUserId())).list();
//            for (Todo todo : todosTmp) {
//                todos.add(ofy().load().key(Key.create(Todo.class, todo.getId())).now());
//            }
            todos = ofy().consistency(ReadPolicy.Consistency.STRONG).load().type(Todo.class).filter("userId", user.getUserId()).list();
        }

        logger.info("todos:" + todos);

//        ofy().clear();
        return todos;
    }

    @ApiMethod(name = "create", httpMethod =  ApiMethod.HttpMethod.POST)
    public Todo create(User user, Todo todo) {
        logger.info("creating todo : " + todo.toString());

        todo.setUserId(user.getUserId());
        todo.setLastEdit(new Date());
        ofy().save().entity(todo).now();

//        ofy().clear();
        return todo;
    }

    @ApiMethod(name = "update", httpMethod =  ApiMethod.HttpMethod.PUT)
    public Todo update(User user, Todo editedTodo) {
        logger.info("updating todo with id " + editedTodo.getId());

        if (editedTodo.getId() == null || editedTodo.getUserId() == null || !editedTodo.getUserId().equals(user.getUserId())  ) {
            return null;
        }

        Todo todo = ofy().load().key(Key.create(Todo.class, editedTodo.getId())).now();
        todo.setCompleted(editedTodo.isCompleted());
        todo.setTitle(editedTodo.getTitle());
        todo.setLastEdit(new Date());
        ofy().save().entity(todo).now();

//        ofy().delete().type(Todo.class).id(editedTodo.getId()).now();
//        editedTodo.setId(null);
//        ofy().save().entity(editedTodo).now();

//        ofy().clear();
        return editedTodo;
    }

    @ApiMethod(name = "remove", httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(User user, Todo todo) {
        logger.info("removing todo : " + todo.toString());

        if (todo.getId() == null || todo.getUserId() == null || !todo.getUserId().equals(user.getUserId())) {
            return;
        }

        Todo todoloaded = ofy().load().type(Todo.class).id(todo.getId()).now();
//        ofy().delete().key(Key.create(Todo.class, todo.getId())).now();
        ofy().delete().type(Todo.class).id(todo.getId()).now();
//        ofy().clear();
    }
}
