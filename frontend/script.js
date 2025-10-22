

const SERVER_URL = "http://localhost:8081";
const token = localStorage.getItem("token");

function login() {

    const email =document.getElementById("email").value;
     const password =document.getElementById("password").value;

     fetch (`${SERVER_URL}/auth/login` ,{
         method:"POST",
         headers:{"content-type": "application/json"},
         body: JSON.stringify({email,password})

     })

      .then(response =>{
        if(!response.ok){
            return response.text().then(msg =>{
           throw new Error(msg || "Login failed");
        });
           
        }
        return response.json();
    })
        .then(data =>{
         localStorage.setItem("token",data.token);
         window.location.href="todos.html";

        })
        .catch(error=>
            alert(error.message)
        );
      
}


function register() {
    const email =document.getElementById("email").value;
     const password =document.getElementById("password").value;

     fetch (`${SERVER_URL}/auth/register` ,{
         method:"POST",
         headers:{"content-type": "application/json"},
         body: JSON.stringify({email,password})
     })

      .then(response =>{
        if(response.ok){
            alert("register successfully, please login");
            window.location.href ="login.html"

        }
        else{
            return response.json().then(data =>{ throw new Error(data.message || "register failed")})
        }
      }).catch(error =>{
        alert(error.message);
      })

}


function createTodoCard(todo) {
    const card =document.createElement("div");
     card.className="todo-card";

    
    const checkbox = document.createElement("input");
     checkbox.type = "checkbox"
     checkbox.checked =todo.isCompleted;
     checkbox.addEventListener("change", function () {
        const updateTodo = {
            id: todo.id,
            title: todo.title,
            isCompleted: checkbox.checked
        };
        updateTodoStatus(updateTodo);
     });

     const span =document.createElement("span");
     span.textContent =todo.title;

     if(todo.isCompleted){
        span.style.textDecoration ="line-through";
        span.style.color="#aaa";

     }
     const deleteBtn = document.createElement("button");
     deleteBtn.textContent ="x";
     deleteBtn.onclick =function(){deleteTodo(todo.id);};

     card.appendChild(checkbox);
     card.appendChild(span);
     card.appendChild(deleteBtn);

     return card;
}

function loadTodos() {

    if(!token){

        alert("please Login First");
        window.location.href ="login.html";
        return;
    }
    fetch (`${SERVER_URL}/api/todo` ,{
         method:"GET",
         headers:{Authorization :`Bearer ${token}`},
        
     })

      .then(response =>{
        if(!response.ok){
           return response.text().then(msg => {
          throw new Error(msg || "Failed to get todos");
       });
        }
        return response.json();
    })
        .then((todos) =>{
            const todoList =document.getElementById("todo-list");
            todoList.innerHTML="";

            if(!todos || todos.length === 0){

                todoList.innerHTML =`<P id = "empty-message"> NO TODOS YET.Add one below!</P>`;
            }
            else {
                todos.forEach (todo =>{

                    todoList.appendChild(createTodoCard(todo));
                });
            }
         

        })
        .catch(error=>{
            alert(error.message);

            document.getElementById("todo-list").innerHTML = `<P style ="color:red">failed to load Todos</P>`;
        })


}

function addTodo() {

    const input =document.getElementById("new-todo");
    const todoText =input.value.trim();

    fetch (`${SERVER_URL}/api/todo/create` ,{
         method:"POST",
         headers:{"content-type": "application/json",
            Authorization: `Bearer ${token}`

            },

            body: JSON.stringify({title:  todoText, isCompleted : false})
         
     })

      .then(response =>{
        if(!response.ok){
          return response.text().then(msg =>{
          throw new Error(msg || "failed to add todo");
       });
        }
        return response.json();
    })
    .then(()=> {

        input.value="";

         loadTodos();
        
        })
        
        .catch(error=>{
            alert(error.message);
        })


}

function updateTodoStatus(todo) {
     fetch (`${SERVER_URL}/api/todo/${todo.id}` ,{
         method:"PUT",
         headers:{"content-type": "application/json",
            Authorization: `Bearer ${token}`

            },

            body: JSON.stringify(todo)
         
     })

      .then(response =>{
        if(!response.ok){
           return response.text().then(msg =>{
          throw new Error(msg || "failed to update todo");
       });
        }
        return response.json();
    })
    .then(()=> loadTodos())
        
        .catch(error=>{
            alert(error.message);
        })

}

function deleteTodo(id) {



     fetch (`${SERVER_URL}/api/todo/${id}` ,{
         method:"DELETE",
         headers:{Authorization: `Bearer ${token}`},
         
     })

      .then(response =>{
        if(!response.ok){
           return response.text().then(msg =>{
          throw new Error(msg || "failed to delete todo");
       });
        }
        return response.text();
    })
    .then(()=> loadTodos())
        
        .catch(error=>{
            alert(error.message);
        })

}


document.addEventListener("DOMContentLoaded", function () {
    if (document.getElementById("todo-list")) {
        loadTodos();
    }
});