import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import { registerMicroApps, start } from 'qiankun';



// 注册微应用（核心配置）
registerMicroApps([
  {
    name: 'app-home', // 子应用1名称
    entry: 'http://localhost:3001', // 子应用运行地址
    container: '#micro-container', // 子应用挂载节点
    activeRule: '/home', // 路由匹配规则
  },
  {
    name: 'app-user', // 子应用2名称
    entry: 'http://localhost:3002',
    container: '#micro-container',
    activeRule: '/user',
  },
]);

// 启动微前端
start();

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);