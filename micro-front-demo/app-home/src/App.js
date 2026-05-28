import { useEffect, useState } from 'react';
import axios from 'axios';

function App() {
  const [homeData, setHomeData] = useState(null);

  // 从 BFF 获取首页数据
  useEffect(() => {
    axios.get('http://localhost:3000/api/home').then(res => {
      setHomeData(res.data.data);
    });
  }, []);

  return (
    <div>
      <h2>📄 首页子应用</h2>
      {homeData && (
        <div>
          <h3>{homeData.title}</h3>
          <p>{homeData.content}</p>
        </div>
      )}
    </div>
  );
}
export default App;