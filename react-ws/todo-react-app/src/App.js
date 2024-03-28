import './App.css';
import Todo from "./Todo"
import AddTodo from "./AddTodo"
import React, {useState, useEffect} from "react"
import {Container, List, Paper } from "@mui/material"
import { call } from "./ApiService"

function App() {
  const [items, setItems] = useState([]);

  useEffect( () =>  {
    call("/todo", "GET", null)
    .then((response) => setItems(response.data));
  }, []);

  const addItem = (item) =>  {
    call("/todo", "POST", item)
    .then((response) => setItems(response.data))
  }
  const deleteItem = (item) => {
    call("/todo", "DELETE", item)
    .then((response) => setItems(response.data))
  }
  const editItem = () => {
    setItems([...items])
  }

  let todoItems = items.length > 0 && 
  (
    <Paper style={{margin: 16}}>
      <List>
        {items.map((item) => <Todo item={item} key={item.id} deleteItem={deleteItem} editItem={editItem}/>)};
      </List>
    </Paper>
  )

  return (
    <div className="App">
      <Container maxWidth="md">
        <AddTodo addItem={addItem}/>
        <div className="TodoList">{todoItems}</div>
      </Container>
    </div>
  );
}

export default App;
