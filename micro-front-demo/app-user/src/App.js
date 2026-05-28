import { useEffect, useState } from 'react';
import axios from 'axios';

function App() {
  const [userInfo, setUserInfo] = useState(null);

  // 从 BFF 获取用户信息
  useEffect(() => {
    axios.get('http://localhost:3000/api/user').then(res => {
      setUserInfo(res.data.data);
    });
  }, []);

  return (
    <div>
      <h2>👤 个人中心子应用</h2>
      {userInfo && (
        <div>
          <p>用户名：{userInfo.name}</p>
          <p>年龄：{userInfo.age}</p>
          <p>ID：{userInfo.id}</p>
        </div>
      )}
    </div>
  );
}
export default App;