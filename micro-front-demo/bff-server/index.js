const express = require('express');
const cors = require('cors');
const app = express();
app.use(cors()); // 解决跨域

// BFF 接口：模拟获取用户信息（真实场景这里对接后端服务）
app.get('/api/user', (req, res) => {
  res.json({
    code: 0,
    data: { name: '微前端用户', age: 25, id: 1001 }
  });
});

// BFF 接口：模拟获取首页数据
app.get('/api/home', (req, res) => {
  res.json({
    code: 0,
    data: { title: '微前端首页', content: '这是独立的首页子应用' }
  });
});

app.listen(3000, () => {
  console.log('✅ BFF 服务运行在 http://localhost:3000');
});