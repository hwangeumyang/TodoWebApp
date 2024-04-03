import React, {useState} from "react";
import { ListItem, ListItemText, InputBase, Checkbox, ListItemSecondaryAction } from "@mui/material";
import { IconButton } from "@mui/material";
import DeleteOutlined from "@mui/icons-material/DeleteOutlined"

const Todo = (props) => {
    const [item, setItem] = useState(props.item);
    const [readOnly, setReadOnly] = useState(true);
    const [titleMemory, setTitleMemory] = useState("");
    const deleteItem = props.deleteItem;
    const editItem = props.editItem;

    // deletEventHandler 작성
    const deleteEventHandler = () => {
        deleteItem(item);
    }
    const editEventHandler = (e) => {
        setItem({...item, title: e.target.value});
    }
    const turnOffReadOnly = () => {
        setTitleMemory(item.title);
        // console.log(titleMemory);
        setReadOnly(false);
    }
    const turnOnReadOnly = (e) => {
        console.log(e);
        if(e.key === "Enter") {
            setReadOnly(true);
            editItem(item);
        } else if(e.key ==="Escape") {
            item.title = titleMemory;
            setReadOnly(true);
        }
    }
    const checkboxEventHandler = (e) => {
        item.done = e.target.checked;
        editItem(item);
    }

    return (
        <ListItem>
            <Checkbox checked = {item.done} onChange={checkboxEventHandler}/>
            <ListItemText>
                <InputBase
                 inputProps={{"aria-label": "naked", readOnly: readOnly}}
                 onClick={turnOffReadOnly}
                 onChange={editEventHandler}
                 onKeyDown={turnOnReadOnly}
                 type = "text"
                 id={item.id}
                 name={item.id}
                 value={item.title}
                 multiline={true}
                 fullWidth={true}
                 />
            </ListItemText>
            <ListItemSecondaryAction>
                <IconButton aria-label="Delete Todo"
                  onClick={deleteEventHandler}>
                    <DeleteOutlined />
                </IconButton>
            </ListItemSecondaryAction>
        </ListItem>
    );
};

export default Todo;