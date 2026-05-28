import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';

// 👇 必须导出这 3 个生命周期，qiankun 才能加载
export async function bootstrap() {
  console.log('app-home 启动');
}
export async function mount(props) {
  const root = ReactDOM.createRoot(
    props.container
      ? props.container.querySelector('#root')
      : document.getElementById('root')
  );
  root.render(<App />);
}
export async function unmount() {
  console.log('app-home 卸载');
}

// 独立运行
if (!window.__POWERED_BY_QIANKUN__) {
  const root = ReactDOM.createRoot(document.getElementById('root'));
  root.render(<App />);
}