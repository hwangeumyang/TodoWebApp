import './App.css';
import Todo from "./Todo"
import React, {useState} from "react"
import {List, Paper } from "@mui/material"

function App() {
  const [items, setItems] = useState(
    [
      {
        id:"0",
        title: "Hellow world 1",
        done: true
      },
      {id: "1", title: "Hello world 2", done: false},
    ]
  );
  let todoItems = items.length > 0 && 
  (
    <Paper style={{margin: 16}}>
      <List>
        {items.map((item) => <Todo item={item} key={item.id} />)};
      </List>

    </Paper>
  )
  

  

  return (
    <div className="App">
      {todoItems}
    </div>
  );
}

export default App;
