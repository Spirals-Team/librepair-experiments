'use strict';

var todoStorage = {
    fetch: function (that) {
        fetch('list').then(function (response) {
            return response.json()
        }).then(function (result) {
            that.todos = result || []
        }).catch(function (err) {
            return console.error(err);
        })
    },
    edit: function (todos) {
        fetch('edit', {
            method: 'POST',
            body: JSON.stringify(todos)
        }).then(function (data) {
            return console.log(data);
        }).catch(function (err) {
            return console.error(err);
        })
    },
    remove: function (todo) {
        fetch('remove', {
            method: 'POST',
            body: JSON.stringify(todo)
        }).then(function (data) {
            return console.log(data);
        }).catch(function (err) {
            return console.error(err);
        })
    },
    add: function (todo) {
        fetch('add', {
            method: 'POST',
            body: JSON.stringify(todo)
        }).then(function (data) {
            return console.log(data);
        }).catch(function (err) {
            return console.error(err);
        })
    }
}

// visibility filters
var filters = {
    all: function (todos) {
        return todos
    },
    active: function (todos) {
        return todos.filter(function (todo) {
            return !todo.done
        })
    },
    done: function (todos) {
        return todos.filter(function (todo) {
            return todo.done
        })
    }
}

// app Vue instance
var app = new Vue({
    // app initial state
    data: {
        todos: [],
        newTodo: '',
        editedTodo: null,
        visibility: 'all'
    },

    created: function () {
        var that = this
        todoStorage.fetch(that);
    },

    computed: {
        filteredTodos: function () {
            return filters[this.visibility](this.todos)
        },
        remaining: function () {
            return filters.active(this.todos).length
        },
        allDone: {
            get: function () {
                return this.remaining === 0
            },
            set: function (value) {
                this.todos.forEach(function (todo) {
                    todo.done = value
                })
                var that = this
                todoStorage.edit(this.todos)
                todoStorage.fetch(that)
            }
        }
    },

    filters: {
        pluralize: function (n) {
            return n === 1 ? 'item' : 'items'
        }
    },

    methods: {
        addTodo: function () {
            var value = this.newTodo && this.newTodo.trim()
            if (!value) {
                return
            }
            var that = this
            todoStorage.add({
                id: null,
                description: value,
                done: false
            })
            todoStorage.fetch(that)
            this.newTodo = ''
        },

        removeTodo: function (todo) {
            var that = this
            todoStorage.remove(todo)
            todoStorage.fetch(that)
        },

        editTodo: function (todo) {
            this.beforeEditCache = todo.description
            this.editedTodo = todo
            todoStorage.edit([todo])
            var that = this
            todoStorage.fetch(that)
        },

        doneEdit: function (todo) {
            if (!this.editedTodo) {
                return
            }
            this.editedTodo = null
            todo.description = todo.description.trim()
            if (!todo.description) {
                this.removeTodo(todo)
            }
            todoStorage.edit([todo])
            var that = this
            todoStorage.fetch(that)
        },

        doneTodo: function (todo) {
            todo.done = !todo.done
            todoStorage.edit([todo])
            var that = this
            todoStorage.fetch(that)
        },

        cancelEdit: function (todo) {
            this.editedTodo = null
            todo.description = this.beforeEditCache
        }
    },

    // a custom directive to wait for the DOM to be updated
    // before focusing on the input field.
    directives: {
        'todo-focus': function (el, binding) {
            if (binding.value) {
                el.focus()
            }
        }
    }
})

// handle routing
function onHashChange() {
    var visibility = window.location.hash.replace(/#\/?/, '')
    if (filters[visibility]) {
        app.visibility = visibility
    } else {
        window.location.hash = ''
        app.visibility = 'all'
    }
}

window.addEventListener('hashchange', onHashChange)
onHashChange()

// mount
app.$mount('.todoapp')
