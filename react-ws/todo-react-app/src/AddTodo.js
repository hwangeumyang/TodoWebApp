import React, {useState} from "react";

import {Button, Grid, TextField} from "@mui/material"

const AddTodo = (props) => {
    // 사용자 입력 데이터 오브젝트
    const [item, setItem] = useState({title: ""});
    const addItem = props.addItem;

    const onInputChange = (e) => {
        setItem({title: e.target.value});
        console.log(item);
    }
    const onButtonClick = () => {
        addItem(item);
        setItem({ title: "" })
    }
    const enterKeyEventHandler = (e) => {
        console.log(e);
        if (e.key === 'Enter') {
            onButtonClick();
        }
    }

    return (
        <Grid container style={ {marginTop: 20}} >
            <Grid xs={11} md={11} item style={{paddingRight:16}}>
                {/* TextField는 onChage를 props로 받는다. */}
                {/* OnKeyPressed는 deprecated 되었으므로 onKeyDown으로 변경. */}
                <TextField placeholder="Add Todo here"
                 onChange={onInputChange} onKeyDown={enterKeyEventHandler}
                 value={item.title} fullWidth />
            </Grid>
            <Grid xs={1} md={1} item >
                <Button fullWidth style={{height: '100%' }} color="secondary"
                 varaint="outlined" onClick={onButtonClick}>
                    +
                </Button>
            </Grid>
        </Grid>
    );

}

export default AddTodo;