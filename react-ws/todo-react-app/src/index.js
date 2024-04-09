import React from 'react'; // 리액트를 쓰기 위해 임포트
import ReactDOM from 'react-dom/client'; // 리액트 DOM을 쓰기 위해 임포트
import './index.css'; // css import
import App from './App'; // App 컴포넌트 import
import reportWebVitals from './reportWebVitals'; // 지금은 무시
import AppRouter from './AppRouter';


const root = ReactDOM.createRoot(document.getElementById('root')); // ReactDOM이 내부의 컴포넌트를 'root'앨리먼트에 render
//App 컴포넌트 사용 예제
root.render(
  <AppRouter tab="home" />
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
