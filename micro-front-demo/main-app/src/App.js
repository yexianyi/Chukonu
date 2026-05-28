import logo from './logo.svg';
import './App.css';

function App() {
  return (
    <div style={{ padding: '20px' }}>
      <h1>🏠 微前端基座应用</h1>
      <div style={{ display: 'flex', gap: '20px', marginBottom: '20px' }}>
        {/* 点击切换子应用 */}
        <a href="/home">首页子应用</a>
        <a href="/user">个人中心子应用</a>
      </div>
      {/* 子应用渲染在这里 */}
      <div id="micro-container" style={{ border: '1px solid #eee', padding: '20px' }}></div>
    </div>
  );
}
export default App;
