import './App.css';
import Todo from "./Todo"
import AddTodo from "./AddTodo"
import React, {useState} from "react"
import {Container, List, Paper } from "@mui/material"

function App() {
  const [items, setItems] = useState([]);

  const addItem = (item) =>  {
    item.id = "ID-" + items.length;
    item.done = false;
    items.push(item)
    setItems([...items])
    //setItems([...items, item]); // 업데이트는 반드시 setItems를 이용
    console.log("items: ", items);
  }
  const deleteItem = (item) => {
    const newItems = items.filter(e => e.id !== item.id);
    setItems([...newItems])
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
